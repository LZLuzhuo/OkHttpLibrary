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

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * Description: SSL certificate manager
 *
 * @Author: Luzhuo
 * @Creation Date: 2020/5/16 11:02
 * @Copyright: Copyright 2020 Luzhuo. All rights reserved.
 **/
public class SSLManager {

    public static SSLSocketFactory getSSLSocketFactory() throws NoSuchAlgorithmException, KeyManagementException {
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, new TrustManager[] { getX509TrustManager() }, null);
        SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
        return sslSocketFactory;
    }

    public static X509TrustManager getX509TrustManager() {
        X509TrustManager mTrustManager = new X509TrustManager() {
            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[]{};
            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException { }
            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException { }
        };
        return mTrustManager;
    }
}
