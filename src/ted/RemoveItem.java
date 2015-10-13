package ted;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class RemoveItem
 */
@WebServlet("/RemoveItem")
public class RemoveItem extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Driver db = new Driver();
		HttpSession session = request.getSession();
		db.Connect();
		String id = request.getParameter("id");
		db.RemoveItem_has_Category(id);
		db.RemoveItem(id);
		
		db.Close();
		session.setAttribute("message", "You have successfully deleted a item");
		response.sendRedirect("Message.jsp");
	}

}
