package com.example.kk.demo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.xutils.common.Callback;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends Activity implements View.OnClickListener {
    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView=new WebView(this, null);
        findViewById(R.id.xuils).setOnClickListener(this);
        findViewById(R.id.httpclient).setOnClickListener(this);
        findViewById(R.id.urlconnection).setOnClickListener(this);
        findViewById(R.id.webview).setOnClickListener(this);
        webView.getSettings().setSupportMultipleWindows(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.xuils:
                xutils_post();
                break;
            case R.id.httpclient:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        httpclient();
                    }
                }).start();
                break;
            case R.id.urlconnection:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        urlconnection();
                    }
                }).start();
                break;
            case R.id.webview:
                webView.loadUrl("http://www.sina.com.cn");
                break;
        }
    }

    private void urlconnection() {
        URL url = null;
        try {
            url = new URL("http://127.0.0.1:8082/");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.getResponseCode();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void httpclient() {
        List<BasicNameValuePair> params = new LinkedList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("param1", "中国"));
        params.add(new BasicNameValuePair("param2", "value2"));
        //对参数编码
        String param = URLEncodedUtils.format(params, "UTF-8");
        //baseUrl
        String baseUrl = "http://127.0.0.1:8081/";
        //将URL与参数拼接
        HttpGet getMethod = new HttpGet(baseUrl + "?" + param);
        HttpClient httpClient = new DefaultHttpClient();
        try {
            HttpResponse response = httpClient.execute(getMethod); //发起GET请求
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    private void xutils_post() {
        RequestParams requestParams = new RequestParams("http://www.baidu.com/");
        requestParams.addBodyParameter("test", "true");
        requestParams.setMethod(HttpMethod.POST);
        x.http().post(requestParams, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.i("xuhu", "xuhu", ex);
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Log.i("xuhu", "xuhu", cex);
            }

            @Override
            public void onFinished() {

            }
        });
    }
}
