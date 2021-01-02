package com.example.favoriteservice.controller;


import com.example.favoriteservice.dao.FavoriteDao;
import com.example.favoriteservice.pojo.Favorite;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api(value = "乘客查看收藏订单")
public class FavoriteController {

    @Autowired
    private FavoriteDao repository;

    @ApiOperation(value = "乘客查看收藏订单", notes = "需要传入乘客的user_id，从而根据该id返回乘客收藏的所有订单id，返回格式为list")
    @GetMapping("/favorite/{userId}")
    @ResponseBody
    public List<Favorite> favoriteList(@PathVariable("userId") String userId) {
        return repository.findAllByUserId(userId);
    }
}
