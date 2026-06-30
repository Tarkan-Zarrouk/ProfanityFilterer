package com.filter.helper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.concurrent.CompletableFuture;
public class ProfanityAssister {
    // private static HttpURLConnection connection;
    // private static boolean outputVal;
    private static boolean isProfanity;
    public static boolean isProfanity(String message) {
        CompletableFuture.runAsync(() -> {
            HttpURLConnection connection = null; // Localized safely inside the thread
            try {
                URL url = new URI("https://vector.profanity.dev").toURL();
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setDoOutput(true);

                String json = "{\"message\":\"" + message + "\"}"; // copied from profanity.dev's javascript api call body
                connection.setConnectTimeout(500); // ms
                connection.setReadTimeout(500); // ms
                connection.getOutputStream().write(json.getBytes());
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                try {
                    String line;
                    String response = "";
                    while((line = reader.readLine()) != null) {
                        response += line;
                    }
                    isProfanity = response.contains("true");
                } finally {
                    if(reader != null) {
                        reader.close();
                    }
                }
            } catch(Exception e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect(); 
                }
            }
        });
        // System.out.println(isProfanity);
        return isProfanity;
    }
}
