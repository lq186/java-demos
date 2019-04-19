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
    FileName: StackMapFrame.java
    Date: 2019/4/19
    Author: lq
*/
package com.lq186.tools.decompiler.reader.attribute.frame;

import com.lq186.tools.decompiler.reader.IReader;
import com.lq186.tools.decompiler.reader.basic.U1;

import java.io.IOException;
import java.io.InputStream;

public abstract class StackMapFrame implements IReader {

    private U1 frameTypeU1;

    public static final StackMapFrame getInstance(InputStream inputStream) throws IOException {
        final U1 frameTypeU1 = new U1();
        frameTypeU1.read(inputStream);

        final byte frameType = frameTypeU1.getValue();
        StackMapFrame stackMapFrame = null;
        if (0 <= frameType && frameType <= 63) {
            stackMapFrame = new SameFrame();
        } else if (64 <= frameType && frameType <= 127) {
            stackMapFrame = new SameLocals1StackItemFrame();
        } else {
            throw new RuntimeException("未知的 frame type");
        }

        if (null != stackMapFrame) {
            stackMapFrame.frameTypeU1 = frameTypeU1;
            stackMapFrame.read(inputStream);
        }

        return stackMapFrame;
    }

    public byte getFrameType() {
        return frameTypeU1.getValue();
    }

}
