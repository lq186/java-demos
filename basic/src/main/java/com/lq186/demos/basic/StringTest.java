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
    FileName: StringTest.java
    Date: 2019/5/10
    Author: lq
*/
package com.lq186.demos.basic;

import org.junit.Test;

public final class StringTest {

    @Test
    public void test01() {
        String str01 = "abc";
        String str02 = new String("abc");
        System.out.printf("str01 == str02 ? %s\n", str01 == str02); // false

        String str03 = "abc";
        System.out.printf("str02 == str03 ? %s\n", str02 == str03); // false

        System.out.printf("str01 == str03 ? %s\n", str01 == str03); // true
    }

}
