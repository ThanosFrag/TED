package ted;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Recommendation
 */
@WebServlet("/Index")
public class Index extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 
		 HttpSession session = request.getSession();
		 if (session.getAttribute("login")==null){
			 response.sendRedirect("index.jsp");
			 return;
		 }
		 Driver db = new Driver();
		 db.Connect();
		 ResultSet mybids = null;
		 ResultSet mutuals = null;
		 ResultSet finalres = null;
		 int id = Integer.parseInt((String)session.getAttribute("login"));
		 int myitemsid;
		 int mutualsId;
		 int recId;
		 int k = 3;
		 int counter=0;
		 HashMap<Integer,Integer> map = new HashMap<Integer,Integer>();
		 ArrayList<Integer> myArrayBid = new ArrayList<Integer>();
		 ArrayList<Integer> reccomended = new ArrayList<Integer>();
		 
		 mybids = db.prepRecs(id,0,0);
		 try {
			while (mybids.next()) {
				myitemsid = mybids.getInt("ItemId");
				myArrayBid.add(myitemsid);
				mutuals = db.prepRecs(myitemsid,id,1);
				while (mutuals.next()) {
					mutualsId = mutuals.getInt("UserId");
					if (!map.containsKey(mutualsId))
						map.put(mutualsId, 1);
					else
						map.put(mutualsId,map.get(mutualsId) + 1);
				}
			}
		    Object[] a = map.entrySet().toArray();
		    Arrays.sort(a, new Comparator() {
		        public int compare(Object o1, Object o2) {
		            return ((Map.Entry<Integer, Integer>) o2).getValue().compareTo(
		                    ((Map.Entry<Integer, Integer>) o1).getValue());
		        }
		    });
		    for (Object e : a) {
		    	if (k == counter)
		    		break;
		        finalres = db.prepRecs(((Map.Entry<Integer, Integer>) e).getKey(),0, 0);
		        while (finalres.next()) {
		        	recId = finalres.getInt("ItemId");
		        	if (!myArrayBid.contains(recId) && !reccomended.contains(recId)) {
		        		reccomended.add(recId);
		        	}
		        }
		        counter++;
		    }
		    request.setAttribute("recs",(ArrayList<Integer>)reccomended);
			RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
			dispatcher.forward(request,response);
		    
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
