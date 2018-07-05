<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>  
  <head>  
    <title>测试</title>  
  </head>  
    
  <body>  
  	<form action="<c:url value='/user/login'/>" method="post">
  		用户名<input type="text" id="username" name="username" value="hkw"/>
 		  密码<input type="text" id="password" name="password" value="123456"/>
   		<input type="submit" />
  	</form>
   
  </body>  
</html>  