package com.alphawang.designpattern.creational.prototype;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Prototype implements Cloneable {

    private String name;

    private List list = new ArrayList<>();

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
