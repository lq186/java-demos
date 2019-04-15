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
    FileName: ClassFileReader.java
    Date: 2019/4/15
    Author: lq
*/
package com.lq186.tools.decompiler;

import com.lq186.tools.decompiler.util.BytesUtils;
import org.apache.commons.codec.binary.Hex;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public final class ClassFileReader {

    private static final String CLASS_FILE_NAME = "D:\\Temp\\TestClassLoad.class";

    private static final byte[] u8 = new byte[8];

    private static final byte[] u4 = new byte[4];

    private static final byte[] u2 = new byte[2];

    private static final byte[] u1 = new byte[1];

    public static void main(String[] args) throws FileNotFoundException, IOException {

        try (InputStream inputStream = new FileInputStream(CLASS_FILE_NAME)) {
            inputStream.read(u4);
            String magic = Hex.encodeHexString(u4);
            System.out.println("magic -> 0x" + magic);

            inputStream.read(u2);
            int minorVersion = toInt(u2);
            System.out.println("minor version -> " + minorVersion);

            inputStream.read(u2);
            int majorVersion = toInt(u2);
            System.out.println("major version -> " + majorVersion);

            inputStream.read(u2);
            int constantPoolCount = toInt(u2);
            System.out.println("constant pool count -> " + constantPoolCount);

            Map<Integer, String> constantPoolMap = new HashMap<>();
            for (int i = 1; i < constantPoolCount; ++i) {
                readConstantPool(inputStream);
            }
        }

    }

    private static final void readConstantPool(InputStream inputStream) throws IOException {
        inputStream.read(u1);
        int constantTag = toInt(u1);
        System.out.println("constant flag -> " + constantTag);

        switch (constantTag) {
            case ConstantInfo.UTF8:
                inputStream.read(u2);
                int length = toInt(u2);
                System.out.println("utf-8 缩略编码字符串占用字节数 -> " + length);
                byte[] bytes = new byte[length];
                inputStream.read(bytes);
                System.out.printf("长度为[%d]的utf-8缩略编码字符串 -> %s \n", length, new String(bytes));
                break;
            case ConstantInfo.INTEGER:
                inputStream.read(u4);
                int intValue = BytesUtils.toInt(u4);
                System.out.println("int value -> " + intValue);
                break;
            case ConstantInfo.FLOAT:
                inputStream.read(u4);
                float floatValue = BytesUtils.toFloat(u4);
                System.out.println("float value -> " + floatValue);
                break;
            case ConstantInfo.LONG:
                inputStream.read(u8);
                long longValue = BytesUtils.toLong(u8);
                System.out.println("long value -> " + longValue);
                break;
            case ConstantInfo.DOUBLE:
                inputStream.read(u8);
                double doubleValue = BytesUtils.toDouble(u8);
                System.out.println("double value -> " + doubleValue);
                break;
            case ConstantInfo.CLASS:
                inputStream.read(u2);
                int classIndex = toInt(u2);
                System.out.println("class index -> " + classIndex);
                break;
            case ConstantInfo.STRING:
                inputStream.read(u2);
                int stringIndex = toInt(u2);
                System.out.println("string index -> " + stringIndex);
                break;
            case ConstantInfo.FIELD_REF:
            case ConstantInfo.METHOD_REF:
            case ConstantInfo.INTERFACE_METHOD_REF:
                inputStream.read(u2);
                int classIndex2 = toInt(u2);
                System.out.println("field ref / method ref / interface method ref [class index] -> " + classIndex2);
                inputStream.read(u2);
                int nameAndTypeIndex2 = toInt(u2);
                System.out.println("field ref / method ref / interface method ref [name and type index] -> " + nameAndTypeIndex2);
                break;
            case ConstantInfo.NAME_AND_TYPE:
                inputStream.read(u2);
                int nameIndex = toInt(u2);
                System.out.println("name and type [name index] -> " + nameIndex);
                inputStream.read(u2);
                int descIndex = toInt(u2);
                System.out.println("name and type [desc index] -> " + descIndex);
                break;
            default:
                throw new RuntimeException("未知的常量类型 -> " + constantTag);
        }
    }

    private static final int toInt(byte[] bytes) {
        return Integer.parseInt(Hex.encodeHexString(bytes), 16);
    }

}
