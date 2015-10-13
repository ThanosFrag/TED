package ted;

import java.io.IOException;
import java.io.PrintWriter;
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
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login")

public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Driver db = new Driver();
		int id=0;
		int req=0;
		PrintWriter out = response.getWriter();
		db.Connect();
		String name = request.getParameter("name");
		String pass = request.getParameter("password");	
		pass = MD5.crypt(pass);
		
		String email = null;
		ResultSet res = db.Finduser(name, pass);

		String FirstName = null;
		try {
			if (res==null || res.first()==false){
				db.Close();
				request.setAttribute("username", 1);
				RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
				dispatcher.forward(request,response);	
				return;
				
			}			
	
			FirstName = res.getString(1);
			id = res.getInt("idUSer");
			req = res.getInt("ReqCond");
			email = res.getString("Email");

		} catch (SQLException e) {
			e.printStackTrace();
		}
		int access = 0;
		try {
			access = res.getInt("Access_Level");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if (access != 1){
			HttpSession session = request.getSession();
			if(req==1)
				session.setAttribute("login", Integer.toString(id));
			session.setAttribute("name", (String)name);
			session.setAttribute("message", (String)"You have successfully logged in !!");
			request.setAttribute("name", (String)name);
			request.setAttribute("email", (String)email);
			response.sendRedirect("/TED/Index");
		//	RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
		//	dispatcher.forward(request,response);
		}
		else {
			
			HttpSession session = request.getSession();
			session.setAttribute("login", Integer.toString(id));
			session.setAttribute("name", (String)name);
			session.setAttribute("message", (String)"You have successfully logged in !!");
			session.setAttribute("admin",Integer.toString(id));
			response.sendRedirect("Message.jsp");

		}

			
		db.Close();
	}

}
