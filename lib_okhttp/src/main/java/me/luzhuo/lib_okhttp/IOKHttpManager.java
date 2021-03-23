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

import java.io.IOException;

import me.luzhuo.lib_okhttp.bean.PostType;
import me.luzhuo.lib_okhttp.callback.IContentCallback;
import me.luzhuo.lib_okhttp.exception.NetErrorException;


/**
 * Description:
 *
 * @Author: Luzhuo
 * @Creation Date: 2020/5/16 11:13
 * @Copyright: Copyright 2020 Luzhuo. All rights reserved.
 **/
public interface IOKHttpManager {
    /**
     * sync get
     *
     * @return
     */
    public String get(String url) throws IOException, NetErrorException;

    /**
     * async get
     *
     * @param url
     * @param callback
     */
    public void get(String url, final IContentCallback callback);

    /**
     * sync post
     *
     * @param url  Support http and https.
     * @param data json
     * @param type Json
     * @return
     * @throws IOException
     * @throws NetErrorException
     */
    public String post(String url, String data, PostType type) throws IOException, NetErrorException;

    /**
     * async post
     *
     * @param url      Support http and https.
     * @param data     json
     * @param type     Json
     * @param callback
     */
    public void post(String url, String data, PostType type, final IContentCallback callback);
}
