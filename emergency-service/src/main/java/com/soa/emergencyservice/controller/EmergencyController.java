package com.soa.emergencyservice.controller;

import com.soa.emergencyservice.dao.EmergencyDao;
import com.soa.emergencyservice.entity.Contact;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *  1、修改了contact表，由于之前的user_id当主键不能唯一标识了，我感觉
 *    这个表应该可以不用user_id，而是随便弄一个唯一标识一条紧急联系人
 *    的id，注意删除一条紧急联系人要传一个id，id是从1开始自增的。
 *
 *  2、最大紧急联系人数量就前端规定吧（
 */

@RestController
public class EmergencyController {

    @Autowired
    private EmergencyDao repository;

    /**
     *  添加紧急联系人
     */
    @ApiOperation(value = "乘客添加紧急联系人", notes = "乘客添加紧急联系人，输入identity（身份）和emergency_phone（电话号码）")
    @PostMapping("/emergency/add")
    @ResponseBody
    public Contact add(@RequestParam("identity") String identity,
                       @RequestParam("emergency_phone") String emergency_phone) {
        Contact contact = new Contact();
        contact.setIdentity(identity);  // 传入身份
        contact.setEmergency_phone(emergency_phone);  // 传入电话号码
        return repository.save(contact);
    }

    /**
     *  获取所有紧急联系人列表
     */
    @ApiOperation(value = "乘客查看所有紧急联系人", notes = "乘客查看所有紧急联系人")
    @GetMapping("/emergency")
    @ResponseBody
    public List<Contact> list() {
        return repository.findAll();
    }

    /**
     *  删除一条紧急联系人
     */
    @ApiOperation(value = "乘客根据id删除一条紧急联系人", notes = "乘客根据id删除一条紧急联系人")
    @DeleteMapping("/emergency/{id}")
    @ResponseBody
    public void deleteEmergency(@PathVariable("id") Integer id) {
        repository.deleteById(id);
    }
}
