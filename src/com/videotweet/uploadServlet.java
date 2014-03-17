package com.videotweet;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3ObjectSummary;

/**
 * Servlet implementation class uploadServlet
 */
public class uploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 AmazonS3 s3client = new AmazonS3Client(new ClasspathPropertiesFileCredentialsProvider());
		 String bucketName="vthallam";
		 String cdn="https://d1qa6uzcp7bzx.cloudfront.net";
		 try{
		 List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
     	File file = null;
     	for (FileItem item : items) {
             if (!item.isFormField())
             {
                 String fileName = FilenameUtils.getName(item.getName());
                 String root = getServletContext().getRealPath("/");
					File path = new File(root + "/uploads");
					if (!path.exists()) {
						boolean status = path.mkdirs();
					}
					// 1. save to local file system
					file = new File(path + "/" + fileName);
					item.write(file);
             }
         }
     	if(file != null)
     	{
     		if(file.getName().toUpperCase().contains("GIF") || file.getName().toUpperCase().contains("JPG") || file.getName().toUpperCase().contains("JPEG") 
     				|| file.getName().toUpperCase().contains("PDF") || file.getName().toUpperCase().contains("DOC") || file.getName().toUpperCase().contains("TXT")){
             	request.setAttribute("message", "Incompatible file types");
     			RequestDispatcher rd=request.getRequestDispatcher("upload.jsp");
     			rd.forward(request,response);
             }
     		else
     			{
	                s3client.putObject(new PutObjectRequest(bucketName, file.getName(), file).withCannedAcl(CannedAccessControlList.PublicRead));
	             }
     	}
     	GeneratePresignedUrlRequest req = new GeneratePresignedUrlRequest(bucketName, file.getName());
	 	 URL objectURL = s3client.generatePresignedUrl(req);
	 	 String cfURL=cdn+"/"+file.getName();
     	Connection con=DBConnection.openConnection();
     	PreparedStatement preparedStatement = con.prepareStatement("insert into VIDEO_INFO values(?,?,?,?,?,?)"); 
     	preparedStatement.setString(1,file.getName());
     	preparedStatement.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
     	preparedStatement.setURL(3, objectURL);
     	preparedStatement.setString(4, cfURL);
     	preparedStatement.setInt(5, 0);
     	preparedStatement.setInt(6, 0);
     	preparedStatement.executeUpdate();
     	/* List<VideoBean> vdList=new ArrayList<VideoBean>();
     	 ObjectListing objectListing = s3client.listObjects(new ListObjectsRequest()
         .withBucketName(bucketName));
     	 for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
		 	 //GeneratePresignedUrlRequest req = new GeneratePresignedUrlRequest(bucketName, objectSummary.getKey());
		 	// URL objectURL = s3client.generatePresignedUrl(req);
             VideoBean vd=new VideoBean();
             vd.setName(objectSummary.getKey());
             vd.setUrl(cdn+"/"+objectSummary.getKey());
             vdList.add(vd);
 }*/
     	//request.setAttribute("vdList", vdList);
     	 RequestDispatcher rd1=request.getRequestDispatcher("showVideos.jsp");
			rd1.forward(request,response);
		 }
		 catch(Exception e){
			 e.printStackTrace();
		 }
		/* String filePath=request.getParameter("pic");
		 System.out.println(filePath);
		 s3.putObject(new PutObjectRequest(bucketName, key, createSampleFile(filePath)));
		 RequestDispatcher rd1=request.getRequestDispatcher("result.jsp");
			rd1.forward(request,response);*/
	}

}
