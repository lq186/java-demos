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
    FileName: AbstractPcFactory.java
    Date: 2019/4/8
    Author: lq
*/
package com.lq186.demo.designpattern.factory.abst;

import com.lq186.demo.designpattern.factory.abst.keyboard.IKeyboard;
import com.lq186.demo.designpattern.factory.abst.mouse.IMouse;

/**
 * 抽象工厂，提供一般的共同的流程或者聚合的方法
 */
public abstract class AbstractPcFactory {

    // 制定共同的流程
    public IMouse createMouse() {
        System.out.println(">> 生产鼠标");
        IMouse mouse = doCreateMouse();
        System.out.println(">> 给鼠标贴牌");
        return mouse;
    }

    public IKeyboard createKeyboard() {
        System.out.println(">> 生产键盘");
        IKeyboard keyboard = doCreateKeyboard();
        System.out.println(">> 给键盘贴牌");
        return keyboard;
    }

    protected abstract IMouse doCreateMouse();

    protected abstract IKeyboard doCreateKeyboard();

}
