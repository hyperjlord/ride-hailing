package top.wx.controller;

import io.swagger.annotations.ApiParam;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.ApiOperation;

import top.wx.common.JsonResult;
import top.wx.pojo.Passenger;
import top.wx.pojo.Driver;
import top.wx.pojo.Car;
import top.wx.service.Userservice;


@RestController
public class UserController {
	
	@Autowired
	private Userservice userservice;
		
	//乘客注册
	@ResponseBody
	@ApiOperation(value = "乘客注册", notes = "乘客注册")
	@RequestMapping(value = "/register", headers = {
			"content-type=application/json" }, consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public JsonResult register(@ApiParam(value ="乘客信息" ) @RequestBody Passenger user) {

		//System.out.println("LOADING……");
		//System.out.println(user.getUserId());
		//System.out.println(user.getPassword());
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
	@ApiOperation(value = "司机注册", notes = "司机注册")
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
	@ApiOperation(value = "乘客登录", notes = "乘客登录")
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
	@ApiOperation(value = "司机登录", notes = "司机登录")
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

	//上传车辆信息
	@ApiOperation(value = "上传车辆信息", notes = "上传车辆信息")
	@ResponseBody
	@RequestMapping(value = "/uploadCar", headers = {
			"content-type=application/json" }, consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public JsonResult uploadCar(@RequestBody Car car) {

		//判断车辆信息不为空
		//车辆图片可以为空
		if(StringUtils.isBlank(car.getDriverId()) || StringUtils.isBlank(car.getCarNumber()) || StringUtils.isBlank(car.getBrand()) || StringUtils.isBlank(car.getModel()) || StringUtils.isBlank(car.getColor()) ) {
			if(StringUtils.isBlank(car.getDriverId())) {
				return JsonResult.errorMsg("司机信息不能为空");
			}
			if(StringUtils.isBlank(car.getCarNumber())) {
				return JsonResult.errorMsg("车牌号不能为空");
			}
			if(StringUtils.isBlank(car.getBrand())) {
				return JsonResult.errorMsg("车辆品牌不能为空");
			}
			if(StringUtils.isBlank(car.getModel())) {
				return JsonResult.errorMsg("车辆型号不能为空");
			}
			if(StringUtils.isBlank(car.getColor())) {
				return JsonResult.errorMsg("车辆颜色不能为空");
			}
		}

		//判断司机id是否存在  返回值类型为Car
		if(userservice.findDriverIdIsExist(car.getDriverId())) {
			userservice.saveCar(car);
		}else {
			return JsonResult.errorMsg("司机不存在");
		}
		return JsonResult.buildData(car);
	}

	//上传司机头像
	@ApiOperation(value = "上传司机头像", notes = "上传司机头像")
	@ResponseBody
	@RequestMapping(value = "/uploadDriverIcon", headers = {
			"content-type=application/json" }, consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public JsonResult uploadDriverIcon(@RequestBody Driver driver) {
		//判断头像url不为空
		if (StringUtils.isBlank(driver.getPicUrl())) {
			return JsonResult.errorMsg("头像url不能为空");
		}

		//判断司机id是否存在 储存头像
		if (userservice.findDriverIdIsExist(driver.getDriverId())) {
			userservice.saveDriverPicUrl(driver);
		} else {
			return JsonResult.errorMsg("司机不存在");
		}
		return JsonResult.buildData(driver);
	}

	//上传乘客头像
	@ApiOperation(value = "上传乘客头像", notes = "上传乘客头像")
	@ResponseBody
	@RequestMapping(value = "/uploadPassengerIcon", headers = {
			"content-type=application/json" }, consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public JsonResult uploadPassengerIcon(@RequestBody Passenger passenger) {
		//判断头像url不为空
		if (StringUtils.isBlank(passenger.getPicUrl())) {
			return JsonResult.errorMsg("头像url不能为空");
		}

		//判断乘客id是否存在 储存头像
		if (userservice.findDriverIdIsExist(passenger.getUserId())) {
			userservice.savePassengerPicUrl(passenger);
		} else {
			return JsonResult.errorMsg("乘客不存在");
		}
		return JsonResult.buildData(passenger);
	}

	//根据司机id查询司机信息
	@ApiOperation(value = "根据司机id查询司机信息", notes = "根据司机id查询司机信息")
	@ResponseBody
	@RequestMapping(value = "/InquireDriverInfo", headers = {
			"content-type=application/json" }, consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public JsonResult InquireDriverInfo(@RequestBody Driver driver) {
		String id = driver.getDriverId();

		//判断用户id和密码不为空
		if(StringUtils.isBlank(id)) {
			return JsonResult.errorMsg("账号不能为空");
		}

		Driver driverReslut=userservice.getDriverInfo(id);
		return JsonResult.buildData(driverReslut);
	}

	//根据司机id查询司机信息
	@ApiOperation(value = "根据乘客id查询乘客信息", notes = "根据乘客id查询乘客信息")
	@ResponseBody
	@RequestMapping(value = "/InquirePassengerInfo", headers = {
			"content-type=application/json" }, consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public JsonResult InquirePassengerInfo(@RequestBody Passenger passenger) {
		String id = passenger.getUserId();

		//判断用户id和密码不为空
		if(StringUtils.isBlank(id)) {
			return JsonResult.errorMsg("账号不能为空");
		}

		Passenger passengerResult=userservice.getPassengerInfo(id);
		return JsonResult.buildData(passengerResult);
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

