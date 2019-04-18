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
    FileName: AttributeInfo.java
    Date: 2019/4/17
    Author: lq
*/
package com.lq186.tools.decompiler.reader.attribute;

import com.lq186.tools.decompiler.consts.AttributeName;
import com.lq186.tools.decompiler.reader.IReader;
import com.lq186.tools.decompiler.reader.basic.U2;
import com.lq186.tools.decompiler.reader.basic.U4;
import com.lq186.tools.decompiler.reader.constantpool.ConstantInfo;
import com.lq186.tools.decompiler.reader.constantpool.ConstantUtf8Info;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

public abstract class AttributeInfo implements IReader {

    private U2 attributeNameIndexU2;

    private U4 attributeLengthU4;

    private ConstantInfo[] constantInfos;

    public static final AttributeInfo getInstance(InputStream inputStream, final ConstantInfo[] constantInfos) throws IOException {
        final U2 attributeNameIndexU2 = new U2();
        attributeNameIndexU2.read(inputStream);
        final short attributeNameIndex = attributeNameIndexU2.getValue();
        final String attributeName = ((ConstantUtf8Info) constantInfos[attributeNameIndex]).getUtf8String();

        final U4 attributeLengthU4 = new U4();
        attributeLengthU4.read(inputStream);

        AttributeInfo attributeInfo = null;
        switch (attributeName) {
            case AttributeName.CODE:
                attributeInfo = new CodeAttributeInfo();
                break;
            case AttributeName.BOOTSTRAP_METHODS:
                attributeInfo = new BootstrapMethodsAttributeInfo();
                break;
            case AttributeName.CONSTANT_VALUE:
                attributeInfo = new ConstantValueAttributeInfo();
                break;
            case AttributeName.DEPRECATED:
                attributeInfo = new DeprecatedAttributeInfo();
                break;
            case AttributeName.EXCEPTIONS:
                attributeInfo = new ExceptionsAttributeInfo();
                break;
            case AttributeName.INNER_CLASSES:
                attributeInfo = new InnerClassesAttributeInfo();
                break;
            case AttributeName.LINE_NUMBER_TABLE:
                attributeInfo = new LineNumberTableAttributeInfo();
                break;
            case AttributeName.LOCAL_VARIABLE_TABLE:
                attributeInfo = new LocalVariableTableAttributeInfo();
                break;
            case AttributeName.LOCAL_VARIABLE_TYPE_TABLE:
                attributeInfo = new LocalVariableTypeTableAttributeInfo();
                break;
            case AttributeName.SIGNATURE:
                attributeInfo = new SignatureAttributeInfo();
                break;
            case AttributeName.SOURCE_FILE:
                attributeInfo = new SourceFileAttributeInfo();
                break;
            case AttributeName.SYNTHETIC:
                attributeInfo = new SyntheticAttributeInfo();
                break;
            default:
                throw new RuntimeException("未知的属性名称: " + attributeName);
        }
        if (null != attributeInfo) {
            attributeInfo.attributeNameIndexU2 = attributeNameIndexU2;
            attributeInfo.constantInfos = constantInfos;
            attributeInfo.attributeLengthU4 = attributeLengthU4;
            attributeInfo.read(inputStream);
        }
        return attributeInfo;
    }

    public short getAttributeNameIndex() {
        return attributeNameIndexU2.getValue();
    }

    protected ConstantInfo[] getConstantInfos() {
        return constantInfos;
    }

    public int getAttributeLength() {
        return attributeLengthU4.getValue();
    }

    protected abstract void buildString(StringBuilder builder);

    public String displayInfo() {
        StringBuilder builder = new StringBuilder();
        builder.append("{ \n")
                .append("\t attribute name index: ").append(getAttributeNameIndex()).append(", \n");
        try {
            builder.append("\t attribute name: ").append(((ConstantUtf8Info) constantInfos[getAttributeNameIndex()]).getUtf8String()).append(", \n");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        builder.append("\t attribute length: ").append(getAttributeLength()).append(", \n");
        buildString(builder);
        builder.append("}");
        return builder.toString();
    }
}
