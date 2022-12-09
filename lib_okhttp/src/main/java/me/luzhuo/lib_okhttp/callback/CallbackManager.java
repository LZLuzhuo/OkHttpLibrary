/* Copyright 2022 Luzhuo. All rights reserved.
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

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;

import java.io.File;

public class CallbackManager {
    private CallbackManager(){}
    private static class Instance {
        private static final CallbackManager INSTANCE = new CallbackManager();
        private static final Handler main = new Handler(Looper.getMainLooper());
    }

    public static CallbackManager getInstance() {
        return Instance.INSTANCE;
    }

    public void onSuccess(final IContentCallback callback, final int code, final String data) {
        if (callback == null) return;
        Instance.main.post(new Runnable() {
            @Override
            public void run() {
                callback.onSuccess(code, data);
            }
        });
    }

    public void onError(final IContentCallback callback, final int code, final String error) {
        if (callback == null) return;
        Instance.main.post(new Runnable() {
            @Override
            public void run() {
                callback.onSuccess(code, error);
            }
        });
    }

    public void onSuccess(final IBitmapCallback callback, final int code, final Bitmap data) {
        if (callback == null) return;
        Instance.main.post(new Runnable() {
            @Override
            public void run() {
                callback.onSuccess(code, data);
            }
        });
    }

    public void onError(final IBitmapCallback callback, final int code, final String error) {
        if (callback == null) return;
        Instance.main.post(new Runnable() {
            @Override
            public void run() {
                callback.onError(code, error);
            }
        });
    }

    public void onStart(final IFileCallback callback, final long total) {
        if (callback == null) return;
        Instance.main.post(new Runnable() {
            @Override
            public void run() {
                callback.onStart(total);
            }
        });
    }

    public void onProgress(final IFileCallback callback, final long progress) {
        if (callback == null) return;
        Instance.main.post(new Runnable() {
            @Override
            public void run() {
                callback.onProgress(progress);
            }
        });
    }

    public void onSuccess(final IFileCallback callback, final int code, final File filepath) {
        if (callback == null) return;
        Instance.main.post(new Runnable() {
            @Override
            public void run() {
                callback.onSuccess(code, filepath);
            }
        });
    }

    public void onError(final IFileCallback callback, final int code, final String error) {
        if (callback == null) return;
        Instance.main.post(new Runnable() {
            @Override
            public void run() {
                callback.onError(code, error);
            }
        });
    }
}
