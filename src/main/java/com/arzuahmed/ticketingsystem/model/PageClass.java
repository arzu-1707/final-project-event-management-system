package com.arzuahmed.ticketingsystem.model;

import com.arzuahmed.ticketingsystem.exception.pageExceptions.PageNumberOutOfRangeException;
import com.arzuahmed.ticketingsystem.exception.pageExceptions.PageSizeOutOfRangeException;
import com.arzuahmed.ticketingsystem.model.enums.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;


@Data
@AllArgsConstructor
public class PageClass <T> implements Pageable {
    private List<T> content;
    private final int size;
    private final int number;
    private final Sort sort;

    public PageClass(int size, int number){
        this.size = size;
        this.number = number;
        this.sort = Sort.unsorted();
    }

    public PageClass(Page<T> springPage){
        this.content = springPage.getContent();
        this.size = springPage.getSize();
        this.number = springPage.getNumber();
        this.sort = springPage.getSort();
    }

    public PageClass(int size, int number, Sort sort){
        if (size<0){
            throw new PageSizeOutOfRangeException(ErrorCode.PAGE_SIZE_OUT_OF_RANGE_EXCEPTION);
        } else if (number<0){
            throw new PageNumberOutOfRangeException(ErrorCode.INVALID_PAGE_NUMBER_EXCEPTION);
        }

        this.size = size;
        this.number = number;
        this.sort = sort;
    }

    @Override
    public int getPageNumber() {
        return number;
    }

    @Override
    public int getPageSize() {
        return size;
    }

    @Override
    public long getOffset() {
        return (long) size* number;
    }

    @Override
    public Sort getSort() {
        return sort;
    }

    @Override
    public Pageable next() {
        return new PageClass(getPageNumber() +1, getPageSize(), sort);
    }

    @Override
    public Pageable previousOrFirst() {
        return getPageNumber() == 0 ? this : new PageClass(getPageNumber()- 1, getPageSize(), sort) ;
    }

    @Override
    public Pageable first() {
        return new PageClass(0, getPageSize(), sort);
    }

    @Override
    public Pageable withPage(int pageNumber) {
        return null;
    }

    @Override
    public boolean hasPrevious() {
        return getPageNumber()>0;
    }
}
