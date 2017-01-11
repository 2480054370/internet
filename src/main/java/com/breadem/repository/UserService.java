package com.breadem.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.breadem.domain.User;



@Component("userservice")
@Transactional
public class UserService {
	@Autowired
	private UserDao userDao;

	
//	public List<String> check(String uname, String upwd){
//		return userDao.check(uname, upwd);
//	}
	
	public Integer getFindById(String studentlD){
		return userDao.getFindById(studentlD);
	}
	
	public List<String> getStudentlD(){
		return userDao.getStudentlD();
	}
	
	public String getPwd(String studentlD){
		return userDao.getPwd(studentlD);
	}
	
	public List<User> findProfile(String studentlD){
		return userDao.findProfile(studentlD);
	}
	
	public User get(String studentlD){
		return userDao.get(studentlD);
	}
	
	public void update(User user){
		userDao.update(user);
	}
	
	public void register(User user){
		userDao.register(user);
	}

	public String getEmailUser(String email){
		return userDao.getEmailUser(email);
	}
	
	public String getUserCode(String user){
		return userDao.getUserCode(user);
	}
}
