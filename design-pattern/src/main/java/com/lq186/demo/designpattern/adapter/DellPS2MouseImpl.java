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
    FileName: DellPS2MouseImpl.java
    Date: 2019/4/11
    Author: lq
*/
package com.lq186.demo.designpattern.adapter;

public final class DellPS2MouseImpl implements IPS2Mouse {

    @Override
    public void displayPS2() {
        System.out.println("Dell PS2 type mouse.");
    }

}
