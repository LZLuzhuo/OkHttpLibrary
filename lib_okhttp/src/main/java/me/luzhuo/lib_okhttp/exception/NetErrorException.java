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
package me.luzhuo.lib_okhttp.exception;

/**
 * Description: response code is not [200,300) and 302
 *
 * @Author: Luzhuo
 * @Creation Date: 2020/5/16 13:08
 * @Copyright: Copyright 2020 Luzhuo. All rights reserved.
 **/
public class NetErrorException extends Exception {
    private int code;
    public NetErrorException(int code){
        this.code = code;
    }

    @Override
    public String toString() {
        return "NetErrorException: The status code of response is not [200,300) and 302.";
    }
}
