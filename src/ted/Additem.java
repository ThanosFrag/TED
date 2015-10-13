package ted;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Date;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

/**
 * Servlet implementation class Additem
 */
@MultipartConfig
@WebServlet("/additem")
public class Additem extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String SAVE_DIR = "WebContent\\images\\users";
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected String Convert(String date){
		if (date==null) return null;
		String[] parts = date.split(" ");
		String[] date_part = parts[0].split("-");
		String temp  = date_part[2]+"-" + date_part[1]+"-" +date_part[0];
		temp = temp + " " + parts[1];
		return temp;
		
		
		
		
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Driver db = new Driver();
		HttpSession session = request.getSession();
		// Create a factory for disk-based file items
		DiskFileItemFactory factory = new DiskFileItemFactory();
		List<FileItem> items =null;
		ResultSet result = null;
		// Configure a repository (to ensure a secure temp location is used)
		ServletContext servletContext = this.getServletConfig().getServletContext();
		File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
		factory.setRepository(repository);
		ArrayList<Integer> categories = new ArrayList<Integer>();
		// Create a new file upload handler
		ServletFileUpload upload = new ServletFileUpload(factory);
		// Parse the request
		try {
			items = upload.parseRequest(request);
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	 

		if (session==null){
			response.sendRedirect("/TED/index.jsp");
			return;
		}
		String Sid =(String)session.getAttribute("login");
		if (Sid==null){
			response.sendRedirect("/TED/index.jsp");
			return;
		}
		int id  = Integer.parseInt(Sid);
		db.Connect();

		String current = null;
	 // Process the uploaded items
	    Iterator<FileItem> iter = items.iterator();
	    String fieldname = null;
	    String filename = null;
	    String name=null;
		String Buy=null;
		String Description=null;
		String Country=null;
		String started=null;
		String ended=null;
		String x=null;
		String y=null;
		String category=null;
		byte[] buffer = null;
		String format = null;
		while (iter.hasNext()) {
	        FileItem item = iter.next();
	        fieldname = item.getFieldName();
	        if (!item.isFormField()) {
	        	
	            filename = FilenameUtils.getName(item.getName());
	            if(filename.length()>0)
	            {
		             InputStream filecontent = item.getInputStream();
	
		             buffer = new byte[filecontent.available()];
		             filecontent.read(buffer);
		             current= System.getProperty("user.home");
		             File dir = new File(current+"images");
		             dir.mkdir();
		             
		             String[] _parts = filename.split("\\.");
		             
		             format = _parts[_parts.length-1];

	             
	            }
	        }
        else {
        	
        	switch(fieldname){
        		case "Name" :
        			name = item.getString();break;
        		case "Buy":
        			Buy = item.getString();
        			if (Buy==null || Buy.isEmpty())
        				Buy = "-1.0";
        			
        			break;
        		case "Description":
        			Description =  item.getString();break;
        		case "Country" :
        			Country =  item.getString();break;
        		case "started" :        			
        			started =  Convert(item.getString());break;
        		case "ended" :
        			ended =  Convert(item.getString());break;
        		case "lat" :
        			x =  item.getString();break;
        		case "long" :
        			y =  item.getString();break;
        		default :
        			category = item.getString();
        			if (category!=null){
        				categories.add(db.ReturnCategoryId(category));
        				
        				
        			}
        			
        	}
        	
        }
	  }
	    
			int itemId = db.AddItem(name,0.0,started,ended,Description,Double.parseDouble(Buy),Country,id,x,y,format);
            File targetFile = new File(current+File.separator+"images"+File.separator+itemId+"."+format);
            OutputStream outStream = new FileOutputStream(targetFile);
            outStream.write(buffer);
            outStream.close();
			Iterator<Integer> CategoryIterator = categories.iterator();
			while (CategoryIterator.hasNext()) {
				db.AddItem_has_Category(itemId,CategoryIterator.next());
			}			
			session.setAttribute("message", (String)"You have successfully added a item !!");
			response.sendRedirect("Message.jsp");
		}
	}

