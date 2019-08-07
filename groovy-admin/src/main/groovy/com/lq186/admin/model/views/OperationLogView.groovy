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
    FileName: OperationLogView.java
    Date: 2019/8/5
    Author: lq
*/

@ApiModel(value = "OperationLogView", description = "操作日志信息")
class OperationLogView {

    @ApiModelProperty("日志数据ID")
    String id

    @ApiModelProperty("用户ID #{UserView.id}")
    String userId

    @ApiModelProperty("操作类型[1 登录 | 2 注销 | 3 修改密码 | 4 新增数据 | 5 修改数据 | 6 删除数据]")
    Integer operationType

    @ApiModelProperty("操作数据")
    String operationData

    @ApiModelProperty("操作数据结果")
    String operationResult

    @ApiModelProperty("操作备注")
    String operationNotes

    @ApiModelProperty("日志时间, 单位: 毫秒(ms)")
    Long logTime

    @ApiModelProperty("日志失效时间, 单位: 毫秒(ms), 超过失效时间的日志将会被清理")
    Long invalidTime

}
