<%
String path = request.getContextPath();  
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; 
request.setAttribute("basePath",basePath);
%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>download echo</title>
<script type="text/javascript" src="${basePath}/static/js/jquery.js"></script>
<script type="text/javascript">
	$(function(){
		$('#getSound').click(function(){
			
		var url=$('#url').val();
		if(url.length<=0){
			alert("Please re-enter the URL and try again!");
			return;
		}
		
		$.post("${basePath}/echo/casound",{"url":url},function(data){
			if(data.status==1){
				$('#soundurl').text("http://www.app-echo.com/sound/"+data.msg.soundId);
				$('#soundname').text(data.msg.name);
				$('#fileurl').text(data.msg.source);
				$('#imageurl').text(data.msg.pic);
			}else{
				alert(data.msg);
			}
		});

		});
	})
</script>
</head>
<body>
	
	URL：<input type="text" name="url" id="url"><button  id="getSound">search</button>
	Eg:http://www.app-echo.com/sound/601685
	<table>
		<tr><td>SOUND URL:</td><td id="soundurl"></td></tr>
		<tr><td>SOUND NAME:</td><td id="soundname"></td></tr>
		<tr><td>FILE URL:</td><td id="fileurl"></td></tr>
		<tr><td>IMAGE URL:</td><td id="imageurl"></td></tr>
	</table>
	<img alt="" src="">
</body>
</html>