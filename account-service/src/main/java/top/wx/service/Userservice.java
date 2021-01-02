package top.wx.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

import top.wx.Mapper.UserMapper;
import top.wx.pojo.Passenger;
import top.wx.pojo.Driver;
import top.wx.pojo.Car;

@Service
public class Userservice {
	
	@Autowired
	private UserMapper userMapper;
	
	public boolean findUserIdIsExist(String userId) {
		Passenger user=userMapper.findUserIdIsExist(userId);
		return user == null ? false : true;
	}

	public boolean findDriverIdIsExist(String driverId) {
		Driver driver=userMapper.findDriverIdIsExist(driverId);
		return driver == null ? false : true;
	}
	public void saveUser(Passenger user) {
		//user.setUserId(UUID.randomUUID().toString());
		userMapper.saveUser(user);
	}

	public void saveDriver(Driver driver) {
		userMapper.saveDriver(driver);
	}

	public void saveCar(Car car) {
		userMapper.saveCar(car);
	}

	public void saveDriverPicUrl(Driver driver){ userMapper.saveDriverPicUrl(driver); }

	public void savePassengerPicUrl(Passenger passenger){ userMapper.savePassengerPicUrl(passenger); }

	public Passenger queryUserForLogin(String userId, String password) {
		return userMapper.queryUserForLogin(userId,password);
	}

	public Driver queryDriverForLogin(String driverId, String password) {
		return userMapper.queryDriverForLogin(driverId,password);
	}

	public Passenger getPassengerInfo(String userId){
		return userMapper.getPassengerInfo(userId);
	}

	public Driver  getDriverInfo(String driverId){
		return userMapper.getDriverInfo(driverId);
	}
}
