package com.example.orderservice.service;

import com.example.orderservice.dao.DriverMapper;
import com.example.orderservice.dao.OrderMapper;
import com.example.orderservice.dao.PassengerMapper;
import com.example.orderservice.pojo.Driver;
import com.example.orderservice.pojo.Order;
import com.example.orderservice.pojo.Passenger;
import com.example.orderservice.qo.SetOrderQO;
import com.example.orderservice.vo.FinishOrderVo;
import com.example.orderservice.vo.OrderDetailVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {
    @Resource
    private OrderMapper orderMapper;
    @Resource
    private PassengerMapper passengerMapper;
    @Resource
    private DriverMapper driverMapper;

    public List<Order> selectAll() {
        return orderMapper.findAllOrders();
    }

    public List<OrderDetailVo>selectAllDetail(){
        return orderMapper.findAllDetail();
    }

    public List<Order> getMatchOrders(Double lon, Double lat) {
        return orderMapper.findMatchOrders(lon, lat);
    }

    public Order selectById(String user_id) {
        return orderMapper.findOrderById(user_id);
    }

    public void setNewOrder(SetOrderQO setOrderQO) {
        Order order=new Order();
        order.setOrder_id(UUID.randomUUID().toString());
        order.setUser_id(setOrderQO.getUser_id());
        order.setType(setOrderQO.getType());
        order.setState(0);
        order.setPrice(setOrderQO.getPrice());
        order.setDatetime(setOrderQO.getDatetime());
        order.setFrom_name(setOrderQO.getFrom_name());
        order.setTo_name(setOrderQO.getTo_name());
        order.setFrom_lon(setOrderQO.getFrom_lon());
        order.setFrom_lat(setOrderQO.getFrom_lat());
        order.setTo_lon(setOrderQO.getTo_lon());
        order.setTo_lat(setOrderQO.getTo_lat());
        order.setDescription(setOrderQO.getDescription());
        order.setState(0);
        order.setDriver_id(null);
        orderMapper.saveOrder(order);
    }

    public int takeOrder(String order_id,String driver_id){
        return orderMapper.takeOrder(order_id,driver_id);
    }

    public int pickUp(String order_id){
        return orderMapper.pickUp(order_id);
    }
    public FinishOrderVo finishOrder(String order_id){
        //先获取一个用于返回消息的对象
        FinishOrderVo finishOrderVo=new FinishOrderVo();
        //根据订单id获取订单对象
        Order order= orderMapper.findOrderById(order_id);
        if(order.getType()!=2){
            finishOrderVo.setInfo("该请求不合法，因为该订单目前状态不是”进行中(state=2)“");
            return finishOrderVo;
        }
        //根据订单id获取用户id，根据订单id获取司机id
        String u_id = passengerMapper.findPassengerFromOrder(order_id);
        String d_id = driverMapper.findDriverFromOrder(order_id);
        //根据用户id获取用户对象，根据司机id获取司机对象
        Passenger passenger= passengerMapper.findPassengerById(u_id);
        Driver driver = driverMapper.findDriverById(d_id);
        if(passenger.getBalance()<order.getPrice()){
            finishOrderVo.setInfo("用户余额不足无法完成订单");
            return finishOrderVo;
        }
        //根据订单金额扣除用户余额，相应地司机余额对应增加
        passenger.setBalance(passenger.getBalance()-order.getPrice());
        driver.setBalance(driver.getBalance()+order.getPrice());
        try{
        //保存修改后的余额数据
        passengerMapper.updateBalance(passenger);
        driverMapper.updateBalance(driver);
        //修改订单状态，使订单变为已完成
        orderMapper.finishOrder(order_id);
        }
        catch (Exception e){
            finishOrderVo.setInfo(e.toString());
        }
        return finishOrderVo;
    }
    public void saveComment(String order_id, Double score,String content ){
        orderMapper.saveComment(order_id,score,content);
    }
}
