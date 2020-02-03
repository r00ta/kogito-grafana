package org.acme.kogito.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HttpHelper {
    private static final CloseableHttpClient httpclient = HttpClients.createDefault();

    private ObjectMapper mapper = new ObjectMapper();

    private String baseHost;

    public HttpHelper(String baseHost){
        this.baseHost = baseHost;
    }

    public String doGet(String path) {
        HttpGet request = new HttpGet(baseHost + path);
        HttpResponse response = null;
        try {
            response = httpclient.execute(request);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                // return it as a String
                String result = EntityUtils.toString(entity);
                System.out.println(result);
                return result;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String doPost(String path, String params) {

        HttpPost post = new HttpPost(baseHost + path);
        System.out.println("Going to post to: " + path + "\n with: " + params);
        try {
            post.setEntity(new StringEntity(params, ContentType.APPLICATION_JSON));
            CloseableHttpResponse response = httpclient.execute(post);
            String result = EntityUtils.toString(response.getEntity());
            System.out.println("I've got " + result);
            return result;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
