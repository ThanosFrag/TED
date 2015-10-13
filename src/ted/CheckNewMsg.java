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
 * Servlet implementation class CheckNewMsg
 */
@WebServlet("/CheckNewMsg")
public class CheckNewMsg extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		if (session.getAttribute("login")==null){response.sendRedirect("index.jsp");return;}
		int id = Integer.parseInt((String)session.getAttribute("login"));
		Driver db = new Driver();
		

		db.Connect();
		int messages=0;
		messages = db.getUnreadMessage(id);
		
		session.setAttribute("messages", ""+messages);
		db.Close();
	}

}
