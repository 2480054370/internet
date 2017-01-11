package com.breadem.web;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.breadem.domain.Chat;
import com.breadem.domain.GetChat;
import com.breadem.domain.GetUser;
import com.breadem.domain.User;
import com.breadem.repository.ChatService;
import com.breadem.utils.WebSocketConfig;

@Controller
public class GreetingController {
	@Resource(name = "chatservice")
	private ChatService chatservice;

	@Resource
    private SimpMessagingTemplate simpMessagingTemplate; 
    
    @MessageMapping("/" + "201300")
    public void greeting(GetChat message) throws Exception{
    	Thread.sleep(1000); // simulated delay
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("Inname", message.getInputname());
        map.put("Ouname", message.getOutputname());
        map.put("message", message.getMessage());
        this.simpMessagingTemplate.convertAndSend("/topic/" + "123456", map);
    }
    
    @RequestMapping("/chatRoom")
	public String ChatIndex(Model model, HttpServletRequest request){
    	return "chatRoom";
    }
    
    @RequestMapping(value = "/chat_check", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> chat_check(HttpServletRequest request,HttpServletResponse response,@RequestBody GetChat getchat){
		Map<String,Object> map = new HashMap<String,Object>();
		Chat message = new Chat();
		message.setInputname(getchat.getInputname());
		message.setOutputname(getchat.getOutputname());
		message.setMessage(getchat.getMessage());
		chatservice.SaveChat(message);
		map.put("msg", "success");
		return map;
	}

}
