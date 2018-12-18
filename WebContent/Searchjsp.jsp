<%@page import="project4.DataAnalysis"%>
<%@page import="project4.TagCloud"%>
<%@page import="org.json.JSONObject"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="project4.QuerySearch" %>
<%@ page import="java.util.List" %>
<%@page import="java.io.PrintWriter"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
   <% 
      String query=request.getParameter("query");
      String to=request.getParameter("to");
      String filter=request.getParameter("filter");
      JSONObject json=null;
     // Code for Query Results
     if(to==null)
       { 
    	 json=QuerySearch.getJson(query, "0");
    	 if(filter==null)
         {
        	 json=QuerySearch.getJson(query,"0");
         }
         else
         {  
        	 json=QuerySearch.getJson(query+"&fq="+filter,"0");
         }
       }
     else
     {
    	 json=QuerySearch.getJson(query,to);
    	 if(filter==null)
         {
        	 json=QuerySearch.getJson(query,to);
         }
         else
         {  
        	 json=QuerySearch.getJson(query+"&fq="+filter,to);
         }
     }
    
     
      List<String> result=QuerySearch.getResults(json);
     response.setCharacterEncoding("UTF-8");
      PrintWriter outwriter=response.getWriter();
      
      StringBuffer outprint=new StringBuffer();
      response.setContentType("text/plain");
      response.setHeader("Cache-Control", "no-cache");
      response.setHeader("pragma","no-cache");
      if(result.size()==0)
      {
    	  outprint.append("No Results"); 
      }
      
       for(int i=0; i<result.size(); i++)
       {
    	  outprint.append(result.get(i)+"\n");   
       }
        outwriter.print(outprint);
   %>
</body>
</html>