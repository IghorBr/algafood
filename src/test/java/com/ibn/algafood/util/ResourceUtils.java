package com.ibn.algafood.util;

import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

public class ResourceUtils {

    private ResourceUtils() {

    }

    public static String getContentFromResource(String resourceName) {
        try {
            InputStream stream = org.springframework.util.ResourceUtils.class.getResourceAsStream(resourceName);
            return StreamUtils.copyToString(stream, Charset.forName("UTF-8"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
