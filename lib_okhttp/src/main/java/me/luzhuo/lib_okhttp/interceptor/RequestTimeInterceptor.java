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
 * @Creation Date: 2020/5/16 10:09
 * @Copyright: Copyright 2020 Luzhuo. All rights reserved.
 **/
public abstract class RequestTimeInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        long startTime = System.nanoTime();

        Response response = chain.proceed(request);
        long endTime = System.nanoTime();

        requestTIme(request.url().toString(), (int)((endTime - startTime) / 1e6d));
        return response;
    }

    public abstract void requestTIme(String requestUrl, int requestTime);
}
