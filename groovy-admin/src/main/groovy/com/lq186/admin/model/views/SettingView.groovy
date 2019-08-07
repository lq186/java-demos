package com.lq186.admin.model.views

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

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
    FileName: SettingView.java
    Date: 2019/8/5
    Author: lq
*/

@ApiModel(value = "SettingView", description = "配置信息")
class SettingView {

    @ApiModelProperty("配置信息数据ID")
    String id

    @ApiModelProperty("配置组名称")
    String itemGroup

    @ApiModelProperty("配置项")
    String itemKey

    @ApiModelProperty("配置值")
    String itemValue

    @ApiModelProperty("配置描述")
    String itemDescription

}
