package com.alphawang.demo.alphawebsocket.dto;

import lombok.Data;

@Data
public class Message {
    
    private String from;
    private String text;
    
    public String toString() {
        return String.format("FROM: %S. \nTEXT: %s", from, text);
    }

}
