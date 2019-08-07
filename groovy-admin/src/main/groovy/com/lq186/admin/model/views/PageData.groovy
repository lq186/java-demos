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
    FileName: PageData.java
    Date: 2019/8/5
    Author: lq
*/

@ApiModel(value = "PageData", description = "分页数据信息")
class PageData<T> {

    @ApiModelProperty("页码")
    Integer page

    @ApiModelProperty("每页记录数量")
    Integer size

    @ApiModelProperty(value = "是否是首页", notes = "[true: 是 | false: 否]")
    Boolean first

    @ApiModelProperty(value = "是否是尾页", notes = "[true: 是 | false: 否]")
    Boolean last

    @ApiModelProperty("记录总数量")
    Integer totalElements

    @ApiModelProperty("总页数")
    Integer totalPages

    @ApiModelProperty("记录内容")
    List<T> content

}
