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
    FileName: StaticBlockSingleton.java
    Date: 2019/4/8
    Author: lq
*/
package com.lq186.demo.designpattern.singleton;

/**
 * 静态代码块单例(饿汉式)
 */
public final class StaticBlockSingleton {

    /**
     * 推迟到类初始化的时候初始化单例实例
     */
    private final static StaticBlockSingleton INSTANCE;

    static {
        INSTANCE = new StaticBlockSingleton();
    }

    /**
     * 私有构造函数
     */
    private StaticBlockSingleton() {
        MockSleep.sleep();
    }

    public static final StaticBlockSingleton getInstance() {
        return INSTANCE;
    }
}
