package cn.com.sparknet.nclk.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author tangzj
 */
public class HttpClientUtils {

    private static final String DEFAULT_ENCODING = "UTF-8";
    private static final int SOCKET_TIMEOUT = 5000;
    private static final int CONNECT_TIMEOUT = 5000;
    private static final int CONNECTION_REQUEST_TIMEOUT = 5000;

    private static CloseableHttpClient HTTP_CLIENT;

    private static CloseableHttpClient getInstance() {
        if (HTTP_CLIENT == null) {
            synchronized (HttpClientUtils.class) {
                if (HTTP_CLIENT == null) {
                    // 请求配置
                    RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(SOCKET_TIMEOUT)
                            .setConnectionRequestTimeout(CONNECTION_REQUEST_TIMEOUT).setConnectTimeout(CONNECT_TIMEOUT).build();
                    HTTP_CLIENT = HttpClients.custom().setDefaultRequestConfig(requestConfig).
                            setRetryHandler(new DefaultHttpRequestRetryHandler(0, false)).build();
                }
            }
        }
        return HTTP_CLIENT;
    }

    /**
     * 普通get请求
     *
     * @param url
     * @return
     * @throws IOException
     */
    public static String get(String url) throws IOException {
        return getInstance().execute(new HttpGet(url), new StringResponseHandler());
    }

    /**
     * 普通post请求
     *
     * @param url
     * @param params
     * @return
     * @throws IOException
     */
    public static String post(String url, Map<String, Object> params) throws IOException {

        List<NameValuePair> nameValuePairs = new ArrayList<>();
        if (params != null) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                nameValuePairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
            }
        }
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, DEFAULT_ENCODING));
        return getInstance().execute(httpPost, new StringResponseHandler());
    }

    private static class StringResponseHandler implements ResponseHandler<String> {

        @Override
        public String handleResponse(HttpResponse response) throws IOException {
            int status = response.getStatusLine().getStatusCode();
            if (status >= 200 && status < 300) {
                HttpEntity entity = response.getEntity();
                return entity != null ? EntityUtils.toString(entity) : null;
            } else {
                throw new ClientProtocolException("Unexpected response status: " + status);
            }
        }
    }

}
