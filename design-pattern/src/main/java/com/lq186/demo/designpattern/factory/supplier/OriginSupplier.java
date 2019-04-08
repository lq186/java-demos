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
    FileName: OriginSupplier.java
    Date: 2019/4/8
    Author: lq
*/
package com.lq186.demo.designpattern.factory.supplier;

import com.lq186.demo.designpattern.factory.ISupplier;

public class OriginSupplier implements ISupplier {

    private static final String ORIGIN = "橘子";

    @Override
    public String supply() {
        System.out.printf("这是[%s]提供商\n", ORIGIN);
        return ORIGIN;
    }
}
