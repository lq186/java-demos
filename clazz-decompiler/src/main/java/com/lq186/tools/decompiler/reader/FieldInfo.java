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
    FileName: FieldInfo.java
    Date: 2019/4/17
    Author: lq
*/
package com.lq186.tools.decompiler.reader;

import com.lq186.tools.decompiler.reader.attribute.AttributeInfo;
import com.lq186.tools.decompiler.reader.basic.U2;
import com.lq186.tools.decompiler.reader.constantpool.ConstantInfo;

import java.io.IOException;
import java.io.InputStream;

public final class FieldInfo implements IReader {

    private final ConstantInfo[] constantInfos;

    private final U2 accessFlagU2 = new U2();

    private final U2 nameIndexU2 = new U2();

    private final U2 descriptorIndexU2 = new U2();

    private final U2 attributesCountU2 = new U2();

    private AttributeInfo[] attributeInfos;

    public FieldInfo(ConstantInfo[] constantInfos) {
        this.constantInfos = constantInfos;
    }

    @Override
    public void read(InputStream inputStream) throws IOException {
        accessFlagU2.read(inputStream);
        nameIndexU2.read(inputStream);
        descriptorIndexU2.read(inputStream);
        attributesCountU2.read(inputStream);

        final short attributesCount = attributesCountU2.getValue();
        attributeInfos = new AttributeInfo[attributesCount];
        for (short i = 0; i < attributesCount; ++i) {
            attributeInfos[i] = AttributeInfo.getInstance(inputStream, constantInfos);
        }
    }

    public short getAccessFlag() {
        return accessFlagU2.getValue();
    }

    public short getNameIndex() {
        return nameIndexU2.getValue();
    }

    public short getDescriptorIndex() {
        return descriptorIndexU2.getValue();
    }

    public short getAttributesCount() {
        return attributesCountU2.getValue();
    }
}
