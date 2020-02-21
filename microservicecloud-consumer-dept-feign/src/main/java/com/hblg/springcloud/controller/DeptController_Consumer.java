package com.hblg.springcloud.controller;

import com.hblg.entity.Dept;
import com.hblg.springcloud.service.DeptClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author i
 * @create 2020/2/18 16:27
 * @Description
 */
@RestController
public class DeptController_Consumer {

    @Autowired
    private DeptClientService deptClientService = null;

    @RequestMapping(value = "/consumer/dept/get/{id}")
    public Dept get(@PathVariable("id") Long id){
        return this.deptClientService.get(id);
    }

    @RequestMapping(value = "/consumer/dept/list")
    public List<Dept> list(){
        return this.deptClientService.list();
    }

    @RequestMapping(value = "/consumer/dept/add")
    public boolean add(Dept dept){
        return this.deptClientService.add(dept);
    }
}
