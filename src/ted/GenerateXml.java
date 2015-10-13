package ted;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import ted.item.Bid;
import ted.item.Bidder;
import ted.item.Bids;
import ted.item.Category;
import ted.item.Item;
import ted.item.Items;
import ted.item.Location;
import ted.item.ObjectFactory;
import ted.item.Seller;

/**
 * Servlet implementation class GenerateXml
 */
@WebServlet("/GenerateXml")
public class GenerateXml extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Driver db = new Driver();
		HttpSession session = request.getSession();
		ObjectFactory factory = new  ObjectFactory();
		Items items = factory.createItems();
		Item item = null;
		Bids bids = null;
		Seller seller = null;
		Bidder bidder = null;
		Bid bid = null;
		ResultSet dbitems = null,dbbids=null,dbseller=null,dbcategories=null;
		db.Connect();
		dbitems = db.getItems();
		String first=null;
		int number=0;
		try {
			while (dbitems.next()){
				item = new Item();
				Location location  = new Location();
				bids = new Bids();
				seller = new Seller();
				item.setItemID(Integer.toString(dbitems.getInt("idItem")));
				item.setName(dbitems.getString("Name"));
				dbcategories  = db.getItemCategories(dbitems.getInt("idItem"));
				while (dbcategories.next()){
					Category category = factory.createCategory();
					category.setvalue(dbcategories.getString(1));
					item.getCategory().add(category);
				}
				
				dbbids = db.getBidsInfo(dbitems.getInt("idItem"));
				while (dbbids.next()){
					bid = new Bid();
					number++;
					bidder = new Bidder();
					bidder.setUserID(dbbids.getString("Username"));
					if ( dbbids.getString("UserRating") !=null)
						bidder.setRating(dbbids.getString("UserRating"));
					else
						bidder.setRating("0");
					bid.setBidder(bidder);
					bid.setAmount(dbbids.getString("Amount"));
					bid.setTime(dbbids.getString("Time"));
					bids.getBid().add(bid);
					first = dbbids.getString("Amount");
					
				}
				item.setNumberOfBids(Integer.toString(number));
				item.setFirstBid(first);
				number = 0;
				item.setCurrently(Double.toString(dbitems.getDouble("Currently")));
				item.setBuyPrice(Double.toString(dbitems.getDouble("BuyPrice")));
				item.setCountry(dbitems.getString("Country"));
				item.setStarted(dbitems.getString("Started"));
				item.setEnds(dbitems.getString("Ended"));
				if (dbitems.getString("Latitude")!=null && dbitems.getString("Longtitude")!=null){
					
					location.setLatitude(dbitems.getString("Latitude"));
					location.setLongitude(dbitems.getString("Longtitude"));
					item.setLocation(location);
				}
				item.setDescription(dbitems.getString("Description"));
				dbseller = db.getUserInfo(dbitems.getInt("idUser"));
				dbseller.first();

				if (dbseller.getString("UserRating")!=null)
					seller.setRating(dbseller.getString("UserRating"));
				else
					seller.setRating("0");
				seller.setUserID(dbseller.getString("Username"));
				item.setSeller(seller);
				items.getItem().add(item);

				
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			//Marshalling
			String current;
            current = System.getProperty("user.home");
            File dir = new File(current+File.separator + "XML_Output");
            dir.mkdir();
			File file = new File(current+File.separator + "XML_Output"+File.separator+"items.xml");
			JAXBContext jaxbContext = JAXBContext.newInstance(Items.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
			true);
			jaxbMarshaller.marshal(items, file);
			jaxbMarshaller.marshal(items, System.out);
			}
			catch(Exception ex)
			{
			System.out.println(ex);
			}		
		
		
		db.Close();
		session.setAttribute("message", (String)"You have successfully exported the items to xml !!");
		response.sendRedirect("Message.jsp");
	}

}
