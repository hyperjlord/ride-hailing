package top.wx.Mapper;

import top.wx.pojo.Passenger;

public interface UserMapper {

	public Passenger findUserIdIsExist(String userId);

	public void saveUser(Passenger user);

	public Passenger queryUserForLogin(String userId, String password);

}
