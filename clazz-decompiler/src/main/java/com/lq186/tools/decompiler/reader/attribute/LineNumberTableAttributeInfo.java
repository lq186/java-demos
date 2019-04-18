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
    FileName: LineNumberTableAttributeInfo.java
    Date: 2019/4/17
    Author: lq
*/
package com.lq186.tools.decompiler.reader.attribute;

import com.lq186.tools.decompiler.reader.IReader;
import com.lq186.tools.decompiler.reader.basic.U2;

import java.io.IOException;
import java.io.InputStream;

public final class LineNumberTableAttributeInfo extends AttributeInfo {

    private final U2 lineNumberTableLengthU2 = new U2();

    private LineNumberInfo[] lineNumberInfos;


    @Override
    public void read(InputStream inputStream) throws IOException {
        lineNumberTableLengthU2.read(inputStream);

        final short length = getLineNumberTableLength();
        lineNumberInfos = new LineNumberInfo[length];
        for (short i = 0; i < length; ++i) {
            final LineNumberInfo lineNumberInfo = new LineNumberInfo();
            lineNumberInfo.read(inputStream);
            lineNumberInfos[i] = lineNumberInfo;
        }
    }

    @Override
    protected void buildString(StringBuilder builder) {
        final short length = getLineNumberTableLength();
        builder.append("\t line number table length: ").append(length).append(", \n");
        if (length > 0) {
            builder.append("\t line number info: [ \n");

            LineNumberInfo lineNumberInfo;
            for (short i = 0; i < length; ++i) {
                lineNumberInfo = lineNumberInfos[i];
                builder.append("\t\t { \n");
                builder.append("\t\t\t start pc: ").append(lineNumberInfo.getStartPc()).append(", \n");
                builder.append("\t\t\t line number: ").append(lineNumberInfo.getLineNumber()).append("\n");
                builder.append("\t\t }").append(i < (length - 1) ? ", \n" : "\n");
            }

            builder.append("\t ], \n");
        }
    }

    public short getLineNumberTableLength() {
        return lineNumberTableLengthU2.getValue();
    }

    public static class LineNumberInfo implements IReader {

        private final U2 startPcU2 = new U2();

        private final U2 lineNumberU2 = new U2();

        @Override
        public void read(InputStream inputStream) throws IOException {
            startPcU2.read(inputStream);
            lineNumberU2.read(inputStream);
        }

        public short getStartPc() {
            return startPcU2.getValue();
        }

        public short getLineNumber() {
            return lineNumberU2.getValue();
        }
    }
}
