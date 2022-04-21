package net.bfci.android.clubnews;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Genius on 29/07/2018.
 */
public class HttpConnectionHandler {

    public String ServiceCall(String reqUrl){

        String result = null;
        try {
            URL url = new URL(reqUrl);
            try {
                HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                connection.setRequestMethod("GET");
                InputStream stream = new BufferedInputStream(connection.getInputStream());

                result = JSONToString(stream);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return result;
    }

    private String JSONToString(InputStream open) {

        String line;
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(open));

        try {
            while((line = reader.readLine()) !=null){

                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            open.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return  sb.toString();
    }

}
