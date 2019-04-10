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
    Date: 2019/4/10
    Author: lq
*/
package com.lq186.demo.designpattern.prototype;

/**
 * 原型模式(Prototype Pattern)用于创建重复的对象，并能保证性能。提供了一种创建对象的方式。
 * <p>
 * 创建对象的几种方式:
 * (1) 使用 new 关键字, 会显式的调用构造函数
 * (2) 使用 clone 方法, 不会调用构造函数
 * (3) 使用反射机制, 会显式的调用构造函数
 * (4) 使用反序列化, 不会调用构造函数
 * <p>
 * 参考网址:
 * https://www.cnblogs.com/lfxiao/p/6812835.html
 * 主要优点:
 * (1) 当创建新的对象实例比较复杂时, 使用原型模式可以简化对象的创建过程, 通过复制一个已有实例可以提高新实例的创建效率;
 * (2) 扩展性较好, 由于在原型模式中提供了抽象原型类, 在客户端可以针对抽象原型类进行编程, 而将具体原型类写在配置文件中,
 * 增加或减少产品类对原有系统都没有任何影响;
 * (3) 原型提供了简化的创建结构, 工厂方法模式常需要有一个产品类等级结构相同的工厂等级结构, 原型模式中产品的复制是通过
 * 封装在原型类中的克隆方法实现的, 无需专门的工厂类来创建产品;
 * (4) 可以使用深克隆的方式保存对象的状态, 使用原型模式将对象复制一份并将其对象保存起来, 以便在需要的使用使用。如恢复到
 * 某一历史状态, 可辅助实现撤销操作。
 * <p>
 * 主要缺点:
 * (1) 需要为每一个类配备一个克隆方法, 而且该克隆方法位于一个类的内部, 当对已有的类进行改造时, 需要修改源码，违背了开闭原则。
 * (2) 需要实现深克隆时需要编写较为复杂的代码, 而且当对象之间存在多层的嵌套引用时, 为了实现深克隆, 每一层对象对应的类都要实现深克隆,
 * 实现比较麻烦。
 * <p>
 * 适用场景:
 * (1) 创建新对象成本比较大(如初始化需要占用较长的时间, 较多的CPU资源或网络资源), 新的对象可以通过原型模式对已有对象复制得到,
 * 如果是相似对象, 可以对其成员变量进行修改;
 * (2) 如果系统要保存对象的状态, 且对象的状态变化很小, 或者对象本身占用内存较小, 可以使用原型模式配合备忘录模式实现;
 * (3) 需要避免使用分层次的工厂类来创建分层次的对象, 并且类的实例对象只有一个或很少的几个组合状态, 通过原型模式获取新实例可能
 * 比使用构造函数创建新实例更加方便。
 */