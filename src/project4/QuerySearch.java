package project4;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONArray;
import org.json.JSONObject;

public class QuerySearch 
{
	public static JSONObject getJson(String query,String from) throws UnsupportedOperationException, IOException
	{
		//String baseurl="http://localhost:8983/solr/IRF18P1/select?&start="+from+"&rows=10&indent=on&q="+query+"&wt=json";
		JSONObject json;
 	    String jsonresponse="";
 	    // Add deftype=dismax in query url to add dismax query parser 
 	    //Add qf=text_en^2 text_de^5 text_ru^10 in query url to boost the fields
 	   String encodequery="";
 	  if(query.contains("&fq"))
 	  {
 	   String splitquery[]=query.split("&");
 	   System.out.println(splitquery[0]);
 	   if(splitquery[1].contains("social unrest")||splitquery[1].contains("mexico city"))
 	     {
 		   String splitfq[]=splitquery[1].split(":");
 		   
 		   splitquery[1]=splitfq[0]+":"+URLEncoder.encode(splitfq[1],"UTF-8");
 	     }
 	   
 	   encodequery =URLEncoder.encode(splitquery[0],"UTF-8");
 	   encodequery=encodequery+"&"+splitquery[1];
 	   
 	  }
 	  else
 	  {
 		 encodequery=URLEncoder.encode(query,"UTF-8");
 	  }
 	    String baseurl="http://localhost:8983/solr/IRF18P1/select?&start="+from+"&rows=10&indent=on&q="+encodequery+"&wt=json";
 		
 	    HttpGet htptpget=new HttpGet(baseurl);
 		HttpClient httpclient = HttpClientBuilder.create().build();
 	    HttpResponse response;
 		response=httpclient.execute(htptpget);
     	if(response.getEntity()==null)
     	{
     		json=null;
     		System.out.println("Hello");
     	}
     	else 
     	{   
     		HttpEntity entity=response.getEntity();
     		System.out.println(entity.getContentEncoding());
     		Scanner sc=new Scanner(entity.getContent(),"UTF-8");
     	    while(sc.hasNext())
     	    {  
     	    	jsonresponse=jsonresponse+sc.nextLine();
     	    }
     	    
     		sc.close();
     		json=new JSONObject(jsonresponse);
     		System.out.println(json);
     	}
     	return json;
	}
	public static List<String> getResults(JSONObject json) throws UnsupportedEncodingException
	{   JSONObject response=json.getJSONObject("response");
		JSONArray jsonarray=response.getJSONArray("docs");
		List<String> data=new ArrayList<String>();
		System.out.println(jsonarray.length());
		/*if(to<jsonarray.length())
		   {
			 for (int i=from; i<to; i++)
			 {   JSONArray tweetarray=new JSONArray();
				 JSONObject tweetdata=jsonarray.getJSONObject(i);
				 System.out.println(tweetdata);
				 String date=tweetdata.getJSONArray("tweet_date").getString(0);
				 String text=tweetdata.getJSONArray("tweet_text").getString(0);
				 String id=tweetdata.getString("id");
				 data.add(date);
				 data.add(text);
				 data.add(id);
			  }
		   }
		else
		{*/ String text="";
			for (int i=0; i<jsonarray.length(); i++)
			 {
				JSONObject tweetdata=jsonarray.getJSONObject(i);
				 String date=tweetdata.getJSONArray("tweet_date").getString(0);
				  text=tweetdata.getJSONArray("tweet_text").getString(0);
				 String id=tweetdata.getJSONArray("tweet_id").get(0).toString();
				 String username=tweetdata.getJSONArray("username").get(0).toString();
				 String handleid=tweetdata.getJSONArray("handleid").get(0).toString();
				 String handlename=tweetdata.getJSONArray("handlename").get(0).toString();
				 String profile=tweetdata.getJSONArray("profile").get(0).toString();
				 System.out.println(username);
				 data.add(date);
				 data.add(text);
				 data.add(id);
				 data.add(username);
				 data.add(handleid);
				 data.add(handlename);
				 data.add(profile);
			  }
		 System.out.println(data);
		//}
		return data;
	}

}
