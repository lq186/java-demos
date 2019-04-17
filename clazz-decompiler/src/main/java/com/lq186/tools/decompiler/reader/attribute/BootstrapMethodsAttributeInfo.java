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
    FileName: BootstrapMethodsAttributeInfo.java
    Date: 2019/4/17
    Author: lq
*/
package com.lq186.tools.decompiler.reader.attribute;

import com.lq186.tools.decompiler.reader.IReader;
import com.lq186.tools.decompiler.reader.basic.U2;

import java.io.IOException;
import java.io.InputStream;

public final class BootstrapMethodsAttributeInfo extends AttributeInfo {

    private final U2 bootstrapMethodsCountU2 = new U2();

    private BootstrapMethod[] bootstrapMethods;

    @Override
    public void read(InputStream inputStream) throws IOException {
        bootstrapMethodsCountU2.read(inputStream);

        final short bootstrapMethodsCount = bootstrapMethodsCountU2.getValue();
        bootstrapMethods = new BootstrapMethod[bootstrapMethodsCount];
        for (short i = 0; i < bootstrapMethodsCount; ++i) {
            final BootstrapMethod bootstrapMethod = new BootstrapMethod();
            bootstrapMethod.read(inputStream);
            bootstrapMethods[i] = bootstrapMethod;
        }
    }

    public short getBootstrapMethodsCount() {
        return bootstrapMethodsCountU2.getValue();
    }

    public BootstrapMethod[] getBootstrapMethods() {
        return bootstrapMethods;
    }

    @Override
    protected void buildString(StringBuilder builder) {
        builder.append("\t bootstrap methods count: ").append(getBootstrapMethodsCount()).append(", \n");
        if (0 < getBootstrapMethodsCount()) {
            builder.append("\t bootstrap methods: { \n");
            for (BootstrapMethod bootstrapMethod : bootstrapMethods) {
                builder.append("\t\t method ref index: ").append(bootstrapMethod.getBootstrapMethodRefIndex()).append(", \n");
                builder.append("\t\t method arguments count: ").append(bootstrapMethod.getBootstrapMethodArgumentsCount()).append(", \n");
                builder.append("\t\t method arguments: ").append(bootstrapMethod.getBootstrapArguments()).append("\n");
            }
            builder.append("\t }");
        }
    }

    public static class BootstrapMethod implements IReader {

        private final U2 bootstrapMethodRefIndexU2 = new U2();

        private final U2 bootstrapMethodArgumentsCountU2 = new U2();

        private final U2 bootstrapArgumentsU2 = new U2();

        @Override
        public void read(InputStream inputStream) throws IOException {
            bootstrapMethodRefIndexU2.read(inputStream);
            bootstrapMethodArgumentsCountU2.read(inputStream);
            bootstrapArgumentsU2.read(inputStream);
        }

        public short getBootstrapMethodRefIndex() {
            return bootstrapMethodRefIndexU2.getValue();
        }

        public short getBootstrapMethodArgumentsCount() {
            return bootstrapMethodArgumentsCountU2.getValue();
        }

        public short getBootstrapArguments() {
            return bootstrapArgumentsU2.getValue();
        }
    }
}
