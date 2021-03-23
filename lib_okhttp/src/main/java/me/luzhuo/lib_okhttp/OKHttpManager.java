/* Copyright 2020 Luzhuo. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package me.luzhuo.lib_okhttp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import me.luzhuo.lib_file.FileManager;
import me.luzhuo.lib_okhttp.bean.PostType;
import me.luzhuo.lib_okhttp.callback.IBitmapCallback;
import me.luzhuo.lib_okhttp.callback.IContentCallback;
import me.luzhuo.lib_okhttp.callback.IFileCallback;
import me.luzhuo.lib_okhttp.exception.NetErrorException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Description:
 *
 * @Author: Luzhuo
 * @Creation Date: 2020/5/16 11:13
 * @Copyright: Copyright 2020 Luzhuo. All rights reserved.
 **/
public class OKHttpManager implements IOKHttpManager{
    protected OkHttpClient client;

    public OkHttpClient getClient() {
        return client;
    }

    /**
     * Build a client without verifying SSL certificate.
     *
     * Example:
     * <pre>
     * OKHttpManager manager = new OKHttpManager(
     *         new RequestTimeInterceptor() {
     *             public void requestTIme(String requestUrl, int requestTime) {
     *                 Log.e(TAG, "" + requestUrl + " : " + requestTime);
     *             }
     *         },
     *         new TokenInterceptor() {
     *             public String getToken() {
     *                 return "66666666";
     *             }
     *         }
     * );
     *
     * // sync post
     * String data = manager.post(url, "666", PostType.JSON);
     *
     * // async post
     * manager.post(url, "666", PostType.JSON, new IContentCallback() {
     *     public void onSuccess(int code, String data) {
     *         Log.e(TAG, "" + code + " : " + data);
     *     }
     *
     *     public void onError(int code, String error) {
     *         Log.e(TAG, "" + code + " : " + error);
     *     }
     * });
     * </pre>
     *
     * @param interceptor Interceptor
     * @throws KeyManagementException
     * @throws NoSuchAlgorithmException
     */
    public OKHttpManager(Interceptor... interceptor) throws KeyManagementException, NoSuchAlgorithmException {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                //.cache(new Cache(new File(""), 10 * 1024 * 1024)); // 10MB
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .callTimeout(30, TimeUnit.SECONDS)
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String s, SSLSession sslSession) {
                        return true;
                    }
                })
                .sslSocketFactory(SSLManager.getSSLSocketFactory(), SSLManager.getX509TrustManager());

                for (int i = 0; i < interceptor.length; ++i) {
                    builder.addInterceptor(interceptor[i]);
                }

        client = builder.build();
    }

    @Deprecated
    public OKHttpManager(InputStream in){
        // TODO your own ssl
    }

    public String get(String url) throws IOException, NetErrorException {
        if (TextUtils.isEmpty(url)) return "";

        Request request = getRequest(url);
        Response response = client.newCall(request).execute();
        if(response.isSuccessful()) return response.body().string();
        else if(response.isRedirect()) return response.body().string();
        else throw new NetErrorException(response.code());
    }

    /**
     * async get
     * @param url
     * @param callback
     */
    public void get(String url, final IContentCallback callback) {
        if (TextUtils.isEmpty(url)) {
            callback.onError(-1, "url 为空");
            return;
        }

        Request request = getRequest(url);

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onError(-1, e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()/* [200,300) */ || response.isRedirect()/* 302 */) {
                    String message = response.body().string();
                    callback.onSuccess(response.code(), message);
                } else {
                    callback.onError(response.code(), response.body().string());
                }
            }
        });
    }

    public void getBitmap(String url, final IBitmapCallback callback){
        if (TextUtils.isEmpty(url)) {
            callback.onError(-1, "url 路径为空");
            return;
        }

        Request request = getRequest(url);

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onError(-1, e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()/* [200,300) */ || response.isRedirect()/* 302 */) {
                    InputStream inputStream = response.body().byteStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    callback.onSuccess(response.code(), bitmap);
                } else {
                    callback.onError(response.code(), response.body().string());
                }
            }
        });
    }

    /**
     * 下载图片 (同步下载)
     */
    public Bitmap getBitmap(String url) throws IOException, NetErrorException {
        if (TextUtils.isEmpty(url)) return null;

        Request request = this.getRequest(url);
        Response response = this.client.newCall(request).execute();
        if (response.isSuccessful() || response.isRedirect()) {
            InputStream inputStream = response.body().byteStream();
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            return bitmap;
        } else {
            throw new NetErrorException(response.code());
        }
    }

    /**
     * 下载文件 (同步下载)
     */
    public File downloadFile(String url, File localFile) throws IOException, NetErrorException {
        if (TextUtils.isEmpty(url) || localFile == null) return localFile;

        Request request = this.getRequest(url);
        Response response = this.client.newCall(request).execute();

        if (response.isSuccessful() || response.isRedirect()) {

            if (!localFile.getParentFile().exists()) { localFile.getParentFile().mkdirs(); }
            if (!localFile.exists()) { localFile.createNewFile(); }

            InputStream inputStream = response.body().byteStream();
            new FileManager().Stream2File(inputStream, localFile.getAbsolutePath());
            return localFile;
        } else {
            throw new NetErrorException(response.code());
        }
    }

    /**
     * 下载文件 (异步下载)
     */
    public void downloadFile(String url, final File localFile, final IFileCallback callback) {
        if (TextUtils.isEmpty(url) || localFile == null) {
            callback.onError(-1, "url 或 localFile 参数异常");
            return;
        }

        Request request = this.getRequest(url);
        this.client.newCall(request).enqueue(new Callback() {
            public void onFailure(Call call, IOException e) {
                if (callback != null) callback.onError(-1, e.getMessage());
            }

            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful() || response.isRedirect()) {

                    if (!localFile.getParentFile().exists()) { localFile.getParentFile().mkdirs(); }
                    if (!localFile.exists()) { localFile.createNewFile(); }

                    InputStream inputStream = response.body().byteStream();
                    BufferedInputStream bis = new BufferedInputStream(inputStream);
                    BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(localFile));
                    byte[] bys = new byte[10240];
                    long progress = 0;

                    // start download
                    long total = response.body().contentLength();
                    if (callback != null) callback.onStart(total);

                    int len;
                    while((len = bis.read(bys)) != -1) {
                        bos.write(bys, 0, len);
                        bos.flush();
                        progress += len;
                        if (callback != null) callback.onProgress(progress);
                    }

                    bis.close();
                    bos.close();

                    if (callback != null) callback.onSuccess(response.code(), localFile);
                } else {
                    if (callback != null) callback.onError(response.code(), response.body().string());
                }
            }
        });
    }

    /**
     * build get request
     * @param url Support http and https.
     * @return
     */
    protected Request getRequest(String url){
        Request request = new Request.Builder()
                .url(url)
                .build();
        return request;
    }

    public String post(String url, String data, PostType type) throws IOException, NetErrorException {
        if (TextUtils.isEmpty(url)) return "";
        if (data == null) data = "";

        Request request = getRequest(url, data, type);

        Response response = client.newCall(request).execute();
        if(response.isSuccessful() || response.isRedirect()) return response.body().string();
        else throw new NetErrorException(response.code());
    }

    public void post(String url, String data, PostType type, final IContentCallback callback) {
        if (TextUtils.isEmpty(url)) {
            callback.onError(-1, "url 为空");
            return;
        }
        if (data == null) data = "";

        Request request = getRequest(url, data, type);

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onError(-1, e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()/* [200,300) */ || response.isRedirect()/* 302 */) {
                    String message = response.body().string();
                    callback.onSuccess(response.code(), message);
                } else {
                    callback.onError(response.code(), response.body().string());
                }
            }
        });
    }

    /**
     * build post request
     * @param url Support http and https.
     * @param data json
     * @param type Json
     * @return
     */
    protected Request getRequest(String url, String data, PostType type){
        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(type.getType(), data))
                .build();
        return request;
    }
}
