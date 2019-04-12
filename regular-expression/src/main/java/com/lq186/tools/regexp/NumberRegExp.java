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
    FileName: NumberRegExp.java
    Date: 2019/4/12
    Author: lq
*/
package com.lq186.tools.regexp;

import java.util.regex.Pattern;

public final class NumberRegExp {

    // 工具类, 不允许实例化
    private NumberRegExp() {

    }

    public static final boolean isInteger(String text) {
        return matches("^[0-9]*$", text);
    }

    public static final boolean isIntegerWithLength(String text, int numberLength) {
        return matches(String.format("^\\d{%d}$", numberLength), text);
    }

    public static final boolean isIntegerWithMinLength(String text, int minLength) {
        return matches(String.format("^\\d{%d,}$", minLength), text);
    }

    public static final boolean isIntegerWithLengthBetween(String text, int minLength, int maxLength) {
        return matches(String.format("^\\d{%d,%d}$", minLength, maxLength), text);
    }

    public static final boolean isMoney(String text, boolean enableNegative) {
        final String regex = enableNegative ? "^(\\-)?[0-9]{1,}(\\.[0-9]{1,2})?$" :
                "^[0-9]{1,}(\\.[0-9]{1,2})?$";
        return matches(regex, text);
    }

    public static final boolean isNumber(String text) {
        return matches("^(\\-|\\+)?\\d+(\\.\\d+)?$", text);
    }

    public static final boolean isPhone(String text, boolean strictMode) {
        String regex = strictMode ? "^1([38][0-9]|4[579]|5[0-3,5-9]|6[6]|7[0135678]|9[89])\\d{8}$" :
                "^1[0-9]{10}$";
        return matches(regex, text);
    }

    public static final boolean isEmail(String text) {
        return matches("^[a-zA-Z0-9]{1,}@[a-zA-Z0-9]{1,}\\.[a-zA-Z0-9]{1,}", text);
    }

    public static final boolean matches(String regex, String text) {
        return Pattern.matches(regex, text);
    }

}
