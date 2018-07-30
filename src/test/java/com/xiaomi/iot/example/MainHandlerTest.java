package com.xiaomi.iot.example;

import org.apache.commons.io.IOUtils;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.client.util.StringContentProvider;
import org.eclipse.jetty.http.HttpMethod;
import org.eclipse.jetty.server.Server;
import org.junit.Assert;
import org.junit.Test;

import java.io.*;
import java.nio.charset.Charset;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public class MainHandlerTest {

    @Test
    public void test1() throws Exception {
        Server server = new Server(9880);
        server.setHandler(new ServiceHandler());
        server.start();

        runClient();

//        server.join();
    }

    private void runClient() throws Exception {
        HttpClient httpClient = new HttpClient();
        httpClient.start();

        for (int i = 0; i < 7; i++) {
            ContentResponse response = sendTestRequest(httpClient, i);
            InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream("test-case-" + i + "-expected.txt");
            String expected = IOUtils.toString(resourceAsStream, Charset.defaultCharset());
            System.out.println("Test" + i + " - Expected response: " + expected);
            System.out.println("Test" + i + " - Actual response: " + response.getContentAsString());
            Assert.assertTrue(response.getContentAsString().equals(expected));
        }

    }

    private ContentResponse sendTestRequest(HttpClient httpClient, int i) throws IOException, InterruptedException, ExecutionException, TimeoutException {
        InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream("./test-case-"+ i +".json");
        String string = IOUtils.toString(resourceAsStream, Charset.defaultCharset());
        StringContentProvider stringContentProvider = new StringContentProvider("application/json", string, Charset.defaultCharset());
        System.out.println("Test" + i + " - Request: " + string);
        ContentResponse response =  httpClient.newRequest("localhost",9880)
                .method(HttpMethod.POST)
                .header("User_Token", "imaxiaomilover")
                .header("Content-Type", "application/json")
                .content(stringContentProvider, "application/json")
                .send();
        return response;
    }
}
