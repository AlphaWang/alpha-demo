package com.alphawang.jdk.guava.model;

import com.google.common.base.MoreObjects;
import lombok.Data;
import lombok.NonNull;

/**
 * Created by Alpha on Mar/13/15.
 */
@Data
public class City {
    @NonNull
    private Long id;
    @NonNull
    private String name;
    @NonNull
    private Long population;

    public String toString() {
        return MoreObjects.toStringHelper(this)
            .omitNullValues()
            .addValue(name)
            //            .add("name", name)
            .toString();
    }
}
