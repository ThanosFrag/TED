package ted;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class GetInactiveitem
 */
@WebServlet("/GetInactiveitem")
public class GetInactiveitem extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 HttpSession session = request.getSession();
		 Driver db = new Driver();
		 db.Connect();
		 int page = 1;
		 int recordsPerPage = 6;
		 int noOfPages;
	     if(request.getParameter("page") != null)
	            page = Integer.parseInt(request.getParameter("page"));
		 ResultSet result = db.getInactiveItems((page-1)*recordsPerPage,recordsPerPage,(String)session.getAttribute("login"));
	     int noOfRecords = db.getNoOfRecords();
	     noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
		 if (result!=null){
			try {
				if(result.first())
					result.beforeFirst();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		 }
		 else{
			 response.sendRedirect("index.jsp");
			return; 
		 }
		request.setAttribute("result", (ResultSet)result);
        request.setAttribute("noOfPages", noOfPages);
        request.setAttribute("currentPage", page);
		RequestDispatcher dispatcher = request.getRequestDispatcher("Inactivedisplay.jsp");
		dispatcher.forward(request,response);
	}

}
