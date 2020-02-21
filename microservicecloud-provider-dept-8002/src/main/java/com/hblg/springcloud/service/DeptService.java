package com.hblg.springcloud.service;

import com.hblg.entity.Dept;

import java.util.List;

/**
 * @author i
 * @create 2020/2/18 15:46
 * @Description
 */
public interface DeptService {

    //添加
    public boolean  add(Dept dept);

    //查询
    public Dept get(long id);

    //
    public List<Dept> list();
}
