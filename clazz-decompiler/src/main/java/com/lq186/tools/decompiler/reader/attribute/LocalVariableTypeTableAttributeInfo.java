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
    FileName: LocalVariableTypeTableAttributeInfo.java
    Date: 2019/4/17
    Author: lq
*/
package com.lq186.tools.decompiler.reader.attribute;

import com.lq186.tools.decompiler.reader.IReader;
import com.lq186.tools.decompiler.reader.basic.U2;

import java.io.IOException;
import java.io.InputStream;

public final class LocalVariableTypeTableAttributeInfo extends AttributeInfo {

    private final U2 localVariableTypeTableLengthU2 = new U2();

    private LocalVariableTypeInfo[] localVariableTypeTable;

    @Override
    public void read(InputStream inputStream) throws IOException {
        localVariableTypeTableLengthU2.read(inputStream);

        final short length = getLocalVariableTypeTableLength();
        localVariableTypeTable = new LocalVariableTypeInfo[length];
        for (short i = 0; i < length; ++i) {
            final LocalVariableTypeInfo localVariableTypeInfo = new LocalVariableTypeInfo();
            localVariableTypeInfo.read(inputStream);
            localVariableTypeTable[i] = localVariableTypeInfo;
        }
    }

    @Override
    protected void buildString(StringBuilder builder) {
        final short length = getLocalVariableTypeTableLength();
        builder.append("\t local variable type table length: ").append(length).append(", \n");
        if (length > 0) {
            builder.append("\t local variable type info: [ \n");

            LocalVariableTypeInfo localVariableTypeInfo;
            for (short i = 0; i < length; ++i) {
                localVariableTypeInfo = localVariableTypeTable[i];
                builder.append("\t\t { \n");
                builder.append("\t\t\t start pc: ").append(localVariableTypeInfo.getStartPc()).append(", \n");
                builder.append("\t\t\t length: ").append(localVariableTypeInfo.getLength()).append(", \n");
                builder.append("\t\t\t name index: ").append(localVariableTypeInfo.getNameIndex()).append(", \n");
                builder.append("\t\t\t signature index: ").append(localVariableTypeInfo.getSignatureIndex()).append(", \n");
                builder.append("\t\t\t index: ").append(localVariableTypeInfo.getIndex()).append(" \n");
                builder.append("\t\t }").append(i < (length - 1) ? ", \n" : "\n");
            }

            builder.append("\t ], \n");
        }
    }

    private short getLocalVariableTypeTableLength() {
        return localVariableTypeTableLengthU2.getValue();
    }

    public LocalVariableTypeInfo[] getLocalVariableTypeTable() {
        return localVariableTypeTable;
    }

    public static class LocalVariableTypeInfo implements IReader {

        private final U2 startPcU2 = new U2();

        private final U2 lengthU2 = new U2();

        private final U2 nameIndexU2 = new U2();

        private final U2 signatureIndexU2 = new U2();

        private final U2 indexU2 = new U2();

        @Override
        public void read(InputStream inputStream) throws IOException {
            startPcU2.read(inputStream);
            lengthU2.read(inputStream);
            nameIndexU2.read(inputStream);
            signatureIndexU2.read(inputStream);
            indexU2.read(inputStream);
        }

        public short getStartPc() {
            return startPcU2.getValue();
        }

        public short getLength() {
            return lengthU2.getValue();
        }

        public short getNameIndex() {
            return nameIndexU2.getValue();
        }

        public short getSignatureIndex() {
            return signatureIndexU2.getValue();
        }

        public short getIndex() {
            return indexU2.getValue();
        }
    }
}
