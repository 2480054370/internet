package com.breadem.web;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.breadem.domain.Contact;
import com.breadem.domain.PartyArticle;
import com.breadem.domain.Photo;
import com.breadem.domain.User;
import com.breadem.repository.PartyDAO;
import com.breadem.repository.UserService;
import com.breadem.utils.FileUploadUtils;

@Controller
public class PartyController {
	@Resource
	private PartyDAO partyDao;
	@Resource(name = "userservice")
	private  UserService UserService;


	
	@RequestMapping(value="/party",method=RequestMethod.GET)
	public String show(Model model, HttpServletRequest request){
		String thislD = null;
		if(request.getCookies() != null){
			Cookie[] cookies = request.getCookies();
			for (int i = 0; i < cookies.length; i++) {
				if(cookies[i].getName().equals("test")){
					thislD = cookies[i].getValue();
				}else{
					thislD = null;
				}
			}
		}
		List<User> user = UserService.findProfile(thislD);
		model.addAttribute("posts", user);
		
		Integer id = UserService.getFindById(thislD);
		
		List<PartyArticle> list = partyDao.fillAll(id);
		Contact contact = partyDao.findContact("123");
		model.addAttribute("contact", contact);
		model.addAttribute("size", list.size());
		model.addAttribute("list",list);
		return "party";
	}
	@RequestMapping(value="/search",method=RequestMethod.GET)
	@ResponseBody
	public List<PartyArticle> search(@RequestParam("key")String key){
		List<PartyArticle> list = partyDao.search(key,null);
		return list;
	}
	
	@RequestMapping(value="/DeleteArticle",method=RequestMethod.GET)
	@ResponseBody
	public String deleteArticle(@RequestParam("id")String id){
		partyDao.deleteArticle(id);
		return "party";
	}
	
	@RequestMapping(value="/update",method=RequestMethod.GET)
	@ResponseBody
	public void update(
			@RequestParam("topic")String topic,
			@RequestParam("starttime")String starttime,
			@RequestParam("endtime")String endtime,
			@RequestParam("money")Integer money,
			@RequestParam("note")String note,
			@RequestParam("address")String address,
			@RequestParam("id")String id){
		PartyArticle article = partyDao.fillPartyArticleById(id);
		article.setAddress(address);
		article.setTopic(topic);
		article.setStarttime(starttime);
		article.setEndtime(endtime);
		article.setMoney(money);
		article.setNote(note);
		partyDao.update(article);
	}
	
	@RequestMapping(value="/edit",method=RequestMethod.GET)
	@ResponseBody
	public PartyArticle edit(@RequestParam("id")String id){
		PartyArticle article = partyDao.fillPartyArticleById(id);
		return article;
	}
	
	@RequestMapping(value="/party/addArticle",method=RequestMethod.POST)
	@ResponseBody
	public PartyArticle addArticle(
			@RequestParam("topic")String topic,
			@RequestParam("starttime")String starttime,
			@RequestParam("endtime")String endtime,
			@RequestParam("money")Integer money,
			@RequestParam("note")String note,
			@RequestParam("address")String address,
			Model model,@RequestParam("file")MultipartFile[] listfile, HttpServletRequest request) throws IllegalStateException, IOException{
		
		String thislD = null;
		if(request.getCookies() != null){
			Cookie[] cookies = request.getCookies();
			for (int i = 0; i < cookies.length; i++) {
				if(cookies[i].getName().equals("test")){
					thislD = cookies[i].getValue();
				}else{
					thislD = null;
				}
			}
		}
		
		List<Photo> Photos = new ArrayList<>();
		//设置图片缓存地址
		/*String Path =this.getClass().getClassLoader().getResource("").getPath()+"static/upload/";
		File file = new File(Path);
		//判断文件夹是否存在，不存在就创建文件夹
		if(!file.isDirectory()){
			file.mkdir();
		}*/
		
		if(null==topic&&null==starttime&&null==endtime&&null==money&&null==note&&null==address){
			return new PartyArticle();
		}
		
		PartyArticle partyArticle = SaveArticle(topic, starttime, endtime, money, note, address, thislD);
		for(MultipartFile mfile:listfile){
			String FileName ="party-"+new Date().getTime()+"-" +mfile.getOriginalFilename();
			FileUploadUtils fileUpload = new FileUploadUtils(FileName, mfile.getBytes());
			System.out.println(11111);
         	fileUpload.upload();
			//File fPath = new File(Path+FileName);
			//mfile.transferTo(fPath);
			Photo pt = new Photo();
			pt.setId(UUID.randomUUID().toString().replace("-", ""));
			//pt.setAddress(Path+FileName);
			pt.setName(FileName);
			pt.setSize(mfile.getSize());
			pt.setPartyArticle(partyArticle);
			pt.setFormat(mfile.getContentType());
			Photos.add(pt);
		}
		//Photo pt = new Photo();
		//pt.setId(UUID.randomUUID().toString().replace("-", ""));
		//设置随机id
		partyArticle.setPhotos(Photos);
		partyArticle.setIdeatime(new Date().getTime());
		partyDao.saveArticle(partyArticle);
		return partyArticle;
	}
	public PartyArticle SaveArticle(String topic,String starttime,String endtime,Integer money,String note,String address, String thislD){
		User user = UserService.get(thislD);
		PartyArticle partyArticle = new PartyArticle();
		partyArticle.setAddress(address);
		partyArticle.setEndtime(endtime);
		partyArticle.setMoney(money);
		partyArticle.setNote(note);
		partyArticle.setTopic(topic);
		partyArticle.setStarttime(starttime);
		partyArticle.setId(UUID.randomUUID().toString().replace("-", ""));
		partyArticle.setUser(user);
		return partyArticle;
	}
}
