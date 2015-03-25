package me.gbwl.mp.servlet;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 * @Title: ReceiveMsgServlet.java<br>
 * @package: me.gbwl.mp.servlet<br>
 * @Description:TODO<br>
 * @author gbwl<br>
 * @date 2015年1月28日 上午11:24:03<br>
 */
public class CreateMenuServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(CreateMenuServlet.class);
	private static final String	secret	= "ba47df997d103e766466183570e95f6f";
	private static final String appId	= "wx0fe4e566979c78fe";
	private static Date 		getTime;		// 获取到token的时间
	private static String		access_token;	// token值

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
