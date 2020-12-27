package top.wx.controller;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import top.wx.common.JsonResult;
import top.wx.pojo.Passenger;
import top.wx.pojo.Driver;
import top.wx.service.Userservice;


@RestController
public class UserController {
	
	@Autowired
	private Userservice userservice;
		
	//乘客注册
	//@PostMapping("/register")
	@ResponseBody
	@RequestMapping(value = "/register", headers = {
			"content-type=application/json" }, consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public JsonResult register(@RequestBody Passenger user) {

		//System.out.println("LOADING……");
		//System.out.println(user.getUserId());
		//System.out.println(user.getPassword());
		//System.out.println(user.getName());
		//System.out.println(user.getSex());
		//System.out.println(user.getBalance());
		//判断用户id和密码不为空
		if(StringUtils.isBlank(user.getUserId()) || StringUtils.isBlank(user.getPassword()) ) {
			return JsonResult.errorMsg("账号和密码不能为空");
		}
			
		//判断用户id是否存在
		if(!userservice.findUserIdIsExist(user.getUserId())) {
			userservice.saveUser(user);
		}else {
			return JsonResult.errorMsg("账号已存在");
		}
		user.setPassword("");//不显示密码	
		return JsonResult.buildData(user);
	}

	//司机注册
	//@PostMapping("/registerDriver")
	@ResponseBody
	@RequestMapping(value = "/registerDriver", headers = {
			"content-type=application/json" }, consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public JsonResult registerDriver(@RequestBody Driver driver) {

		//System.out.println("LOADING……");
		//判断用户id和密码不为空
		if(StringUtils.isBlank(driver.getDriverId()) || StringUtils.isBlank(driver.getPassword()) ) {
			return JsonResult.errorMsg("账号和密码不能为空");
		}

		//判断用户id是否存在
		if(!userservice.findDriverIdIsExist(driver.getDriverId())) {
			userservice.saveDriver(driver);
		}else {
			return JsonResult.errorMsg("账号已存在");
		}
		driver.setPassword("");//不显示密码
		return JsonResult.buildData(driver);
	}

	//乘客登录
	@ResponseBody
	@RequestMapping(value = "/login", headers = {
			"content-type=application/json" }, consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public JsonResult login(@RequestBody Passenger user) {
		String id = user.getUserId();
		String password = user.getPassword();
		
		//判断用户id和密码不为空
		if(StringUtils.isBlank(id) || StringUtils.isBlank(password) ) {
			return JsonResult.errorMsg("账号和密码不能为空");
		}
			
		//判断用户id是否存在  返回值类型为User
		Passenger userReslut=userservice.queryUserForLogin(id,password);
		if(userReslut != null) {
			userReslut.setPassword("");
			return JsonResult.buildData(userReslut);
		}else {
			return JsonResult.errorMsg("账号或密码不正确");
		}		
	}

	//司机登录
	@ResponseBody
	@RequestMapping(value = "/loginDriver", headers = {
			"content-type=application/json" }, consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public JsonResult loginDriver(@RequestBody Driver driver) {
		String id = driver.getDriverId();
		String password = driver.getPassword();

		//判断用户id和密码不为空
		if(StringUtils.isBlank(id) || StringUtils.isBlank(password) ) {
			return JsonResult.errorMsg("账号和密码不能为空");
		}

		//判断用户id是否存在  返回值类型为User
		Driver driverReslut=userservice.queryDriverForLogin(id,password);
		if(driverReslut != null) {
			driverReslut.setPassword("");
			return JsonResult.buildData(driverReslut);
		}else {
			return JsonResult.errorMsg("账号或密码不正确");
		}
	}
	
	//微信登录
	/*@PostMapping("/wxLogin")
	public JsonResult wxLogin(String code) {

		System.out.println("code:" + code);
//		登录凭证校验。通过 wx.login 接口获得临时登录凭证 code 后传到开发者服务器调用此接口完成登录流程
//		请求地址
//		GET https://api.weixin.qq.com/sns/jscode2session?
//				appid=APPID&
//				secret=SECRET&
//				js_code=JSCODE&
//				grant_type=authorization_code

		String url = "https://api.weixin.qq.com/sns/jscode2session";
		Map<String, String> param = new HashMap<>();
		param.put("appid", "appid");
		param.put("secret", "开发者秘钥");
		param.put("js_code", code);
		param.put("grant_type", "authorization_code");

		//发起get请求
		String wxResult = HttpClientUtil.doGet(url, param);
		System.out.println(wxResult);

		return JsonResult.buildData("微信登录成功");
	 */
	}

