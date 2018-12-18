<%@page import="project4.TrendTwitter"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="project4.DataAnalysis"%>
<%@page import="project4.TagCloud"%>
<%@page import="org.json.JSONObject"%>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<%
String query=request.getParameter("query"); 
int nyc=0,paris=0,delhi=0,bangkok=0,mexico=0,politics=0,env=0,crime=0,social=0,infra=0;
String tagcloud=null;
String a[]=new String[6];
List<String> phrases=null;
if(query!=null)
{
// Code for Tag Cloud
 JSONObject jsoncloud=TagCloud.getTrends(query,"hashtags");
 tagcloud=TagCloud.processJson(jsoncloud, "hashtags");
 // Code for city count
 JSONObject cityjson=DataAnalysis.getJson(query+"&fq=city:nyc");
  nyc=DataAnalysis.getResultCount(cityjson);
 cityjson=DataAnalysis.getJson(query+"&fq=city:paris");
  paris=DataAnalysis.getResultCount(cityjson);
 cityjson=DataAnalysis.getJson(query+"&fq=city:delhi");
  delhi=DataAnalysis.getResultCount(cityjson);
 cityjson=DataAnalysis.getJson(query+"&fq=city:bangkok");
  bangkok=DataAnalysis.getResultCount(cityjson);
 cityjson=DataAnalysis.getJson(query+"&fq=city:mexico%20city");
  mexico=DataAnalysis.getResultCount(cityjson);
 // Code for topic count
 cityjson=DataAnalysis.getJson(query+"&fq=topic:politics");
  politics=DataAnalysis.getResultCount(cityjson);
 cityjson=DataAnalysis.getJson(query+"&fq=topic:environment");
  env=DataAnalysis.getResultCount(cityjson);
 cityjson=DataAnalysis.getJson(query+"&fq=topic:crime");
  crime=DataAnalysis.getResultCount(cityjson);
 cityjson=DataAnalysis.getJson(query+"&fq=topic:social%20unrest");
  social=DataAnalysis.getResultCount(cityjson);
 cityjson=DataAnalysis.getJson(query+"&fq=topic:infra");
  infra=DataAnalysis.getResultCount(cityjson);
 
 
 // Sentiment Code 
 JSONObject trendingsentiment=TrendTwitter.getTrends(query,"sentiment");
 List<String> sentiment=TrendTwitter.countSentiment(trendingsentiment, "sentiment");

 for (int j=0; j<sentiment.size(); j++)
 {
	 a[j]=sentiment.get(j);
 }
}
%>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="css/slave.css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.6.0/Chart.min.js"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
    <script src="https://cdn.zingchart.com/zingchart.min.js"></script>
  <script>
    zingchart.MODULESDIR = "https://cdn.zingchart.com/modules/";
    ZC.LICENSE = ["569d52cefae586f634c54f86dc99e6a9", "ee6b7db5b51705a13dc2339db3edaf6d"];
  </script>
  <style>
    
    #tab3 {
      height: 100%;
      width: 100%;
      min-height: 150px;
    }
    
    .zc-ref {
      display: none;
    }
  </style>
