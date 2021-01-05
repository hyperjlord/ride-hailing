package com.example.orderservice.controller;

import com.example.orderservice.dao.OrderMapper;
import com.example.orderservice.pojo.Comment;
import com.example.orderservice.pojo.Order;
import com.example.orderservice.qo.SetOrderQO;
import com.example.orderservice.service.OrderService;
import com.example.orderservice.vo.*;
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
    @Autowired
    public OrderService orderService;
    //@GetMapping(value="/hello",produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "hello world",notes = "测试用的helloworld")
    @GetMapping("/hello")
    public String helloworld(){
        return "hello world";
    }

    @ApiOperation(value = "查询所有订单",notes="返回数据库中所有的订单信息")
    @GetMapping("/selectall")
    public List<Order> selectall(){
        return orderService.selectAll();
    }

    @ApiOperation(value = "查询所有状态的订单详情",notes="根据用户id和订单状态返回订单详情（顺风车打车订单都有）")
    @GetMapping("/order/all/uid/{user_id}/state/{state}")
    public List<OrderDetailVo> selectAllByUidAndState(@PathVariable("user_id")String user_id,@PathVariable("state") int state){
        return orderService.findAllByUidAndState(user_id,state);
    }

    @ApiOperation(value = "查询所有状态订单详情",notes="根据司机id和订单状态返回订单详情（顺风车打车订单都有）")
    @GetMapping("/order/all/did/{driver_id}/state/{state}")
    public List<OrderDetailVo> selectAllByDidAndState(@PathVariable("driver_id")String driver_id,@PathVariable("state") int state){
        return orderService.findAllByDidAndState(driver_id,state);
    }

    @ApiOperation(value = "根据订单id返回订单详情",notes = "根据订单id返回订单详情")
    @GetMapping("/order/{type}/oid/{order_id}")
    public OrderDetailVo selectByOid(@PathVariable("order_id") String order_id,@PathVariable("type") int type){
        return orderService.findOrderByOid(order_id,type);
    }

    @ApiOperation(value = "根据用户id,订单种类返回订单详情",notes = "根据用户id，订单种类返回订单详情")
    //@GetMapping("/selectbyid")
    @GetMapping("/order/{type}/uid/{user_id}")
    public List<OrderDetailVo> selectByUId(@ApiParam(value="订单种类(1为顺风车，2为打车)") @PathVariable("type") int type,
                                          @ApiParam(value="用户id") @PathVariable("user_id") String user_id
    ){
        return orderService.findOrderByUid(user_id,type);
    }

    @ApiOperation(value = "根据用户id,订单种类及订单状态返回订单",notes = "根据用户id，订单种类及订单状态返回订单")
    //@GetMapping("/selectbyid")
    @GetMapping("/order/{type}/uid/{user_id}/state/{state}")
    public List<OrderDetailVo> selectByUIdAndState(@ApiParam(value="订单种类(1为顺风车，2为打车)") @PathVariable("type") int type,
                                          @ApiParam(value="用户id") @PathVariable("user_id") String user_id,
                                          @ApiParam(value="订单状态") @PathVariable("state") int state
    ){
        return orderService.findOrderByUidAndState(user_id,type,state);
    }

    @ApiOperation(value = "根据司机id,订单种类及订单状态返回订单",notes = "根据用户id，订单种类及订单状态返回订单")
    @GetMapping("/order/{type}/did/{driver_id}/state/{state}")
    public List<OrderDetailVo> selectByDIdAndState(@ApiParam(value="订单种类(1为顺风车，2为打车)") @PathVariable("type") int type,
                                                   @ApiParam(value="司机id") @PathVariable("driver_id") String driver_id,
                                                   @ApiParam(value="订单状态") @PathVariable("state") int state
    ){
        return orderService.findOrderByDidAndState(driver_id,type,state);
    }

    //返回乘客目的地与司机的目的地最近的订单，按距离从小到大排序
    @ApiOperation(value = "查询乘客出发地与司机当前位置最近的未接单的订单(用于打车)",
            notes = "返回乘客目的地与司机的目的地最近的未接单的订单，按距离(距离为乘客与司机目的地距离加上乘客与司机的起点距离)从小到大排序(距离单位是米)"
    )
    @GetMapping("/order/2/nearest")
    public List<OrderWithDistanceVO> getNearestOrders(@ApiParam(value ="司机出发地的经度" ) @RequestParam("lon") Double lon,
                                                    @ApiParam(value ="司机出发地的纬度" ) @RequestParam("lat") Double lat
                                                    ){
        return  orderService.getNearestOrders(lon,lat);
    }

    @ApiOperation(value = "查询乘客与司机起点距离加终点距离最近的未接单的订单（用于顺风车）",
            notes = "返回乘客与司机起点距离加终点距离最近的未接单的订单，按距离(距离为乘客与司机目的地距离加上乘客与司机的起点距离)从小到大排序(距离单位是米)"
    )
    @GetMapping("/order/1/nearest")
    public List<OrderWithDistanceVO> getMatchOrders(@ApiParam(value ="司机出发地的经度" ) @RequestParam("from_lon") Double from_lon,
                                                    @ApiParam(value ="司机出发地的纬度" ) @RequestParam("from_lat") Double from_lat,
                                                    @ApiParam(value ="司机目的地的经度" ) @RequestParam("to_lon") Double to_lon,
                                                    @ApiParam(value ="司机目的地的纬度" ) @RequestParam("to_lat") Double to_lat,
                                                    @ApiParam(value="司机的出发日期时间") @RequestParam("datetime") Date datetime
    ){
        int type=1;
        return  orderService.getMatchOrders(from_lon,from_lat,to_lon,to_lat,type,datetime);
    }

    @ApiOperation(value = "乘客发布需求订单",notes="乘客发布需求订单,type=1为顺风车，type=2为打车")
    @PostMapping("/setuporder")
    public SetOrderVO setUpOrder(@RequestBody SetOrderQO setOrderQO) {
        SetOrderVO setOrderVO=new SetOrderVO();
        if(setOrderQO==null){
            setOrderVO.setErrorInfo("请求失败");
            return setOrderVO;
        }
        String order_id=orderService.setNewOrder(setOrderQO);
        setOrderVO.setErrorInfo("请求成功");
        setOrderVO.setOrder_id(order_id);
        return setOrderVO;
    }

    @ApiOperation(value = "司机接单",
            notes = "司机确认接单时调用该请求，订单状态由0变为1（订单已被接单）")
    @GetMapping("/takeorder" )
    public int takeOrder(@RequestParam("order_id") String order_id,@RequestParam("driver_id") String driver_id){
        return orderService.takeOrder(order_id,driver_id);
    }

    @ApiOperation(value = "司机接人",
            notes="前端确认司机与乘客距离符合要求后调用该请求,订单状态由1(已接单)变为2（订单进行中）")
    @GetMapping("/pickup")
    public int pickUp(@RequestParam("order_id") String order_id){
        return orderService.pickUp(order_id);
    }

    @ApiOperation(value = "结束订单",notes = "根据订单id结束订单，前提是订单目前状态为进行中，即state=2")
    @GetMapping("/finishorder")
    public FinishOrderVo finshOrder(@RequestParam("order_id") String order_id){
        return orderService.finishOrder(order_id);
    }

    @ApiOperation(value = "评价订单",notes = "用户对已完成的订单进行打分评价")
    @PostMapping("/savecomment")
    public void saveComment(@ApiParam("评论对应的订单id")@RequestParam("order_id") String order_id,
                            @ApiParam("用户给此次订单打的分数")@RequestParam("score") Double score,
                            @ApiParam("评论的具体内容")@RequestParam("content") String content){
        orderService.saveComment(order_id,score,content);
    }

    @ApiOperation(value = "取消订单", notes="根据订单id取消订单，前提是订单还处于未接单（0）或已接单（1）状态")
    @DeleteMapping("/order/{type}/delete/oid/{order_id}")
    public void cancerOrder(@PathVariable("order_id") String order_id,
                            @PathVariable("type") int type
    ){
        orderService.cancerOrder(order_id,type);
    }

    @ApiOperation(value = "更新司机位置信息", notes = "更新司机位置信息")
    @PostMapping("/location/post/driver/{driver_id}/{lon}/{lat}")
    public void PostDriverLocation(@ApiParam("司机id")@PathVariable String driver_id,
                                   @ApiParam("司机当前位置经度")@PathVariable Double lon,
                                   @ApiParam("司机当前位置纬度")@PathVariable Double lat
    ){
        orderService.updateDriverLocation(driver_id,lon,lat);
    }

    @ApiOperation(value = "获取司机位置信息", notes = "根据司机id获取司机的最新位置信息")
    @GetMapping("/location/get/driver/{driver_id}")
    public DriverLocationVO GetDriverLocation(@ApiParam("司机id")@PathVariable String driver_id){
        return orderService.findDriverLocation(driver_id);
    }

    @ApiOperation(value = "获取附近司机位置信息", notes = "根据一个经纬度坐标获取附近司机的最新位置信息，按距离从小到达排列")
    @GetMapping("/location/get/driver/near/{lon}/{lat}")
    public List<DriverNearbyVo> GetNearestDriverLocation(@ApiParam("查询经度")@PathVariable Double lon,
                                                     @ApiParam("查询纬度") @PathVariable Double lat
                                                     ){
        return orderService.findNearestDriver(lon,lat);
    }
    @ApiOperation(value = "根据订单id获取评论",notes="如上")
    @GetMapping("comment/{order_id}")
    public Comment findCommentById(@PathVariable("order_id") String order_id){
        return orderService.findCommentById(order_id);
    }
}
