/*    
    Copyright ©2019 lq186.com 
 
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
 
        http://www.apache.org/licenses/LICENSE-2.0
 
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
*/
/*
    FileName: BytesUtils.java
    Date: 2019/4/15
    Author: lq
*/
package com.lq186.tools.decompiler.util;

public final class BytesUtils {

    public static final short toShort(byte[] bytes) {
        short value = 0;
        for (int i = 0; i < bytes.length; ++i) {
            value <<= 8;
            value |= (bytes[i] & 0xFF);
        }
        return value;
    }

    public static final int toInt(byte[] bytes) {
        int value = 0;
        for (int i = 0; i < bytes.length; ++i) {
            value <<= 8;
            value |= (bytes[i] & 0xFF);
        }
        return value;
    }

    public static final long toLong(byte[] bytes) {
        long value = 0;
        for (int i = 0; i < bytes.length; ++i) {
            value <<= 8;
            value |= (bytes[i] & 0xFF);
        }
        return value;
    }

}
