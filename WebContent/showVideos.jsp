<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="com.videotweet.DBConnection,java.sql.Connection,java.sql.PreparedStatement,java.sql.ResultSet"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<table>
<% Connection con=DBConnection.openConnection();
String sql="select name,cflink from VIDEO_INFO";
PreparedStatement ps=con.prepareStatement(sql);
ResultSet rs=ps.executeQuery();
while(rs.next()){
	//String name=rs.getString("name");
	//String cfLink=rs.getString("cflink");	

%>
<tr>
<td valign="middle">
				<p><%=rs.getString("name") %></p>
<video width="356" height="200" controls>
<source src="<%=rs.getString("cflink")%>" />
</video>
</td>
		</tr>
		<%
		}

%>
</table>
</body>
</html>