package com.filter.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class ProfanityAssister {
    private static HttpURLConnection connection;
    public static boolean isProfanity(String message) {
        try {
            URL url = new URI("https://vector.profanity.dev").toURL();
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true); // url output intent := true
            String json = "{\"message\":\"" + message + "\"}";
            connection.setConnectTimeout(500); // 5 seconds response time
            connection.setReadTimeout(500); // 5 seconds read time
            connection.getOutputStream().write(json.getBytes()); // returns the output

            InputStreamReader isr = new InputStreamReader(connection.getInputStream());
            BufferedReader reader = new BufferedReader(isr);
            String responseLine;
            String output = "";
            while((responseLine = reader.readLine()) != null) {
                output += responseLine;
            }
            output = output.substring(2,19).replace("\"", "");
            return output.contains("true");

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch(URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
