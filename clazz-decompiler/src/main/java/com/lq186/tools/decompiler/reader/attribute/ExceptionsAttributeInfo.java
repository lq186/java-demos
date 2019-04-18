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
    FileName: ExceptionsAttributeInfo.java
    Date: 2019/4/17
    Author: lq
*/
package com.lq186.tools.decompiler.reader.attribute;

import com.lq186.tools.decompiler.reader.basic.U2;

import java.io.IOException;
import java.io.InputStream;

public final class ExceptionsAttributeInfo extends AttributeInfo {

    private final U2 exceptionsCountU2 = new U2();

    private U2[] exceptionIndexU2Table;

    @Override
    public void read(InputStream inputStream) throws IOException {
        exceptionsCountU2.read(inputStream);
        final short exceptionsCount = getExceptionsCount();

        if (exceptionsCount > 0) {
            exceptionIndexU2Table = new U2[exceptionsCount];
            for (short i = 0; i < exceptionsCount; ++i) {
                final U2 exceptionIndexU2 = new U2();
                exceptionIndexU2.read(inputStream);
                exceptionIndexU2Table[i] = exceptionIndexU2;
            }
        }
    }

    @Override
    protected void buildString(StringBuilder builder) {
        final short exceptionsCount = getExceptionsCount();
        builder.append("\t exceptions count: ").append(exceptionsCount).append(", \n");
        if (exceptionsCount > 0) {
            builder.append("\t exception table: [ \n");
            final short[] exceptionIndexTable = getExceptionIndexTable();
            for (short i = 0; i < exceptionsCount; ++i) {
                builder.append("\t\t { \n");
                builder.append("\t\t\n exception index: ").append(exceptionIndexTable[i]).append("\n");
                builder.append("\t\t }").append(i < (exceptionsCount - 1) ? ", " : "").append("\n");
            }
            builder.append("\t ], \n");
        }
    }

    public short getExceptionsCount() {
        return exceptionsCountU2.getValue();
    }

    public short[] getExceptionIndexTable() {
        final short exceptionsCount = getExceptionsCount();
        if (exceptionsCount > 0) {
            final short[] exceptionIndexTable = new short[exceptionsCount];
            for (short i = 0; i < exceptionsCount; ++i) {
                exceptionIndexTable[i] = exceptionIndexU2Table[i].getValue();
            }
            return exceptionIndexTable;
        } else {
            return null;
        }
    }
}
