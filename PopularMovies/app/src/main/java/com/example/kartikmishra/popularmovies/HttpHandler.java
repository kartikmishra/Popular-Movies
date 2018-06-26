package com.example.kartikmishra.popularmovies;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpHandler {
    private static final String TAG = HttpHandler.class.getSimpleName();



    public  String makeServiceCall(String requrl){

        String response = null;

        try {

            URL url = new URL(requrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            //READ THE RESPONSE
            InputStream in = new BufferedInputStream(conn.getInputStream());
            response = covertStreamToString(in);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return  response;
    }

    public String covertStreamToString(InputStream in) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        StringBuilder sb = new StringBuilder();
        String line;

        try {
            while((line = reader.readLine())!=null){
                sb.append(line).append('\n');

            }
        }catch (IOException e){
            e.printStackTrace();
        }
        finally {
            try {
                in.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }

        return sb.toString();
    }

}
