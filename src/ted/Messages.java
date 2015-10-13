package ted;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * Servlet implementation class Messages
 */
@WebServlet("/Messages")
public class Messages extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Driver db = new Driver();
	//	PrintWriter out = response.getWriter();
		db.Connect();
		HttpSession session = request.getSession();
		ResultSet result = null;
		String to = null;
		String subject = null;
		String message = null;
		String from = null;
		from = request.getParameter("from");
		to = request.getParameter("to");
		subject = request.getParameter("subject");
		message = request.getParameter("message");
		db.insertMessage(from , to, subject, message);
		session.setAttribute("message", "You have successfully send a message");
		response.sendRedirect("Message.jsp");		
		
		db.Close();		
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Driver db = new Driver();
		db.Connect();
		HttpSession session = request.getSession();
		ResultSet result = null;
		String type = "Inbox";
		type = request.getParameter("type");
		String id = (String)session.getAttribute("login");
		if (type.equals("Inbox")) {
			result = db.getMessage(0, Integer.parseInt(id), 0);
			request.setAttribute("result", result);
			request.setAttribute("type", type);
			RequestDispatcher dispatcher = request.getRequestDispatcher("Inbox.jsp");
			dispatcher.forward(request,response);
		}
		else {
			result = db.getMessage(4, Integer.parseInt(id), 0);
			request.setAttribute("result", result);
			request.setAttribute("type", type);
			RequestDispatcher dispatcher = request.getRequestDispatcher("Inbox.jsp");
			dispatcher.forward(request,response);
		}
		
		db.Close();		
	}

}