<title>Insert title here</title>
</head>
<body>
     <!-- Tab links -->
     <div class="tab">
        <button class="tablinks" id="default" onclick="openTab(event, 'tab1')">Sentimental Analysis</button>
        <button class="tablinks" onclick="openTab(event, 'tab2')">Active Cities</button>
        <button class="tablinks" onclick="openTab(event, 'tab3')">Trending Hashtags</button>
        <button class="tablinks" onclick="openTab(event, 'tab4')">Trending Topics</button>
      </div>

      <!-- Tab content -->
      <div id="tab1" class="tabcontent" style="height:500px">
        <canvas id="myChart"></canvas>
            <script>
              let myChart = document.getElementById('myChart').getContext('2d');
				myChart.height = 500;
              // Global Options
              Chart.defaults.global.defaultFontFamily = 'Lato';
              Chart.defaults.global.defaultFontSize = 18;
              Chart.defaults.global.defaultFontColor = '#777';
              var sentiments=[];
             <%for (int z=0; z<6; z++)
            	 {
            	   %>sentiments[<%=z%>]='<%=a[z]%>';
            	 <%}%>
            
              if(sentiments[0]=='Positive')
            	 {
            	   var positive=sentiments[1];
            	   if(sentiments[2]=='Negative')
            		 {
            		   var negative=sentiments[3]
            		   var neutral=sentimets[5]
            		 }
            	   else
            		 {
            		   var negative=sentiments[5]
            		   var neutral=sentimets[3]
            		 }
            	  }
              else
            	 {
            	  if(sentiments[0]=='Negative')
             	 {
             	   var negative=sentiments[1];
             	   if(sentiments[2]=='Positive')
             		 {
             		   var positive=sentiments[3]
             		   var neutral=sentiments[5]
             		 }
             	   else
             		 {
             		   var positive=sentiments[5]
             		   var neutral=sentiments[3]
             		 }
             	  }
            	  else
            		  {
            		     var neutral=sentiments[1]
            		     
            		    if(sentiments[2]=='Positive')
            		    { var positive=''+sentiments[3]
            		      var negative=''+sentiments[5]
            		    }
            		    else
            		    {
            		    	var positive=''+sentiments[5]
              		        var negative=''+sentiments[3]
            		    }
            		  }
            	 }
              let massPopChart = new Chart(myChart, {
                type:'doughnut', // bar, horizontalBar, pie, line, doughnut, radar, polarArea
                data:{
                  labels:['Neutral', 'Positive', 'Negative'],
                  datasets:[{
                    label:'Sentiments',
                    data:[
                      neutral,
                      positive,
                      negative
                    ],
                    //backgroundColor:'green',
                    backgroundColor:[
                      'rgba(255, 99, 132, 0.6)',
                      'rgba(54, 162, 235, 0.6)',
                      'rgba(255, 206, 86, 0.6)'
                    ],
                    borderWidth:1,
                    borderColor:'#777',
                    hoverBorderWidth:3,
                    hoverBorderColor:'#000'
                  }]
                },
                options:{
                	responsive: true,
                	maintainAspectRatio: false,
                  title:{
                    display:true,
                    text:'Sentimental Analysis',
                    fontSize:25
                  },
                  legend:{
                    display:true,
                    position:'right',
                    labels:{
                      fontColor:'#000'
                    }
                  },
                  layout:{
                    padding:{
                      left:50,
                      right:0,
                      bottom:0,
                      top:0
                    }
                  },
                  tooltips:{
                    enabled:true
                  }
                }
              });
            </script>
      </div>

      <div id="tab2" class="tabcontent">
            <canvas id="myChart1"></canvas>
            <script>
            var tagcloud='<%=tagcloud%>'
            var nyc='<%=nyc%>'
            var delhi='<%=delhi%>'
            var bangkok='<%=bangkok%>'
            var mexico='<%=mexico%>'
            var paris='<%=paris%>'
            
              let myChart1 = document.getElementById('myChart1').getContext('2d');

              // Global Options
              Chart.defaults.global.defaultFontFamily = 'Lato';
              Chart.defaults.global.defaultFontSize = 18;
              Chart.defaults.global.defaultFontColor = '#777';

              let massPopChart1 = new Chart(myChart1, {
                type:'bar', // bar, horizontalBar, pie, line, doughnut, radar, polarArea
                data:{
                  labels:['New York City', 'Delhi', 'Bangkok', 'Mexico City', 'Paris'],
                  datasets:[{
                    label:'Population',
                    data:[
                      nyc,
                      delhi,
                      bangkok,
                      mexico,
                      paris
                    ],
                    //backgroundColor:'green',
                    backgroundColor:[
                      'rgba(255, 99, 132, 0.6)',
                      'rgba(54, 162, 235, 0.6)',
                      'rgba(255, 206, 86, 0.6)',
                      'rgba(255, 162, 235, 0.6)',
                      'rgba(54, 255, 235, 0.6)'
                    ],
                    borderWidth:1,
                    borderColor:'#777',
                    hoverBorderWidth:3,
                    hoverBorderColor:'#000'
                  }]
                },
                options:{
                  title:{
                    display:true,
                    text:'Active Cities',
                    fontSize:25
                  },
                  legend:{
                    display:true,
                    position:'right',
                    labels:{
                      fontColor:'#000'
                    }
                  },
                  layout:{
                    padding:{
                      left:50,
                      right:0,
                      bottom:0,
                      top:0
                    }
                  },
                  tooltips:{
                    enabled:true
                  }
                }
              });
            </script>
      </div>

      <div id="tab3" class="tabcontent">
            <script>
            var tagcloud='<%=tagcloud%>'
		    var myConfig = {
		      type: 'wordcloud',
		      options: {
		        text: tagcloud
		        }
		    };
		
		    zingchart.render({
		      id: 'tab3',
		      data: myConfig,
		      height: 400,
		      width: '100%'
		    });
		  </script>
      </div>
      
      <div id="tab4" class="tabcontent" style="height:1000px">
            <canvas id="myChart2"></canvas>
            <script>
              let myChart2 = document.getElementById('myChart2').getContext('2d');
              var pol='<%=politics%>'
              var env='<%=env%>'
              var crime='<%=crime%>'
              var social='<%=social%>'
              var infra='<%=infra%>'
              // Global Options
              Chart.defaults.global.defaultFontFamily = 'Lato';
              Chart.defaults.global.defaultFontSize = 18;
              Chart.defaults.global.defaultFontColor = '#777';

              let massPopChart2 = new Chart(myChart2, {
                type:'radar', // bar, horizontalBar, pie, line, doughnut, radar, polarArea
                data:{
                  labels:['Politics', 'Environment', 'Crime', 'Social Unrest', 'Infrastructure'],
                  datasets:[{
                    label:'Number of Tweets',
                    data:[
                      pol,
                       env,
                      crime,
                     social,
                      infra
                    ],
                    //backgroundColor:'green',
                    backgroundColor:[
                      'rgba(255, 99, 132, 0.6)',
                      'rgba(54, 162, 235, 0.6)',
                      'rgba(255, 206, 86, 0.6)',
                      'rgba(255, 162, 235, 0.6)',
                      'rgba(54, 255, 235, 0.6)'
                    ],
                    borderWidth:1,
                    borderColor:'#777',
                    hoverBorderWidth:3,
                    hoverBorderColor:'#000'
                  }]
                },
                options:{
                	responsive: true,
                    maintainAspectRatio: false,
                    scale: {
                        ticks: {
                            beginAtZero: true,
                            
                        },
                        pointLabels: {
                            fontSize: 20
                          }
                    },	
                  title:{
                    display:true,
                    text:'Trending Topics',
                    fontSize:25
                  },
                  legend:{
                    display:true,
                    position:'right',
                    labels:{
                      fontColor:'#000'
                    }
                  },
                  layout:{
                    padding:{
                      left:50,
                      right:0,
                      bottom:0,
                      top:0
                    }
                  },
                  tooltips:{
                    enabled:true
                  }
                }
              });
            </script>
      </div>
      <script>
    function openTab(evt, tab) {
    // Declare all variables
    var i, tabcontent, tablinks;

    // Get all elements with class="tabcontent" and hide them
    tabcontent = document.getElementsByClassName("tabcontent");
    for (i = 0; i < tabcontent.length; i++) {
        tabcontent[i].style.display = "none";
    }

    // Get all elements with class="tablinks" and remove the class "active"
    tablinks = document.getElementsByClassName("tablinks");
    for (i = 0; i < tablinks.length; i++) {
        tablinks[i].className = tablinks[i].className.replace(" active", "");
    }

    // Show the current tab, and add an "active" class to the button that opened the tab
    document.getElementById(tab).style.display = "block";
    evt.currentTarget.className += " active";
    
    
}

    document.getElementById("default").click();
    
</script>
      
</body>
</html>