/* Copyright 2021 Luzhuo. All rights reserved.
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

import java.io.File;

/**
 * Description: 
 * @Author: Luzhuo
 * @Creation Date: 2021/10/15 0:22
 * @Copyright: Copyright 2021 Luzhuo. All rights reserved.
 **/
public interface IFileCallback {

    /**
     * 开始下载文件
     */
    void onStart(long total);

    /**
     * 正在下载文件的进度
     */
    void onProgress(long progress);

    /**
     * 文件下载成功
     */
    void onSuccess(int code, File filepath);

    /**
     * 文件下载异常
     */
    void onError(int code, String error);
}
