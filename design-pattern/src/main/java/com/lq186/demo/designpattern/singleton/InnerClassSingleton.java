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
    FileName: InnerClassSingleton.java
    Date: 2019/4/8
    Author: lq
*/
package com.lq186.demo.designpattern.singleton;

/**
 * 内部类方式(推荐使用)
 */
public final class InnerClassSingleton {

    /**
     * 私有构造
     */
    private InnerClassSingleton() {
        MockSleep.sleep();
    }

    /**
     * 采用类的加载保证单例初始化的唯一
     *
     * @return
     */
    public static final InnerClassSingleton getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * 静态私有内部类
     */
    private static class SingletonHolder {

        // 内部类持有单例类的静态常量
        private static final InnerClassSingleton INSTANCE = new InnerClassSingleton();

    }
}
