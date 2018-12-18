package project4;

import java.io.IOException;
import java.util.Scanner;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONArray;
import org.json.JSONObject;

public class Translate 
{   public static String getURL(String query,String language)
	{
	     String APIkey="trnsl.1.1.20181114T194712Z.e0e9572a7b9ff0ee.029626697caa42da6d27b0ef5b9f11013066d63f";
	     String translatequery="https://translate.yandex.net/api/v1.5/tr.json/translate?key="+APIkey+"&text="+query+"&lang="+language+"&format=plain";
	     return translatequery;
	}
	public static String translatelanguage(String url) throws UnsupportedOperationException, IOException
	 {      JSONObject json;
	     
		    HttpGet htptpget=new HttpGet(url);
			HttpClient httpclient = HttpClientBuilder.create().build();
		    HttpResponse response;
			response=httpclient.execute(htptpget);
			if(response.getEntity()==null)
	 	{
	 		json=null;
	 		System.out.println("Hello");
	 	}
	 	else 
	 	{   String jsonresponse="";
	 		HttpEntity entity=response.getEntity();
	 		Scanner sc=new Scanner(entity.getContent());
	 	    while(sc.hasNext())
	 	    {
	 	    	jsonresponse=jsonresponse+sc.nextLine();
	 	    }
	 	    
	 		sc.close();
	 		json=new JSONObject(jsonresponse);
	 	}
			JSONArray jsonarray=json.getJSONArray("text");
	        return jsonarray.getString(0);
	 }


}
