package com.breadem.web;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.aspectj.weaver.ast.Var;
import org.hibernate.annotations.Check;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.breadem.utils.FileUploadUtils;
import com.breadem.utils.RedisConfig;
import com.breadem.domain.GetUser;
import com.breadem.domain.User;
import com.mysql.fabric.Response;
import com.breadem.repository.UserService;

@Controller
@RequestMapping("/")
public class LoginController {
	@Resource(name = "userservice")
	private UserService UserService;
	private Integer number;
	@Resource(name = "redisconfig")
	private RedisConfig redisconfig;
	@Autowired
	// 邮箱发送
	private JavaMailSender mailSender;

	// 账户名
	private String studentlD;
	// redis初始键值
	private Integer session_number = 0;

	// @RequestMapping(value = "/login", method = RequestMethod.GET)
	// public String getUser(String account, String pwd) {
	// List<String> list= UserService.getUsername();
	// for(String item : list){
	// if(item.equals(account)){
	// String getUserpwd = UserService.getPwd(item);
	// if(getUserpwd == null){
	// System.out.println("pwd is error");
	// }else{
	// System.out.println("login success");
	// return "redirect:/";
	// }
	// break;
	// }else{
	// System.out.println("account is null");
	// }
	// }
	// //UserService.check(account, pwd);
	// return "login";
	// }

	@RequestMapping("/")
	public String loginHtml(Model model, HttpServletRequest request) {
		String thislD = null;
		if (request.getCookies() != null) {
			Cookie[] cookies = request.getCookies();
			for (int i = 0; i < cookies.length; i++) {
				if (cookies[i].getName().equals("test")) {
					thislD = cookies[i].getValue();
				} else {
					thislD = null;
				}
			}
		}
		List<User> user = UserService.findProfile(thislD);
		model.addAttribute("posts", user);
		return "index";
	}

	@RequestMapping("/login")
	public String indexHtml(HttpServletRequest request, HttpServletResponse response) {
		return "login";
	}

	// @RequestMapping("/party")
	// public String Party(){
	// return "party";
	// }

	@RequestMapping("/profile")
	public String Profile(Model model, HttpServletRequest request) {
		String thislD = null;
		if (request.getCookies() != null) {
			Cookie[] cookies = request.getCookies();
			for (int i = 0; i < cookies.length; i++) {
				if (cookies[i].getName().equals("test")) {
					thislD = cookies[i].getValue();
				} else {
					thislD = null;
				}
			}
		}
		List<User> user = UserService.findProfile(thislD);
		model.addAttribute("posts", user);
		return "myProfile";
	}

	@RequestMapping("/config")
	public String Config(Model model, HttpServletRequest request) {
		String thislD = null;
		if (request.getCookies() != null) {
			Cookie[] cookies = request.getCookies();
			for (int i = 0; i < cookies.length; i++) {
				if (cookies[i].getName().equals("test")) {
					thislD = cookies[i].getValue();
				} else {
					thislD = null;
				}
			}
		}
		List<User> user = UserService.findProfile(thislD);
		model.addAttribute("posts", user);
		return "myConfig";
	}

