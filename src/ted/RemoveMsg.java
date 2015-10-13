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
 * Servlet implementation class RemoveMsg
 */
@WebServlet("/RemoveMsg")
public class RemoveMsg extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		Driver db = new Driver();
		HttpSession session = request.getSession();
		if (session.getAttribute("login")==null){response.sendRedirect("index.jsp");return;}
		db.Connect();
		db.getMessage(3, 0, Integer.parseInt(id));
		db.Close();		
		response.sendRedirect("Messages?type=Inbox");
		return;
		
		
		
	}

}
