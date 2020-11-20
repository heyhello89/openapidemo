package com.okestro.openapidemo.service;

import com.okestro.openapidemo.util.CsvReader;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;


public class TestCsvReader {

    public static void main(String[] args) throws IOException {
        CsvReader test = new CsvReader();
        String path = "/test";
        test.readCsv(path);
    }

}
