package ted;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

/**
 * Servlet implementation class ChangeItem
 */
@WebServlet("/ChangeItem")
public class ChangeItem extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Driver db = new Driver();
		HttpSession session = request.getSession();
		db.Connect();
		String id = request.getParameter("id");
		ResultSet result = db.getItem(id);
		request.setAttribute("item", result);
		result = db.getItemCategories(Integer.parseInt(id));
		request.setAttribute("cat", result);
		
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("modify.jsp");
		dispatcher.forward(request,response);
		db.Close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
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
		String image =null;
		String Description=null;
		String Country=null;
		String started=null;
		String ended=null;
		String x=null;
		String y=null;
		String category=null;
		String ItemID=null;
		byte[] buffer = null;
		boolean im=false;
		String format = null;
		current= System.getProperty("user.home");
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
		            
		             File dir = new File(current+"images");
		             dir.mkdir();
		             
		             String[] _parts = filename.split("\\.");
		             format = _parts[_parts.length-1];

	             
	            }
	            else{
	            	im=true;
	            	format = image.split("\\.")[1];
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
        			started =  item.getString();break;
        		case "ended" :
        			ended =  item.getString();break;
        		case "lat" :
        			x =  item.getString();break;
        		case "long" :
        			y =  item.getString();break;
        		case "IMAGE":
        			image = item.getString();break;
        		case "ID":
        		ItemID = item.getString();break;
        		default :
        			category = item.getString();
        			if (category!=null){
        				categories.add(db.ReturnCategoryId(category));
        				
        				
        			}
        			
        	}
        	
        }
	  }
	    	db.RemoveItem_has_Category(ItemID);
	    	db.RemoveItem(ItemID);
			int itemId = db.AddItem(name,0.0,started,ended,Description,Double.parseDouble(Buy),Country,id,x,y,format);
            File targetFile = new File(current+File.separator+"images"+File.separator+itemId+"."+format);
            OutputStream outStream = new FileOutputStream(targetFile);
            if (im){
            	
            	File file = new File(current+File.separator+image);
            	InputStream inStream = new FileInputStream(file);
            	
                byte[] buf = new byte[1024];
       
                int bytesRead;
       
                while ((bytesRead = inStream.read(buf)) > 0) {
        
                    outStream.write(buf, 0, bytesRead);
        
                }
                inStream.close();
                outStream.close();

            }
            else{
	            
	            outStream.write(buffer);
	            outStream.close();
            }
			Iterator<Integer> CategoryIterator = categories.iterator();
			while (CategoryIterator.hasNext()) {
				db.AddItem_has_Category(itemId,CategoryIterator.next());
			}		
			

			session.setAttribute("message", (String)"You have successfully changed a item !!");
			response.sendRedirect("Message.jsp");
	}

}
