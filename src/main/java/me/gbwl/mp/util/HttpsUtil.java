package me.gbwl.mp.util;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpMethodRetryHandler;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnRouteParams;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.cookie.ClientCookie;
import org.apache.http.cookie.CookieSpec;
import org.apache.http.cookie.CookieSpecFactory;
import org.apache.http.cookie.MalformedCookieException;
import org.apache.http.cookie.SetCookie;
import org.apache.http.entity.mime.FormBodyPart;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.BasicExpiresHandler;
import org.apache.http.impl.cookie.BrowserCompatSpec;
import org.apache.http.params.HttpParams;

import com.alibaba.fastjson.JSON;

/**
 * @Title: HttpsUtil.java<br>
 * @package: me.gbwl.main.util<br>
 * @Description:TODO<br>
 * @author gbwl<br>
 * @date 2015年3月20日 上午9:40:58<br>
 */
public class HttpsUtil {

	public DefaultHttpClient client = new DefaultHttpClient();
	public static final String HEAD_USER_AGENT = "User-Agent";
	public static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/28.0.1500.95 Safari/537.36 AlexaToolbar/alxg-3.1";
	public static final String HEAD_REFER = "Referer";
	public static final String PROXY_HOST = "127.0.0.1";
	public static final int PROXY_PORT = 8087;
	public static final Pattern REF_REG = Pattern
			.compile("(^[a-zA-Z]+://[^/]+)[/]?");

	public static final String[] DATE_PATTERNS = new String[] {
			"EEE, dd MMM yyyy HH:mm:ss zzz", "EEE, dd-MMM-yy HH:mm:ss zzz",
			"EEE MMM d HH:mm:ss yyyy", "EEE, dd-MMM-yyyy HH:mm:ss z",
			"EEE, dd-MMM-yyyy HH-mm-ss z", "EEE, dd MMM yy HH:mm:ss z",
			"EEE dd-MMM-yyyy HH:mm:ss z", "EEE dd MMM yyyy HH:mm:ss z",
			"EEE dd-MMM-yyyy HH-mm-ss z", "EEE dd-MMM-yy HH:mm:ss z",
			"EEE dd MMM yy HH:mm:ss z", "EEE,dd-MMM-yy HH:mm:ss z",
			"EEE,dd-MMM-yyyy HH:mm:ss z", "EEE, dd-MM-yyyy HH:mm:ss z",
			"E, dd-MMM-yyyy HH:mm:ss zzz", "EEEE, dd-MMM-yy HH:mm:ss zzz" };

