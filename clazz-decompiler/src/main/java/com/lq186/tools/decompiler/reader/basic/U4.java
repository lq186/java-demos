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
    FileName: U4.java
    Date: 2019/4/16
    Author: lq
*/
package com.lq186.tools.decompiler.reader.basic;

import com.lq186.tools.decompiler.reader.IReader;
import com.lq186.tools.decompiler.util.BytesUtils;

import java.io.IOException;
import java.io.InputStream;

public final class U4 implements IReader {

    private int value;

    @Override
    public void read(InputStream inputStream) throws IOException {
        byte[] bytes = new byte[4];
        inputStream.read(bytes);
        this.value = BytesUtils.toInt(bytes);
    }

    public int getValue() {
        return value;
    }

    public String toHex() {
        return Integer.toHexString(value);
    }

    public float toFloat() {
        return Float.intBitsToFloat(value);
    }
}
