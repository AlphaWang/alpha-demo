package com.alphawang.mongodb.orm.framework.common;

import java.io.Serializable;

public class ResultMessage<T> implements Serializable {
    private T data;
    private String code;
}
