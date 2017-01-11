package com.breadem.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "User")
public class User {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id; // 主键自增Id
	
	@Column(nullable = false)
	private String studentlD;  //学号
	private String username;   //用户名
	@Column(nullable = false)
	private String userpwd;    //密码
	private String usermail;   //邮箱
	private String phonenumber;//手机号
	private String address;	   //地址
	private String sex;        //性别
	private String birthday;   //生日
	private String hobby;      //爱好
	private String saying;     //宣言
	private String vercode;	   //验证码
	private String headimgfile;//头像图片路径
	private String backimgfile;//背景图片路径
	
	private String cfphonenumber;
	private String cfaddress;
	private String cfbirthday;
	private String cfsex;
	private String cfhobby;
	private String cfinfo;
	private String cfphoto;
	private String cfreport;
	private String cfmail;
	
	@OneToMany(mappedBy = "user",cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
	private List<PartyArticle> list;
	
	@OneToMany(mappedBy = "user",cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
	private List<ClassPhoto> plist;
	
	@JsonManagedReference
	public List<PartyArticle> getList() {
		return list;
	}
	public void setList(List<PartyArticle> list) {
		this.list = list;
	}
	public List<ClassPhoto> getPlist() {
		return plist;
	}
	public void setPlist(List<ClassPhoto> plist) {
		this.plist = plist;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getstudentlD() {
		return studentlD;
	}
	public void setstudentlD(String studentlD) {
		this.studentlD = studentlD;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUserpwd() {
		return userpwd;
	}
	public void setUserpwd(String userpwd) {
		this.userpwd = userpwd;
	}
	public String getUsermail() {
		return usermail;
	}
	public void setUsermail(String usermail) {
		this.usermail = usermail;
	}
	public String getPhonenumber() {
		return phonenumber;
	}
	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getHobby() {
		return hobby;
	}
	public void setHobby(String hobby) {
		this.hobby = hobby;
	}
	public String getSaying() {
		return saying;
	}
	public void setSaying(String saying) {
		this.saying = saying;
	}
	public String getVercode() {
		return vercode;
	}
	public void setVercode(String vercode) {
		this.vercode = vercode;
	}
	public String getCfphonenumber() {
		return cfphonenumber;
	}
	public void setCfphonenumber(String cfphonenumber) {
		this.cfphonenumber = cfphonenumber;
	}
	public String getCfaddress() {
		return cfaddress;
	}
	public void setCfaddress(String cfaddress) {
		this.cfaddress = cfaddress;
	}
	public String getCfbirthday() {
		return cfbirthday;
	}
	public void setCfbirthday(String cfbirthday) {
		this.cfbirthday = cfbirthday;
	}
	public String getCfsex() {
		return cfsex;
	}
	public void setCfsex(String cfsex) {
		this.cfsex = cfsex;
	}
	public String getCfhobby() {
		return cfhobby;
	}
	public void setCfhobby(String cfhobby) {
		this.cfhobby = cfhobby;
	}
	public String getCfinfo() {
		return cfinfo;
	}
	public void setCfinfo(String cfinfo) {
		this.cfinfo = cfinfo;
	}
	public String getCfphoto() {
		return cfphoto;
	}
	public void setCfphoto(String cfphoto) {
		this.cfphoto = cfphoto;
	}
	public String getCfreport() {
		return cfreport;
	}
	public void setCfreport(String cfreport) {
		this.cfreport = cfreport;
	}
	public String getCfmail() {
		return cfmail;
	}
	public void setCfmail(String cfmail) {
		this.cfmail = cfmail;
	}
	public String getHeadimgfile() {
		return headimgfile;
	}
	public void setHeadimgfile(String headimgfile) {
		this.headimgfile = headimgfile;
	}
	public String getBackimgfile() {
		return backimgfile;
	}
	public void setBackimgfile(String backimgfile) {
		this.backimgfile = backimgfile;
	}

	
	
	
}
