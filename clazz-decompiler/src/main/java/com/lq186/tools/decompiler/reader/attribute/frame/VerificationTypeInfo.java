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
    FileName: VerificationTypeInfo.java
    Date: 2019/4/19
    Author: lq
*/
package com.lq186.tools.decompiler.reader.attribute.frame;

import com.lq186.tools.decompiler.consts.VerificationTypeInfoTag;
import com.lq186.tools.decompiler.reader.IReader;
import com.lq186.tools.decompiler.reader.basic.U1;

import java.io.IOException;
import java.io.InputStream;

public abstract class VerificationTypeInfo implements IReader {

    private U1 tagU1;

    public static final VerificationTypeInfo getInstance(InputStream inputStream) throws IOException {
        final U1 tagU1 = new U1();
        tagU1.read(inputStream);

        final byte tag = tagU1.getValue();
        VerificationTypeInfo verificationTypeInfo = null;
        switch (tag) {
            case VerificationTypeInfoTag.ITEM_TOP:
                verificationTypeInfo = new TopVariableInfo();
                break;
            case VerificationTypeInfoTag.ITEM_INTEGER:
                verificationTypeInfo = new IntegerVariableInfo();
                break;
            case VerificationTypeInfoTag.ITEM_DOUBLE:
                verificationTypeInfo = new DoubleVariableInfo();
                break;
            case VerificationTypeInfoTag.ITEM_FLOAT:
                verificationTypeInfo = new FloatVariableInfo();
                break;
            case VerificationTypeInfoTag.ITEM_LONG:
                verificationTypeInfo = new LongVariableInfo();
                break;
            case VerificationTypeInfoTag.ITEM_NULL:
                verificationTypeInfo = new NullVariableInfo();
                break;
            case VerificationTypeInfoTag.ITEM_UNINITIALIZED_THIS:
                verificationTypeInfo = new UninitializedThisVariableInfo();
                break;
            case VerificationTypeInfoTag.ITEM_OBJECT:
                verificationTypeInfo = new ObjectVariableInfo();
                break;
            case VerificationTypeInfoTag.ITEM_UNINITIALIZED:
                verificationTypeInfo = new UninitializedVariableInfo();
                break;
            default:
                throw new RuntimeException("未知的verification type info tag");
        }
        if (null != verificationTypeInfo) {
            verificationTypeInfo.tagU1 = tagU1;
            verificationTypeInfo.read(inputStream);
        }
        return verificationTypeInfo;
    }

    public byte getTag() {
        return tagU1.getValue();
    }
}
