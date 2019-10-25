package com.alphawang.ops.stack;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
public class StackTraceController {

    @RequestMapping("/test")
    @ResponseBody
    public String test() {
        StringBuilder sb = new StringBuilder();

        for (Map.Entry<Thread, StackTraceElement[]> entry : Thread.getAllStackTraces().entrySet()) {
            Thread thread = entry.getKey();
            StackTraceElement[] stacks = entry.getValue();

            sb.append("\n\r\n\r").append("Thread: ").append(thread.getName());
            for (StackTraceElement stack : stacks) {
                sb.append("\t").append(stack).append("\n\r");
            }
        }
        return sb.toString();
    }

    @RequestMapping("/test2")
    @ResponseBody
    public Map<Thread, StackTraceElement[]> test2() {

        return Thread.getAllStackTraces();
    }
}
