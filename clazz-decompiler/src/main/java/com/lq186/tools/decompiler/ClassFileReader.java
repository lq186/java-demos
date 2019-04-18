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
    Date: 2019/4/15
    Author: lq
*/
package com.lq186.tools.decompiler;

import com.lq186.tools.decompiler.consts.AccessFlag;
import com.lq186.tools.decompiler.consts.ConstantTag;
import com.lq186.tools.decompiler.reader.ClassFile;
import com.lq186.tools.decompiler.reader.FieldInfo;
import com.lq186.tools.decompiler.reader.MethodInfo;
import com.lq186.tools.decompiler.reader.attribute.AttributeInfo;
import com.lq186.tools.decompiler.reader.constantpool.ConstantClassInfo;
import com.lq186.tools.decompiler.reader.constantpool.ConstantInfo;
import com.lq186.tools.decompiler.reader.constantpool.ConstantUtf8Info;
import com.lq186.tools.decompiler.util.BytesUtils;
import org.apache.commons.codec.binary.Hex;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public final class ClassFileReader {

    private static final String CLASS_FILE_NAME = "D:\\Temp\\TestClassLoad.class";

    public static void main(String[] args) throws FileNotFoundException, IOException {

        try (InputStream inputStream = new FileInputStream(CLASS_FILE_NAME)) {

            ClassFile classFile = new ClassFile();
            classFile.read(inputStream);

            System.out.println("magic -> " + classFile.getMagic());
            System.out.println("minor version -> " + classFile.getMinorVersion());
            System.out.println("major version -> " + classFile.getMajorVersion());

            short constantPoolCount = classFile.getConstantPool().getConstantPoolCount();
            System.out.println("constant pool count -> " + constantPoolCount);
            final ConstantInfo[] constantInfos = classFile.getConstantPool().getConstantInfos();
            if (null != constantInfos && constantInfos.length > 0) {
                for (short index = 1; index < constantPoolCount; ++index) {
                    System.out.println(constantInfos[index].displayInfo());
                }
            }

            final short fieldsCount = classFile.getFieldsCount();
            System.out.println("fields count -> " + fieldsCount);
            FieldInfo[] fieldInfos = classFile.getFieldInfos();
            for (short i = 0; i < fieldsCount; ++i) {
                System.out.println(fieldInfos[i].displayInfo());
            }

            final short methodsCount = classFile.getMethodsCount();
            System.out.println("methods count -> " + methodsCount);
            MethodInfo[] methodInfos = classFile.getMethodInfos();
            for (short i = 0; i < methodsCount; ++i) {
                System.out.println(methodInfos[i].displayInfo());
            }

            final short attributesCount = classFile.getAttributesCount();
            System.out.println("attributes count -> " + attributesCount);
            AttributeInfo[] attributeInfos = classFile.getAttributeInfos();
            for (short i = 0; i < attributesCount; ++i) {
                System.out.println(attributeInfos[i].displayInfo());
            }


            final short thisClassIndex = classFile.getThisClassIndex();
            ConstantInfo constantInfo = constantInfos[thisClassIndex];
            if (constantInfo instanceof ConstantClassInfo) {
                final short classNameIndex = ((ConstantClassInfo) constantInfo).getClassIndex();
                System.out.println("class name: " + ((ConstantUtf8Info) constantInfos[classNameIndex]).getUtf8String());
            }
            final String accessFlagText = AccessFlag.getClassAccessFlagsText(classFile.getAccessFlag());
            System.out.println("access flag text: " + accessFlagText);

            final short superClassIndex = classFile.getSupperClassIndex();
            constantInfo = constantInfos[superClassIndex];
            if (constantInfo instanceof ConstantClassInfo) {
                final short classNameIndex = ((ConstantClassInfo) constantInfo).getClassIndex();
                System.out.println("super class name: " + ((ConstantUtf8Info) constantInfos[classNameIndex]).getUtf8String());
            }

            for (int i = 1; i < constantInfos.length; ++i) {
                ConstantInfo cInfo = constantInfos[i];
                if (cInfo.getTag() == ConstantTag.CLASS) {
                    System.out.println("import " + ((ConstantUtf8Info) constantInfos[((ConstantClassInfo) cInfo).getClassIndex()]).getUtf8String());
                }
            }
        }
    }
}
