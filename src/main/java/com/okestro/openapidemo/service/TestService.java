package com.okestro.openapidemo.service;

import com.okestro.openapidemo.dto.BusApiHeaderDto;
import com.okestro.openapidemo.dto.BusApiBodyDto;
import com.okestro.openapidemo.dto.BusApiDto;
import com.okestro.openapidemo.dto.BusApiResultDto;
import com.okestro.openapidemo.repository.BusApiBodyRepository;
import com.okestro.openapidemo.repository.BusApiIdInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.yaml.snakeyaml.util.UriEncoder;

import javax.annotation.PostConstruct;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.net.URI;
import java.util.*;

@EnableScheduling
@Service
@Slf4j
public class TestService {

    @Value("${test.api.url}")
    private String apiUrl;

    @Value("${test.api.serviceKey}")
    private String serviceKey;

    @Autowired
    private BusApiBodyRepository busApiBodyRepository;

    @Autowired
    private BusApiIdInfoRepository busApiIdInfoRepository;

    @PostConstruct
    void postConstruct() {
        log.info("test.api.url={}", apiUrl);
        log.info("test.api.serviceKey={}", serviceKey);
        apiUrl = apiUrl == null ? "" : apiUrl.trim();
        serviceKey = serviceKey == null ? "" : serviceKey.trim();
    }

    // TODO: kafka sender 구성
    @Scheduled(cron="${test.api.scheduled.exp}")
    public void BusApiMain() {
        List<String> id_info = busApiIdInfoRepository.findBusRouteId();
        int count = 0;
        for (String busRouteId: id_info) {
            try {
                String headerCode = TestService.BusApi(busApiBodyRepository, apiUrl, serviceKey, busRouteId);
                count += 1;
                /* 7 : request 요청 건수 초과 */
                if ("7".equals(headerCode)) {
                    break;
                } else if (count > 5) {
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("busRouteId : " + busRouteId);
            }
        }
    }

    public static String BusApi(BusApiBodyRepository busApiBodyRepository, String apiUrl, String serviceKey, String busRouteId) throws Exception {

        /* uri 생성 */
        BusApiDto dto = new BusApiDto();
        Map<String, String> params = new HashMap<>();

        UriEncoder.encode(busRouteId);
        params.put("serviceKey", serviceKey);
        params.put("busRouteId", UriEncoder.encode(busRouteId));

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiUrl);
        for (Map.Entry<String, String> entry : params.entrySet()) {
            builder.queryParam(entry.getKey(), entry.getValue());
        }

        URI uri = URI.create(builder.build(false).toUriString());
        log.info("URI : {}", uri);
//        System.out.println(uri);
//        uri = "http://ws.bus.go.kr/api/rest/buspos/getBusPosByRtid?serviceKey=1JUdQNlvEneBvGD546ShDvW7mVdVA%2Bd9k%2F7y7CkSgw%2BoERiCt5x3vzDE1qPZGP7uNJ0DL2EiKurdGZRV5ZuuUQ%3D%3D&busRouteId=100100124";

        /* Http Client 생성 */
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(httpClient);

        log.info("httpClient : {}", httpClient.toString());

        /* HttpClient를 추상화해서 제공(json, xml 등) */
        RestTemplate restTemplate = new RestTemplate(requestFactory);

        log.info("restTemplate : {}", restTemplate.toString());

        /* Http Header setting */
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<?> httpEntity = new HttpEntity<>(headers);

        //"http://ws.bus.go.kr/api/rest/buspos/getBusPosByRtid?serviceKey=1JUdQNlvEneBvGD546ShDvW7mVdVA%2Bd9k%2F7y7CkSgw%2BoERiCt5x3vzDE1qPZGP7uNJ0DL2EiKurdGZRV5ZuuUQ%3D%3D&busRouteId=100100124";

        ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, String.class);

        String statusCode = String.valueOf(response.getStatusCode());
        dto.setStatus(statusCode);
        StringReader reader = new StringReader(Objects.requireNonNull(response.getBody()));

        /* XML을 객체로 변환 */
        JAXBContext jaxbContext = JAXBContext.newInstance(BusApiDto.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        BusApiResultDto resultDto = null;

        String headerCode = String.valueOf(10);

        try {
            resultDto = (BusApiResultDto) jaxbUnmarshaller.unmarshal(reader);
        }catch(Exception e) {
            dto.setMessage(e.getMessage());
            log.error("Status Code : " + statusCode + "Error Message : " + e.getMessage());
            return headerCode;
        }

        assert resultDto != null;
        BusApiHeaderDto msgHeader = resultDto.getMsgHeader();

        /* 데이터 입력 */
        if (!"200".equals(dto.getStatus()) || dto.getMessage() !=null) {
            headerCode = msgHeader.getHeaderCd();
            dto.setMessage(msgHeader.getHeaderMsg());
            dto.setCode(headerCode);
            if (!"0".equals(headerCode)) {
                log.error("busRouteId : " + busRouteId + ", Status Code : " + statusCode + ", Header Code : " + headerCode);
                return headerCode;
            }
        }

        for (BusApiBodyDto busApiBodyDto : resultDto.getMsgBody().busApiBodyDtos) {
            if (busApiBodyDto != null) {
                try {
                    busApiBodyDto.setBusRouteId(busRouteId);
                    busApiBodyRepository.save(busApiBodyDto);
                } catch (DataIntegrityViolationException e) {
                    return headerCode;
                }
            } else {
                log.debug(busRouteId);
            }
        }
        return headerCode;
    }
}
