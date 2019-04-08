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
    FileName: Meal.java
    Date: 2019/4/8
    Author: lq
*/
package com.lq186.demo.designpattern.builder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public final class Meal {

    private final List<IFood> foods = new ArrayList<>();

    public void addFood(IFood food) {
        foods.add(food);
    }

    public BigDecimal getCost() {
        BigDecimal cost = BigDecimal.ZERO;
        for (IFood food : foods) {
            cost = cost.add(food.price());
        }
        return cost;
    }

    public void displayMeal() {
        System.out.println("\n\n>> display start >>");
        foods.forEach(food -> System.out.printf("{名称: %s, 价格: %.2f, 包装: %s}\n", food.name(), food.price(), food.packing().pack()));
        System.out.println("<< display end <<");
        System.out.printf(">> cost: %.2f\n\n", getCost());
    }

}
