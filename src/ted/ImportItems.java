package ted;

import java.io.Console;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.nio.CharBuffer;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

import ted.item.Bid;
import ted.item.Bidder;
import ted.item.Bids;
import ted.item.Category;
import ted.item.Item;
import ted.item.Items;
import ted.item.Seller;

/**
 * Servlet implementation class ImportItems
 */
@WebServlet("/ImportItems")
public class ImportItems extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	
	public String check(String e){
		 String dateformats[] = {"","yyyy-MM-dd HH:mm:ss", "yyyy-M-d HH:mm:ss","MMM-dd-y HH:mm:ss"};	
		 Date date = new Date();
	     if (e != null) {
	         for (String parse : dateformats) {
	             SimpleDateFormat sdf = new SimpleDateFormat(parse);
	             try {
	                 if (sdf.parse(e).before(date)) return "Item is bought";
	             } catch (ParseException e2) {
					
	             }
	         }
	     }	     
		return null;
	}	
	
	int insert(Driver db,String name,int Rating) throws SQLException{
		ResultSet result = null;
		result = db.Finduser(name);
		if (result==null || !result.first())
			return db.insertUser(name,Rating);				
		else
			return db.updateUser(name, Rating);
		
	}
	int insert(Driver db,String name,int Rating,String Country,String Location) throws SQLException{
		ResultSet result = null;
		result = db.Finduser(name);
		
		if (result==null || !result.first())
			return db.insertUser(name,Rating,Country,Location);				
		else{
			return db.updateUser(name, Rating,Country,Location);
		}
	}	
	static List<Integer> pickNRandom(List<Integer> lst, int n) {
	    List<Integer> copy = new LinkedList<Integer>(lst);
	    Collections.shuffle(copy);
	    return copy.subList(0, n);
	}
	
	void SaveToDb(Items items) throws SQLException, NumberFormatException, ParseException{
		Driver db = new Driver();
		Iterator<Item> item_iter = items.getItem().iterator();
		Iterator<Bid> bid_iter  =null;
		Bid bid;
		Item item;
		int id,item_id,bidder_id;
		db.Connect();
		Seller seller = null;
		Bidder bidder = null;	
		String currently=null,amount,buy=null;
		
		String image = "/images"+File.separator+"default.jpg";
		ResultSet result = db.ReturnCategories();
		List<Integer> category_id = new ArrayList<Integer>();
		int totalcategories=0;
		while (result.next()){
			category_id.add(result.getInt("idCategory"));
			totalcategories++;
		}
		
		while (item_iter.hasNext()){
			buy = null;
			currently=null;
			item = item_iter.next();
			seller = item.getSeller();
			id = insert(db,seller.getUserID(),Integer.parseInt(seller.getRating()));
			db.updatePass(id);
			currently = item.getCurrently();
			buy = item.getBuyPrice();
			if (buy!=null){
				buy  = buy.replace('$',' ');
				buy = buy.replace(",","");
			}
			if (currently!=null){
				currently = currently.replace('$', ' ');
				currently = currently.replace(",","");
					
			}
			Double _buy;
			boolean isbought = false;
			if (buy!=null)
				_buy = Double.parseDouble(buy);
			else
				_buy = -1.0;
			if (check(item.getEnds())!=null){
				isbought = true;
				_buy = Double.parseDouble(currently);
			}
			item_id = db.AddItem(item.getName(), Double.parseDouble(currently), item.getStarted(), item.getEnds(), item.getDescription(), _buy, item.getCountry(), id, item.getLocation().getLatitude(), item.getLocation().getLongitude(),image);
			if (item_id ==-1)
				continue;
			Random random = new Random();
			List<Integer> RandomCat= pickNRandom(category_id,random.nextInt(totalcategories)+1);
			Iterator<Integer> CategoryIterator = RandomCat.iterator();
			while (CategoryIterator.hasNext()) {
				db.AddItem_has_Category(item_id,CategoryIterator.next());
			}
			bid_iter = item.getBids().getBid().iterator();
			boolean flag = false;
			while (bid_iter.hasNext()){
				bid = bid_iter.next();
				bidder = bid.getBidder();
				if (bidder.getLocation()!=null)
					bidder_id = insert(db,bidder.getUserID(),Integer.parseInt(bidder.getRating()),bidder.getCountry(),bidder.getLocation().getvalue());
				else
					bidder_id = insert(db,bidder.getUserID(),Integer.parseInt(bidder.getRating()),bidder.getCountry(),null);
				db.updatePass(bidder_id);
				amount  = bid.getAmount();
				amount = amount.replace('$', ' ');
				amount = amount.replace(",","");
				db.insertBid(bid.getTime(),item_id, bidder_id,Double.parseDouble(amount));
				
			}
		
		}
		
		
		
		
		db.Close();

		
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		DiskFileItemFactory factory = new DiskFileItemFactory();
		List<FileItem> items =null;
		ResultSet result = null;
		// Configure a repository (to ensure a secure temp location is used)
		ServletContext servletContext = this.getServletConfig().getServletContext();
		File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
		factory.setRepository(repository);
		// Create a new file upload handler
		ServletFileUpload upload = new ServletFileUpload(factory);
		try {
			items = upload.parseRequest(request);
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	 

		if (session==null){
			response.sendRedirect("/TED/index.jsp");
			return;
		}
		String Sid =(String)session.getAttribute("login");
		if (Sid==null){
			response.sendRedirect("/TED/index.jsp");
			return;
		}

		int id  = Integer.parseInt(Sid);
		Iterator<FileItem> iter = items.iterator();
		while (iter.hasNext()) {
	        FileItem item = iter.next();
	        String fieldname = item.getFieldName();
	        if (!item.isFormField()) {
	        	
	            String filename = FilenameUtils.getName(item.getName());
	            if(filename.length()>0)
	            {
		             InputStream filecontent = item.getInputStream();
	
		             byte[] buffer = new byte[filecontent.available()];
		             filecontent.read(buffer);
		             String file = new String(buffer, "UTF-8");   // if the charset is UTF-8
		             
		             Reader rfile = (Reader) new StringReader(file);
		     		 JAXBContext context;
					try {
						context = JAXBContext.newInstance(Items.class);
			    		 Unmarshaller unmarshaller = context.createUnmarshaller();
			    		 Items _Items = (Items)unmarshaller.unmarshal(rfile); 

			    		 SaveToDb(_Items);
			    		 
					} catch (JAXBException | NumberFormatException | SQLException | ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 

		    		
	            }
	        }
		}
		

		session.setAttribute("message", (String)"You have successfully imported the xml files !!");
		response.sendRedirect("Message.jsp");
	

	}

}
