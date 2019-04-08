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
    FileName: SynchronizedSingleton.java
    Date: 2019/4/8
    Author: lq
*/
package com.lq186.demo.designpattern.singleton;

/**
 * 推迟到使用时初始化单例对象
 * 同步方法确保安全, 每次需要进行加锁(不推荐使用)
 */
public final class SynchronizedSingleton {

    private static SynchronizedSingleton instance;

    /**
     * 私有构造函数
     */
    private SynchronizedSingleton() {
        MockSleep.sleep();
    }

    /**
     * 同步方法，确保单例
     *
     * @return
     */
    public static final synchronized SynchronizedSingleton getInstance() {
        if (null == instance) {
            instance = new SynchronizedSingleton();
        }
        return instance;
    }
}
