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
    FileName: DoubleCheckSingleton.java
    Date: 2019/4/8
    Author: lq
*/
package com.lq186.demo.designpattern.singleton;

/**
 * 双重检查(推荐)
 */
public final class DoubleCheckSingleton {

    private static DoubleCheckSingleton instance;

    /**
     * 私有构造
     */
    private DoubleCheckSingleton() {
        MockSleep.sleep();
    }

    /**
     * 双重检查获取单例
     *
     * @return
     */
    public static final DoubleCheckSingleton getInstance() {
        if (null == instance) {
            synchronized (DoubleCheckSingleton.class) {
                if (null == instance) {
                    instance = new DoubleCheckSingleton();
                }
            }
        }
        return instance;
    }

}
