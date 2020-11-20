package com.okestro.openapidemo.util;

import org.springframework.beans.factory.annotation.Value;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CsvReader {

    public List<List<String>> readCsv(String path) throws IOException {

        List<List<String>> result = new ArrayList<List<String>>();
        BufferedReader bufferedReader = null;

        String line = null;
//        URI path = (URI) path;
        System.out.println(path);
        bufferedReader = Files.newBufferedReader(Paths.get(path));

        while ((line = bufferedReader.readLine()) != null) {
            List<String> tmpList = Arrays.asList(line.split(","));
            System.out.println(tmpList);
            result.add(tmpList);
        }

        bufferedReader.close();
        return result;
    }
}
