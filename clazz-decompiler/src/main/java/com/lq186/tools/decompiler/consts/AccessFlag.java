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
    FileName: AccessFlag.java
    Date: 2019/4/16
    Author: lq
*/
package com.lq186.tools.decompiler.consts;

public final class AccessFlag {

    public static class Class {
        public static final int PUBLIC = 0x0001;

        public static final int FINAL = 0x0010;

        public static final int SUPER = 0x0020;

        public static final int INTERFACE = 0x0200;

        public static final int ABSTRACT = 0x0400;

        // 标识这个类不是由用户代码产生
        public static final int SYNTHETIC = 0x1000;

        // 标识这是一个注解
        public static final int ANNOTATION = 0x2000;

        // 标识这是一个枚举
        public static final int ENUM = 0x4000;
    }

    public static class Field {
        public static final int PUBLIC = 0x0001;

        public static final int PRIVATE = 0x0002;

        public static final int PROTECTED = 0x0004;

        public static final int STATIC = 0x0008;

        public static final int FINAL = 0x0010;

        public static final int VOLATILE = 0x0040;

        public static final int TRANSIENT = 0x0080;

        // 是否为编译器自己主动产生
        public static final int SYNTHETIC = 0x1000;

        public static final int ENUM = 0x4000;
    }

    public static class Method {
        public static final int PUBLIC = 0x0001;

        public static final int PRIVATE = 0x0002;

        public static final int PROTECTED = 0x0004;

        public static final int STATIC = 0x0008;

        public static final int FINAL = 0x0010;

        public static final int SYNCHRONIZED = 0x0020;

        // 是否是有编译器产生的桥接方法
        public static final int BRIDGE = 0x0040;

        // 是否接受不定参数
        public static final int VARARGS = 0x0080;

        public static final int NATIVE = 0x0100;

        public static final int ABSTRACT = 0x0400;

        // 是否为 strictfp
        public static final int STRICTFP = 0x0800;

        // 是否为编译器自己主动产生
        public static final int SYNTHETIC = 0x1000;

    }

    public static class InnerClass {

        public static final int PIBLIC = 0x0001;

        public static final int PRIVATE = 0x0002;

        public static final int PROTECTED = 0x0004;

        public static final int STATIC = 0x0008;

        public static final int FINAL = 0x0010;

        public static final int INTERFACE = 0x0020;

        public static final int ABSTRACT = 0x0400;

        // 编译器主动产生
        public static final int SYNTHETIC = 0x1000;

        public static final int ANNOTATION = 0x4000;

        public static final int ENUM = 0x4000;
    }
}
