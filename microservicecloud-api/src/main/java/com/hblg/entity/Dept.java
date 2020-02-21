package com.hblg.entity;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


import java.io.Serializable;

/**
 * @author i
 * @create 2019/11/24 17:09
 * @Description
 */
@SuppressWarnings("serial")
@NoArgsConstructor
@Data
@Accessors(chain = true)
public class Dept implements Serializable {//必须序列化

    private Long deptNo;//主键
    private String dname;//部门名称
    private String db_source;//来自哪个数据库，因为微服务可以一个服务对应一个数据库，同一个信息存储在不同的数据库中

    public Dept(String dname) {
        this.dname = dname;
    }

}
