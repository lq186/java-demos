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
    FileName: ClassFile.java
    Date: 2019/4/16
    Author: lq
*/
package com.lq186.tools.decompiler.reader;

import com.lq186.tools.decompiler.reader.attribute.AttributeInfo;
import com.lq186.tools.decompiler.reader.basic.U2;
import com.lq186.tools.decompiler.reader.basic.U4;
import com.lq186.tools.decompiler.reader.constantpool.ConstantPool;

import java.io.IOException;
import java.io.InputStream;

public final class ClassFile implements IReader {

    private final U4 magicU4 = new U4();

    private final U2 minorVersionU2 = new U2();

    private final U2 majorVersionU2 = new U2();

    private final ConstantPool constantPool = new ConstantPool();

    private final U2 accessFlagU2 = new U2();

    private final U2 thisClassIndexU2 = new U2();

    private final U2 supperClassIndexU2 = new U2();

    private final U2 interfacesCountU2 = new U2();

    private U2[] interfacesIndexU2;

    private final U2 fieldsCountU2 = new U2();

    private FieldInfo[] fieldInfos;

    private final U2 methodsCountU2 = new U2();

    private MethodInfo[] methodInfos;

    private final U2 attributesCountU2 = new U2();

    private AttributeInfo[] attributeInfos;

    @Override
    public void read(InputStream inputStream) throws IOException {
        magicU4.read(inputStream);
        minorVersionU2.read(inputStream);
        majorVersionU2.read(inputStream);
        constantPool.read(inputStream);

        accessFlagU2.read(inputStream);
        thisClassIndexU2.read(inputStream);
        supperClassIndexU2.read(inputStream);
        interfacesCountU2.read(inputStream);

        final short interfacesCount = interfacesCountU2.getValue();
        interfacesIndexU2 = new U2[interfacesCount];
        for (short i = 0; i < interfacesCount; ++i) {
            final U2 interfaceIndexU2 = new U2();
            interfaceIndexU2.read(inputStream);
            interfacesIndexU2[i] = interfaceIndexU2;
        }

        fieldsCountU2.read(inputStream);
        final short fieldsCount = fieldsCountU2.getValue();
        fieldInfos = new FieldInfo[fieldsCount];
        for (short i = 0; i < fieldsCount; ++i) {
            fieldInfos[i] = new FieldInfo(constantPool.getConstantInfos());
            fieldInfos[i].read(inputStream);
        }

        methodsCountU2.read(inputStream);
        final short methodsCount = methodsCountU2.getValue();
        methodInfos = new MethodInfo[methodsCount];
        for (short i = 0; i < methodsCount; ++i) {
            methodInfos[i] = new MethodInfo(constantPool.getConstantInfos());
            methodInfos[i].read(inputStream);
        }

        attributesCountU2.read(inputStream);
        final short attributesCount = attributesCountU2.getValue();
        attributeInfos = new AttributeInfo[attributesCount];
        for (short i = 0; i < attributesCount; ++i) {
            attributeInfos[i] = AttributeInfo.getInstance(inputStream, constantPool.getConstantInfos());
        }
    }

    public String getMagic() {
        return magicU4.toHex();
    }

    public short getMinorVersion() {
        return minorVersionU2.getValue();
    }

    public short getMajorVersion() {
        return majorVersionU2.getValue();
    }

    public ConstantPool getConstantPool() {
        return constantPool;
    }

    public short getAccessFlag() {
        return accessFlagU2.getValue();
    }

    public short getThisClassIndex() {
        return thisClassIndexU2.getValue();
    }

    public short getSupperClassIndex() {
        return supperClassIndexU2.getValue();
    }

    public short getInterfacesCount() {
        return interfacesCountU2.getValue();
    }

    private short[] getInterfacesIndex() {
        final short interfacesCount = getInterfacesCount();
        short[] interfacesIndex = new short[interfacesCount];
        for (short i = 0; i < interfacesCount; ++i) {
            interfacesIndex[i] = interfacesIndexU2[i].getValue();
        }
        return interfacesIndex;
    }

    public short getFieldsCount() {
        return fieldsCountU2.getValue();
    }

    public FieldInfo[] getFieldInfos() {
        return fieldInfos;
    }

    public short getMethodsCount() {
        return methodsCountU2.getValue();
    }

    public MethodInfo[] getMethodInfos() {
        return methodInfos;
    }

    public short getAttributesCount() {
        return attributesCountU2.getValue();
    }

    public AttributeInfo[] getAttributeInfos() {
        return attributeInfos;
    }
}
