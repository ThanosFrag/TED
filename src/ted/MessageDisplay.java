package ted;

import java.io.IOException;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class MessageDisplay
 */
@WebServlet("/MessageDisplay")
public class MessageDisplay extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 Driver db = new Driver();
		 db.Connect();
		 ResultSet result = null;
		 
		 int id  = Integer.parseInt(request.getParameter("id"));
		 db.getMessage(1,0,id);
		 result = db.getMessage(2, 0, id);
		 request.setAttribute("result", result);
		 RequestDispatcher dispatcher = request.getRequestDispatcher("InboxDisplay.jsp");
		 dispatcher.forward(request,response);
		 
	}

}
