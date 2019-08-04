package com.warehouse_management.service.impl;

import com.warehouse_management.dao.ProductDao;
import com.warehouse_management.po.Product;
import com.warehouse_management.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Override
    public Product selectById(int id) {
        return productDao.selectById(id);
    }

    @Override
    public List<Product> selectAll(Map map) {
        return productDao.selectAll(map);
    }

    @Override
    public void insert(Product commodties) {
        productDao.insert(commodties);
    }

    @Override
    public void update(Product commodties) {
        productDao.update(commodties);
    }

    @Override
    public void deleteById(int id) {
        productDao.deleteById(id);
    }
}
