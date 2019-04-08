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
    FileName: SimpleSupplierFactory.java
    Date: 2019/4/8
    Author: lq
*/
package com.lq186.demo.designpattern.factory;

import com.lq186.demo.designpattern.factory.supplier.AppleSupplier;
import com.lq186.demo.designpattern.factory.supplier.CarSupplier;
import com.lq186.demo.designpattern.factory.supplier.OriginSupplier;

/**
 * 简单工厂
 */
public final class SimpleSupplierFactory {

    public static final ISupplier getSupply(String supplierName) {
        switch (supplierName) {
            case "apple":
                return new AppleSupplier();
            case "origin":
                return new OriginSupplier();
            case "car":
                return new CarSupplier();
            default:
                throw new RuntimeException("未知的supplier名称");
        }
    }

}
