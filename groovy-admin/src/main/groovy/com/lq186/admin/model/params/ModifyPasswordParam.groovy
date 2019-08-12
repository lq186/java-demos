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
    FileName: ModifyPasswordParam.java
    Date: 2019/8/5
    Author: lq
*/

@ApiModel(value = "ModifyPasswordParam", description = "修改密码参数")
class ModifyPasswordParam {

    @ApiModelProperty("旧密码")
    String oldPassword

    @ApiModelProperty("新密码")
    String newPassword

    @ApiModelProperty("确认新密码")
    String confirmPassword

}
