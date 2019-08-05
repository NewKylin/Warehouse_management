package com.warehouse_management.controller;

import com.alibaba.fastjson.JSON;
import com.warehouse_management.po.Product;
import com.warehouse_management.service.ProductService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.json.Json;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Controller
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    Log log = LogFactory.getLog(this.getClass());

    //跳转至列表页面
    @RequestMapping("/product/home.action")
    public String list(Model model){
        List<Product> productList = productService.selectAll(null);
        model.addAttribute("productList",productList);//货物列表
        return "/product/home.jsp";//转向首页
    }
    @RequestMapping("/product/toAdd.action")
    public String toAdd(Model model){

        return "/product/add.jsp";//转向添加页
    }

    @RequestMapping("/product/add.action")
    public String add(Model model,Product product){
        productService.insert(product);
        try {
            sendMsgToMQ(product,"insert");
        }catch (AmqpException e){
            e.printStackTrace();
        }
        //重新刷新至分页列表页首页
        return list(model);
    }

    @RequestMapping("/product/toEdit.action")
    public String toEdit(Model model,Integer id){
        if(id!=null){
            model.addAttribute("product", productService.selectById(id));
        }
        return "/product/edit.jsp";//转向编辑页
    }

    @RequestMapping("/product/edit.action")
    public String edit(Model model,Product product){
        productService.update(product);
        try {
            sendMsgToMQ(product,"update");
        }catch (AmqpException ex){
            ex.printStackTrace();
        }
        //重新刷新至分页列表页首页
        return list(model);
    }


    @RequestMapping("/product/delete.action")
    public String delete(Model model,Integer id){
        productService.deleteById(id);
        try {
            Product product = new Product();
            product.setId(id);
            sendMsgToMQ(product,"delete");
        }catch (AmqpException e){
            e.printStackTrace();
        }
        //重新刷新至分页列表页首页
        return list(model);
    }
    private void  sendMsgToMQ(Product product,String type){
        try {
            Map<String,Object> msg = new HashMap<>();
            msg.put("itemObject", JSON.toJSON(product).toString());
            msg.put("type",type);
            msg.put("date",System.currentTimeMillis());
            this.rabbitTemplate.convertAndSend("item."+type,JSON.toJSON(msg).toString());
        }catch (AmqpException e){
            e.printStackTrace();
        }
    }
}
