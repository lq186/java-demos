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
    Date: 2019/4/10
    Author: lq
*/
package com.lq186.demo.designpattern.prototype;

import java.io.IOException;

public final class TestBootstrap {

    public static void main(String[] args) {

        try {
            System.out.println(">>>>> 浅拷贝 >>>>>");
            Shape circle1 = ShapeCache.getShape(ShapeType.CIRCLE);
            Shape circle2 = ShapeCache.getShape(ShapeType.CIRCLE);
            compareShape(circle1, circle2);

            System.out.println();
            System.out.println(">>>>> 深拷贝-1 >>>>>");
            circle1 = ShapeCache.getShapeDeepClone(ShapeType.CIRCLE);
            circle2 = ShapeCache.getShapeDeepClone(ShapeType.CIRCLE);
            compareShape(circle1, circle2);

            System.out.println();
            System.out.println(">>>>> 深拷贝-2 >>>>>");
            circle1 = ShapeCache.getShapeDeepClone2(ShapeType.CIRCLE);
            circle2 = ShapeCache.getShapeDeepClone2(ShapeType.CIRCLE);
            compareShape(circle1, circle2);

        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private static void compareShape(Shape shape1, Shape shape2) {
        System.out.printf(">> Get Shape(%s) 1 -> %s\n", shape1.getType(), shape1);
        System.out.printf(">> Get Shape(%s) 2 -> %s\n", shape2.getType(), shape2);
        System.out.printf(">> Shape(%s) 1 == Shape(%s) 2 ? -> %s\n", shape1.getType(), shape2.getType(), (shape1 == shape2));
        System.out.printf(">> Shape(%s).color 1 -> %s\n", shape1.getType(), shape1.getColor());
        System.out.printf(">> Shape(%s).color 2 -> %s\n", shape2.getType(), shape2.getColor());
        System.out.printf(">> Shape(%s).color 1 == Shape(%s).color 2 ? -> %s\n", shape1.getType(), shape2.getType(), (shape1.getColor() == shape2.getColor()));
    }

}
