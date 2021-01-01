package com.example.orderservice.controller;

import com.example.orderservice.dao.OrderMapper;
import com.example.orderservice.pojo.Order;
import com.example.orderservice.qo.SetOrderQO;
import com.example.orderservice.service.OrderService;
import com.example.orderservice.vo.FinishOrderVo;
import com.example.orderservice.vo.OrderDetailVo;
import com.example.orderservice.vo.OrderWithDistanceVO;
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

    @ApiOperation(value = "查询所有订单详情",notes="返回数据库中所有的订单信息，包括订单对应的评论")
    @GetMapping("/selectall/detail")
    public List<OrderDetailVo> selectAllDetail(){
        return orderService.selectAllDetail();
    }

    @ApiOperation(value = "根据订单id返回订单详情",notes = "根据订单id返回订单详情")
    @GetMapping("/order/{type}/oid/{order_id}")
    public List<OrderDetailVo> selectByOid(@PathVariable("order_id") String order_id,@PathVariable("type") String type){
        return null;
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

    //返回乘客目的地与司机的目的地最近的订单，按距离从小到大排序
    @ApiOperation(value = "查询乘客目的地与司机的目的地最近的未接单的订单",
            notes = "返回乘客目的地与司机的目的地最近的未接单的订单，按距离从小到大排序(距离单位是米)"
    )
    @GetMapping("/order/{type}/nearest")
    public List<OrderWithDistanceVO> getMatchOrders(@ApiParam(value ="司机所在的经度" ) @RequestParam("lon") Double longitude,
                                                    @ApiParam(value ="司机所在的纬度" ) @RequestParam("lat") Double latitude,
                                                    @PathVariable("type")int type
                                                    ){
        return  orderService.getMatchOrders(longitude,latitude,type);
    }

    @ApiOperation(value = "乘客发布顺风车需求订单",notes="乘客发布需求订单,type=1为顺风车，type=2为打车")
    @PostMapping("/setuporder/1")
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

    @ApiOperation(value = "乘客发布打车需求订单",notes = "乘客发布打车需求订单，需要的参数为起点终点的经纬度")
    @PostMapping("/setuporder/2")
    public SetOrderVO SetUpOrder(){
        SetOrderVO setOrderVO=new SetOrderVO();

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
    @DeleteMapping("/order/{type}/delete")
    public void cancerOrder(@RequestParam("order_id") String order_id,
                            @PathVariable("type") int type
    ){
        orderService.cancerOrder(order_id,type);
    }
}
