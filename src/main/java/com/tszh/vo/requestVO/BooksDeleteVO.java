package com.tszh.vo.requestVO;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Created by Administrator on 2018/6/20 0020.
 */
public class BooksDeleteVO {

    @NotNull(message = "{BooksDeleteVO.bookIds.not.null}")
    @Size(min = 1,message = "{BooksDeleteVO.bookIds.size.illegal}")
    private List<Integer> bookIds;

    public List<Integer> getBookIds() {
        return bookIds;
    }

    public void setBookIds(List<Integer> bookIds) {
        this.bookIds = bookIds;
    }
}

