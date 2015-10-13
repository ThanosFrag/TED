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
 * Servlet implementation class ManageUsers
 */
@WebServlet("/ManageUsers")
public class ManageUsers extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if (session.getAttribute("admin")==null){
			response.sendRedirect("index.jsp");
			return;
		}
		
		Driver db = new Driver();
		db.Connect();
		int page = 1;
		int recordsPerPage = 10;
		int noOfPages;
	     if(request.getParameter("page") != null)
	            page = Integer.parseInt(request.getParameter("page"));
		ResultSet result = db.listUser((page-1)*recordsPerPage,recordsPerPage,0,null);
	    int noOfRecords = db.getNoOfRecords();
	    noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);

		request.setAttribute("result", (ResultSet)result);
        request.setAttribute("noOfPages", noOfPages);
        request.setAttribute("currentPage", page);	    
		RequestDispatcher dispatcher = request.getRequestDispatcher("Umanage.jsp");
		dispatcher.forward(request,response);
		
		db.Close();
	}

}
