<%@page import="com.skoleni.jeeequa.web.servlet.Hello"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">


<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Who are u?</h1>
        
        <form action="Hello">
            Type your name
            <input id="name" name="name" type="text"/>
            
            <input type="submit" value="Send">
            
        </form>
        
        <br/>
        <a href="DirTree" >DirTree</a>
        
            
        
    </body>
</html>
