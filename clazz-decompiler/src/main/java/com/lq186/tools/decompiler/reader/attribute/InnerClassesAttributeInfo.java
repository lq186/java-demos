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
    FileName: InnerClassesAttributeInfo.java
    Date: 2019/4/17
    Author: lq
*/
package com.lq186.tools.decompiler.reader.attribute;

import com.lq186.tools.decompiler.reader.IReader;
import com.lq186.tools.decompiler.reader.basic.U2;

import java.io.IOException;
import java.io.InputStream;

public final class InnerClassesAttributeInfo extends AttributeInfo {

    private final U2 classesCountU2 = new U2();

    private InnerClassInfo[] innerClassInfos;

    @Override
    public void read(InputStream inputStream) throws IOException {
        classesCountU2.read(inputStream);
        final short classesCount = getClassesCount();
        innerClassInfos = new InnerClassInfo[classesCount];
        for (short i = 0; i < classesCount; ++i) {
            final InnerClassInfo classInfo = new InnerClassInfo();
            classInfo.read(inputStream);
            innerClassInfos[i] = classInfo;
        }

    }

    @Override
    protected void buildString(StringBuilder builder) {
        final short classesCount = getClassesCount();
        builder.append("\t classes count: ").append(classesCount).append(", \n");
        if (classesCount > 0) {
            builder.append("\t classes: [ \n");

            InnerClassInfo classInfo;
            for (short i = 0; i < classesCount; ++i) {
                classInfo = innerClassInfos[i];
                builder.append("\t\t { \n");
                builder.append("\t\t\t class index: ").append(classInfo.getClassIndex()).append(", \n");
                builder.append("\t\t\t outer class index: ").append(classInfo.getOuterClassIndex()).append(", \n");
                builder.append("\t\t\t name index: ").append(classInfo.getNameIndex()).append(", \n");
                builder.append("\t\t\t access flags: ").append(classInfo.getAccessFlags()).append("\n");
                builder.append("\t\t }").append(i < (classesCount - 1) ? ", \n" : "\n");
            }

            builder.append("\t ], \n");
        }
    }

    public short getClassesCount() {
        return classesCountU2.getValue();
    }


    public static class InnerClassInfo implements IReader {

        private final U2 classIndexU2 = new U2();

        private final U2 outerClassIndexU2 = new U2();

        private final U2 nameIndexU2 = new U2();

        private final U2 accessFlagsU2 = new U2();

        @Override
        public void read(InputStream inputStream) throws IOException {
            classIndexU2.read(inputStream);
            outerClassIndexU2.read(inputStream);
            nameIndexU2.read(inputStream);
            accessFlagsU2.read(inputStream);
        }

        public short getClassIndex() {
            return classIndexU2.getValue();
        }

        public short getOuterClassIndex() {
            return outerClassIndexU2.getValue();
        }

        public short getNameIndex() {
            return nameIndexU2.getValue();
        }

        public short getAccessFlags() {
            return accessFlagsU2.getValue();
        }
    }
}
