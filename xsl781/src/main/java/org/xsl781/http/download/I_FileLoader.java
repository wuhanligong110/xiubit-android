/*
 * 
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.xsl781.http.download;

/**
 * 文件下载器接口协议<br>
 * 
 * <b>创建时间</b> 2014-8-11
 * 
 * 
 * @version 1.0
 */
public interface I_FileLoader {
    void doDownload(String url, boolean isResume);

    boolean isStop();

    void stop();
}
