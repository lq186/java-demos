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
package com.lq186.demo.designpattern.builder;

import com.lq186.demo.designpattern.builder.burger.ChickenBurger;
import com.lq186.demo.designpattern.builder.burger.VegBurger;
import com.lq186.demo.designpattern.builder.drink.CokeDrink;

public final class TestBootstrap {

    public static void main(String[] args) {

        System.out.println("建造者模式演示:");

        Meal vegMeal = MealBuilder.buildMealVeg();
        vegMeal.displayMeal();

        Meal chickenMeal = MealBuilder.buildMealChicken();
        chickenMeal.displayMeal();

        MealBuilder builder = new MealBuilder();
        builder.addFood(new VegBurger());
        builder.addFood(new ChickenBurger());
        builder.addFood(new CokeDrink());
        Meal customMeal = builder.build();
        customMeal.displayMeal();

    }

}
