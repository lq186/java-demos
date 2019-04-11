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
    FileName: package-info.java
    Date: 2019/4/11
    Author: lq
*/
package com.lq186.demo.designpattern.adapter;

/**
 * 适配器模式
 * <p>
 * 优点:
 * (1) 将目标类和适配者类解耦, 通过引入一个适配器类来重用现有适配者类, 不需要修改原有结构;
 * (2) 增加了类的复用性和透明性, 将具体的业务实现过程封装在适配者类中, 对与使用者类来说是透明的, 而且提高了适配者类的复用性, 同一适配者
 * 类可以在不同的系统中复用;
 * (3) 灵活性和扩展性比较好, 可以很方便的更换适配器类, 可以在不修改代码的基础上增加新的适配器, 符合[开闭原则];
 * <p>
 * 缺点:
 * (1) 一次只能适配一个适配者类, 不能同时适配多个;
 * (2) 目标类只能为接口
 * <p>
 * 适用环境:
 * 系统已经存在一些类, 需要复用, 但是这些类的接口不符合现有的系统;
 * 解耦目标类和适配者类, 可以方便的更换适配者或适配器类
 * <p>
 * 参考网址:
 * https://www.cnblogs.com/songyaqi/p/4805820.html
 */