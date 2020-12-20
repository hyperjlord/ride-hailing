package com.example.orderservice.controller;

import com.example.orderservice.dao.OrderMapper;
import com.example.orderservice.pojo.Order;
import com.example.orderservice.qo.SetOrderQO;
import com.example.orderservice.service.OrderService;
import com.example.orderservice.vo.FinishOrderVo;
import com.example.orderservice.vo.OrderDetailVo;
import com.example.orderservice.vo.SetOrderVO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.ws.rs.POST;
import java.net.http.HttpRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
public class OrderController {
    class Test{
        public String hello="hello world";
    }
    @Autowired
    public OrderService orderService;
    //@GetMapping(value="/hello",produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "hello world",notes = "测试用的helloworld")
    @GetMapping("/hello")
    public Test helloworld(){
        Test test=new Test();
        return test;
    }
    @ApiOperation(value = "查询所有订单",notes="返回数据库中所有的订单信息")
    @GetMapping("/selectall")
    public List<Order> selectall(){
        return orderService.selectAll();
    }
    @ApiOperation(value = "查询所有订单详情",notes="返回数据库中所有的订单信息，包括订单对应的评论")
    @GetMapping("/selectall/detail")
    public List<OrderDetailVo> selectAllDetail(){
        return orderService.selectAllDetail();
    }

    @ApiOperation(value = "根据用户id返回订单",notes = "根据用户id返回订单")
    @GetMapping("/selectbyid")
    public Order selectById(@ApiParam(value="用户id") @RequestParam("user_id") String user_id){
        return orderService.selectById(user_id);
    }

    //返回乘客目的地与司机的目的地最近的订单，按距离从小到大排序
    @ApiOperation(value = "查询乘客目的地与司机的目的地最近的订单",notes = "返回乘客目的地与司机的目的地最近的订单，按距离从小到大排序")
    @GetMapping("/getmatchorders")
    public List<Order> getMatchOrders(@ApiParam(value ="司机所在的经度" ) @RequestParam("lon") Double longitude,
                                      @ApiParam(value ="司机所在的纬度" ) @RequestParam("lat") Double latitude){
        return  orderService.getMatchOrders(longitude,latitude);
    }
    @ApiOperation(value = "乘客发布需求订单",notes="乘客发布需求订单,type=1为顺风车，type=2为打车")
    @PostMapping("/setuporder")
    public SetOrderVO setUpOrder(@RequestBody SetOrderQO setOrderQO) {
        SetOrderVO setOrderVO=new SetOrderVO();
        if(setOrderQO==null){
            setOrderVO.setErrorInfo("请求失败");
            return setOrderVO;
        }
        orderService.setNewOrder(setOrderQO);
        setOrderVO.setErrorInfo("请求成功");
        return setOrderVO;
    }
    @ApiOperation(value = "司机接单",
            notes = "司机确认接单时调用该请求，订单状态由0变为1（订单已被接单）")
    @GetMapping("/takeorder" )
    public int takeOrder(@Param("order_id") String order_id,@Param("driver_id") String driver_id){
        return orderService.takeOrder(order_id,driver_id);
    }
    @ApiOperation(value = "司机接人",
            notes="前端确认司机与乘客距离符合要求后调用该请求,订单状态由1(已接单)变为2（订单进行中）")
    @GetMapping("/pickup")
    public int pickUp(@Param("order_id") String order_id){
        return orderService.pickUp(order_id);
    }
    @ApiOperation(value = "完成订单",notes = "根据订单id结束订单，前提是订单目前状态为进行中，即state=2")
    @GetMapping("/finishorder")
    public FinishOrderVo finshOrder(@Param("order_id") String order_id){
        return orderService.finishOrder(order_id);
    }
    @ApiOperation(value = "用户评价订单",notes = "用户对已完成的订单进行打分评价")
    @PostMapping("/savecomment")
    public void saveComment(@Param("order_id") String order_id,
                            @Param("score") Double score,
                            @Param("content") String content){
        orderService.saveComment(order_id,score,content);
    }
    /*
    @ApiOperation(value = "乘客发布需求订单",notes="乘客发布顺风车需求订单")
    @PostMapping("/setuporder")
    public String setUpOrder(@RequestParam(value = "user_id",required = false) String user_id,
                             @RequestParam(value = "from_name",required = false) String from_name,
                             @RequestParam(value = "to_name",required = false) String to_name,
                             @RequestParam(value = "from_lon",required = false) Double from_lon,
                             @RequestParam(value = "from_lat",required = false) Double from_lat,
                             @RequestParam(value = "to_lon",required = false) Double to_lon,
                             @RequestParam(value = "to_lat",required = false) Double to_lat,
                             @RequestParam(value = "date",required = false) Date date,
                             @RequestParam(value = "passenger_num",required = false) int passenger_num
    ) {
        if (date != null){
            orderService.insertOrder(user_id,from_name,to_name,from_lon,from_lat,to_lon,to_lat,date,passenger_num);
            return date.toString()+to_name;
        }
        else
            return "not ok";
    }*/

}
