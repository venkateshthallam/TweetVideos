package com.videotweet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;





import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * Servlet implementation class LoginServlet
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username=request.getParameter("username");
		String password=request.getParameter("password");
		System.out.println("username"+username+"Password"+password);
		Connection con=null;
		String storedPass=null;
		String FirstName=null;
		String LastName=null;
		String UserName=null;
		String Password=null;
		try {
			con=DBConnection.openConnection();
			 String query = "SELECT * FROM user where userName = ?";
			  PreparedStatement pst=con.prepareStatement(query);
			//PreparedStatement pst=con.prepareStatement("select * from user where username=?");
			pst.setString(1,username);
			System.out.println("After setting the parameter");
			ResultSet rs=pst.executeQuery();
			System.out.println("After executing the query");
			if(rs==null)
				System.out.println("Null resultSet");
			while(rs.next()){
				FirstName=rs.getString("First_Name");
				LastName=rs.getString("Last_Name");
				UserName=rs.getString("userName");
			storedPass=rs.getString("Password");
			System.out.println(FirstName+""+LastName+""+UserName+""+storedPass);
			}
			System.out.println("retrieved the password");
			System.out.println("storedPass"+storedPass);
			if(password.equalsIgnoreCase(storedPass)){
				System.out.println("Passwords matched!");
				UserBean user = new UserBean();
				user.setFirstName(FirstName);
				user.setLastName(LastName);
				user.setUserName(UserName);
				user.setPassword(storedPass);
				System.out.println("Assigned values to User Bean!");
				HttpSession session=request.getSession(true);
		    	session.setAttribute("user", user);
		    	System.out.println("Set the session!");
		    	RequestDispatcher rd=request.getRequestDispatcher("index.jsp");
				rd.forward(request,response);
			}else{
				request.setAttribute("message", "Invalid Password");
				RequestDispatcher rd=request.getRequestDispatcher("login.jsp");
				rd.forward(request,response);
			}
			
		}catch (ClassNotFoundException e) {
			request.setAttribute("message", "Server connection failed..please try again");
			RequestDispatcher rd=request.getRequestDispatcher("login.jsp");
			rd.forward(request,response);
		}catch (SQLException e) {
			request.setAttribute("message", "Invalid UserName");
			e.printStackTrace();
			RequestDispatcher rd=request.getRequestDispatcher("login.jsp");
			rd.forward(request,response);
		}finally{
			try {
				if(con != null){
					con.close();
				}
			} 
			catch (SQLException e) {
				request.setAttribute("message", "Server busy..please try again later");
			}
			catch(NullPointerException e) {
				request.setAttribute("message", "Server not connected..please try again later");
			}
	    }
	}
}


