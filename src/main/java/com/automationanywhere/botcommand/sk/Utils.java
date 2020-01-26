package com.automationanywhere.botcommand.sk;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

public class Utils {

	
	  private static final Logger logger = LogManager.getLogger(Utils.class);
	
	public static HashMap<String,String> getLearningInstances(IQBotConnection connection) throws Exception {
		HashMap<String,String> lis = new HashMap<String, String>();
		
		URL url = new URL(connection.url+"/IQBot/api/projects");
	

		String result = GETRequest(url,connection.token);
		if (!result.contains("GET NOT WORKED")) {
			
			JSONObject json = new JSONObject(result);
			if (json.getBoolean("success")) {
				JSONArray data = (JSONArray)json.get("data");
				data.forEach(item -> {
					JSONObject jsonobj = (JSONObject) item;
		    	    String id = jsonobj.getString("id");
		    	    String name = jsonobj.getString("name");
		    	    lis.put(name, id);
				});
				
			}
			
		}
		
		return lis;
	}

	
	public static HashMap<String,Integer> getLearningInstanceDetails(IQBotConnection connection,String li_id) throws Exception {
	
		HashMap<String,Integer> details = new HashMap<String,Integer>();
		
		URL url = new URL(connection.url+"/IQBot/api/projects/"+li_id+"/detail-summary");

		String result = GETRequest(url,connection.token);
		if (!result.contains("GET NOT WORKED")) {
			
			JSONObject json = new JSONObject(result);
			if (json.getBoolean("success")) {
				JSONObject data = (JSONObject)json.get("data");
				
				String[] keys = JSONObject.getNames(data);
				
				for (int i = 0; i < keys.length; i++) {
					  Integer value= data.getInt(keys[i]);
			    	    details.put(keys[i], value);
				}				
			}
			
		}
		
		return details;
	}

	
	
	
	
	
	
	public static Integer getValidationsinQueue(IQBotConnection connection,String li_id) throws Exception {
		Integer validations = 0 ;
		
		URL url = new URL(connection.url+"/IQBot/api/validator/"+li_id);

		String result = GETRequest(url,connection.token);
		if (!result.contains("GET NOT WORKED")) {
			JSONObject json = new JSONObject(result);
			if (json.getBoolean("success"))  {
				JSONObject data = (JSONObject)json.get("data");
				validations = data.getInt("remainingDocumentReviewCount");
				
			}
			
		}
		
		return validations;
	}
	
	
	public static Integer getNofOfOutputFiles(IQBotConnection connection,String li_id, String doctype) throws Exception {
		Integer noofFiles = 0 ;
		
		URL url = new URL(connection.url+"/IQBot/gateway/learning-instances/"+li_id+"/files/list?docStatus="+doctype);

		String result = GETRequest(url,connection.token);
		if (!result.contains("GET NOT WORKED")) {
			JSONArray json = new JSONArray(result);
			noofFiles = json.length();
		}
		
		return noofFiles;
	}	
	
	
	public static String  POSTRequest(URL posturl, String token, JSONObject body) throws IOException  {

		int responseCode = 0;
	    HttpURLConnection postConnection = null;
		try {
			postConnection = (HttpURLConnection) posturl.openConnection();
		    postConnection.setRequestMethod("POST");
	        if (token != null)
	        {
	    	    postConnection.setRequestProperty("x-authorization", token);
	        }


		    postConnection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
		    postConnection.setDoOutput(true);
		    OutputStream os = postConnection.getOutputStream();

		    os.write(body.toString().getBytes());
		    
		    os.flush();

		    os.close();

		    responseCode = postConnection.getResponseCode();
		    
		} catch (IOException e) {
			 logger.info("Ex : "+e.toString());
  
		}

	    if (responseCode == HttpURLConnection.HTTP_OK) { //success
	    	
	 

	        BufferedReader in = new BufferedReader(new InputStreamReader(

	            postConnection.getInputStream(),StandardCharsets.UTF_8.name()));

	        String inputLine;

	        StringBuffer response = new StringBuffer();

	        while ((inputLine = in .readLine()) != null) {

	            response.append(inputLine);

	        } in .close();

	        
	        return  response.toString() ;

	    } else {
	    	
	        return "POST NOT WORKED"+Integer.valueOf(responseCode).toString();

	    }

	}
	

	
	public static String  GETRequest(URL geturl, String token) throws IOException  {

		int responseCode = 0; 
	    HttpURLConnection getConnection = null;
		try {
			getConnection = (HttpURLConnection) geturl.openConnection();
		    getConnection.setRequestMethod("GET");
	        if (token != null)
	        {
	    	    getConnection.setRequestProperty("x-authorization", token);
	        }


		    getConnection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
	

		    responseCode = getConnection.getResponseCode();
		    
		} catch (IOException e) {
		    logger.info("Ex : "+e.toString());
  
		}

		
	    if (responseCode == HttpURLConnection.HTTP_OK) { //success
	    	

	        BufferedReader in = new BufferedReader(new InputStreamReader(

	            getConnection.getInputStream(),StandardCharsets.UTF_8.name()));

	        String inputLine;

	        StringBuffer response = new StringBuffer();

	        while ((inputLine = in .readLine()) != null) {

	            response.append(inputLine);

	        } 
	        in .close();
	        return  response.toString() ;

	    } else {
	    	
	        return "GET NOT WORKED"+Integer.valueOf(responseCode).toString();

	    }

	}


}
