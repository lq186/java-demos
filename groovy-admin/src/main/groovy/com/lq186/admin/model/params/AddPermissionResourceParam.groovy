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
    FileName: AddPermissionResourceParam.java
    Date: 2019/8/5
    Author: lq
*/

@ApiModel(value = "AddPermissionResourceParam", description = "新增权限资源信息参数")
class AddPermissionResourceParam {

    @ApiModelProperty("资源标识")
    String resourceId

    @ApiModelProperty("资源名称")
    String resourceName

    @ApiModelProperty("资源值")
    String resourceValue

    @ApiModelProperty(value = "资源类型 [1 目录 | 2 页面视图 | 3 按钮 | 9 API]", allowableValues = "1, 2, 3, 9")
    Integer resourceType

    @ApiModelProperty(value = "上级资源数据ID", required = false)
    String parentResourceDataId

    @ApiModelProperty("排序号")
    Integer serialNumber

}
