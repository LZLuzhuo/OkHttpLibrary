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
package me.luzhuo.lib_okhttp.callback;

/**
 * Description:
 *
 * @Author: Luzhuo
 * @Creation Date: 2020/5/16 11:15
 * @Copyright: Copyright 2020 Luzhuo. All rights reserved.
 **/
public interface IContentCallback {
    /**
     * Callback for successful request
     * @param code response code
     * @param data response data
     */
    public void onSuccess(int code, String data);

    /**
     * Callback for error request
     * @param code response code
     * @param error response data
     */
    public void onError(int code, String error);
}
