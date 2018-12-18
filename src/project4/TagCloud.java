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

public class TagCloud {

    public static JSONObject getTrends(String query,String type) throws ClientProtocolException, IOException
    {   String encodequery=URLEncoder.encode(query,"UTF-8");
    	String baseurl="http://localhost:8983/solr/IRF18P1/select?facet=on&facet.field="+type+"&facet.limit=-1&indent=on&facet.sort=count&q="+encodequery+"&wt=json";
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
	 public static String processJson(JSONObject json,String type) throws IOException
	    {   JSONObject facecounts=json.getJSONObject("facet_counts");
	        System.out.println(facecounts);
	        JSONObject facefields=facecounts.getJSONObject("facet_fields");
	        System.out.println(facefields);
	    	JSONArray jsonarray=facefields.getJSONArray(type);
	    	System.out.println(jsonarray);
	    	String tagcloud="";
	    	for(int i=0; i<10; i=i+2)
	    	{
	    		String trendingwords=jsonarray.getString(i);
	    		System.out.println(trendingwords);
	    		int count=jsonarray.getInt(i+1);
	    		for (int j=0; j<count; j++)
	     		{
	     			tagcloud=tagcloud+trendingwords+" ";
	     		}
	    	}
	    	return tagcloud;
	    }
	 public static List<String> processPhrases(JSONObject json,String type) throws IOException
	    {   JSONObject facecounts=json.getJSONObject("facet_counts");
	        
	        JSONObject facefields=facecounts.getJSONObject("facet_fields");
	       
	    	JSONArray jsonarray=facefields.getJSONArray(type);
	    	int loop=0;
	    	List<String> phrases=new ArrayList<String>();
	       if(jsonarray.length()<10)
	       {
	    	   loop=jsonarray.length();
	       }
	       else
	       {
	    	   loop=10;
	       }
	    	for(int i=0; i<loop; i=i+2)
	    	{
	    		String trendingphrases=jsonarray.getString(i);
	    		System.out.println(trendingphrases);
	    		int count=jsonarray.getInt(i+1);
	    		phrases.add(trendingphrases);
	    		phrases.add(count+"");
	        }
	       
	    	return phrases;
	    }
	/* public static BufferedWriter openFile() throws FileNotFoundException
	 {
		    File filedir = new File("tagcloud.txt");
			FileOutputStream fos = new FileOutputStream(filedir,true);
		    BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(fos,Charset.forName("UTF-8")));
		    return writer;
		    
	 }*/
	/* public static String writeFile(String hashtag,int count) throws IOException
	 {   
		 for (int j=0; j<count; j++)
 		{
 			tagcloud=tagcloud+hashtag+" ";
 		}
		return tagcloud;
	 }*/
}



