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
package me.luzhuo.lib_okhttp.bean;

import okhttp3.MediaType;

/**
 * Description:
 * 服务期根据 Content-Type 字段来获知请求消息体是用何种方式编码
 *
 * @Author: Luzhuo
 * @Creation Date: 2020/5/15 16:29
 * @Copyright: Copyright 2020 Luzhuo. All rights reserved.
 **/
public enum PostType {
    /**
     * 告知服务期消息主体是序列化后的JSON字符串
     */
    JSON("application/json; charset=utf-8"),

    /**
     * 告知服务期消息主体是序列化后的XML字符串
     */
    XML("text/xml; charset=utf-8"),

    /**
     * MarkDown文档
     */
    MarkDown("text/x-markdown; charset=utf-8"),

    /**
     * POST请求, 浏览器原生<form>表单, 默认以该方式提交
     *
     * 数据格式:
     * key1=val1&key2=val2
     */
    Form("application/x-www-form-urlencoded; charset=utf-8"),

    /**
     * POST请求, 使用<form>表单上传文件
     *
     * 数据格式:
     * POST http://www.example.com HTTP/1.1
     * Content-Type:multipart/form-data; boundary=----WebKitFormBoundaryrGKCBY7qhFd3TrwA
     *
     * ------WebKitFormBoundaryrGKCBY7qhFd3TrwA
     * Content-Disposition: form-data; name="text"
     * ------WebKitFormBoundaryrGKCBY7qhFd3TrwA
     */
    @Deprecated
    Multipart("multipart/form-data; boundary=xxx-xxx-xxx");


    private MediaType mediaType;
    private PostType(String mediaType){
        this.mediaType = MediaType.get(mediaType);
    }
    public MediaType getType() {
        return mediaType;
    }
}