	public HttpsUtil() {
		client.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new HttpMethodRetryHandler() {

					@Override
					public boolean retryMethod(HttpMethod method,
							IOException arg, int count) {
						return count <= 1 ? true : false;
					}
				});
		class LenientCookieSpec extends BrowserCompatSpec {
			public LenientCookieSpec() {
				super();
				registerAttribHandler(ClientCookie.EXPIRES_ATTR,
						new BasicExpiresHandler(DATE_PATTERNS) {
							@Override
							public void parse(SetCookie cookie, String value)
									throws MalformedCookieException {
								value = StringUtils.replace(value, "\"", "");
								super.parse(cookie, value);
							}
						});
			}
		}
		client.getCookieSpecs().register("chinasource",
				new CookieSpecFactory() {
					public CookieSpec newInstance(HttpParams params) {
						return new LenientCookieSpec();
					}
				});
		client.getParams().setParameter(ClientPNames.COOKIE_POLICY,
				"chinasource");
		client.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 30000);
	}

	public InputStream get(String url) throws Exception {
		return get(url, null);
	}

	public InputStream get(String url, HttpParams params) throws Exception {
		if (StringUtils.isBlank(url))
			return null;
		HttpGet get = new HttpGet(url);
		try {
			get.addHeader(HEAD_USER_AGENT, USER_AGENT);
			get.addHeader(HEAD_REFER, refer(url));

			if (null != params)
				get.setParams(params);
			HttpResponse response = client.execute(get);
			if (null != response) {
				int code = response.getStatusLine().getStatusCode();
				HttpEntity entity = response.getEntity();
				if (code == HttpStatus.SC_OK && null != entity) {
					InputStream in = entity.getContent();
					try {
						return IOUtils.toBufferedInputStream(entity
								.getContent());
					} finally {
						IOUtils.closeQuietly(in);
					}
				}
				if (code == HttpStatus.SC_MOVED_TEMPORARILY
						|| code == HttpStatus.SC_MOVED_PERMANENTLY
						|| code == HttpStatus.SC_SEE_OTHER
						|| code == HttpStatus.SC_TEMPORARY_REDIRECT) {
					String nurl = response.getLastHeader("Location").getValue();
					System.out.println(nurl);
					return get(nurl, null);
				}
			}
		} finally {
			get.releaseConnection();
			client.getConnectionManager().shutdown();
		}
		return null;
	}

	public InputStream post(String url, FormBodyPart... parts) throws Exception {
		if (StringUtils.isBlank(url))
			return null;
		HttpPost post = new HttpPost(url);
		try {
			post.addHeader(HEAD_USER_AGENT, USER_AGENT);
			post.addHeader(HEAD_REFER, refer(url));
			MultipartEntity enty = new MultipartEntity();
			if (null != parts)
				for (FormBodyPart part : parts)
					enty.addPart(part);
			post.setEntity(enty);
			HttpResponse response = client.execute(post);
			if (null != response) {
				int code = response.getStatusLine().getStatusCode();
				HttpEntity entity = response.getEntity();
				if (code == HttpStatus.SC_OK && null != entity) {
					InputStream in = entity.getContent();
					try {

						return IOUtils.toBufferedInputStream(entity
								.getContent());
					} finally {
						IOUtils.closeQuietly(in);
					}
				}
			}
		} finally {
			post.releaseConnection();
			client.getConnectionManager().shutdown();
		}
		return null;
	}

	public static String refer(String url) {
		Matcher matcher = REF_REG.matcher(url);
		while (matcher.find()) {
			return matcher.group();
		}
		return "";
	}

	@SuppressWarnings("deprecation")
	public HttpsUtil proxy() {
		try {
			SSLContext ctx = SSLContext.getInstance("TLS");
			X509TrustManager tm = new X509TrustManager() {

				@Override
				public void checkClientTrusted(X509Certificate[] arg0,
						String arg1) throws CertificateException {
					// TODO Auto-generated method stub

				}

				@Override
				public void checkServerTrusted(X509Certificate[] arg0,
						String arg1) throws CertificateException {
					// TODO Auto-generated method stub

				}

				@Override
				public X509Certificate[] getAcceptedIssuers() {
					// TODO Auto-generated method stub
					return null;
				}
			};
			try {
				ctx.init(null, new TrustManager[] { tm }, null);
			} catch (KeyManagementException e) {
				e.printStackTrace();
			}
			SSLSocketFactory ssf = new SSLSocketFactory(ctx);
			ssf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			ClientConnectionManager ccm = client.getConnectionManager();
			SchemeRegistry sr = ccm.getSchemeRegistry();
			sr.register(new Scheme("https", ssf, 443));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		HttpHost proxy = new HttpHost(PROXY_HOST, PROXY_PORT);
		client.getParams().setParameter(ConnRouteParams.DEFAULT_PROXY, proxy);
		return this;
	}

	public static void main(String[] args) throws IOException, Exception {
		// HttpsUtil httpsUtil = new HttpsUtil();
		String access_token = "";
		String json = IOUtils
				.toString(new HttpsUtil()
						.get("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wx0fe4e566979c78fe&secret=ba47df997d103e766466183570e95f6f"));
		System.out.println("json=" + json);
		Map<?, ?> map = JSON.parseObject(json, Map.class);
		for (Map.Entry<?, ?> entry : map.entrySet()) {
			if (entry.getKey().equals("access_token")) {
				System.out.println("access_token=" + entry.getValue());
				access_token = entry.getValue().toString();
			} else if (entry.getKey().equals("expires_in")) {
				System.out.println("expires_in=" + entry.getValue());
			}
		}
		String result = IOUtils
				.toString(new HttpsUtil()
						.post("https://api.weixin.qq.com/cgi-bin/menu/create",
								new FormBodyPart("access_token",
										new StringBody(access_token)),
								new FormBodyPart(
										"body",
										new StringBody(
												"{\"button\":[{\"type\":\"view\",\"name\":\"个人主页\",\"url\":\"http://www.imarkofu.com/\"},{\"name\":\"博客目录\",\"sub_button\":[{\"type\":\"view\",\"name\":\"Java\",\"url\":\"http://www.imarkofu.com/\"},{\"type\":\"view\",\"name\":\"人生\",\"url\":\"http://www.imarkofu.com/\"},{\"type\":\"click\",\"name\":\"赞一下\",\"key\":\"V1001_GOOD\"}]},{\"view\":\"view\",\"name\":\"关于\",\"url\":\"http://www.imarkofu.com/\"}]}"))));
		System.out.println("result=" + result);
	}
}
