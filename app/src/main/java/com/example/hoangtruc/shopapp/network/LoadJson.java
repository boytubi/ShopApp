package com.example.hoangtruc.shopapp.network;

import android.net.Uri;
import android.os.AsyncTask;
import android.print.PrinterId;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoadJson extends AsyncTask<String, Void, String> {

    private String url;
    private List<HashMap<String, String>> attrs;
    private StringBuilder data;
    private boolean checkGet = true;

    public LoadJson(String url) {
        this.url = url;
        checkGet = true;
    }

    public LoadJson(String url, List<HashMap<String, String>> attrs) {
        this.url = url;
        this.attrs = attrs;
        checkGet = false;
    }

    @Override
    protected String doInBackground(String... strings) {
        String data_json = "";
        URL new_url;
        try {
            new_url = new URL(url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) new_url.openConnection();

            if (checkGet) {
                data_json = requestGet(httpURLConnection);

            } else {
                data_json = requestPost(httpURLConnection);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data_json;
    }

    private String requestGet(HttpURLConnection httpURLConnection) {
        String dataGet = "";
        InputStream inputStream = null;
        try {
            httpURLConnection.connect();

            inputStream = httpURLConnection.getInputStream();
            InputStreamReader reader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(reader);

            data = new StringBuilder();
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                data.append(line);
            }
            dataGet = data.toString();
            bufferedReader.close();
            reader.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return dataGet;
    }

    private String requestPost(HttpURLConnection httpURLConnection) {
        String dataPost = "";
        String key = "";
        String value = "";

        try {
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);

            Uri.Builder builder = new Uri.Builder();

            int count = attrs.size();
            for (int i = 0; i < count; i++) {
                for (Map.Entry<String, String> values : attrs.get(i).entrySet()) {
                    key = values.getKey();
                    value = values.getValue();
                }
                builder.appendQueryParameter(key, value);
            }
            String query = builder.build().getEncodedQuery();

            OutputStream outputStream = httpURLConnection.getOutputStream();
            OutputStreamWriter streamWriter = new OutputStreamWriter(outputStream);
            BufferedWriter bufferedWriter = new BufferedWriter(streamWriter);
            bufferedWriter.write(query);
            bufferedWriter.flush();
            bufferedWriter.close();
            streamWriter.close();
            outputStream.close();
            dataPost = requestGet(httpURLConnection);
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataPost;
    }

}
