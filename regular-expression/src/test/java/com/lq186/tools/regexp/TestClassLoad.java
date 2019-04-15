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
    FileName: TestClassLoad.java
    Date: 2019/4/15
    Author: lq
*/
package com.lq186.tools.regexp;

public class TestClassLoad {

    public static void main(String[] args) {
        staticFunction();
    }

    static TestClassLoad testClassLoad = new TestClassLoad();

    static {
        System.out.println("1");
    }

    {
        System.out.println("2");
    }

    TestClassLoad() {
        System.out.println("3");
        System.out.printf("a = %d, b = %d\n", a, b);
    }

    public static void staticFunction() {
        System.out.println("4");
        System.out.printf("b = %d\n", b);
    }

    int a = 110;
    static int b = 119;


}
