package top.wx.Mapper;

import top.wx.pojo.Passenger;
import top.wx.pojo.Driver;

public interface UserMapper {

	public Passenger findUserIdIsExist(String userId);

	public Driver findDriverIdIsExist(String driverId);

	public void saveUser(Passenger user);

	public void saveDriver(Driver driver);

	public Passenger queryUserForLogin(String userId, String password);

	public Driver queryDriverForLogin(String driverId, String password);

}
