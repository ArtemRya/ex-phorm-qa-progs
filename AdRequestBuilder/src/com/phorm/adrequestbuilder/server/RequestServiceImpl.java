package com.phorm.adrequestbuilder.server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.impl.cookie.DateUtils;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.phorm.adrequestbuilder.client.RequestService;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class RequestServiceImpl extends RemoteServiceServlet implements RequestService {

	public List<String> executeRequest(String scheme, String host, String path, String params, String method, String clientCookie) throws IllegalArgumentException {
		List<String> answer = new ArrayList<String>();
		
		HttpClient httpclient = new DefaultHttpClient();

		HttpResponse response = null;

		httpclient = WebClientDevWrapper.wrapClient(httpclient);

		HttpHost target = new HttpHost(host, -1, scheme);

		Header[] headers = null;
		HttpEntity entity;

		// Create a local instance of cookie store
		CookieStore cookieStore = new BasicCookieStore();

		// Create local HTTP context
		HttpContext localContext = new BasicHttpContext();
		
		// Bind custom cookie store to the local context
		localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
		
		try {
			// ---------------- Perform Request ------------------

			if (method.equals("GET")) {
				
				String[] cookieFields = null;
				String cookieString = clientCookie;
				if (cookieString == null) cookieString = "";
				if (!cookieString.isEmpty()) {
					cookieFields = ParseCookie(cookieString);
					BasicClientCookie cookie = new BasicClientCookie("uid", cookieFields[0]);
					cookie.setExpiryDate(DateUtils.parseDate(cookieFields[1]));
					cookie.setDomain(cookieFields[2]);
					cookie.setPath(cookieFields[3]);
					cookieStore.addCookie(cookie);
				}
				
				HttpGet get = new HttpGet(path + "?" + params);

				get.addHeader("Content-type", "application/xml");
				get.addHeader("Accept", "*/*");

				response = httpclient.execute(target, get, localContext);

				headers = response.getAllHeaders();
			
				cookieString = GetCookieFromHeaders(headers);

				if (!cookieString.isEmpty()) {
					answer.add(0, cookieString);
				} else {
					answer.add(0, "");
				}
				
				if (response.getStatusLine().toString().contains("HTTP/1.1 200 OK")) {
					entity = response.getEntity();
					answer.add(1, convertStreamToString(entity.getContent()));
				} else {
					answer.add(1, response.getStatusLine().getReasonPhrase());
				}

			} else if (method.equals("POST")) {
				if (GetContentType(headers).contains("application/xml")) {
				} else if (GetContentType(headers).contains("text/plain")) {
					
				} else {

				}
			}

		} catch (ClientProtocolException ex) {
			answer.add(1, ex.getMessage());
		} catch (IOException exx) {
			answer.add(1, exx.getMessage());
		} catch (IllegalStateException exc) {
			answer.add(1, exc.getMessage());
		} catch (TransformerFactoryConfigurationError TFe) {
			answer.add(1, TFe.getMessage());
		} catch (Exception e) {
			answer.add(1, e.getMessage());
		} finally {
			httpclient.getConnectionManager().shutdown();
		}
		
		return answer;
	}
	
	public String requestServer(String method, String scheme, String host,
			String path, String params, String userToken, String userKey,
			String reportParams) throws IllegalArgumentException {

		String serverInfo = getServletContext().getServerInfo();
		String userAgent = getThreadLocalRequest().getHeader("User-Agent");

		// Escape data from the client to avoid cross-site script vulnerabilities.
		userAgent = escapeHtml(userAgent);

		HttpClient httpclient = new DefaultHttpClient();

		DocumentBuilderFactory factory = null;
		DocumentBuilder builder = null;
		Document doc = null;

		HttpResponse response = null;

		httpclient = WebClientDevWrapper.wrapClient(httpclient);

		HttpHost target = new HttpHost(host, -1, scheme);

		Header[] headers = null;
		HttpEntity entity;

		try {
			// ---------------- Perform Request ------------------

			if (method.equals("GET")) {
				HttpGet get = new HttpGet(path + "?" + params);

				String timestamp = getTimestamp();

				get.addHeader("X-Oix-Timestamp", timestamp);

				try {
					get.addHeader("Authorization", "OIX-TIMESTAMP " + userToken + ":" + generateSignature(timestamp, userKey));
				} catch (InvalidKeyException e1) {
					return e1.getMessage();
				} catch (NoSuchAlgorithmException e2) {
					return e2.getMessage();
				}

				get.addHeader("Content-type", "application/xml");
				get.addHeader("Accept", "*/*");

				response = httpclient.execute(target, get);

				if (response.getStatusLine().toString().contains("HTTP/1.1 200 OK")) {

					entity = response.getEntity();

					headers = response.getAllHeaders();

					try {
						factory = DocumentBuilderFactory.newInstance();
						builder = factory.newDocumentBuilder();
					} catch (ParserConfigurationException pex) {
						return pex.getMessage();
					}

					try {
						doc = builder.parse(new InputSource(entity.getContent()));
					} catch (SAXException SAXe) {
						return SAXe.getMessage();
					} catch (IOException IOe) {
						return IOe.getMessage();
					}

					Transformer xformer = TransformerFactory.newInstance().newTransformer();
					xformer.setOutputProperty(OutputKeys.METHOD, "xml");
					xformer.setOutputProperty(OutputKeys.INDENT, "yes");
					xformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "10");
					xformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
					Source source = new DOMSource(doc);
					
					File tempResult = File.createTempFile("PrevResult", ".dat");
					tempResult.deleteOnExit();
				    
					Result result = new StreamResult(tempResult);

					xformer.transform(source, result);

					return ReadXMLFile(tempResult);
				} else {
					return response.getStatusLine().getReasonPhrase();
				}

			} else if (method.equals("POST")) {
				HttpPost post = new HttpPost(path + "?" + params);

				StringEntity reqEntity = new StringEntity(reportParams);
				reqEntity.setContentType("application/xml");
				reqEntity.setChunked(true);

				post.setEntity(reqEntity);

				String timestamp = getTimestamp();

				post.addHeader("X-Oix-Timestamp", timestamp);

				try {
					post.addHeader("Authorization", "OIX-TIMESTAMP " + userToken + ":" + generateSignature(timestamp, userKey));
				} catch (InvalidKeyException e1) {
					e1.printStackTrace();
				} catch (NoSuchAlgorithmException e2) {
					e2.printStackTrace();
				}

				post.setHeader("Content-type", "application/xml");
				post.setHeader("Accept", "*/*");

				response = httpclient.execute(target, post);

				entity = response.getEntity();

				headers = response.getAllHeaders();

				if (GetContentType(headers).contains("application/xml")) {
					try {
						factory = DocumentBuilderFactory.newInstance();
						builder = factory.newDocumentBuilder();
					} catch (ParserConfigurationException pex) {
						
					}

					try {
						doc = builder.parse(new InputSource(entity.getContent()));
					} catch (SAXException SAXe) {
						
					} catch (IOException IOe) {
						
					}

					Transformer xformer = TransformerFactory.newInstance().newTransformer();
					xformer.setOutputProperty(OutputKeys.METHOD, "xml");
					xformer.setOutputProperty(OutputKeys.INDENT, "yes");
					xformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "10");
					xformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
					Source source = new DOMSource(doc);
					Result result = new StreamResult(new File("PrevResult"));

					xformer.transform(source, result);

					File file = new File("PrevResult");
					return ReadXMLFile(file);

				} else if (GetContentType(headers).contains("text/plain")) {
					/*CommandLine cmdLine;
					DefaultExecutor executor = new DefaultExecutor();
					executor.setExitValue(1);

					FileOutputStream fos = null;
					if (parameters.getText().toLowerCase().contains("format=excel")) {
						fos = new FileOutputStream(txtResults.getText() + "/" + txtReportxlsx.getText());
					} else if (parameters.getText().contains("format=CSV")) {
						fos = new FileOutputStream(txtResults.getText() + "/" + txtReportcsv.getText());
						text_1.append(entity.getContent().toString() + "\n");
					} else
						fos = new FileOutputStream("d:/report.txt");
					entity.writeTo(fos);
					if (parameters.getText().toLowerCase().contains("format=csv")) {
						File file = new File(txtResults.getText() + "/" + txtReportcsv.getText());
						textResult.append(ReadXMLFile(file));
					}
					fos.close();
					if (btnOpenReportAutomatically.getSelection() && parameters.getText().contains("format=excel")) {
						cmdLine = CommandLine.parse(txtResultView.getText() + " " + txtResults.getText() + "/" + txtReportxlsx.getText());
						executor.execute(cmdLine);
					}*/
					
				} else {

				}
			}

		} catch (ClientProtocolException ex) {
			return ex.getMessage();
		} catch (IOException exx) {
			return exx.getMessage();
		} catch (IllegalStateException exc) {
			return exc.getMessage();
		} catch (TransformerConfigurationException Te) {
			return Te.getMessage();
		} catch (TransformerFactoryConfigurationError TFe) {
			return TFe.getMessage();
		} catch (TransformerException Texe) {
			return Texe.getMessage();
		} finally {
			// System.out.println(resToken.getStatusLine());
			httpclient.getConnectionManager().shutdown();
			// progressBar.setSelection(0);
		}

		return "Hello, " + method + "!<br><br>I am running " + serverInfo + ".<br><br>It looks like you are using:<br>" + userAgent;
	}

	/**
	 * Escape an html string. Escaping data received from the client helps to
	 * prevent cross-site script vulnerabilities.
	 * 
	 * @param html
	 *            the html string to escape
	 * @return the escaped string
	 */
	private String escapeHtml(String html) {
		if (html == null) {
			return null;
		}
		return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;");
	}

	public static String getTimestamp() {
		long timestamp = System.currentTimeMillis();
		return Long.toString(timestamp);
	}

	public static String generateSignature(String timestamp, String userKey)
			throws NoSuchAlgorithmException, InvalidKeyException {
		byte key[] = DatatypeConverter.parseBase64Binary(userKey);
		SecretKeySpec secretKey = new SecretKeySpec(key, "HmacSHA1");

		Mac mac = Mac.getInstance("HmacSHA1");
		mac.init(secretKey);
		byte bytes[] = String.valueOf(timestamp).getBytes();
		byte signature[] = mac.doFinal(bytes);
		return DatatypeConverter.printBase64Binary(signature);
	}

	public static String ReadXMLFile(File file) throws IOException {
		int len;
		char[] chr = new char[4096];
		final StringBuffer buffer = new StringBuffer();
		final FileReader reader = new FileReader(file);
		try {
			while ((len = reader.read(chr)) > 0) {
				buffer.append(chr, 0, len);
			}
		} finally {
			reader.close();
		}
		return buffer.toString();
	}

	public static String GetContentType(Header[] headers) {
		String ret = null;
		for (int i = 0; i < headers.length; i++) {
			if (headers[i].getName().contains("Content-Type")) {
				ret = headers[i].getValue();
			}
		}
		return ret;
	}

	public static String GetCookieFromHeaders(Header[] headers) {
		String ret = "";
		for (int i = 0; i < headers.length; i++) {
			if (headers[i].getName().contains("Set-Cookie")) {
				ret = headers[i].getValue();
			}
		}
		return ret;
	}
	
	public static String convertStreamToString(InputStream is) throws Exception {
	    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
	    StringBuilder sb = new StringBuilder();
	    String line = null;
	    while ((line = reader.readLine()) != null) {
	      sb.append(line + "\n");
	    }
	    is.close();
	    return sb.toString();
	  }

	public static void printHeader(Header[] headers) {
		for (int i = 0; i < headers.length; i++) {
			System.out.println(headers[i].toString() + "\n");
		}
	}

	public static void SaveCookie(String host, String cookie) {
		System.setProperty(host, cookie);
	}
	
	public static String RestoreCookie(String host) {
		return System.getProperty(host, "");
	}
	
	public static String ClearCookie(String host) {
		return System.clearProperty(host);
	}
	
	public static String[] ParseCookie(String cookie) {
		String[] values = cookie.split(";");
		String[] ret = new String[4];
		
		for (String str : values) {
			if (str.contains("uid=")) {
				ret[0] = str.substring(4);
			} else if (str.contains("expires=")) {
				ret[1] = str.substring(9);
			} else if (str.contains("domain=")) {
				ret[2] = str.substring(8);
			} else if (str.contains("path=")) {
				ret[3] = str.substring(6);
			}
		}
				
		return ret;
	}
	
	@Override
	public String GetCookie(String host) {
		String uid = "";
		String temp = "";
		
		temp = RestoreCookie(host);
		uid = ParseCookie(temp)[0];
		
		return uid;
	}
	
	@Override
	public String EraseCookie(String host) {
		return ClearCookie(host);
	}
}
