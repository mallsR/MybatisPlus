package com.xiaoR.mp.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;

import java.util.List;

/**
 * @author xiaoR
 * @version 1.0
 * @date 2025/8/26
 * @description 分页查询的返回体,
 *              当返回结果给前端时,采用VO, 当可能需要返回结果给其他服务时(微服务中),采用DTO
 */
@Data
@ApiModel(description = "分页数据")
public class PageDTO<T> {
    @ApiModelProperty("总记录数")
    private Long total;
    @ApiModelProperty("总页数")
    private Long pages;
    @ApiModelProperty("当前页数据")
    private List<T> list;
}
