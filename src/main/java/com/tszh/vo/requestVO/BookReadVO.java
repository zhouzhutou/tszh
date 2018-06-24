package com.tszh.vo.requestVO;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Created by Administrator on 2018/5/28 0028.
 */
public class BookReadVO {

    @NotNull(message = "{BookReadVO.pageSize.not.null}")
    @Min(value = 1,message = "{BookReadVO.pageSize.min.illegal}")
    private Integer pageSize;

    @NotNull(message = "{BookReadVO.pageNumber.not.null}")
    @Min(value = 1,message = "{BookReadVO.pageNumber.min.illegal}")
    private Integer pageNumber;

    //排序字段
    private String sortName;

    //排序顺序（"asc"或者"desc"）
    private String sortOrder;

    private Short status;

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getSortName() {
        return sortName;
    }

    public void setSortName(String sortName) {
        this.sortName = sortName;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }
}
