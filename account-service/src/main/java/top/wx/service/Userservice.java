package top.wx.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import top.wx.Mapper.UserMapper;
import top.wx.pojo.Passenger;
import top.wx.pojo.Driver;

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

	public Passenger queryUserForLogin(String userId, String password) {
		return userMapper.queryUserForLogin(userId,password);
	}

	public Driver queryDriverForLogin(String driverId, String password) {
		return userMapper.queryDriverForLogin(driverId,password);
	}
}
