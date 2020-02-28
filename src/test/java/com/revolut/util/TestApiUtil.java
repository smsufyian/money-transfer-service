package com.revolut.util;


import com.google.gson.Gson;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.revolut.config.GuiceModule;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.client.util.StringContentProvider;

import java.util.Map;

public class TestApiUtil {

    private static HttpClient httpClient;
    private static Gson gson;

    static {
        httpClient = new HttpClient();
        Injector injector = Guice.createInjector(new GuiceModule());
        gson = injector.getBinding(Key.get(Gson.class)).getProvider().get();
    }

    public static TestApiResponse post(String url, Object body) throws Exception {

        httpClient.start();
        ContentResponse response = httpClient.POST(url)
                .content(new StringContentProvider(gson.toJson(body)), "application/json")
                .send();
        Map<String, Object> content = gson.fromJson(response.getContentAsString(), Map.class);
        int httpStatus = response.getStatus();
        httpClient.stop();
        return new TestApiResponse(content, httpStatus);

    }

    public static TestApiResponse get(String url) throws Exception {

        httpClient.start();
        ContentResponse response = httpClient.GET(url);
        Map<String, Object> content = gson.fromJson(response.getContentAsString(), Map.class);
        int httpStatus = response.getStatus();
        httpClient.stop();
        return new TestApiResponse(content, httpStatus);

    }



}
