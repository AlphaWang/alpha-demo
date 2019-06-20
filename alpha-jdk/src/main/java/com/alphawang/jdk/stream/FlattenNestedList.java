package com.alphawang.jdk.stream;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FlattenNestedList {

    public static void main(String[] args) {
        List<String> tags1 = Lists.newArrayList("a", "b", "c");
        List<String> tags2 = Lists.newArrayList("d", "e", "f");

        List<Item> items = Lists.newArrayList(new Item(tags1), new Item(tags2));

        List<String> flatList = new ArrayList<>();
        items.forEach(item -> flatList.addAll(item.getTags()));
        System.out.println(flatList);

        List<String> flatList2 = items.stream()
            .flatMap(item -> item.getTags().stream())
            .collect(Collectors.toList());
        System.out.println(flatList2);
    }

    @Data
    @AllArgsConstructor
    static class Item {
        private List<String> tags;
    }
}
