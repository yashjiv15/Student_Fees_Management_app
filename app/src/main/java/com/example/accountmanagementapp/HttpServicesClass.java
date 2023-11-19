package com.example.accountmanagementapp;



import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.HttpEntity;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.HttpResponse;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.NameValuePair;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.ClientProtocolException;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.HttpClient;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.entity.UrlEncodedFormEntity;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.methods.HttpGet;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.methods.HttpPost;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.methods.HttpUriRequest;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.conn.ClientConnectionManager;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.impl.client.DefaultHttpClient;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.message.BasicNameValuePair;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.params.BasicHttpParams;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.protocol.HTTP;


import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.ArrayList;

public class HttpServicesClass {
    public int responseCode;

    public String message;

    public String response;

    public ArrayList<NameValuePair> ArrayListParams;

    public ArrayList <NameValuePair> headers;

    public String UrlHolder;

    public String getResponse()
    {
        return response;
    }

    public String getErrorMessage()
    {
        return message;
    }

    public int getResponseCode()
    {
        return responseCode;
    }

    public HttpServicesClass(String url)
    {
        HttpServicesClass.this.UrlHolder = url;

        ArrayListParams = new ArrayList<NameValuePair>();

        headers = new ArrayList<NameValuePair>();
    }

    public void AddParam(String name, String value)
    {
        ArrayListParams.add(new BasicNameValuePair(name, value));
    }

    public void AddHeader(String name, String value)
    {
        headers.add(new BasicNameValuePair(name, value));
    }

    public void ExecuteGetRequest() throws Exception
    {
        String MixParams = "";

        if(!ArrayListParams.isEmpty())
        {
            MixParams += "?";

            for(NameValuePair p : ArrayListParams)
            {
                String paramString = p.getName() + "=" + URLEncoder.encode(p.getValue(),"UTF-8");

                if(MixParams.length() > 1)
                {
                    MixParams  +=  "&" + paramString;
                }
                else
                {
                    MixParams += paramString;
                }
            }
        }

        HttpUriRequest httpGet = new HttpGet(UrlHolder + MixParams);

        for(NameValuePair h : headers)
        {
            httpGet.addHeader(h.getName(), h.getValue());
        }

        executeRequest(httpGet, UrlHolder);
    }

    public void ExecutePostRequest() throws Exception
    {
        HttpPost httpPost = new HttpPost(UrlHolder);
        for(NameValuePair h : headers)
        {
            httpPost.addHeader(h.getName(), h.getValue());
        }

        if(!ArrayListParams.isEmpty())
        {
            httpPost.setEntity(new UrlEncodedFormEntity(ArrayListParams, HTTP.UTF_8));
        }

        executeRequest(httpPost, UrlHolder);
    }

    private void executeRequest(HttpUriRequest request, String url)
    {
        HttpParams httpParameters = (HttpParams) new BasicHttpParams();

        HttpConnectionParams.setConnectionTimeout(httpParameters, 10000);

        HttpConnectionParams.setSoTimeout(httpParameters, 10000);

        HttpClient httpClient = new DefaultHttpClient((ClientConnectionManager) httpParameters);

        HttpResponse httpResponse;
        try
        {
            httpResponse = httpClient.execute(request);
            responseCode = httpResponse.getStatusLine().getStatusCode();
            message = httpResponse.getStatusLine().getReasonPhrase();

            HttpEntity entity = httpResponse.getEntity();
            if (entity != null)
            {
                InputStream inputStream = entity.getContent();

                response = convertStreamToString(inputStream);

                inputStream.close();
            }
        }
        catch (ClientProtocolException e)
        {
            httpClient.getConnectionManager().shutdown();
            e.printStackTrace();
        }
        catch (IOException e)
        {
            httpClient.getConnectionManager().shutdown();
            e.printStackTrace();
        }
    }

    private String convertStreamToString(InputStream is)
    {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));

        StringBuilder stringBuilder = new StringBuilder();

        String line = null;
        try
        {
            while ((line = bufferedReader.readLine()) != null)
            {
                stringBuilder.append(line + "\n");
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                is.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        return stringBuilder.toString();
    }

}