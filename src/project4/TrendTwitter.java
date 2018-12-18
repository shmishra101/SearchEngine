package project4;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONArray;
import org.json.JSONObject;

public class TrendTwitter 
{
	public static void main(String args[]) throws ClientProtocolException, IOException
	{
		JSONObject json=getTrends("nyc","sentiment");
		System.out.print(countSentiment(json, "sentiment"));
	//	List<String> trends=processJson(json, "hashtags");
		//System.out.println(trends);
	}
    public static JSONObject getTrends(String query,String type) throws ClientProtocolException, IOException
    {   String encodequery=URLEncoder.encode(query,"UTF-8");
    	String baseurl="http://localhost:8983/solr/IRF18P1/select?facet=on&facet.field="+type+"&facet.limit=-1&indent=on&facet.sort=count&q="+encodequery+"&wt=json";
    	System.out.println(baseurl);
    	JSONObject json;
 	    String jsonresponse="";
 	    // Add deftype=dismax in query url to add dismax query parser 
 	    //Add qf=text_en^2 text_de^5 text_ru^10 in query url to boost the fields
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
     		Scanner sc=new Scanner(entity.getContent());
     	    while(sc.hasNext())
     	    {   
     	    	jsonresponse=jsonresponse+sc.nextLine();
     	    }
     	    
     		sc.close();
     		json=new JSONObject(jsonresponse);
     	}
     	return json;
    }
    public static List<String> processJson(JSONObject json,String type)
    {   JSONObject facecounts=json.getJSONObject("facet_counts");
        JSONObject facefields=facecounts.getJSONObject("facet_fields");
    	JSONArray jsonarray=facefields.getJSONArray(type);
    	List<String> trends=new ArrayList<String>();
    	for(int i=0; i<10; i=i+2)
    	{
    		String trendingwords=jsonarray.getString(i);
    		trends.add(trendingwords);
    	}
    	return trends;
    }
   public static List<String> countSentiment(JSONObject json,String type)
   {
	   JSONObject facecounts=json.getJSONObject("facet_counts");
       JSONObject facefields=facecounts.getJSONObject("facet_fields");
   	   JSONArray jsonarray=facefields.getJSONArray(type);
   	   List<String> trends=new ArrayList<String>();
   	for(int i=0; i<6; i++)
   	{
   		String trendingwords=jsonarray.get(i).toString();
   		trends.add(trendingwords);
   	}
   	return trends;
   	
   }

}
