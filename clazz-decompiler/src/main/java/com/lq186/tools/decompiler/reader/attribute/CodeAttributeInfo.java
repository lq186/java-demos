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
    FileName: CodeAttributeInfo.java
    Date: 2019/4/17
    Author: lq
*/
package com.lq186.tools.decompiler.reader.attribute;

import com.lq186.tools.decompiler.reader.IReader;
import com.lq186.tools.decompiler.reader.basic.U2;
import com.lq186.tools.decompiler.reader.basic.U4;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

public final class CodeAttributeInfo extends AttributeInfo {

    private final U2 maxStackU2 = new U2();

    private final U2 maxLocalsU2 = new U2();

    private final U4 codeLengthU4 = new U4();

    private byte[] codeBytes;

    private final U2 exceptionTableLengthU2 = new U2();

    private ExceptionInfo[] exceptionTable;

    private final U2 attributesCountU2 = new U2();

    private AttributeInfo[] attributes;

    @Override
    public void read(InputStream inputStream) throws IOException {
        maxStackU2.read(inputStream);
        maxLocalsU2.read(inputStream);
        codeLengthU4.read(inputStream);

        final int codeLength = codeLengthU4.getValue();
        codeBytes = new byte[codeLength];
        inputStream.read(codeBytes);

        exceptionTableLengthU2.read(inputStream);
        final short exceptionTableLength = exceptionTableLengthU2.getValue();
        exceptionTable = new ExceptionInfo[exceptionTableLength];
        for (short i = 0; i < exceptionTableLength; ++i) {
            final ExceptionInfo exceptionInfo = new ExceptionInfo();
            exceptionInfo.read(inputStream);
            exceptionTable[i] = exceptionInfo;
        }

        attributesCountU2.read(inputStream);
        final short attributesCount = attributesCountU2.getValue();
        attributes = new AttributeInfo[attributesCount];
        for (short i = 0; i < attributesCount; ++i) {
            attributes[i] = AttributeInfo.getInstance(inputStream, getConstantInfos());
        }
    }

    public short getMaxStack() {
        return maxStackU2.getValue();
    }

    public short getMaxLocals() {
        return maxLocalsU2.getValue();
    }

    public int getCodeLength() {
        return codeLengthU4.getValue();
    }

    public short getExceptionTableLength() {
        return exceptionTableLengthU2.getValue();
    }

    public String getCode() throws UnsupportedEncodingException {
        return new String(codeBytes, "UTF-8");
    }

    public ExceptionInfo[] getExceptionTable() {
        return exceptionTable;
    }

    public short getAttributesCount() {
        return attributesCountU2.getValue();
    }

    public AttributeInfo[] getAttributes() {
        return attributes;
    }

    public static class ExceptionInfo implements IReader {

        private final U2 startPcU2 = new U2();

        private final U2 endPcU2 = new U2();

        private final U2 handlerPcU2 = new U2();

        private final U2 catchTypeIndexU2 = new U2();

        @Override
        public void read(InputStream inputStream) throws IOException {
            startPcU2.read(inputStream);
            endPcU2.read(inputStream);
            handlerPcU2.read(inputStream);
            catchTypeIndexU2.read(inputStream);
        }

        public short getStartPc() {
            return startPcU2.getValue();
        }

        public short getEndPc() {
            return endPcU2.getValue();
        }

        public short getHandlerPc() {
            return handlerPcU2.getValue();
        }

        public short getCatchTypeIndex() {
            return catchTypeIndexU2.getValue();
        }
    }
}
