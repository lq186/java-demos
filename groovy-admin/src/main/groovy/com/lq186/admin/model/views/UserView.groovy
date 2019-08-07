package com.lq186.admin.model.views

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

import javax.persistence.Column

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
    FileName: UserView.java
    Date: 2019/8/5
    Author: lq
*/

@ApiModel(value = "UserView", description = "管理员用户信息")
class UserView {

    @ApiModelProperty("管理员用户信息数据ID")
    String id

    @ApiModelProperty("用户名, 登录使用")
    String username

    @ApiModelProperty("显示名称, 展示使用")
    String displayName

    @ApiModelProperty("是否超级用户 [true 是 | false 不是]")
    Boolean superUser

    @ApiModelProperty("使用状态 [1 正常 | 2 锁定 | 3 停用]")
    Integer useState

    @ApiModelProperty("有效时间, 单位: 毫秒(ms)")
    Long activeTime

    @ApiModelProperty("失效时间, 单位: 毫秒(ms)")
    Long invalidTime

    @ApiModelProperty("数据创建时间, 单位: 毫秒(ms)")
    Long createTime

}
