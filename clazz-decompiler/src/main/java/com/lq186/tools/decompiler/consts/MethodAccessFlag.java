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
    FileName: MethodAccessFlag.java
    Date: 2019/4/17
    Author: lq
*/
package com.lq186.tools.decompiler.consts;

public enum MethodAccessFlag {

    PUBLIC((short) 0x0001, "public"),

    PRIVATE((short) 0x0002, "private"),

    PROTECTED((short) 0x0004, "protected"),

    STATIC((short) 0x0008, "static"),

    FINAL((short) 0x0010, "final"),

    SYNCHRONIZED((short) 0x0020, "synchronized"),

    // 是否是有编译器产生的桥接方法
    BRIDGE((short) 0x0040, "bridge"),

    // 是否接受不定参数
    VARARGS((short) 0x0080, "varargs"),

    NATIVE((short) 0x0100, "native"),

    ABSTRACT((short) 0x0400, "abstract"),

    // 是否为 strictfp
    STRICTFP((short) 0x0800, "strictfp"),

    // 是否为编译器自己主动产生
    SYNTHETIC((short) 0x1000, "synthetic");

    private final short value;

    private final String text;

    MethodAccessFlag(short value, String text) {
        this.value = value;
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public short getValue() {
        return value;
    }
}
