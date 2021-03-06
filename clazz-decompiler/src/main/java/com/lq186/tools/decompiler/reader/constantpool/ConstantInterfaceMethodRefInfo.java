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
    FileName: ConstantInterfaceMethodRefInfo.java
    Date: 2019/4/16
    Author: lq
*/
package com.lq186.tools.decompiler.reader.constantpool;

import com.lq186.tools.decompiler.reader.basic.U2;

import java.io.IOException;
import java.io.InputStream;

public final class ConstantInterfaceMethodRefInfo extends ConstantInfo {

    private final U2 classIndexU2 = new U2();

    private final U2 nameAndTypeIndexU2 = new U2();

    @Override
    public void read(InputStream inputStream) throws IOException {
        classIndexU2.read(inputStream);
        nameAndTypeIndexU2.read(inputStream);
    }

    public short getClassIndex() {
        return classIndexU2.getValue();
    }

    public short getNameAndTypeIndex() {
        return nameAndTypeIndexU2.getValue();
    }

    @Override
    protected void buildString(StringBuilder builder) {
        builder.append("\t class index: ").append(getClassIndex()).append(" \n");
        builder.append("\t name and type index: ").append(getClassIndex()).append(" \n");
    }
}
