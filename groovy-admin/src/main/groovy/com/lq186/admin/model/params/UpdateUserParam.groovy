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
    FileName: UpdateUserParam.java
    Date: 2019/8/5
    Author: lq
*/

@ApiModel(value = "UpdateUserParam", description = "修改管理员用户信息参数")
class UpdateUserParam {

    @ApiModelProperty("用户名")
    String username

    @ApiModelProperty("显示名称")
    String displayName

    @ApiModelProperty("是否超级用户 [true 是 | false 否]")
    Boolean superUser

    @ApiModelProperty(value = "使用状态 [1 正常 | 2 锁定 | 3 停用]", allowableValues = "1, 2, 3")
    Integer useState

    @ApiModelProperty("有效时间, 单位: 毫秒(ms)")
    Long activeTime

    @ApiModelProperty("失效时间, 单位: 毫秒(ms)")
    Long invalidTime

}
