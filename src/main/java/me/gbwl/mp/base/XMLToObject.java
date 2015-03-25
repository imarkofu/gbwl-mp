package me.gbwl.mp.base;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class XMLToObject {

	/**
	 * 测试，输出对象与xml
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		String xmlStr = "<xml><ToUserName><![CDATA[gh_659359dfc722]]></ToUserName><FromUserName><![CDATA[oWovyt1OlyPEiAqx3h0s2_rq1fD4]]></FromUserName><CreateTime>1384744707</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[吧[<adfsfs>]]]></Content><MsgId>5947433229874103383</MsgId></xml>";
		try {
			Document document = DocumentHelper.parseText(xmlStr);

			WXMsg msg = createObjfromXML(document, WXMsg.class);
			System.out.println(msg);
		} catch (DocumentException e) {
			e.printStackTrace();
		}

		// outputXML(createDocument(), System.out);
	}

	/**
	 * 根据给定的document生成指定的对象
	 * 
	 * @param document
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T createObjfromXML(Document document, Class<T> clazz) {
		XStream xstream = new XStream(new DomDriver());
		// 要将xml解析为那个对象
		xstream.processAnnotations(clazz);
		// 1.2.2用法
		// Annotations.configureAliases(xstream, clazz);
		// 也可以接收一个InputStream来作为输入
		// xstream.fromXML(input);
		return (T) xstream.fromXML(document.asXML());
	}

	/**
	 * 输出XML
	 * 
	 * @param document
	 * @param stream
	 */
	public static void outputXML(Document document, PrintStream stream) {
		PrintWriter out = new PrintWriter(stream);
		OutputFormat format = OutputFormat.createPrettyPrint();
		XMLWriter writer = new XMLWriter(out, format);
		try {
			writer.write(document);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
