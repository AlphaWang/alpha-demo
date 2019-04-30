package com.alphawang.nacos.controller;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("config")
public class ConfigController {

    /**
     * For Nacos Config:
     * - dataId: cart.page
     * - group: DEFAULT_GROUP
     * - content: itemCount
     * 
     * curl -X POST "http://127.0.0.1:8848/nacos/v1/cs/configs?dataId=cart.page&group=DEFAULT_GROUP&content=itemCount=70"
     */
    @NacosValue(value = "${itemCount:10}", autoRefreshed = true)
    private String itemCount;
    
    @RequestMapping("cart-count")
    public String getItemCount() {
        return itemCount;
    }
}
