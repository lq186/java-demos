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
    FileName: ConstantTag.java
    Date: 2019/4/16
    Author: lq
*/
package com.lq186.tools.decompiler.reader.constantpool;

import com.lq186.tools.decompiler.consts.ConstantTag;
import com.lq186.tools.decompiler.reader.IReader;
import com.lq186.tools.decompiler.reader.basic.U1;

import java.io.IOException;
import java.io.InputStream;

public abstract class ConstantInfo implements IReader {

    private U1 tagU1 = new U1();

    private short index;

    public static final ConstantInfo getInstance(InputStream inputStream, final short index) throws IOException {
        final U1 tag = new U1();
        tag.read(inputStream);

        ConstantInfo value = null;
        switch (tag.getValue()) {
            case ConstantTag.CLASS:
                value = new ConstantClassInfo();
                break;
            case ConstantTag.DOUBLE:
                value = new ConstantDoubleInfo();
                break;
            case ConstantTag.FIELD_REF:
                value = new ConstantFieldRefInfo();
                break;
            case ConstantTag.FLOAT:
                value = new ConstantFloatInfo();
                break;
            case ConstantTag.INTEGER:
                value = new ConstantIntegerInfo();
                break;
            case ConstantTag.INTERFACE_METHOD_REF:
                value = new ConstantInterfaceMethodRefInfo();
                break;
            case ConstantTag.LONG:
                value = new ConstantLongInfo();
                break;
            case ConstantTag.METHOD_REF:
                value = new ConstantMethodRefInfo();
                break;
            case ConstantTag.NAME_AND_TYPE:
                value = new ConstantNameAndTypeInfo();
                break;
            case ConstantTag.STRING:
                value = new ConstantStringInfo();
                break;
            case ConstantTag.UTF8:
                value = new ConstantUtf8Info();
                break;
            default:
                throw new RuntimeException("未知的常量类型: " + tag.getValue());
        }
        if (null != value) {
            value.tagU1 = tag;
            value.index = index;
            value.read(inputStream);
        }
        return value;
    }

    public short getIndex() {
        return index;
    }

    public byte getTag() {
        return tagU1.getValue();
    }

    protected abstract void buildString(StringBuilder builder);

    public String displayInfo() {
        StringBuilder builder = new StringBuilder();
        builder.append("{ \n")
                .append("\t tag: ").append(getTag()).append(", \n")
                .append("\t index: ").append(getIndex()).append(", \n");
        buildString(builder);
        builder.append("}");
        return builder.toString();
    }
}
