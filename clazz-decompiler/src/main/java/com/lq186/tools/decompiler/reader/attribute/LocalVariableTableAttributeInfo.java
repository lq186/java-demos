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
    FileName: LocalVariableTableAttributeInfo.java
    Date: 2019/4/17
    Author: lq
*/
package com.lq186.tools.decompiler.reader.attribute;

import com.lq186.tools.decompiler.reader.IReader;
import com.lq186.tools.decompiler.reader.basic.U2;

import java.io.IOException;
import java.io.InputStream;

public final class LocalVariableTableAttributeInfo extends AttributeInfo {

    private final U2 localVariableTableLengthU2 = new U2();

    private LocalVariableInfo[] localVariableTable;

    @Override
    public void read(InputStream inputStream) throws IOException {
        localVariableTableLengthU2.read(inputStream);

        final short length = getLocalVariableTableLength();
        localVariableTable = new LocalVariableInfo[length];
        for (short i = 0; i < length; ++i) {
            final LocalVariableInfo localVariableInfo = new LocalVariableInfo();
            localVariableInfo.read(inputStream);
            localVariableTable[i] = localVariableInfo;
        }
    }

    @Override
    protected void buildString(StringBuilder builder) {
        final short length = getLocalVariableTableLength();
        builder.append("\t local variable table length: ").append(length).append(", \n");
        if (length > 0) {
            builder.append("\t local variable info: [ \n");

            LocalVariableInfo localVariableInfo;
            for (short i = 0; i < length; ++i) {
                localVariableInfo = localVariableTable[i];
                builder.append("\t\t { \n");
                builder.append("\t\t\t start pc: ").append(localVariableInfo.getStartPc()).append(", \n");
                builder.append("\t\t\t length: ").append(localVariableInfo.getLength()).append(", \n");
                builder.append("\t\t\t name index: ").append(localVariableInfo.getNameIndex()).append(", \n");
                builder.append("\t\t\t descriptor index: ").append(localVariableInfo.getDescriptorIndex()).append(", \n");
                builder.append("\t\t\t index: ").append(localVariableInfo.getIndex()).append(" \n");
                builder.append("\t\t }").append(i < (length - 1) ? ", \n" : "\n");
            }

            builder.append("\t ], \n");
        }
    }

    public short getLocalVariableTableLength() {
        return localVariableTableLengthU2.getValue();
    }


    public static class LocalVariableInfo implements IReader {

        private final U2 startPcU2 = new U2();

        private final U2 lengthU2 = new U2();

        private final U2 nameIndexU2 = new U2();

        private final U2 descriptorIndexU2 = new U2();

        private final U2 indexU2 = new U2();

        @Override
        public void read(InputStream inputStream) throws IOException {
            startPcU2.read(inputStream);
            lengthU2.read(inputStream);
            nameIndexU2.read(inputStream);
            descriptorIndexU2.read(inputStream);
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

        public short getDescriptorIndex() {
            return descriptorIndexU2.getValue();
        }

        public short getIndex() {
            return indexU2.getValue();
        }
    }
}
