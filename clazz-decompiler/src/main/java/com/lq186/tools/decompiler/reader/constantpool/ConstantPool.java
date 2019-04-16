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
    FileName: ConstantPool.java
    Date: 2019/4/16
    Author: lq
*/
package com.lq186.tools.decompiler.reader.constantpool;

import com.lq186.tools.decompiler.reader.IReader;
import com.lq186.tools.decompiler.reader.basic.U2;

import java.io.IOException;
import java.io.InputStream;

public class ConstantPool implements IReader {

    private final U2 constantPoolCountU2 = new U2();

    private ConstantInfo[] constantInfos;

    @Override
    public void read(InputStream inputStream) throws IOException {
        constantPoolCountU2.read(inputStream);
        constantInfos = new ConstantInfo[constantPoolCountU2.getValue()];
        for (short i = 1; i < constantPoolCountU2.getValue(); ++i) {
            constantInfos[i] = ConstantInfo.getInstance(inputStream, i);
        }
    }

    public short getConstantPoolCount() {
        return constantPoolCountU2.getValue();
    }

    public ConstantInfo[] getConstantInfos() {
        return constantInfos;
    }
}
