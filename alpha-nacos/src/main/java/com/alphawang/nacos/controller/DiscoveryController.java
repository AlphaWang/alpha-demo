package com.alphawang.nacos.controller;

import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("discovery")
public class DiscoveryController {

    @NacosInjected
    private NamingService namingService;

    /**
     * 注册服务：
     * curl -X PUT 'http://127.0.0.1:8848/nacos/v1/ns/instance?serviceName=cart_front_api&ip=127.0.0.1&port=8080'
     * 
     * 查询服务：
     * http://localhost:8080/discovery/get?serviceName=cart_front_api
     */
    @RequestMapping("get")
    public List<Instance> get(@RequestParam String serviceName) {
        try {
            return namingService.getAllInstances(serviceName);
        } catch (NacosException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}
