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
package com.lq186.demo.designpattern.factory;

import com.lq186.demo.designpattern.factory.provide.AppleSupplierProvider;
import com.lq186.demo.designpattern.factory.provide.CarSupplierProvider;
import com.lq186.demo.designpattern.factory.provide.OriginSupplierProvider;

public final class TestBootstrap {

    public static void main(String[] args) {
        testSimpleSupplierFactory();
        testMultiMethodSupplierFactory();
        testProvider();
    }

    private static final void testSimpleSupplierFactory() {
        System.out.println(">> 简单工厂 >>");
        testSimpleSupplierFactory("apple");
        testSimpleSupplierFactory("origin");
        testSimpleSupplierFactory("car");
        System.out.println(">> 简单工厂 >>");
    }

    private static final void testSimpleSupplierFactory(String supplierName) {
        ISupplier supplier = SimpleSupplierFactory.getSupply(supplierName);
        System.out.printf(">> SimpleSupplierFactory >> %s >> %s\n", supplierName, supplier);
        supplier.supply();
    }

    private static final void testMultiMethodSupplierFactory() {
        System.out.println(">> 多方法工厂 >>");
        ISupplier supplier = MultiMethodSupplierFactory.appleSupplier();
        System.out.printf(">> MultiMethodSupplierFactory >> %s\n", supplier);
        supplier.supply();
        supplier = MultiMethodSupplierFactory.originSupplier();
        System.out.printf(">> MultiMethodSupplierFactory >> %s\n", supplier);
        supplier.supply();
        supplier = MultiMethodSupplierFactory.carSupplier();
        System.out.printf(">> MultiMethodSupplierFactory >> %s\n", supplier);
        supplier.supply();
        System.out.println(">> 多方法工厂 >>");
    }

    private static final void testProvider() {
        System.out.println(">> 工厂方法 >>");
        testProvider(new AppleSupplierProvider());
        testProvider(new OriginSupplierProvider());
        testProvider(new CarSupplierProvider());
        System.out.println(">> 工厂方法 >>");
    }

    private static final void testProvider(IProvider provider) {
        ISupplier supplier = provider.provide();
        System.out.printf(">> IProvider >> %s\n", supplier);
        supplier.supply();
    }

}
