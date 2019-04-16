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
    Date: 2019/4/16
    Author: lq
*/
package com.lq186.tools.decompiler.reader;

import com.lq186.tools.decompiler.reader.basic.U2;
import com.lq186.tools.decompiler.reader.basic.U4;
import com.lq186.tools.decompiler.reader.constantpool.ConstantPool;

import java.io.IOException;
import java.io.InputStream;

public final class ClassFileReader implements IReader {

    private final U4 magicU4 = new U4();

    private final U2 minorVersionU2 = new U2();

    private final U2 majorVersionU2 = new U2();

    private final ConstantPool constantPool = new ConstantPool();

    @Override
    public void read(InputStream inputStream) throws IOException {
        magicU4.read(inputStream);
        minorVersionU2.read(inputStream);
        majorVersionU2.read(inputStream);
        constantPool.read(inputStream);
    }

    public String getMagic() {
        return magicU4.toHex();
    }

    public short getMinorVersion() {
        return minorVersionU2.getValue();
    }

    public short getMajorVersion() {
        return majorVersionU2.getValue();
    }

    public ConstantPool getConstantPool() {
        return constantPool;
    }
}
