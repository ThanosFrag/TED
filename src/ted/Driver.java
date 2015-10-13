package ted;
import java.io.InputStream;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import ted.item.Category;
public class Driver {

	private Connection connection = null; 
	private PreparedStatement stmt = null;
	private int noOfRecords;
	public void Connect(){
		
		try {
		    String connectionURL = "jdbc:mysql://localhost/db";
		    
		    Class.forName("com.mysql.jdbc.Driver").newInstance(); 
		    connection = DriverManager.getConnection(connectionURL, "root", "1");

		}catch(Exception ex){
			System.out.println("Unable to connect to database"+ex);
		}  		
		
	}
	public void Close(){
		try {
			connection.close();
			
		} catch (SQLException e) {
			System.out.println("Unable to connect to database"+e);
		}
		connection = null;
	}
	boolean Checkuser(String Name,String Email) {
		ResultSet result=null;
		try {
		stmt = connection.prepareStatement("Select * From User where Username = ? or Email = ?");
		stmt.setString(1, Name);
	    stmt.setString(2, Email);
		
	    result = stmt.executeQuery();
	    if (result!=null && result.next()==true)
	    	return false;
	    else
	    	return true;	    
	    
	    
		} catch (SQLException e) {
			System.out.println("Unable to execute"+e);	
			
			return false;
		}

	 
	}
	public boolean IsAdmin(int id){
		ResultSet result=null;
		try {
		stmt = connection.prepareStatement("Select Access_Level From User where id = ?");
		stmt.setInt(1, id);
		
	    result = stmt.executeQuery();
	    if (result.getInt(1)==1)
	    	return true;
	    else
	    	return false;
		} catch (SQLException e) {
			System.out.println("Unable to execute"+e);	
			
			return false;
		}		
		
	}
	ResultSet Finduser(String Name) {
		ResultSet result=null;
		try {
		stmt = connection.prepareStatement("Select * From User where Username = ?");
		stmt.setString(1, Name);
		
	    result = stmt.executeQuery();
	    if (result!=null && result.next()==true){
	    	return result; 
	    }
	    else
	    	return null;
	    
	    
		} catch (SQLException e) {
			System.out.println("Unable to execute"+e);	
			
			return null;
		}

	 
	}	
	ResultSet Finduser(String Name,String Password) {
		ResultSet result=null;
		try {
		stmt = connection.prepareStatement("Select Name,Access_Level,idUser,ReqCond,Email From User where Username = ? and pass1 = ?");
		stmt.setString(1, Name);
	    stmt.setString(2, Password);
		
	    result = stmt.executeQuery();
	    if (result!=null && result.next()==true){
	    	return result; 
	    }
	    else
	    	return null;
	    
	    
		} catch (SQLException e) {
			System.out.println("Unable to execute"+e);	
			
			return null;
		}

	 
	}
	int updateUser(String name,int Rating,String Country,String Location){
		try {
		    stmt = connection.prepareStatement("UPDATE User set UserRating = ?,Country = ? , Location = ? where Username = ? ");
		    stmt.setString(2, Country);
		    stmt.setString(3, Location);
		    stmt.setString(4, name);
		    stmt.setInt(1, Rating);
			stmt.executeUpdate();
			
			stmt = connection.prepareStatement("Select idUser from User where Username= ? ");
			stmt.setString(1,name);
			
			ResultSet result = stmt.executeQuery() ;
			result.next();
			return result.getInt(1);
			} catch (SQLException e) {
				System.out.println("Unable to execute"+e);			
			}
		return -1;
	}	
	int updateUser(String name,int Rating){
		try {
		    stmt = connection.prepareStatement("UPDATE User set UserRating = ? where Username = ? ");
		    stmt.setString(2, name);
		    stmt.setInt(1, Rating);
			stmt.executeUpdate();
			
			stmt = connection.prepareStatement("Select idUser from User where Username= ? ");
			stmt.setString(1,name);
			
			ResultSet result = stmt.executeQuery() ;
			result.next();
			return result.getInt(1);
			} catch (SQLException e) {
				System.out.println("Unable to execute"+e);			
			}
		return -1;
	}
	int insertUser(String name,int Rating,String Country,String Location){
		try {
	    stmt = connection.prepareStatement("INSERT INTO User (Username,UserRating,Country,Location,ReqCond) values (?, ?,?,?,1)",Statement.RETURN_GENERATED_KEYS);
	    stmt.setString(1, name);
	    stmt.setInt(2, Rating);
	    stmt.setString(3, Country);
	    stmt.setString(4, Location);
		stmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Unable to execute"+e);			
		}
        try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            }
            else {
                throw new SQLException("Creating user failed, no ID obtained.");
            }
            
        
		} catch (SQLException e) {
			System.out.println("Unable to Execute "+e);			
		}
		return -1;       
		
	}	
	void updatePass(int id){
		try{
			String pass = MD5.crypt("123");
			stmt = connection.prepareStatement("update User set pass1 = ? where idUser=? ");
		    stmt.setString(1, pass);
		    stmt.setInt(2, id);		
		    stmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Unable to execute"+e);			
		}
	}
	
	int insertUser(String name,int Rating){
		try {
	    stmt = connection.prepareStatement("INSERT INTO User (Username,UserRating,ReqCond) values (?, ?,1)",Statement.RETURN_GENERATED_KEYS);
	    stmt.setString(1, name);
	    stmt.setInt(2, Rating);
		stmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Unable to execute"+e);			
		}
        try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            }
            else {
                throw new SQLException("Creating user failed, no ID obtained.");
            }
            
        
		} catch (SQLException e) {
			System.out.println("Unable to Execute "+e);			
		}
		return -1;       
		
	}	
	void insertUser(String name,String email,String pass,String fullname,String Country,String Location){
		try {
	    stmt = connection.prepareStatement("INSERT INTO User (Username, Email ,pass1,Name,Country,Location) values (?, ?, ?,?,?,?)");
	    stmt.setString(1, name);
	    stmt.setString(2, email);	
	    stmt.setString(3, pass);
	    stmt.setString(4, fullname);
	    stmt.setString(5, Country);
	    stmt.setString(6, Location);
		stmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Unable to execute"+e);			
		}
		
	}
	public void insertBid(int Iid,int Uid,double Amount) throws ParseException{
		Date current = new Date();
		DateFormat dateFormat = new SimpleDateFormat("dd-M-yyyy HH:mm:ss");
		String currentd  = dateFormat.format(current);
		
		try {
		    stmt = connection.prepareStatement("INSERT INTO Bids (Time, Amount ,ItemId,UserId) values (?, ?, ?,?)");
		    stmt.setString(1, currentd);
		    stmt.setDouble(2, Amount);	
		    stmt.setInt(3, Iid);
		    stmt.setInt(4, Uid);
			stmt.executeUpdate();
			stmt = connection.prepareStatement("UPDATE Item set Currently=? where idItem = ?");
		    stmt.setDouble(1, Amount);	
		    stmt.setInt(2, Iid);	
		    stmt.executeUpdate();
			
			
			} catch (SQLException e) {
				System.out.println("Unable to execute"+e);			
			}		
		
	}
	public void insertBid(String currentd,int Iid,int Uid,double Amount) throws ParseException{
		
		try {
		    stmt = connection.prepareStatement("INSERT INTO Bids (Time, Amount ,ItemId,UserId) values (?, ?, ?,?)");
		    stmt.setString(1, currentd);
		    stmt.setDouble(2, Amount);	
		    stmt.setInt(3, Iid);
		    stmt.setInt(4, Uid);
			stmt.executeUpdate();
			stmt = connection.prepareStatement("UPDATE Item set Currently=? where idItem = ?");
		    stmt.setDouble(1, Amount);	
		    stmt.setInt(2, Iid);	
		    stmt.executeUpdate();
			
			
			} catch (SQLException e) {
				System.out.println("Unable to execute"+e);			
			}		
		
	}	
	public ResultSet listUser(int offset, int noOfRecords,int choice,String Name) {
		ResultSet result=null;
		ResultSet res = null;
		try {
		if (choice == 0) {
			stmt = connection.prepareStatement("Select SQL_CALC_FOUND_ROWS Username From User Limit ? Offset ?");
		    stmt.setInt(1,noOfRecords);
		    stmt.setInt(2,offset);
		    result = stmt.executeQuery();
	   	    
		    stmt = connection.prepareStatement("SELECT FOUND_ROWS()");
		    res = stmt.executeQuery();

	        if(res.next())
	            this.noOfRecords = res.getInt(1);
		    if (result!=null && result.next()==true && this.noOfRecords > 0)
		    	return result; 
		    else 
		    	return null;
		}
		else if (choice == 1) {
			stmt = connection.prepareStatement("Select Username,Email,ReqCond From User Where Username = ?");
			stmt.setString(1, Name);
		}
		else if (choice == 2){
			stmt = connection.prepareStatement("UPDATE User SET ReqCond = ? WHERE Username = ?");
			stmt.setInt(1, 1);
			stmt.setString(2, Name);
			stmt.executeUpdate();
			return null;
		}
		else if (choice == 3) {
			stmt = connection.prepareStatement("DELETE FROM User WHERE Username = ?");
			stmt.setString(1, Name);
			stmt.executeUpdate();
			return null;
		}
	    result = stmt.executeQuery();
	    if (result!=null && result.next()==true)
	    	return result; 
	    else 
	    	return null;
	    
	    
		} catch (SQLException e) {
			System.out.println("Unable to execute"+e);	
			
			return null;
		}
	}
	public ResultSet ReturnCategories(){
		ResultSet result = null;
		try {
		    stmt = connection.prepareStatement("select CategoryName,idCategory from Category");
			result = stmt.executeQuery();
			} catch (SQLException e) {
				System.out.println("Unable to execute"+e);			
			}	
		return result;

	}	
	public int ReturnCategoryId(String name){
		ResultSet result = null;
		try {
		    stmt = connection.prepareStatement("select * from Category where CategoryName = ?");
		    stmt.setString(1, name);
			result = stmt.executeQuery();
			if (result==null || !result.first()){
				stmt = connection.prepareStatement("INSERT INTO Category (CategoryName) Values (?)",Statement.RETURN_GENERATED_KEYS);
				stmt.setString(1, name);
				stmt.executeUpdate();
				try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
			        if (generatedKeys.next()) {
			        	return generatedKeys.getInt(1);
		            }
		            else {
		                throw new SQLException("Creating user failed, no ID obtained.");
		            }			
	
				}
			}
			result.first();
			return result.getInt("idCategory");
			} catch (SQLException e) {
				System.out.println("Unable to execute"+e);			
			}	
		
		return -1;
		

	}	
	public int AddItem(String name,double Currently,String started,String ended,String Description,Double BuyPrice,String Country,int id,String x,String y,String image){
		int Itemid;
		
	
		
		
		String query = "INSERT INTO Item(Name,Currently,BuyPrice,Started,Ended,Description,Country,idUser,Image)VALUES (?,?,?,?,?,?,?,?,?)";
		try {
			stmt = connection.prepareStatement("select * from Item where Name = ? and idUser = ?");
			stmt.setString(1, name);
			stmt.setInt(2, id);
			ResultSet result  = stmt.executeQuery();
			stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		    stmt.setString(1, name);
		    stmt.setDouble(2, Currently);	
		    stmt.setDouble(3, BuyPrice);
		    stmt.setString(4, started);
		    stmt.setString(5, ended);
		    stmt.setString(6, Description);

		    stmt.setString(7, Country);
		    stmt.setInt(8, id);
		    stmt.setString(9, image);
			stmt.executeUpdate();
	        try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
	        	
	            if (generatedKeys.next()) {
	            	Itemid  = generatedKeys.getInt(1);
	            	if (!image.equals("/images/default.jpg")){
	            		stmt = connection.prepareStatement("UPDATE Item set Image = ? where idItem = ?");
	            		String update = "/images/"+Itemid+"."+image;
	            		
	            		stmt.setString(1,update);
	            		stmt.setInt(2, Itemid);
	            		stmt.executeUpdate();
	            	}
	            	
	            	
	            	
	            	if (x!=null && y!=null && !x.isEmpty() && !y.isEmpty()){
		            	stmt = connection.prepareStatement("UPDATE Item set Latitude = ? , Longtitude = ? where idItem = ?");
		    		    stmt.setDouble(1, Double.parseDouble(x));
		    		    stmt.setDouble(2, Double.parseDouble(y));
		    		    stmt.setInt(3, Itemid);
		    		    stmt.executeUpdate();
	            	}
	            	
	                return Itemid;
	            }
	            else {
	                throw new SQLException("Creating user failed, no ID obtained.");
	            }
	        }
		} catch (SQLException e) {
			System.out.println("Unable to Execute "+e);			
		}
		return -1;
	}		
		public void AddItem_has_Category(int idI,int idC){
			
			try {
				stmt = connection.prepareStatement("INSERT INTO Item_has_category(itemId,CategoryId)VALUES (?,?)");
			    stmt.setInt(1, idI);
			    stmt.setInt(2, idC);
				stmt.executeUpdate();
			} catch (SQLException e) {
				System.out.println("Unable to Execute "+e);			
			}		
		}
			public void RemoveItem_has_Category(String id){
				
				try {
					stmt = connection.prepareStatement("delete from Item_has_category where itemId=?");
				    stmt.setInt(1, Integer.parseInt(id));
					stmt.executeUpdate();
				} catch (SQLException e) {
					System.out.println("Unable to Execute "+e);			
				}	
			}
			public void RemoveItem(String id){
				
				try {
					stmt = connection.prepareStatement("delete from Item where idItem=?");
				    stmt.setInt(1, Integer.parseInt(id));
					stmt.executeUpdate();
				} catch (SQLException e) {
					System.out.println("Unable to Execute "+e);			
				}	
			}
	
	public ResultSet getItemCategories(int id){
		ResultSet result=null;
		List<Category> categories = new ArrayList<Category>() ;
		try {
			String query = "select c.CategoryName from Category c,Item_has_category ic where ic.itemId=? and ic.CategoryId  = c.idCategory";
			stmt = connection.prepareStatement(query);
			stmt.setInt(1,id);
			result = stmt.executeQuery();
			return result;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;		
		
	}
	public ResultSet getBids(String id){
		ResultSet result=null;
		try {
			stmt = connection.prepareStatement("select b.Amount,b.Time,u.Name,u.Surname from Bids b,User u where b.ItemId = ? and b.UserId = u.idUser order by b.Amount DESC");
			stmt.setInt(1, Integer.parseInt(id));
			result = stmt.executeQuery();
			return result;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;	
	}
	public ResultSet getBidsInfo(int id){
		String query= "select Username,UserRating,Time,Amount from Bids,User where UserId = idUser && ItemId=?";
		ResultSet result = null;
		try {
			stmt = connection.prepareStatement(query);
			stmt.setInt(1, id);
			result = stmt.executeQuery();
			return result;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;			
	}
	public ResultSet getItems(int offset, int noOfRecords,String id){

		ResultSet result=null;
		ResultSet res = null;
		
		if (id==null)
			return null;

		try {
			
			stmt = connection.prepareStatement("Select SQL_CALC_FOUND_ROWS idItem,Name,BuyPrice,Started,Ended,Image,Currently "
					+ "From Item where idUser = ? and Started <  cast(now() as datetime) and Ended >  cast(now() as datetime)"
					+ " Order By Started Asc "
					+ " Limit ? Offset ? ");
			stmt.setInt(1, Integer.parseInt(id));
		    stmt.setInt(2,noOfRecords);
		    stmt.setInt(3,offset);
			result = stmt.executeQuery();
			stmt = connection.prepareStatement("SELECT FOUND_ROWS()");
		    res = stmt.executeQuery();

	        if(res.next())
	            this.noOfRecords = res.getInt(1);

			return result;
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	public ResultSet getInactiveItems(int offset, int noOfRecords,String id){

		ResultSet result=null;
		ResultSet res = null;
		
		if (id==null)
			return null;

		try {
			
			stmt = connection.prepareStatement("select SQL_CALC_FOUND_ROWS idItem,Name,BuyPrice,Started,Ended,Image,Currently "
					+ "from Item where idUser = ? and Currently=0.0 and Ended >  cast(now() as datetime)"
					+ " Order By Started Asc "
					+ " Limit ? Offset ? ");
			stmt.setInt(1, Integer.parseInt(id));
		    stmt.setInt(2,noOfRecords);
		    stmt.setInt(3,offset);
			result = stmt.executeQuery();
			stmt = connection.prepareStatement("SELECT FOUND_ROWS()");
		    res = stmt.executeQuery();

	        if(res.next())
	            this.noOfRecords = res.getInt(1);

			return result;
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}	
	public ResultSet getItems(){
		ResultSet result=null;
		try {
			
			stmt = connection.prepareStatement("select * from Item");
			result = stmt.executeQuery();	
			return result;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}	
	public ResultSet getUserInfo(int id){
		ResultSet result=null;		
		try {
			stmt = connection.prepareStatement("select Username,UserRating from User where idUser = ?");
			stmt.setInt(1,id);
			result = stmt.executeQuery();	
			return result;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;		
	}
	public ResultSet getBuddies(int id){
		ResultSet result=null;	
		String query = "select distinct idUser,Username from User where idUser in ("+
				"select UserId from Item,Bids where idUser = ? and idItem=ItemId and BuyPrice=Currently and Amount = BuyPrice) or idUser in ("+
				"select idUser from Item,Bids where UserId = ? and idItem=ItemId and BuyPrice=Currently and Amount = BuyPrice)";
		
		try {
			stmt = connection.prepareStatement(query);
			stmt.setInt(1,id);
			stmt.setInt(2,id);
			result = stmt.executeQuery();	
			return result;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;			
	}
	public ResultSet getItem(String id){
		
		ResultSet result = null;
		try {
			stmt = connection.prepareStatement("select * from Item where idItem = ?");
			stmt.setInt(1, Integer.parseInt(id));
			result = stmt.executeQuery();	
			return result;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public String getSItem(String id){
		
		ResultSet result = null;
		try {
			stmt = connection.prepareStatement("select * from Item where idItem = ? and Started > cast(now() as datetime)");
			stmt.setInt(1, Integer.parseInt(id));
			result = stmt.executeQuery();	
			if (result.first())
			return "Not Active item";
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}	
	public String getEItem(String id){
		
		ResultSet result = null;
		try {
			stmt = connection.prepareStatement("select * from Item where idItem = ? and Ended <  cast(now() as datetime)");
			stmt.setInt(1, Integer.parseInt(id));
			result = stmt.executeQuery();	
			if (result.first())
			return "Item is Bought";
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}	
	public void buy(String id,double buy){
		try {
			stmt = connection.prepareStatement("update Item set Ended = cast(now() as datetime),BuyPrice=? where idItem = ?");
			stmt.setInt(1, Integer.parseInt(id));
			stmt.setDouble(2, buy);
			stmt.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	public ResultSet getUser(int id){
		
		ResultSet result = null;
		try {
			stmt = connection.prepareStatement("select * from User where idUser = ?");
			stmt.setInt(1, id);
			result = stmt.executeQuery();	
			return result;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}	
	public ResultSet getAllItems(){
		Date current = new Date();
		Date itemSDate = new Date();
		Date itemEDate = new Date();
		ResultSet result=null;
	
		DateFormat dateFormat = new SimpleDateFormat("dd-M-yyyy HH:mm:ss");
		try {
			
			stmt = connection.prepareStatement("select idIted,Name,Category,BuyPrice,Started,Ended from Item");
			result = stmt.executeQuery();
			while (result.next()){
				try {
					itemSDate = dateFormat.parse(result.getString(4));
					itemEDate = dateFormat.parse(result.getString(5));
					current  = dateFormat.parse(dateFormat.format(current));
					if (!current.before(itemEDate) ||  !current.after(itemSDate)){
						result.deleteRow();
					}
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			
				
			}
			return result;
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public ResultSet SearchItem(int offset, int noOfRecords,String name,String category,String price,String country) {
		ResultSet result=null;
		ResultSet res=null;
		String[] parts;
		String part1=null;
		String part2=null;
		double price1=0;
		double price2=100000;
		
		if (price != null && !price.equals("All")) {
			parts = price.split("-");
			part1 = parts[0];
			part2 = parts[1];
			price1 = Double.parseDouble(part1);
			price2 = Double.parseDouble(part2);
		}
		if (country == null || country.equals("All"))
			country = "%";
		if (category.equals("All"))
			category = "%";
		try {
		stmt = connection.prepareStatement("Select SQL_CALC_FOUND_ROWS Distinct i.Name,i.Currently,i.Country,i.idItem,i.Image "
				+ "From Item i,Item_has_category ic,Category c "
				+ "Where (i.Name Like ? Or i.Description Like ?) And c.CategoryName Like ? And i.Currently >= ?  And i.Currently <= ? And i.Country Like ? And i.idItem=ic.itemId And ic.CategoryId=c.idCategory "
				+ "Order By i.Currently Asc"
				+ " Limit ? Offset ?");
		stmt.setString(1,"%"+name+"%");
		stmt.setString(2,"%"+name+"%");
		stmt.setString(3,"%"+category+"%");
	    stmt.setDouble(4, price1);
	    stmt.setDouble(5, price2);
	    stmt.setString(6,"%"+country+"%");
	    stmt.setInt(7,noOfRecords);
	    stmt.setInt(8,offset);
		
	    result = stmt.executeQuery();
	   	    
	    stmt = connection.prepareStatement("SELECT FOUND_ROWS()");
	    res = stmt.executeQuery();

        if(res.next())
            this.noOfRecords = res.getInt(1);
	    if (result!=null && result.next()==true && this.noOfRecords > 0)
	    	return result; 
	    else 
	    	return null;
	    
	    
		} catch (SQLException e) {
			System.out.println("Unable to execute"+e);	
			
			return null;
		}
	}
	
    public int getNoOfRecords() {
        return noOfRecords;
    }
    public int getUnreadMessage(int id){
		try {
			int counter=0;
			ResultSet result=null;   	
			stmt = connection.prepareStatement("select * from Message where ToUE = ? and Cond=0");
			stmt.setInt(1,id);
			result = stmt.executeQuery(); 
			while (result.next()) counter++;
			return counter;
		} catch (SQLException e) {
			System.out.println("Unable to execute!"+e);			
		}		
		return 0;
    }
    public void insertMessage(String from,String to,String subject,String msg) {

		try {
			ResultSet result=null;
			stmt = connection.prepareStatement("select idUser from User where Username = ?");
			stmt.setString(1,to);
			result = stmt.executeQuery();
			result.first();
			
			
			
			
			stmt = connection.prepareStatement("INSERT INTO Message (FromUE,ToUE ,Subject,Message,Date) values (?,?,?,?,cast(now() as datetime))");
		    stmt.setInt(2, result.getInt("idUser"));
		    stmt.setInt(1, Integer.parseInt(from));	
		    stmt.setString(3, subject);
		    stmt.setString(4, msg);
			stmt.executeUpdate();

			} catch (SQLException e) {
				System.out.println("Unable to execute"+e);			
			}
		
    }
    

    public ResultSet getMessage(int choice,int userid,int id) {
		ResultSet result=null;
		try {
		if (choice == 0) {
			stmt = connection.prepareStatement("Select m.idMessage,u.Email,m.Cond,m.Subject,m.Message,m.Date From Message m,User u Where m.ToUE = u.idUser And u.idUser = ?");
			stmt.setInt(1, userid);
		}
		else if (choice == 4) {
			stmt = connection.prepareStatement("Select m.idMessage,u.Email,m.Cond,m.Subject,m.Message,m.Date From Message m,User u Where m.FromUE = u.idUser And u.idUser = ?");	
			stmt.setInt(1, userid);
		}
		else if (choice == 1){
			stmt = connection.prepareStatement("UPDATE Message SET Cond = ? WHERE idMessage = ?");
			stmt.setInt(1, 1);
			stmt.setInt(2, id);
			stmt.executeUpdate();
			return null;
		}
		else if (choice == 2) {
			stmt = connection.prepareStatement("Select u1.Email,u2.Email,m.Subject,m.Message From Message m,User u1,User u2 Where m.idMessage = ? And m.ToUE = u1.idUser And m.FromUE = u2.idUser");
			stmt.setInt(1,id);
		}

		else if (choice == 3) {
			stmt = connection.prepareStatement("DELETE FROM Message WHERE idMessage = ?");
			stmt.setInt(1, id);
			stmt.executeUpdate();
			return null;
		}
	    result = stmt.executeQuery();
	    if (result!=null && result.first()==true)
	    	return result; 
	    else 
	    	return null;
	    
	    
		} catch (SQLException e) {
			System.out.println("Unable to execute"+e);	
			
			return null;
		}
    }

    
    public ResultSet prepRecs(int id,int id2,int choice) {
		ResultSet result = null;
		try {
			if (choice == 0) {
				stmt = connection.prepareStatement("Select Distinct ItemId From Bids Where UserId = ?");
				stmt.setInt(1, id);
				result = stmt.executeQuery();
				return result;
			}
			else if (choice == 1) {
				stmt = connection.prepareStatement("Select Distinct UserId From Bids Where ItemId = ? And UserId != ?");
				stmt.setInt(1, id);
				stmt.setInt(2, id2);
				result = stmt.executeQuery();
				return result;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
    }	
	public ResultSet ReturnCountries(){
		ResultSet result = null;
		try {
		    stmt = connection.prepareStatement("Select Distinct Country from Item");
			result = stmt.executeQuery();
			} catch (SQLException e) {
				System.out.println("Unable to execute"+e);			
			}	
		return result;

	}	
}




