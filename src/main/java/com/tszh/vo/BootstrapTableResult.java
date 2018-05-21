package com.tszh.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tszh.vo.requestVO.BookSearchVO;

import java.util.List;

/**
 * Created by Administrator on 2018/5/14 0014.
 */
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class BootstrapTableResult<T> {

    private long total;

    private List<T> rows;

    public BootstrapTableResult()
    {

    }

    public BootstrapTableResult(long total, List<T> rows) {
        this.total = total;
        this.rows = rows;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
