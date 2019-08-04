package com.warehouse_management.dao.impl;

import com.warehouse_management.dao.ProductDao;
import com.warehouse_management.po.Product;
import org.springframework.stereotype.Repository;

@Repository
public class ProductDaoImpl extends BaseDaoImpl<Product> implements ProductDao {
    public ProductDaoImpl(){
        //设置命名空间
        super.setNs("com.warehouse_management.mapper.ProductMapper");
    }
}
