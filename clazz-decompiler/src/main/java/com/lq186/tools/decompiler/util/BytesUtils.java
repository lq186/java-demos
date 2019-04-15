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
    FileName: BytesUtils.java
    Date: 2019/4/15
    Author: lq
*/
package com.lq186.tools.decompiler.util;

public final class BytesUtils {

    public static final byte[] fromInt(final int value) {
        int temp = value;
        final int length = 4;
        byte[] bytes = new byte[length];
        for (int i = 0; i < length; ++i) {
            bytes[i] = new Integer(temp & 0xff).byteValue();
            temp = temp >> 8; // 右移8位
        }
        return bytes;
    }

    public static final int toInt(final byte[] bytes) {
        final int length = bytes.length;
        int[] results = new int[length];
        int result = 0;
        for (int i = 0; i < length; ++i) {
            results[i] = bytes[i] & 0xff;
            if (i > 0) {
                results[i] = results[i] << i * 8;
                result = result | results[i];
            } else {
                result = results[i];
            }
        }
        return result;
    }

    public static final byte[] fromLong(long value) {
        long temp = value;
        final int length = 8;
        byte[] bytes = new byte[length];
        for (int i = 0; i < length; ++i) {
            bytes[i] = new Long(temp & 0xff).byteValue();
            temp = temp >> 8;
        }
        return bytes;
    }

    public static final long toLong(byte[] bytes) {
        final int length = bytes.length;
        long[] results = new long[length];
        long result = 0;
        for (int i = 0; i < length; ++i) {
            results[i] = bytes[i] & 0xff;
            if (i > 0) {
                results[i] = results[i] << i * 8;
                result = result | results[i];
            } else {
                result = results[i];
            }
        }
        return result;
    }

    public static final byte[] fromFloat(float value) {
        int intValue = Float.floatToIntBits(value);
        return fromInt(intValue);
    }

    public static final float toFloat(byte[] bytes) {
        int intValue = toInt(bytes);
        return Float.intBitsToFloat(intValue);
    }

    public static final byte[] fromDouble(double value) {
        long longValue = Double.doubleToLongBits(value);
        return fromLong(longValue);
    }

    public static final double toDouble(byte[] bytes) {
        long longValue = toLong(bytes);
        return Double.longBitsToDouble(longValue);
    }
}
