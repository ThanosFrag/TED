package ted;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Driver db = new Driver();
		PrintWriter out = response.getWriter();
		db.Connect();
		String name = request.getParameter("Name");
		String Email = request.getParameter("Email");
		String pass = request.getParameter("password");
		pass = MD5.crypt(pass);
		String Fullname = request.getParameter("name");
		String Country =  request.getParameter("Country");
		String Location =  request.getParameter("Location");
		if (db.Checkuser(name, Email)){
			db.insertUser(name, Email,pass,Fullname,Country,Location);
			HttpSession session = request.getSession();
			session.setAttribute("name", Fullname);
			request.setAttribute("name", (String)name);
			RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
			session.setAttribute("message", (String)"You have successfully registered !!");
			dispatcher.forward(request,response);
			}
		else{
			request.setAttribute("username", 1);
			RequestDispatcher dispatcher = request.getRequestDispatcher("register.jsp");
			dispatcher.forward(request,response);
			
		}
		db.Close();
	}

}
