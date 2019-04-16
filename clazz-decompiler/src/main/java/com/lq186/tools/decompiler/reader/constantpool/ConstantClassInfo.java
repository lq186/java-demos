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
    FileName: ConstantClassInfo.java
    Date: 2019/4/16
    Author: lq
*/
package com.lq186.tools.decompiler.reader.constantpool;

import com.lq186.tools.decompiler.reader.basic.U2;

import java.io.IOException;
import java.io.InputStream;

public class ConstantClassInfo extends ConstantInfo {

    private final U2 u2 = new U2();

    @Override
    public void read(InputStream inputStream) throws IOException {
        u2.read(inputStream);
    }

    public short getClassIndex() {
        return u2.getValue();
    }

    @Override
    protected void buildString(StringBuilder builder) {
        builder.append("\t class index: ").append(getClassIndex()).append(" \n");
    }
}
