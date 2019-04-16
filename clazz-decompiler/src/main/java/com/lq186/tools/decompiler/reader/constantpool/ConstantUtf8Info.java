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
    FileName: ConstantUtf8Info.java
    Date: 2019/4/16
    Author: lq
*/
package com.lq186.tools.decompiler.reader.constantpool;

import com.lq186.tools.decompiler.reader.basic.U2;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

public final class ConstantUtf8Info extends ConstantInfo {

    private final U2 lengthU2 = new U2();

    private byte[] utf8Bytes;

    @Override
    public void read(InputStream inputStream) throws IOException {
        lengthU2.read(inputStream);
        utf8Bytes = new byte[lengthU2.getValue()];
        inputStream.read(utf8Bytes);
    }

    public short getLength() {
        return lengthU2.getValue();
    }

    public byte[] getUtf8Bytes() {
        return utf8Bytes;
    }

    public String getUtf8String() throws UnsupportedEncodingException {
        return new String(utf8Bytes, "UTF-8");
    }

    @Override
    protected void buildString(StringBuilder builder) {
        builder.append("\t utf-8 length: ").append(getLength()).append(" \n");
        try {
            builder.append("\t utf-8 string: ").append(getUtf8String()).append(" \n");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
