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
    FileName: InnerClassAccessFlag.java
    Date: 2019/4/17
    Author: lq
*/
package com.lq186.tools.decompiler.consts;

public enum InnerClassAccessFlag {

    PUBLIC((short) 0x0001, "public"),

    PRIVATE((short) 0x0002, "private"),

    PROTECTED((short) 0x0004, "protected"),

    STATIC((short) 0x0008, "static"),

    FINAL((short) 0x0010, "final"),

    INTERFACE((short) 0x0020, "interface"),

    ABSTRACT((short) 0x0400, "abstract"),

    // 编译器主动产生
    SYNTHETIC((short) 0x1000, "synthetic"),

    ANNOTATION((short) 0x2000, "annotation"),

    ENUM((short) 0x4000, "enum");

    private final short value;

    private final String text;

    InnerClassAccessFlag(short value, String text) {
        this.value = value;
        this.text = text;
    }

    public short getValue() {
        return value;
    }

    public String getText() {
        return text;
    }
}
