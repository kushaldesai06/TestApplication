package com.example.kushaldesai.HttpConnection;

import android.content.Context;
import android.os.AsyncTask;

import com.example.kushaldesai.Parser.DataParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;

/**
 * Created by kushal.desai on 3/3/2016.
 */
public class HttpConnection extends AsyncTask<String, Void, Object> {

    private URL url;
    private int CONNECTION_TIMEOUT = (30 * 1000);
    private int READ_TIMEOUT = CONNECTION_TIMEOUT - 5000;
    private OutputStream outputStream;
    private InputStream inputStream;
    private OnTaskCompleted onTaskCompleted;
    private String urlString;
    private HttpURLConnection conn;
    private Context context;
    static HostnameVerifier hostnameVerifier = org.apache.http.conn.ssl.SSLSocketFactory.STRICT_HOSTNAME_VERIFIER;

    public HttpConnection(Context context, String url, OnTaskCompleted onTaskCompleted) {
        this.urlString = url;
        this.context = context;
        this.onTaskCompleted = onTaskCompleted;
    }

    @Override
    protected Object doInBackground(String... params) {
        int stat = 110;
        try {
            url = new URL(urlString);
            if (url.getProtocol().toLowerCase().equals("https")) {
                HttpsURLConnection https = (HttpsURLConnection) url.openConnection();
                https.setHostnameVerifier(hostnameVerifier);
                conn = https;
            } else {
                conn = (HttpURLConnection) url.openConnection();
            }


            conn.setDoOutput(false);
            conn.setDoInput(true);
            conn.setRequestMethod("GET");
            conn.setReadTimeout(READ_TIMEOUT);
            conn.setConnectTimeout(CONNECTION_TIMEOUT);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.connect();

            int status = conn.getResponseCode();

            inputStream = conn.getInputStream();
            String response = convertStreamToString(inputStream);
            return new DataParser().parse(response);

        } catch (Exception e) {
            e.printStackTrace();
            conn.disconnect();
            return stat;
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            conn.disconnect();
        }
    }


    private String convertStreamToString(InputStream inputStream)
            throws IOException {
        String responce = "";

        if (inputStream != null) {
            Writer writer = new StringWriter();
            char[] buffer = new char[1024];
            try {
                Reader reader = new BufferedReader(new InputStreamReader(
                        inputStream, "UTF-8"));
                int n;
                while ((n = reader.read(buffer)) != -1) {
                    writer.write(buffer, 0, n);
                }
                responce = writer.toString();
                writer.close();
            } finally {
                // closing InputStream
            }
            return responce;
        } else {
            return "";
        }
    }

    protected void onPreExecute() {
        onTaskCompleted.onTaskStarted();
    }

    protected void onPostExecute(Object result) {
        onTaskCompleted.onTaskCompleted(result);
    }
}
