package ted;

import java.io.IOException;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class SendMsg
 */
@WebServlet("/SendMsg")
public class SendMsg extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if (session.getAttribute("login")==null){response.sendRedirect("index.jsp");return;}
		int id = Integer.parseInt((String)session.getAttribute("login"));
		Driver db = new Driver();
		String query=null;
		ResultSet result = null;

		db.Connect();
		result = db.getBuddies(id);
		
		request.setAttribute("buddies", result);
		RequestDispatcher dispatcher = request.getRequestDispatcher("SendMsg.jsp");
   	 	dispatcher.forward(request,response);  
		db.Close();
	
	}

}
