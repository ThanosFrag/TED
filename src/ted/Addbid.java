package ted;

import java.io.IOException;
import java.text.ParseException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Addbid
 */
@WebServlet("/Addbid")
public class Addbid extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Driver db = new Driver();
		HttpSession session = request.getSession();
		db.Connect();
		String Iid = request.getParameter("id");
		String Uid = request.getParameter("user");
		String Amount = request.getParameter("amount");
		try {
			db.insertBid(Integer.parseInt(Iid), Integer.parseInt(Uid),Double.parseDouble(Amount));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
		
		
		db.buy(Iid,Double.parseDouble(Amount));
		db.Close();
		session.setAttribute("message", "You have successfully bought a item");
		response.sendRedirect("Message.jsp");
		return;			
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Driver db = new Driver();
		db.Connect();
		HttpSession session = request.getSession();
		if (session==null){
			response.sendRedirect("/TED/index.jsp");
			return;
		}
		String Sid =(String)session.getAttribute("login");
		if (Sid==null){
			response.sendRedirect("/TED/index.jsp");
			return;
		}	
		String Amount = request.getParameter("bid");
		String currently = request.getParameter("currently");
		String buy = request.getParameter("buy");
		Double a,b,c ;
		try
		{
		 a =  Double.parseDouble(Amount);
		}
		catch(NumberFormatException e)
		{
			db.Close();
		  session.setAttribute("message", "You entered wrong bid value");
		  response.sendRedirect("Message.jsp");
		  return;
		}
		b = Double.parseDouble(buy);
		if (a>b && b>0.0){
				db.Close();
			  session.setAttribute("message", "You bid over the buy price");
			  response.sendRedirect("Message.jsp");
			  return;			
		}
		c =  Double.parseDouble(currently);
		if (a<c || a.equals(c)){
			db.Close();
			  session.setAttribute("message", "You bid below the minimum");
			  response.sendRedirect("Message.jsp");
			  return;				
		}
		
		String Iid = request.getParameter("iid");
		String Uid = request.getParameter("uid");
		try {
			db.insertBid(Integer.parseInt(Iid), Integer.parseInt(Uid),Double.parseDouble(Amount));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (a.equals(b) && b>0.0){
			db.buy(Iid,a);
			db.Close();
			session.setAttribute("message", "You have successfully bought a item");
			response.sendRedirect("Message.jsp");
			return;	
		}
		db.Close();
		
		  session.setAttribute("message", "You have successfully made a bid");
		  response.sendRedirect("Message.jsp");
		  return;		
		
	}
}
