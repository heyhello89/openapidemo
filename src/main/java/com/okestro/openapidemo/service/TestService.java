package com.okestro.openapidemo.service;

import com.okestro.openapidemo.dto.BusResultDto;
import com.okestro.openapidemo.dto.StatusDto;
import jdk.net.SocketFlow;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@EnableScheduling
@Service
@Slf4j
public class TestService {

    @Value("${test.api.url}")
    private String apiUrl;

    @Value("${test.api.serviceKey}")
    private String serviceKey;

    @Value("${test.api.busRouteId}")
    private String busRouteId;

    @Scheduled(cron="${test.api.scheduled.exp}")
    public void BusApi() throws Exception {

        // uri 생성
        StatusDto dto = new StatusDto();
        Map<String, String> params = new HashMap<>();
        params.put("serviceKey", serviceKey);
        params.put("busRouteId", busRouteId);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiUrl);
        for (Map.Entry<String, String> entry : params.entrySet()) {
            builder.queryParam(entry.getKey(), entry.getValue());
        }

        // Http Client 생성
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(httpClient);

        // HttpClient를 추상화해서 제공(json, xml 등)
        RestTemplate restTemplate = new RestTemplate(requestFactory);

        ResponseEntity<String> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null, String.class);

        dto.setStatus(String.valueOf(response.getStatusCode()));
        StringReader reader = new StringReader(Objects.requireNonNull(response.getBody()));

        JAXBContext jaxbContext = JAXBContext.newInstance(StatusDto.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        BusResultDto resultDto = null;
        try {
            resultDto = (BusResultDto) jaxbUnmarshaller.unmarshal(reader);
        }catch(Exception e) {
            dto.setMessage(e.getMessage().substring(500));
        }

        System.out.println(resultDto);
    }
}
