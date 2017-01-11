package com.breadem.web;



import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.breadem.domain.ClassPhoto;
import com.breadem.domain.User;
import com.breadem.repository.ClassPhotoDao;
import com.breadem.repository.UserService;
import com.breadem.utils.FileUploadUtils;


@Controller
public class PhotoController {
	@Autowired  
    private HttpServletRequest request;  
	@Resource(name = "userservice")
	private  UserService UserService;
	
	@Resource
	private ClassPhotoDao classphotoDao;
	
	@RequestMapping(value="/classPhoto",method=RequestMethod.GET)
	public String photowall(Model model){
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
		
//		Integer id = UserService.getFindById(thislD);
//		List<ClassPhoto> listuser = classphotoDao.findUser(id);
//		model.addAttribute("listuser", listuser);
		
		List<ClassPhoto> list = classphotoDao.fillAll();
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");
        String datetoday = formatter.format(date);
        model.addAttribute("date",datetoday);
		model.addAttribute("list",list);
		
		return "classPhoto";
	}
	
	/*** 
     * 保存文件 
     * @param file 
     * @return 
     */  
    /**
     * @param file	图片文件
     * @return
     */
    private boolean saveFile(MultipartFile file,ClassPhoto photo,Long time) {  
        // 判断文件是否为空  
        if (!file.isEmpty()) {  
            try {  
                // 文件保存路径  
//                String filePath = request.getSession().getServletContext().getRealPath("/") + "upload/"  
//                        + file.getOriginalFilename(); 
            	
//            	String filePath = "D:\\Apache2.2" + "\\htdocs/" + "\\upload/" + time + file.getOriginalFilename();  
//                // 转存文件  
//                file.transferTo(new File(filePath));  
          //      File filePhoto = new File( file.getOriginalFilename());
//                filePhoto.createNewFile();
//                FileOutputStream fos = new FileOutputStream(filePhoto);
//                fos.write(file.getBytes());
//                fos.close();
//            	FileUploadUtils fileUpload = new FileUploadUtils(photo.getAddress(),filePhoto);
//            	fileUpload.upload();
            	FileUploadUtils fileUpload = new FileUploadUtils(photo.getAddress(),file.getBytes());
            	fileUpload.upload();
            	
                classphotoDao.savePhoto(photo);
                return true;  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
        }  
        return false;  
    }  
	
	
//	@RequestMapping(value = "/classPhoto/uploadFile", method = RequestMethod.POST)
//	@ResponseBody
//	public ResponseEntity<?> uploadFile(
//	    @RequestParam("uploadfile") MultipartFile uploadfile) {
//	  
//	  try {
//	    // Get the filename and build the local file path (be sure that the 
//	    // application have write permissions on such directory)
//	    String filename = uploadfile.getOriginalFilename();
//	    String directory = "/var/netgloo_blog/uploads";
//	    String filepath = Paths.get(directory, filename).toString();
//	    
//	    // Save the file locally
//	    BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(filepath)));
//	    stream.write(uploadfile.getBytes());
//	    stream.close();
//	  }
//	  catch (Exception e) {
//	    System.out.println(e.getMessage());
//	    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//	  }
//	  
//	  return new ResponseEntity<>(HttpStatus.OK);
//	} // method uploadFile
    
    @RequestMapping("/classPhoto/uploadFile")  
    @ResponseBody
    public List<ClassPhoto> filesUpload(@RequestParam("files") MultipartFile[] files,
    		@RequestParam("title") String title,@RequestParam("detail") String detail) {  
    	
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
    	User user = UserService.get(thislD);
    	
        //判断file数组不能为空并且长度大于0  
        List<ClassPhoto> listphoto = new ArrayList();
        if(files!=null&&files.length>0){  
            //循环获取file数组中得文件  
            for(int i = 0;i<files.length;i++){  
                MultipartFile file = files[i];  
                Date date = new Date();
                long time = date.getTime();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
                String dateString = formatter.format(date);
                ClassPhoto photo = new ClassPhoto(); 
                photo.setId(UUID.randomUUID().toString().replace("-", ""));
                photo.setTitle(title);
                photo.setRemarks(detail);
                photo.setP_time(dateString);
                photo.setSize((int) file.getSize());
                photo.setAddress(time+file.getOriginalFilename());
                photo.setName(file.getOriginalFilename());
                photo.setSort(time);
                photo.setUser(user);
                photo.setUser_name(user.getUsername());
                listphoto.add(photo);
                //保存文件  
                saveFile(file,photo,time);  
            }  
          
        }  
        
        
        return listphoto;  
    }  
}
