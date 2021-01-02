package top.wx.Mapper;

import java.util.List;

import top.wx.pojo.Passenger;
import top.wx.pojo.Driver;
import top.wx.pojo.Car;

public interface UserMapper {

	public Passenger findUserIdIsExist(String userId);

	public Driver findDriverIdIsExist(String driverId);

	public void saveUser(Passenger user);

	public void saveDriver(Driver driver);

	public void saveCar(Car car);

	public void saveDriverPicUrl(Driver driver);

	public void savePassengerPicUrl(Passenger passenger);

	public Passenger queryUserForLogin(String userId, String password);

	public Driver queryDriverForLogin(String driverId, String password);

	public Passenger getPassengerInfo(String userId);

	public Driver getDriverInfo(String driverId);

}
