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
    FileName: ConstantValueAttributeInfo.java
    Date: 2019/4/17
    Author: lq
*/
package com.lq186.tools.decompiler.reader.attribute;

import com.lq186.tools.decompiler.reader.basic.U2;

import java.io.IOException;
import java.io.InputStream;

public class ConstantValueAttributeInfo extends AttributeInfo {

    private final U2 constantValueIndexU2 = new U2();

    @Override
    public void read(InputStream inputStream) throws IOException {
        constantValueIndexU2.read(inputStream);
    }

    @Override
    protected void buildString(StringBuilder builder) {
        builder.append("\t constant value index: ").append(getConstantValueIndex()).append(", \n");
    }

    public short getConstantValueIndex() {
        return constantValueIndexU2.getValue();
    }
}
