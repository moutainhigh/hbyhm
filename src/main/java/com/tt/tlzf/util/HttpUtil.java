package com.tt.tlzf.util;

import com.tt.tlzf.AIPGException;

import javax.net.ssl.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;


/**
 * @Description
 * @Author meixf@allinpay.com
 * @Date 2018年5月24日
 **/
public class HttpUtil {

	private static final String DEF_ENCODING = "GBK";
	
	static{
		//禁用 HttpURLConnection 的重试机制
		System.setProperty("sun.net.http.retryPost", "false");
	}
	
	public static String post(String xml, String url) throws AIPGException {
		OutputStream ostream = null;
		InputStream istream = null;
		try {
			byte[] postData = xml.getBytes(DEF_ENCODING);
			URLConnection conn = createRequest(url, "POST");

			conn.setRequestProperty("Content-type", "application/xml");
			conn.setRequestProperty("Content-length", String.valueOf(postData.length));
			conn.setRequestProperty("Keep-alive", "false");

			ostream = conn.getOutputStream();
			ostream.write(postData);
			ostream.flush();
			ostream.close();

			ByteArrayOutputStream ms = new ByteArrayOutputStream();
			istream = conn.getInputStream();
			byte[] buf = new byte[4096];
			int count;
			while ((count = istream.read(buf, 0, buf.length)) > 0) {
				ms.write(buf, 0, count);
			}
			istream.close();
			return new String(ms.toByteArray(), DEF_ENCODING);
		} catch (Exception ex) {
			throw new AIPGException("HTTP POST出现异常  " + ex.getMessage(), ex);
		} finally {
			try {
				if(ostream != null){
					ostream.close();
				}
				if(istream != null){
					istream.close();
				}
			} catch (IOException e) {
				throw new AIPGException("关闭流出现异常" + e.getMessage(), e);
			}
		}
	}
	
	
	private static URLConnection createRequest(String strUrl, String strMethod) throws Exception {
		URL url = new URL(strUrl);
		// weblogic
		// URL url = new URL(null,strUrl ,new sun.net.www.protocol.https.Handler());
		URLConnection conn = url.openConnection();
		conn.setDoInput(true);
		conn.setDoOutput(true);
		if (conn instanceof HttpsURLConnection) {
			HttpsURLConnection httpsConn = (HttpsURLConnection) conn;
			httpsConn.setRequestMethod(strMethod);
			httpsConn.setSSLSocketFactory(getSSLSocketFactory());
			httpsConn.setHostnameVerifier(hv);
		} else if (conn instanceof HttpURLConnection) {
			HttpURLConnection httpConn = (HttpURLConnection) conn;
			httpConn.setRequestMethod(strMethod);
			httpConn.setChunkedStreamingMode(0);
		}
		return conn;
	}
	
	private static synchronized SSLSocketFactory getSSLSocketFactory() throws Exception {
		if(sslFactory != null) return sslFactory;
		SSLContext sc = SSLContext.getInstance("TLS");
		sc.init(null, new X509TrustManager[] { X509 }, null);
		sslFactory = sc.getSocketFactory();
		return sslFactory;
	}
	
	private static SSLSocketFactory sslFactory = null;
	
	private static X509TrustManager X509 = new X509TrustManager() {
		public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
		}

		public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
		}

		public X509Certificate[] getAcceptedIssuers() {
			return new X509Certificate[0];
		}
	};
	
	private static HostnameVerifier hv = new HostnameVerifier() {
		@Override
		public boolean verify(String hostname, SSLSession session) {
			return true;
		}
		
	};
}
