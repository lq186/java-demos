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
    FileName: TestBootstrap.java
    Date: 2019/4/11
    Author: lq
*/
package com.lq186.demo.designpattern.adapter;

public final class TestBootstrap {

    public static void main(String[] args) {
        // 已有一个 IPS2Mouse 的实现, 现在需要一个 IUSBMouse 的实现, 需要复用 IPS2Mouse 的实现
        IPS2Mouse ps2Mouse = new DellPS2MouseImpl();

        // 定义一个 USBMouseAdapter , 实现从 IPS2Mouse 到 IUSBMouse 的转换
        IUSBMouse usbMouse = new USBMouseAdapter(ps2Mouse);
        usbMouse.displayUSB();
    }

}
