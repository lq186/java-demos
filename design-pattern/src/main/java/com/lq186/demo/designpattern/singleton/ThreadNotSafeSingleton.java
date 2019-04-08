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
    FileName: ThreadNotSafeSingleton.java
    Date: 2019/4/8
    Author: lq
*/
package com.lq186.demo.designpattern.singleton;

/**
 * 线程不安全的单例，测试对比使用(不可用)
 */
public final class ThreadNotSafeSingleton {

    private static ThreadNotSafeSingleton instance;

    private ThreadNotSafeSingleton() {
        try {
            Thread.sleep(100L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static final ThreadNotSafeSingleton getInstance() {
        if (null == instance) {
            instance = new ThreadNotSafeSingleton();
        }
        return instance;
    }

}
