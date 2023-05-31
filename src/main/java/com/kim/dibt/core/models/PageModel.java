package com.kim.dibt.core.models;

import lombok.Data;
import java.util.List;

@Data
public class PageModel<T> {
    private List<T> data;
    private int pageNumber;
    private int pageSize;
    private int totalPages;
    private long totalElements;
    private boolean hasNext;
    private boolean hasPrevious;
    private String selfLink;
    private String nextLink;
    private String previousLink;
}
