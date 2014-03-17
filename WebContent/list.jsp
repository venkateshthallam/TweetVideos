<%@page import="com.videotweet.UserBean"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@page import="java.util.*,com.videotweet.VideoBean" %>
<%@page import="com.videotweet.DBConnection,java.sql.Connection,java.sql.PreparedStatement,java.sql.ResultSet"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Video Tweeter</title>
<script type="text/javascript">
function updateRating(selectedId){
	document.getElementById("newRating").value = document.getElementById(selectedId).value;
	document.getElementById("videoId").value = selectedId;
	document.getElementById("ratingForm").submit();
}
</script>
</head>
<body>
<tr>
<td align="left"><b>Rating : &nbsp;</b></td>
<td align="left">
<% if(session.getAttribute("user") != null){%>
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
<%}else{%>
N/A
<% }%>
</td>
</tr>

</table>
</body>
</html>