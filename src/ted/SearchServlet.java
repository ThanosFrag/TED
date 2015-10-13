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

/**
 * Servlet implementation class SearchServlet
 */
@WebServlet("/searchS")
public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Driver db = new Driver();
		PrintWriter out = response.getWriter();
		db.Connect();
		ResultSet result=null;
		ResultSet categories=null;
		ResultSet countries=null;
        int page = 1;
        int recordsPerPage = 9;
        int noOfPages;
        String name = null;
        String category = null;;
        String price = null;
        String country = null;
        
		name = request.getParameter("name");
		category = request.getParameter("category");
		price = request.getParameter("price");
		country = request.getParameter("country");
				
        if(request.getParameter("page") != null)
            page = Integer.parseInt(request.getParameter("page"));
        result = db.SearchItem((page-1)*recordsPerPage,recordsPerPage,name,category,price,country);
        int noOfRecords = db.getNoOfRecords();
        noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
        categories = db.ReturnCategories();
        countries = db.ReturnCountries();
        
        request.setAttribute("countries",countries);
        request.setAttribute("categories",categories);
        request.setAttribute("result", result);
        request.setAttribute("noOfPages", noOfPages);
        request.setAttribute("currentPage", page);
        request.setAttribute("name",name);
        request.setAttribute("category",category);
        request.setAttribute("price",price);
        request.setAttribute("country", country);
       
        
		RequestDispatcher dispatcher = request.getRequestDispatcher("DisplaySearch.jsp");
		dispatcher.forward(request,response);
		
		db.Close();
	}

}
