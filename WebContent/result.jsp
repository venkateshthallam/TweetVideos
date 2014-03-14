<!-- This jsp page takes the result set of products from the search servlet and displays it. 
It also provides an option to select the items and save it to the database -->
<%@page import="java.util.ArrayList,com.videotweet.VideoBean"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<table>
	
		<%
			ArrayList<VideoBean> results = (ArrayList<VideoBean>) request.getAttribute("vdList");
			if (results != null) {
				for (VideoBean res : results) {
					
		%>
		<tr>
			<td valign="middle">
				<p><%=res.getName() %> </p>
<video width="356" height="200" controls>
<source src="<%=res.getUrl()%>" />
</video>
</td>
		</tr>
		<%
			}
			}
		%>
		
	</table>
</body>
</html>