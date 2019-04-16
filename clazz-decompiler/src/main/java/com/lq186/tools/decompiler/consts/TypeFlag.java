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
    FileName: TypeFlag.java
    Date: 2019/4/16
    Author: lq
*/
package com.lq186.tools.decompiler.consts;

public final class TypeFlag {

    public static final char BYTE = 'B';

    public static final char CHAR = 'C';

    public static final char DOUBLE = 'D';

    public static final char FLOAT = 'F';

    public static final char INT = 'I';

    public static final char LONG = 'J';

    public static final char SHORT = 'S';

    public static final char BOOLEAN = 'Z';

    // 特殊类型 void
    public static final char VOID = 'V';

    // 对象类型
    public static final char OBJECT = 'L';

    // 指向常量池的索引
    public static final int STRING = 's';

    // type_name_index(2bytes): 枚举类型
    // const_name_index(2bytes): 枚举值
    public static final int ENUM = 'e';

    // class_info_index
    public static final int CLASS = 'c';

    public static final int ANNOTATION = '@';

    public static final int ARRAY = '[';
}
