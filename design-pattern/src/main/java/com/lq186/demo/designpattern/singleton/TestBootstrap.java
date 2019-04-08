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
    Date: 2019/4/8
    Author: lq
*/
package com.lq186.demo.designpattern.singleton;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

public final class TestBootstrap {

    private static final int MAX_THREAD = 10;

    public static void main(String[] args) {
        testSingleton(DoubleCheckSingleton::getInstance);

        testSingleton(InnerClassSingleton::getInstance);

        testSingleton(() -> EnumSingleton.INSTANCE);

        testSingleton(SynchronizedSingleton::getInstance);

        testSingleton(SimpleSingleton::getInstance);

        // 线程不安全，错误写法
        testSingleton(ThreadNotSafeSingleton::getInstance);
    }

    private static void testSingleton(Supplier<?> supplier) {
        long start = System.currentTimeMillis();
        final AtomicInteger atomicInteger = new AtomicInteger(0);
        testWithRunnable(() -> {
            Object instance = supplier.get();
            System.out.printf(">> %s instance: %s\n", instance.getClass().getSimpleName(), instance.toString());
            if (MAX_THREAD == atomicInteger.addAndGet(1)) {
                System.out.printf(">> %s 共用时：%d ms\n", instance.getClass().getSimpleName(), System.currentTimeMillis() - start);
            }
        });
    }

    private static void testWithRunnable(Runnable runnable) {
        Thread[] threads = new Thread[MAX_THREAD];
        for (int i = 0; i < MAX_THREAD; ++i) {
            Thread thread = new Thread(runnable);
            threads[i] = thread;
        }
        for (int i = 0; i < MAX_THREAD; ++i) {
            threads[i].start();
        }
    }

}
