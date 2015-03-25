package me.gbwl.mp.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;

import me.gbwl.mp.base.Constant;
import me.gbwl.mp.base.Sha1Util;
import me.gbwl.mp.base.StringUtil;
import me.gbwl.mp.base.WXMsg;
import me.gbwl.mp.base.XMLToObject;

/**
 * @Title: TestServlet.java<br>
 * @package: me.gbwl.mp.servlet<br>
 * @Description:TODO<br>
 * @author gbwl<br>
 * @date 2015年1月28日 上午11:01:31<br>
 */
public class TestServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(TestServlet.class);

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		String echostr = request.getParameter("echostr");
		logger.info("signature=" + signature + ";timestamp=" + timestamp + ";nonce=" + nonce + "echostr=" + echostr);
		String[] arr = new String[]{timestamp, Constant.token, nonce};
		Arrays.sort(arr);
		if (Sha1Util.encode("SHA1", arr[0]+arr[1]+arr[2]).equals(signature)) {
			response.getWriter().write(echostr);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		request.setCharacterEncoding("utf8");
		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		String echostr = request.getParameter("echostr");
		logger.info("signature=" + signature + ";timestamp=" + timestamp + ";nonce=" + nonce + "echostr=" + echostr);
		
		try {
			BufferedReader br = request.getReader();
			String brStr = "";
			while (br.read()!=-1) {
				brStr+="<"+br.readLine();
			}
			logger.info("[brStr]="+brStr);
			Document document = DocumentHelper.parseText(brStr);
			WXMsg msg = XMLToObject.createObjfromXML(document, WXMsg.class);
			msg.setContent(StringUtil.isEmpty(msg.getContent())?"":msg.getContent().trim());
			logger.info(msg);
			
			response.getWriter().write("<xml><ToUserName><![CDATA["
						+msg.getFromUserName()+"]]></ToUserName><FromUserName><![CDATA["
						+msg.getToUserName()+"]]></FromUserName><CreateTime>"
						+Calendar.getInstance().getTimeInMillis()+"</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA["
						+"开发中，请稍后...\nhttp://198.98.103.176/images/loading.gif]]></Content></xml>");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
