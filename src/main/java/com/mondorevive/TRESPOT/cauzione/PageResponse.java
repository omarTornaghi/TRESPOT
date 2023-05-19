package com.mondorevive.TRESPOT.cauzione;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;
@Getter
public class PageResponse<T> {
    private final int page;
    private final int size;
    private final int totalNumberOfElementsOfPage;
    private final int totalNumberOfPages;
    private final long totalNumberOfElements;
    private final boolean previous;
    private final boolean next;
    private List<T> content;

    public PageResponse(PageResponse<?> page, List<T> content) {
        this.page = page.getPage();
        this.size = page.getSize();
        this.totalNumberOfElementsOfPage = page.getTotalNumberOfElementsOfPage();
        this.totalNumberOfPages = page.getTotalNumberOfPages();
        this.totalNumberOfElements = page.getTotalNumberOfElements();
        this.previous = page.isPrevious();
        this.next = page.isNext();
        this.content = content;
    }

    public void setContent(List<T> content){
        this.content = content;
    }
    public PageResponse(Page<?> page, List<T> content){
        this.page = page.getNumber();
        this.size = page.getSize();
        this.totalNumberOfElementsOfPage = page.getNumberOfElements();
        this.totalNumberOfPages = page.getTotalPages();
        this.totalNumberOfElements =page.getTotalElements();
        this.previous = page.hasPrevious();
        this.next = page.hasNext();
        this.content = content;
    }
}
