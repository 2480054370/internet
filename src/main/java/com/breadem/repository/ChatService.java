package com.breadem.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.breadem.domain.Chat;
import com.breadem.domain.User;

@Component("chatservice")
@Transactional
public class ChatService {
	@Autowired
	private ChatDao chatDao;

	public void SaveChat(Chat message){
		chatDao.SaveChat(message);
	}
}