	@RequestMapping(value = "/index_session", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> checkindex(HttpServletRequest request, HttpServletResponse response) {
		/*
		 * 加上redis判断，先取消
		 */
		// String haveSession = null;
		// Map<String,Object> map = new HashMap<String,Object>();
		// //System.out.println(redisconfig.get("session"));
		// //if(!redisconfig.exists("session")){
		// if(!redisconfig.exists("session" + session_number)){
		// map.put("msg", "no session");
		// }else if(request.getCookies() != null){
		// Cookie[] cookies = request.getCookies();
		// for (int i = 0; i < cookies.length; i++) {
		// if(cookies[i].getName().equals("test")){
		// haveSession = cookies[i].getValue();
		// break;
		// }else{
		// haveSession = "none";
		// }
		// //System.out.println(cookies[i].getName());
		// //System.out.println(cookies[i].getValue());
		// }
		// System.out.println(haveSession);
		// for(Integer i = 1; i <= session_number; i++ ){
		// if(redisconfig.get("session" + i) != null){
		// if(haveSession.equals(redisconfig.get("session" + i))){
		// map.put("msg", "have session");
		// System.out.println("success");
		// }
		// }else if(i == session_number){
		// map.put("msg", "no session");
		// System.out.println("error");
		// }
		// }
		//// if(haveSession.equals(redisSession)){
		//// map.put("msg", "have session");
		//// System.out.println("success");
		//// }else {
		//// map.put("msg", "no session");
		//// System.out.println("error");
		//// }
		// }else{
		// map.put("msg", "no session");
		// System.out.println("exit");
		// }
		// return map;

		Map<String, Object> map = new HashMap<String, Object>();
		String thislD = null;
		if (request.getCookies() != null) {
			Cookie[] cookies = request.getCookies();
			for (int i = 0; i < cookies.length; i++) {
				if (cookies[i].getName().equals("test")) {
					thislD = cookies[i].getValue();
				} else {
					thislD = null;
				}

			}
		}
		if (thislD != null) {
			map.put("msg", "have session");
		} else {
			map.put("msg", "no session");
		}
		return map;
	}

	// @RequestMapping(value = "/party_session", method = RequestMethod.POST)
	// public @ResponseBody Map<String, Object> checkparty(HttpServletRequest
	// request){
	// Map<String,Object> map = new HashMap<String,Object>();
	// String thislD = null;
	// if(request.getCookies() != null){
	// Cookie[] cookies = request.getCookies();
	// for (int i = 0; i < cookies.length; i++) {
	// if(cookies[i].getName().equals("test")){
	// thislD = cookies[i].getValue();
	// }else{
	// thislD = null;
	// }
	//
	// }
	// }
	// if(thislD != null){
	// map.put("msg", "have session");
	// }else{
	// map.put("msg", "no session");
	// }
	// return map;
	// }
	//
	// @RequestMapping(value = "/photo_session", method = RequestMethod.POST)
	// public @ResponseBody Map<String, Object> checkphoto(HttpServletRequest
	// request){
	// Map<String,Object> map = new HashMap<String,Object>();
	// String thislD = null;
	// if(request.getCookies() != null){
	// Cookie[] cookies = request.getCookies();
	// for (int i = 0; i < cookies.length; i++) {
	// if(cookies[i].getName().equals("test")){
	// thislD = cookies[i].getValue();
	// }else{
	// thislD = null;
	// }
	//
	// }
	// }
	// if(thislD != null){
	// map.put("msg", "have session");
	// }else{
	// map.put("msg", "no session");
	// }
	// return map;
	// }
	//
	// @RequestMapping(value = "/profile_session", method = RequestMethod.POST)
	// public @ResponseBody Map<String, Object> checkprofile(HttpServletRequest
	// request){
	// Map<String,Object> map = new HashMap<String,Object>();
	// String thislD = null;
	// if(request.getCookies() != null){
	// Cookie[] cookies = request.getCookies();
	// for (int i = 0; i < cookies.length; i++) {
	// if(cookies[i].getName().equals("test")){
	// thislD = cookies[i].getValue();
	// }else{
	// thislD = null;
	// }
	// }
	// }
	// if(thislD != null){
	// map.put("msg", "have session");
	// }else{
	// map.put("msg", "no session");
	// }
	// return map;
	// }
	//
	// @RequestMapping(value = "/config_session", method = RequestMethod.POST)
	// public @ResponseBody Map<String, Object> checkconfig(HttpServletRequest
	// request){
	// Map<String,Object> map = new HashMap<String,Object>();
	// String thislD = null;
	// if(request.getCookies() != null){
	// Cookie[] cookies = request.getCookies();
	// for (int i = 0; i < cookies.length; i++) {
	// if(cookies[i].getName().equals("test")){
	// thislD = cookies[i].getValue();
	// }else{
	// thislD = null;
	// }
	// }
	// }
	// if(thislD != null){
	// map.put("msg", "have session");
	// }else{
	// map.put("msg", "no session");
	// }
	// return map;
	// }

	@RequestMapping(value = "/profile_authentication", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> updateAuthentication(HttpServletRequest request,
			@RequestBody GetUser getuser) {
		Map<String, Object> map = new HashMap<String, Object>();
		String thislD = null;
		if (request.getCookies() != null) {
			Cookie[] cookies = request.getCookies();
			for (int i = 0; i < cookies.length; i++) {
				if (cookies[i].getName().equals("test")) {
					thislD = cookies[i].getValue();
				} else {
					thislD = null;
				}
			}
		}
		if (thislD != null) {
			User user = UserService.get(thislD);
			user.setPhonenumber(getuser.getPhone());
			user.setUsermail(getuser.getEmail());
			UserService.update(user);
			map.put("phone", getuser.getPhone());
			map.put("email", getuser.getEmail());
			map.put("msg", "have session");
		} else {
			map.put("msg", "no session");
		}

		return map;
	}

	@RequestMapping(value = "/profile_updatepassword", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> updatepassword(HttpServletRequest request, @RequestBody GetUser getuser) {
		Map<String, Object> map = new HashMap<String, Object>();
		String thislD = null;
		if (request.getCookies() != null) {
			Cookie[] cookies = request.getCookies();
			for (int i = 0; i < cookies.length; i++) {
				if (cookies[i].getName().equals("test")) {
					thislD = cookies[i].getValue();
				} else {
					thislD = null;
				}
			}
		}
		if (thislD != null) {
			map.put("msg", "have session");
			String getUserpwd = UserService.getPwd(thislD);
			if (getUserpwd != null & getUserpwd.equals(getuser.getOld_password())) {
				User user = UserService.get(thislD);
				user.setUserpwd(getuser.getNew_password());
				UserService.update(user);
				map.put("data", "success");
			} else {
				map.put("data", "error");
			}
		} else {
			map.put("msg", "no session");
		}

		return map;
	}

	@RequestMapping(value = "/profile_basic", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> updateBasic(HttpServletRequest request, @RequestBody GetUser getuser) {
		Map<String, Object> map = new HashMap<String, Object>();
		String thislD = null;
		if (request.getCookies() != null) {
			Cookie[] cookies = request.getCookies();
			for (int i = 0; i < cookies.length; i++) {
				thislD = cookies[i].getValue();
			}
		}
		if (thislD != null) {
			User user = UserService.get(thislD);
			user.setAddress(getuser.getAddress());
			user.setUsername(getuser.getName());
			user.setBirthday(getuser.getBirth());
			user.setHobby(getuser.getHobby());
			user.setSaying(getuser.getSign());
			user.setSex(getuser.getSex());
			UserService.update(user);
			map.put("address", getuser.getAddress());
			map.put("name", getuser.getName());
			map.put("birth", getuser.getBirth());
			map.put("hobby", getuser.getHobby());
			map.put("sign", getuser.getSign());
			map.put("sex", getuser.getSex());
			map.put("msg", "have session");
		} else {
			map.put("msg", "no session");
		}

		return map;
	}

	@RequestMapping(value = "/config_infoconfig", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> config_infoconfig(HttpServletRequest request,
			@RequestBody GetUser getuser) {
		Map<String, Object> map = new HashMap<String, Object>();
		String thislD = null;
		if (request.getCookies() != null) {
			Cookie[] cookies = request.getCookies();
			for (int i = 0; i < cookies.length; i++) {
				if (cookies[i].getName().equals("test")) {
					thislD = cookies[i].getValue();
				} else {
					thislD = null;
				}
			}
		}
		if (thislD != null) {
			User user = UserService.get(thislD);
			user.setCfphonenumber(getuser.getCfphonenumber());
			user.setCfaddress(getuser.getCfaddress());
			user.setCfbirthday(getuser.getCfbirthday());
			user.setCfsex(getuser.getCfsex());
			user.setCfhobby(getuser.getCfhobby());
			UserService.update(user);
			map.put("msg", "have session");
		} else {
			map.put("msg", "no session");
		}

		return map;
	}

	@RequestMapping(value = "/config_messagenotify", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> config_messagenotify(HttpServletRequest request,
			@RequestBody GetUser getuser) {
		Map<String, Object> map = new HashMap<String, Object>();
		String thislD = null;
		if (request.getCookies() != null) {
			Cookie[] cookies = request.getCookies();
			for (int i = 0; i < cookies.length; i++) {
				if (cookies[i].getName().equals("test")) {
					thislD = cookies[i].getValue();
				} else {
					thislD = null;
				}
			}
		}
		if (thislD != null) {
			User user = UserService.get(thislD);
			user.setCfinfo(getuser.getCfinfo());
			user.setCfphoto(getuser.getCfphoto());
			user.setCfreport(getuser.getCfreport());
			user.setCfmail(getuser.getCfmail());
			UserService.update(user);
			map.put("msg", "have session");
		} else {
			map.put("msg", "no session");
		}

		return map;
	}

	@RequestMapping(value = "/login_check", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> login_check(HttpServletRequest request, HttpServletResponse response,
			@RequestBody GetUser getuser) throws IOException {
		// System.out.println(request.getParameter("account"));
		// System.out.println(request.getParameter("pwd"));
		Map<String, Object> map = new HashMap<String, Object>();
		List<String> list = UserService.getStudentlD();
		if (list.isEmpty()) {
			map.put("msg", "account error");
		} else {
			for (String item : list) {
				if (item.equals(getuser.getAccount())) {
					String getUserpwd = UserService.getPwd(item);
					if (getuser.getPwd() != "" && !getUserpwd.equals((getuser.getPwd()))) {
						System.out.println("pwd is error");
						map.put("msg", "pwd error");
					} else if (getUserpwd.equals(getuser.getPwd())) {
						session_number++;
						studentlD = getuser.getAccount();
						Cookie cookie = new Cookie("test", studentlD);
						cookie.setMaxAge(86400); // 设置cookie的过期时间
						response.addCookie(cookie);
						System.out.println("login success");
						map.put("msg", "login success");
						map.put("sessionId", studentlD);
						// request.getSession().setMaxInactiveInterval(60);
						redisconfig.set("session" + session_number, map.get("sessionId"), (long) 86400);
					}
					break;
				} else if (item == list.get(list.size() - 1)) {
					System.out.println("account is error");
					map.put("msg", "account error");
				}
			}

		}
		System.out.println(map);
		// UserService.check(account, pwd);
		return map;
	}

	@RequestMapping(value = "/register_check", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> register_check(HttpServletRequest request, HttpServletResponse response,
			@RequestBody GetUser getuser) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<String> list = UserService.getStudentlD();
		if (list.isEmpty()) {
			User user = new User();
			user.setUsermail(getuser.getRemail());
			user.setstudentlD(getuser.getReaccount());
			user.setUserpwd(getuser.getRepwd());
			// 设置config默认值
			user.setCfaddress("公开");
			user.setCfbirthday("公开");
			user.setCfhobby("公开");
			user.setCfphonenumber("公开");
			user.setCfsex("公开");
			user.setCfreport("false");
			user.setCfphoto("false");
			user.setCfmail("false");
			user.setCfinfo("false");
			// 设置头像背景默认值
			user.setHeadimgfile("default-avatar");
			user.setBackimgfile("default-banner");
			UserService.register(user);
			map.put("msg", "register success");
		} else {
			for (String item : list) {

				if (item.equals(getuser.getReaccount())) {
					map.put("msg", "reaccount error");
					break;
				} else if (item == list.get(list.size() - 1) & getuser.getRepwd().equals(getuser.getRerepwd())) {
					User user = new User();
					user.setUsermail(getuser.getRemail());
					user.setstudentlD(getuser.getReaccount());
					user.setUserpwd(getuser.getRepwd());
					// 设置config默认值
					user.setCfaddress("公开");
					user.setCfbirthday("公开");
					user.setCfhobby("公开");
					user.setCfphonenumber("公开");
					user.setCfsex("公开");
					user.setCfreport("false");
					user.setCfphoto("false");
					user.setCfmail("false");
					user.setCfinfo("false");
					// 设置头像背景默认值
					user.setHeadimgfile("default-avatar");
					user.setBackimgfile("default-banner");
					UserService.register(user);
					map.put("msg", "register success");
				}
			}
		}
		return map;
	}

	// 发送邮箱验证
	@RequestMapping(value = "/send_email", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> send_email(HttpServletRequest request, HttpServletResponse response,
			@RequestBody GetUser getuser) {
		Map<String, Object> map = new HashMap<String, Object>();
		String findEmail = getuser.getFindemail();
		// 随机生成六位字符串
		String randomSt = "";
		String a = "0123456789abcdefghijklmnopqrstuvwxyz";
		char[] rands = new char[6];
		for (int i = 0; i < rands.length; i++) {
			int rand = (int) (Math.random() * a.length());
			rands[i] = a.charAt(rand);
		}
		for (int i = 0; i < rands.length; i++) {
			randomSt += rands[i];
		}
		String Thisaccount = UserService.getEmailUser(findEmail);
		if (Thisaccount == null) {
			map.put("msg", "account not exist");
		} else {
			// 发送邮件
			try {
				SimpleMailMessage message = new SimpleMailMessage();
				message.setFrom("breadem@aliyun.com");
				message.setTo(findEmail);
				message.setSubject("Bem  找回密码");
				message.setText("您的验证码为：" + randomSt + "，请将此验证码巴拉巴拉巴拉。");
				mailSender.send(message);

				// 保存到数据库
				User user = UserService.get(Thisaccount);
				user.setVercode(randomSt);
				UserService.update(user);
				map.put("msg", "send success");
			} catch (Exception ex) {
				map.put("msg", "send error");
			}
		}
		return map;
	}

	// 验证验证码
	@RequestMapping(value = "/check_vercode", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> check_vercode(HttpServletRequest request, HttpServletResponse response,
			@RequestBody GetUser getuser) {
		Map<String, Object> map = new HashMap<String, Object>();
		String findEmail = getuser.getFindemail();
		String ThisVerCode = getuser.getVercode();
		String Thisaccount = UserService.getEmailUser(findEmail);
		String SerVerCode = UserService.getUserCode(Thisaccount);
		if (ThisVerCode.equals(SerVerCode)) {
			map.put("account", Thisaccount);
			// 从数据库删除验证码
			User user = UserService.get(Thisaccount);
			user.setVercode("");
			UserService.update(user);
			map.put("msg", "code success");
		} else {
			map.put("msg", "code error");
		}
		return map;
	}

	// 验证密码
	@RequestMapping(value = "/check_updatepwd", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> check_updatepwd(HttpServletRequest request, HttpServletResponse response,
			@RequestBody GetUser getuser) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (getuser.getCuppwd().equals(getuser.getCuprepwd())) {
			User user = UserService.get(getuser.getUpaccount());
			user.setUserpwd(getuser.getCuppwd());
			UserService.update(user);
			map.put("msg", "update success");
		} else {
			map.put("msg", "update error");
		}
		return map;
	}

	@RequestMapping(value = "exit", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> exit(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		String thislD = null;
		if (request.getCookies() != null) {
			Cookie[] cookies = request.getCookies();
			for (int i = 0; i < cookies.length; i++) {
				if (cookies[i].getName().equals("test")) {
					thislD = cookies[i].getValue();
				} else {
					thislD = null;
				}
			}
		}
		if (thislD != null) {
			// 删除cookies
			Cookie cookie = new Cookie("test", null);
			cookie.setMaxAge(0);
			response.addCookie(cookie);
			map.put("msg", "success exit");
		} else {
			map.put("msg", "no session");
		}
		return map;
	}

	// 上传头像
	@RequestMapping(value = "/uploadAvatar", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> handleFileUpload(
			@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request,
			HttpServletResponse response) {
		// MultipartHttpServletRequest multipartRequest =
		// (MultipartHttpServletRequest) request;
		// //获取上传的文件
		// MultipartFile file = multipartRequest.getFile("file");
		Date date = new Date();
		long time = date.getTime();
		String thislD = null;
		if (request.getCookies() != null) {
			Cookie[] cookies = request.getCookies();
			for (int i = 0; i < cookies.length; i++) {
				if (cookies[i].getName().equals("test")) {
					thislD = cookies[i].getValue();
				} else {
					thislD = null;
				}
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		if (!file.isEmpty()) {
			try {
				// String filePath = "D:\\Apache2.2" + "\\htdocs/" + "\\upload/"
				// + file.getOriginalFilename();
				// file.transferTo(new File(filePath));
				FileUploadUtils fileUpload = new FileUploadUtils(
						"avatar" + "-" + time + "-" + file.getOriginalFilename(), file.getBytes());
				fileUpload.upload();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				map.put("msg", "上传失败");
				return map;
			} catch (IOException e) {
				e.printStackTrace();
				map.put("msg", "上传失败");
				return map;
			}
			User user = UserService.get(thislD);
			user.setHeadimgfile("avatar" + "-" + time + "-" + file.getOriginalFilename());
			UserService.update(user);
			map.put("msg", "上传成功");
			map.put("file", "avatar" + "-" + time + "-" + file.getOriginalFilename());
			return map;
		} else {
			map.put("msg", "上传失败，因为文件是空的.");
			return map;
		}
	}

	@RequestMapping(value = "/uploadbanner", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> uploadbanner(
			@RequestParam(value = "file2", required = false) MultipartFile file, HttpServletRequest request,
			HttpServletResponse response) {
		// MultipartHttpServletRequest multipartRequest =
		// (MultipartHttpServletRequest) request;
		// //获取上传的文件
		// MultipartFile file = multipartRequest.getFile("file");
		Date date = new Date();
		long time = date.getTime();
		String thislD = null;
		if (request.getCookies() != null) {
			Cookie[] cookies = request.getCookies();
			for (int i = 0; i < cookies.length; i++) {
				if (cookies[i].getName().equals("test")) {
					thislD = cookies[i].getValue();
				} else {
					thislD = null;
				}
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		if (!file.isEmpty()) {
			try {
				// String filePath = "D:\\Apache2.2" + "\\htdocs/" + "\\upload/"
				// + file.getOriginalFilename();
				// file.transferTo(new File(filePath));
				FileUploadUtils fileUpload = new FileUploadUtils(
						"banner" + "-" + time + "-" + file.getOriginalFilename(), file.getBytes());
				fileUpload.upload();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				map.put("msg", "上传失败");
				return map;
			} catch (IOException e) {
				e.printStackTrace();
				map.put("msg", "上传失败");
				return map;
			}
			User user = UserService.get(thislD);
			user.setBackimgfile("banner" + "-" + time + "-" + file.getOriginalFilename());
			UserService.update(user);
			map.put("msg", "上传成功");
			map.put("file", "banner" + "-" + time + "-" + file.getOriginalFilename());
			return map;
		} else {
			map.put("msg", "上传失败，因为文件是空的.");
			return map;
		}
	}
}