package com.example.orderservice.service;

import com.example.orderservice.dao.DriverMapper;
import com.example.orderservice.dao.OrderMapper;
import com.example.orderservice.dao.PassengerMapper;
import com.example.orderservice.pojo.Comment;
import com.example.orderservice.pojo.Driver;
import com.example.orderservice.pojo.Order;
import com.example.orderservice.pojo.Passenger;
import com.example.orderservice.qo.SetOrderQO;
import com.example.orderservice.vo.*;
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

    public List<OrderDetailVo> selectAllDetail(){
        return orderMapper.findAllDetail();
    }

    public List<OrderDetailVo> findAllByUidAndState(String user_id,int state){
        return orderMapper.findAllOrderDetailByUidAndState(user_id,state);
    }

    public List<OrderDetailVo> findAllByDidAndState(String driver_id,int state){
        return orderMapper.findAllOrderDetailByDidAndState(driver_id,state);
    }

    public OrderDetailVo findOrderByOid(String order_id,int type){
        return orderMapper.findOrderDetailByOrderId(order_id,type);
    }

    public List<OrderDetailVo> findOrderByUid(String user_id,int type){
        return orderMapper.findOrderDetailByUserId(user_id,type);
    }

    public List<OrderDetailVo> findOrderByUidAndState(String user_id,int type,int state){
        return  orderMapper.findOrderDetailByUserIdAndState(user_id,type,state);
    }

    public List<OrderDetailVo> findOrderByDidAndState(String driver_id,int type,int state){
        return orderMapper.findOrderDetailByDriverIdAndState(driver_id,type,state);
    }

    public List<OrderWithDistanceVO> getNearestOrders(Double lon,Double lat){
        //获取当前时间
        Date current_time=new Date();
        return orderMapper.findNearestOrders(lon,lat,current_time);
    }

    public List<OrderWithDistanceVO> getMatchOrders(Double from_lon, Double from_lat, Double to_lon, Double to_lat, int type,Date datetime) {
        return orderMapper.findMatchOrders(from_lon, from_lat, to_lon, to_lat, type, datetime);
    }
    //创建新订单
    public String setNewOrder(SetOrderQO setOrderQO) {
        //用来构建订单
        Order order=new Order();
        String order_id=UUID.randomUUID().toString();
        order.setOrder_id(order_id);
        order.setUser_id(setOrderQO.getUser_id());
        order.setType(setOrderQO.getType());
        order.setState(0);
        order.setPassenger_num(setOrderQO.getPassenger_num());
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
        return order_id;
    }

    //司机接单
    public int takeOrder(String order_id,String driver_id){
        Date taken_time=new Date();
        return orderMapper.takeOrder(order_id,driver_id,taken_time);
    }

    //司机接人
    public int pickUp(String order_id){
        return orderMapper.pickUp(order_id);
    }

    //结束订单
    public FinishOrderVo finishOrder(String order_id){
        //先获取一个用于返回消息的对象
        FinishOrderVo finishOrderVo=new FinishOrderVo();
        //根据订单id获取订单对象
        Order order= orderMapper.findOrderById(order_id);
        if(order.getState()!=2){
            finishOrderVo.setInfo("该请求不合法，因为该订单目前状态不是”进行中(state=2)“");
            return finishOrderVo;
        }
        //根据订单id获取用户id，根据订单id获取司机id
        //String u_id = passengerMapper.findPassengerFromOrder(order_id);
        String u_id=order.getUser_id();
        String d_id=order.getDriver_id();
        //String d_id = driverMapper.findDriverFromOrder(order_id);

        //根据用户id获取用户对象
        Passenger passenger= passengerMapper.findPassengerById(u_id);
        if(passenger==null){
            finishOrderVo.setInfo("没获取到对应乘客对象");
            return finishOrderVo;
        }
        //根据司机id获取司机对象
        Driver driver = driverMapper.findDriverById(d_id);
        if(passenger.getBalance()<order.getPrice()){
            finishOrderVo.setInfo("用户余额不足无法完成订单");
            return finishOrderVo;
        }
        if(driver==null){
            finishOrderVo.setInfo("没获取到对应司机对象");
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
    public void cancerOrder(String order_id,int type){
        Order targetOrder=orderMapper.findOrderById(order_id);
        int order_state=targetOrder.getState();
        //只能取消未接单(state=0)和已接单(state=1)的订单
        assert(order_state==0||order_state==1);
        orderMapper.deleteByOid(order_id,type);
    }

    public void updateDriverLocation(String driver_id,Double lon,Double lat){
        Driver driver=driverMapper.findDriverById(driver_id);
        //先判断司机id是否已在location表里存在，如果不是则先插入一条数据，然后才可以执行更新
        if(driver.getDriver_id()==null){
            driverMapper.insertLocation(driver_id);
        }
        //获取当前系统日期时间
        Date current_time=new Date();
        //更新位置信息
        driverMapper.updateLocation(driver_id,lon,lat,current_time);
    }

    public DriverLocationVO findDriverLocation(String driver_id){
        return driverMapper.findLocationById(driver_id);
    }

    public List<DriverNearbyVo> findNearestDriver(Double lon, Double lat){
        return driverMapper.findNearestDriver(lon,lat);
    }
    public Comment findCommentById(String order_id){
        return orderMapper.findCommentById(order_id);
    }
}
