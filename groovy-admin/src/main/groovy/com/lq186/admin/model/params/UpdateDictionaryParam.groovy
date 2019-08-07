package com.lq186.admin.model.params

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
    FileName: UpdateDictionaryParam.java
    Date: 2019/8/5
    Author: lq
*/

@ApiModel(value = "UpdateDictionaryParam", description = "修改字典信息参数")
class UpdateDictionaryParam implements Param {

    @ApiModelProperty("字典信息值")
    String value

    @ApiModelProperty("字典信息显示名称")
    String display

    @ApiModelProperty("字典信息分组")
    String group

}
