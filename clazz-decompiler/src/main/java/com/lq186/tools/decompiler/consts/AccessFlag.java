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

    public static final String getClassAccessFlagsText(final short accessFlag) {
        final ClassAccessFlag[] flags = ClassAccessFlag.values();
        final StringBuilder builder = new StringBuilder();
        for (ClassAccessFlag flag : flags) {
            if (0 != (accessFlag & flag.getValue())) {
                builder.append(", ").append(flag.getText());
            }
        }
        String result = builder.toString();
        return result.length() > 0 ? result.substring(2) : result;
    }

    public static final String getFieldAccessFlagsText(final short accessFlag) {
        final FieldAccessFlag[] flags = FieldAccessFlag.values();
        final StringBuilder builder = new StringBuilder();
        for (FieldAccessFlag flag : flags) {
            if (0 != (accessFlag & flag.getValue())) {
                builder.append(", ").append(flag.getText());
            }
        }
        String result = builder.toString();
        return result.length() > 0 ? result.substring(2) : result;
    }

    public static final String getMethodAccessFlagsText(final short accessFlag) {
        final MethodAccessFlag[] flags = MethodAccessFlag.values();
        final StringBuilder builder = new StringBuilder();
        for (MethodAccessFlag flag : flags) {
            if (0 != (accessFlag & flag.getValue())) {
                builder.append(", ").append(flag.getText());
            }
        }
        String result = builder.toString();
        return result.length() > 0 ? result.substring(2) : result;
    }

    public static final String getInnerClassAccessFlagsText(final short accessFlag) {
        final InnerClassAccessFlag[] flags = InnerClassAccessFlag.values();
        final StringBuilder builder = new StringBuilder();
        for (InnerClassAccessFlag flag : flags) {
            if (0 != (accessFlag & flag.getValue())) {
                builder.append(", ").append(flag.getText());
            }
        }
        String result = builder.toString();
        return result.length() > 0 ? result.substring(2) : result;
    }
}
