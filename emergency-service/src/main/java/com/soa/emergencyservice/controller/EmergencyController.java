package com.soa.emergencyservice.controller;

import com.soa.emergencyservice.dao.EmergencyDao;
import com.soa.emergencyservice.entity.Contact;
import io.swagger.annotations.Api;
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
@Api(value = "紧急联系人管理")
public class EmergencyController {

    @Autowired
    private EmergencyDao repository;

    /**
     *  添加紧急联系人
     */
    @ApiOperation(value = "乘客添加紧急联系人", notes = "乘客添加紧急联系人，输入identity（身份）、userId（账号）、toEmail（邮箱）和emergency_phone（电话号码）")
    @PostMapping("/emergency/add")
    @ResponseBody
    public Contact add(@RequestParam("identity") String identity,
                       @RequestParam("emergency_phone") String emergency_phone,
                       @RequestParam("userId") String userId,
                       @RequestParam("toEmail") String toEmail) {
        Contact contact = new Contact();
        contact.setIdentity(identity);  // 传入身份
        contact.setEmergency_phone(emergency_phone);  // 传入电话号码
        contact.setUserId(userId);  // 传入账号
        contact.setToEmail(toEmail);  // 传入邮箱
        return repository.save(contact);
     }

     /**
      *  获取所有紧急联系人列表
     */
    @ApiOperation(value = "乘客查看所有紧急联系人", notes = "乘客查看所有紧急联系人，传入userId，返回该userid对应的所有紧急联系人的list")
    @GetMapping("/emergency/{userId}")
    @ResponseBody
    public List<Contact> contactList(@PathVariable("userId") String userId) {
        return repository.findByUserId(userId);
    }

    /**
     *  计算紧急联系人数量
     */
    @ApiOperation(value = "紧急联系人数量", notes = "传入userId，返回对应的紧急联系人的数量")
    @GetMapping("/count_emergency/{userId}")
    @ResponseBody
    public int count_emergency(@PathVariable("userId") String userId) {
        return repository.countByUserId(userId);
    }



    /**
     *  删除一条紧急联系人
     */
    @ApiOperation(value = "根据id删除一条紧急联系人", notes = "根据id删除一条紧急联系人")
    @DeleteMapping("/emergency/{id}")
    @ResponseBody
    public void deleteEmergency(@PathVariable("id") Integer id) {
        repository.deleteById(id);
    }
}
