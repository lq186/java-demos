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
    FileName: ShapeCache.java
    Date: 2019/4/10
    Author: lq
*/
package com.lq186.demo.designpattern.prototype;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public final class ShapeCache {

    private static final Map<ShapeType, Shape> SHAPE_MAP = new HashMap<>();

    static {
        SHAPE_MAP.put(ShapeType.CIRCLE, new Circle());
        SHAPE_MAP.put(ShapeType.RECTANGLE, new Rectangle());
        SHAPE_MAP.put(ShapeType.SQUARE, new Square());
    }

    public static final Shape getShape(ShapeType type) throws CloneNotSupportedException {
        return SHAPE_MAP.get(type).clone();
    }

    public static final Shape getShapeDeepClone(ShapeType type) throws CloneNotSupportedException {
        return SHAPE_MAP.get(type).deepClone();
    }

    public static final Shape getShapeDeepClone2(ShapeType type) throws IOException, ClassNotFoundException {
        return SHAPE_MAP.get(type).deepClone2();
    }

}
