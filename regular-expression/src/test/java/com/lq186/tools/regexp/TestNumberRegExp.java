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
    FileName: TestNumberRegExp.java
    Date: 2019/4/12
    Author: lq
*/
package com.lq186.tools.regexp;

import org.junit.Test;

import java.util.function.Function;

public class TestNumberRegExp {

    @Test
    public void testIsMoney() {
        final String regexType = "money format";
        final Function<String, Boolean> func = text -> NumberRegExp.isMoney(text, true);
        test("-2", regexType, func);
        test("2", regexType, func);
        test("2.", regexType, func);
        test("2.0", regexType, func);
        test("2.01", regexType, func);
        test("2.013", regexType, func);
    }

    @Test
    public void testIsNumber() {
        final String regexType = "number format";
        final Function<String, Boolean> func = NumberRegExp::isNumber;
        test("+1", regexType, func);
        test("-1", regexType, func);
        test("1", regexType, func);
        test("1.", regexType, func);
        test("-1.", regexType, func);
        test("1.0", regexType, func);
        test("-1.0", regexType, func);
    }

    @Test
    public void testIsPhone() {
        final String regexType = "phone number format";
        final Function<String, Boolean> func = text -> NumberRegExp.isPhone(text, true);
        test("18612979110", regexType, func);
        test("12345678902", regexType, func);
    }

    @Test
    public void testEmail() {
        final String regexType = "email number format";
        final Function<String, Boolean> func = NumberRegExp::isEmail;
        test("b08050427@sina.cn", regexType, func);
        test("b08050427@sina@sina.com", regexType, func);
    }

    private void test(String text, String regexType, Function<String, Boolean> func) {
        System.out.printf("[%s] is %s ? -> %s \n", text, regexType, func.apply(text));
    }
}
