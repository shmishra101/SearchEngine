package project4;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Scanner;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONObject;

public class DataAnalysis 
{
	public static JSONObject getJson(String query) throws UnsupportedOperationException, IOException
	{   String splitquery[]=query.split("&");
		String encodequery=URLEncoder.encode(splitquery[0],"UTF-8");
		encodequery=encodequery+"&"+splitquery[1];
	    System.out.println(encodequery);
		String baseurl="http://localhost:8983/solr/IRF18P1/select?&indent=on&q="+encodequery+"&wt=json";
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
	public static int getResultCount(JSONObject json)
	{   JSONObject response=json.getJSONObject("response");
	    int citycount=response.getInt("numFound");
	    return citycount;
	}

}
