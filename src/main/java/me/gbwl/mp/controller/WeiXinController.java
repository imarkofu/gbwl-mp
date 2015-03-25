package me.gbwl.mp.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.gbwl.mp.base.Constant;
import me.gbwl.mp.base.Sha1Util;
import me.gbwl.mp.base.WXMsg;
import me.gbwl.mp.base.XMLToObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Title: WeiXinController.java<br>
 * @package: me.gbwl.mp.controller<br>
 * @Description:TODO<br>
 * @author gbwl<br>
 * @date 2015年1月29日 上午11:14:07<br>
 */
@Controller
@RequestMapping(value="/mp")
public class WeiXinController {

	private static final Logger logger = Logger.getLogger(WeiXinController.class);
	
	@RequestMapping(value="/clovec.do", method=RequestMethod.GET)
	public void clovecGet(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=utf-8");
		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		String echostr = request.getParameter("echostr");
		logger.info("[signature]="+signature+",[timestamp]="+timestamp+",[nonce]="+nonce+",[echostr]="+echostr);
		if(signature!=null&&!signature.isEmpty()&&timestamp!=null&&!timestamp.isEmpty()&&nonce!=null&&!nonce.isEmpty()){
			String[] arr = {Constant.token,timestamp,nonce};
			Arrays.sort(arr);
			String tempStr = "";
			for (int i = 0; i < arr.length; i++) {
				tempStr += arr[i];
			}
			String digest = Sha1Util.encode("SHA1", tempStr);
			logger.info("[digest]="+digest);
			if(digest.equals(signature)){
				try {
					PrintWriter out = response.getWriter();
					out.write(echostr);
					out.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	@RequestMapping(value="/clovec.do", method=RequestMethod.POST)
	public void clovecPost(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=utf-8");
		String signature = request.getParameter("signature"); 
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		String echostr = request.getParameter("echostr");
		logger.info("[signature]="+signature+",[timestamp]="+timestamp+",[nonce]="+nonce+",[echostr]="+echostr);
		try {
			BufferedReader br = request.getReader();
			String brStr = "";
			while (br.read()!=-1) {
				brStr+="<"+br.readLine();
			}
			logger.info("[brStr]="+brStr);
			Document document = DocumentHelper.parseText(brStr);
		
			WXMsg msg = XMLToObject.createObjfromXML(document, WXMsg.class);
			msg.setContent(StringUtils.isEmpty(msg.getContent())?"":msg.getContent().trim());
			logger.info(msg);
			if("event".equals(msg.getMsgType())&&"unsubscribe".equals(msg.getEvent())){
				logger.info("取消关注事件");
			} else if ("event".equals(msg.getMsgType())&&"subscribe".equals(msg.getEvent())) {
				logger.info("关注事件");
				response.getWriter().write("<xml><ToUserName><![CDATA["
						+msg.getFromUserName()+"]]></ToUserName><FromUserName><![CDATA["
						+msg.getToUserName()+"]]></FromUserName><CreateTime>"
						+Calendar.getInstance().getTimeInMillis()+"</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA["
						+"谢谢关注，/拥抱\n\n客官有任何问题，都可以给我们留言，我们会在最迟一天内，给予您回复！]]></Content></xml>");
				response.getWriter().flush();
			} else {
				response.getWriter().write("<xml><ToUserName><![CDATA["
						+msg.getFromUserName()+"]]></ToUserName><FromUserName><![CDATA["
						+msg.getToUserName()+"]]></FromUserName><CreateTime>"
						+Calendar.getInstance().getTimeInMillis()+"</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA["
						+"您的留言我们已收到，我们将尽快给予您回复，请稍等/咖啡]]></Content></xml>");
				response.getWriter().flush();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
