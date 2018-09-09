package com.alphawang.mongodb.orm.framework.common;

import java.io.Serializable;
import java.util.List;

public class Page<T> implements Serializable {
    private int pageSize;
    private long start;
    private long total;
    private List<T> rows;
}
