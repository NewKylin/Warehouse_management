package com.warehouse_management.service;

import com.warehouse_management.po.Product;

import java.util.List;
import java.util.Map;

public interface ProductService {
    public Product selectById(int id);//只查询一个，常用于修改
    public List<Product> selectAll(Map map);//根据条件查询多个结果
    public void insert(Product commodties);//插入，用实体作为参数
    public void update(Product commodties);//修改，用实体作为参数
    public void deleteById(int id);//按id删除，删除一条
}
