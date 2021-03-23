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
package me.luzhuo.lib_okhttp.interceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Description:
 *
 * @Author: Luzhuo
 * @Creation Date: 2020/5/16 10:21
 * @Copyright: Copyright 2020 Luzhuo. All rights reserved.
 **/
public abstract class TokenInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        String token = getToken();
        Request request;

        // Rebuild a Request
        request = chain.request().newBuilder()
                .header("token", token)
                .header("Content-Type", "application/json; charset=utf-8")
                .build();

        Response response = chain.proceed(request);
        return response;
    }

    public abstract String getToken();
}
