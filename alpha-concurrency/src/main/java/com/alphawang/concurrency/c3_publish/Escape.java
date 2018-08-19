package com.alphawang.concurrency.c3_publish;

import com.alphawang.concurrency.annotations.NotThreadSafe;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NotThreadSafe
public class Escape {
    
    private int thisCanBeEscape = 0;
    
    public Escape() {
        log.info("Escape {}", Escape.this.thisCanBeEscape);
        
        // 会导致this引用的溢出。
        // 不要在构造函数中启动线程。
        new InnerClass();
    }
    
    private class InnerClass {
        public InnerClass() {
            log.info("InnerClass {}", Escape.this.thisCanBeEscape);
        }
    }

    public static void main(String[] args) {
        Escape escape = new Escape();
//        InnerClass inner = escape.new InnerClass();
    }
}
