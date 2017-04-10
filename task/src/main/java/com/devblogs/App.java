package com.devblogs;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

import com.devblogs.task1.MyObject;
import com.devblogs.task1.MyObjectChild;

public class App {
	private static String alias = "appman1";
	private static String secret = "gromit";
	private static int statusCode;
	private static String content;
	private static String address = "https://api-upstream03.us-east-1.devops-int.avid.com/auth/sso/login/oauth2/ropc/myavid";
	private static String login = "gvorobey10+45@gmail.com";
	//private static String login = "ua.danwer@gmail.com";
	private static String password = "Boroda12";
	
	public static void main(String [] args) {
		place1();
		place2();
	}
	
	public static void place1() {
		MyObject myObject = new MyObject();
		System.out.println(myObject.getData());
	}
	
	public static void place2() {
		MyObject myObject = new MyObjectChild();
		System.out.println(myObject.getData());
	}

	public static void main1(String[] args) {
		try {
			String base64Encode = Base64.encode((alias + ":" + secret).getBytes());

			HttpClient client = HttpClientBuilder.create().build();
			HttpPost post = new HttpPost(address);

			post.addHeader("Authorization", "Basic " + base64Encode);
			post.addHeader("Content-Type", "application/x-www-form-urlencoded");
			
			List<NameValuePair> parameters = new ArrayList<NameValuePair>();
			
			parameters.add(new BasicNameValuePair("grant_type", "password"));
			parameters.add(new BasicNameValuePair("username", login));
			parameters.add(new BasicNameValuePair("password", password));
			
			post.setEntity(new UrlEncodedFormEntity(parameters));

			HttpResponse response = client.execute(post);
			statusCode = response.getStatusLine().getStatusCode();

			System.out.println(statusCode + "");

			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			StringBuffer result = new StringBuffer();
			String line = "";
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
			content = result.toString();

			System.out.println(content);
		} catch (Exception e) {
			e.printStackTrace();
			statusCode = 500;
			System.out.println(statusCode);
		}
	}
}