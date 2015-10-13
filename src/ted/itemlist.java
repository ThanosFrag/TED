package ted;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class itemlist
 */
@WebServlet(name = "/itemlist", urlPatterns = { "/itemlist" })
public class itemlist extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	 HttpSession session = request.getSession();	
	 Driver db = new Driver();
	 db.Connect();
	 int userid = -1;
	 String id = request.getParameter("id");
	 ResultSet result = db.getItem(id) ;
	 String status = null;
	 
		 
	 try {
		if (result==null || result.first()==false) {
			response.sendRedirect("index.jsp");
			return;
		}
		result.first();
		userid = result.getInt("idUser");

		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

     if (result!=null)
    	 System.out.print("Not null 1");
	 status = db.getSItem(id);
	 if (status==null)
		 status = db.getEItem(id);
     request.setAttribute("Iteminfo", result);
     request.setAttribute("status",status);
     request.setAttribute("category", db.getItemCategories(Integer.parseInt(id)));
     result = db.getUser(userid);
     if (result!=null)
    	 System.out.print("Not null 2");
     try {
		result.first();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

     request.setAttribute("Userinfo", result);

     String Uid = (String)session.getAttribute("login");
   
     if (!Integer.toString(userid).equals(Uid)){
    	 
    	 RequestDispatcher dispatcher = request.getRequestDispatcher("itemlist.jsp");
    	 dispatcher.forward(request,response);    	 
     }
     else{ 
         result = db.getBids(id);
    	 request.setAttribute("Bidsinfo", result);
    	 RequestDispatcher dispatcher = request.getRequestDispatcher("myitemlist.jsp");
    	 dispatcher.forward(request,response);  
     }
		 
	 
	 db.Close();
		
		
	}

}
