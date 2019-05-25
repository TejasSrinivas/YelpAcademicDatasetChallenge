/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yelp3;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.sql.DriverManager;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author tejas
 */
public class populateTables {
    
    private static final String YELPUSERPATH = "F:\\Q3\\My Notes\\DB\\Homeworks\\HW3\\YelpDataset\\yelp_user.json";
    private static final String BUSINESSPATH = "F:\\Q3\\My Notes\\DB\\Homeworks\\HW3\\YelpDataset\\yelp_business.json";
    private static final String REVIEWSPATH = "F:\\Q3\\My Notes\\DB\\Homeworks\\HW3\\YelpDataset\\yelp_review.json";
    private static final String CHECKINPATH = "F:\\Q3\\My Notes\\DB\\Homeworks\\HW3\\YelpDataset\\yelp_checkin.json";
    private String l;
//PARSE YELP USER AND INSERT
    public void YelpUser() throws SQLException, FileNotFoundException, IOException, ParseException{
        
        Connection conn = null;
        PreparedStatement preparedStatement1 = null;
        PreparedStatement preparedStatement2 = null;
        PreparedStatement preparedStatement3 = null;
        JSONParser parser = new JSONParser();
  
        String insertYelpUserQuery = "INSERT INTO YELP_USER"+
                "(yelping_since, funny_votes, useful_votes," + 
                "cool_votes, review_count, user_name, user_id," + 
                "fans, average_stars, user_type, ctype_plain," + 
                "ctype_cool, ctype_more, ctype_note," + 
                "ctype_funny, ctype_writer, ctype_hot," + 
                "ctype_profile, ctype_photo, ctype_cute," +  
                "ctype_list ) VALUES" + 
                "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
       
        String insertUserFriendsQuery = "INSERT INTO USER_FRIENDS" + 
               "(user_id, friend_id) VALUES" + "(?,?)";
        
        String insertEliteQuery = "INSERT INTO ELITE_USER" + 
                "(user_id, elite) VALUES" + "(?,?)";
        try
        {
            conn = connectDB();
            preparedStatement1 = conn.prepareStatement(insertYelpUserQuery);
            preparedStatement2 = conn.prepareStatement(insertUserFriendsQuery);
            preparedStatement3 = conn.prepareStatement(insertEliteQuery);
            try (FileReader filereader = new FileReader(YELPUSERPATH)) {
                System.out.println("Reading data from file yelp_user.json");
                BufferedReader br = new BufferedReader(filereader);
                
                while ((l = br.readLine()) != null)
                {
                    Object o = parser.parse(l);
                    JSONObject jsonObject = (JSONObject) o;
                    
                String date_string = (String) jsonObject.get("yelping_since");
                System.out.println(date_string);
    			Calendar calendar = Calendar.getInstance();
                calendar.clear();
                calendar.set(Calendar.DATE, 1);
                calendar.set(Calendar.MONTH, Integer.parseInt(date_string.split("-")[1])-1);
                calendar.set(Calendar.YEAR,Integer.parseInt(date_string.split("-")[0]));
                Date date = (Date) calendar.getTime();
                System.out.println(date);
                java.sql.Date d = new java.sql.Date(date.getTime());
                preparedStatement1.setString(1, d.toString());
                    
                    JSONObject votes = (JSONObject) jsonObject.get("votes");
                    int  funny_votes = ((Long) votes.get("funny")).intValue();
                    int  useful_votes = ((Long) votes.get("useful")).intValue();
                    int  cool_votes = ((Long) votes.get("cool")).intValue();
                    
                    preparedStatement1.setInt(2, funny_votes);
                    preparedStatement1.setInt(3, useful_votes);
                    preparedStatement1.setInt(4, cool_votes);
                    
                    int review_count = ((Long) jsonObject.get("review_count")).intValue();
                    preparedStatement1.setInt(5, review_count);
                    
                    String name = (String) jsonObject.get("name");
                    preparedStatement1.setString(6, name);
                    
                    String user_id = (String) jsonObject.get("user_id");
                    preparedStatement1.setString(7, user_id);
                    
                    int fans = ((Long) jsonObject.get("fans")).intValue();
                    preparedStatement1.setInt(8, fans);
                    
                    float avg_stars = ((Double) jsonObject.get("average_stars")).floatValue();
                    preparedStatement1.setFloat(9, avg_stars);
                    
                    String type = (String) jsonObject.get("type");
                    preparedStatement1.setString(10, type);
                    
                    JSONObject compliments = (JSONObject) jsonObject.get("compliments");
                    
                    //COMPLIMENT TYPE DECLARATIONS
                    int ctype_hot;
                    int ctype_more;
                    int ctype_profile;
                    int ctype_cute;
                    int ctype_list;
                    int ctype_note;
                    int ctype_plain;
                    int ctype_cool;
                    int ctype_funny;
                    int ctype_writer;
                    int ctype_photos;
                    
                    ctype_hot = (compliments.get("hot")!=null) ? ((Long) compliments.get("hot")).intValue() : 0;
                    ctype_more = (compliments.get("more")!=null) ? ((Long) compliments.get("more")).intValue() : 0;
                    ctype_profile = (compliments.get("profile")!=null) ? ((Long) compliments.get("profile")).intValue() : 0;
                    ctype_cute = (compliments.get("cute")!=null) ? ((Long) compliments.get("cute")).intValue() : 0;
                    ctype_list = (compliments.get("list")!=null) ? ((Long) compliments.get("list")).intValue() : 0;
                    ctype_note = (compliments.get("note")!=null) ? ((Long) compliments.get("note")).intValue() : 0;
                    ctype_plain = (compliments.get("plain")!=null) ? ((Long) compliments.get("plain")).intValue() : 0;
                    ctype_cool = (compliments.get("cool")!=null) ? ((Long) compliments.get("cool")).intValue() : 0;
                    ctype_funny = (compliments.get("funny")!=null) ? ((Long) compliments.get("funny")).intValue() : 0;
                    ctype_writer = (compliments.get("writer")!=null) ? ((Long) compliments.get("writer")).intValue() : 0;
                    ctype_photos = (compliments.get("photos")!=null) ? ((Long) compliments.get("photos")).intValue() : 0;
                    
                    preparedStatement1.setInt(11, ctype_hot);
                    preparedStatement1.setInt(12, ctype_more);
                    preparedStatement1.setInt(13, ctype_profile);
                    preparedStatement1.setInt(14, ctype_cute);
                    preparedStatement1.setInt(15, ctype_list);
                    preparedStatement1.setInt(16, ctype_note);
                    preparedStatement1.setInt(17, ctype_plain);
                    preparedStatement1.setInt(18, ctype_cool);
                    preparedStatement1.setInt(19, ctype_funny);
                    preparedStatement1.setInt(20, ctype_writer);
                    preparedStatement1.setInt(21, ctype_photos);
                    
                    preparedStatement1.executeUpdate();
                    
                    //POPULATING USER_FRIENDS TABLE
                    if(jsonObject.get("friends")!=null)
                    {
                        JSONArray friend = (JSONArray) jsonObject.get("friends");
                        Iterator<String> iterator = friend.iterator();
                        String friend_id;
                        
                        while(iterator.hasNext())
                        {
                            friend_id = iterator.next();
                            preparedStatement2.setString(1, user_id);
                            preparedStatement2.setString(2, friend_id);
                            preparedStatement2.executeUpdate();
                        }
                    }
                    
                    //FOR ELITE USERS
                    if(jsonObject.get("elite")!=null)
                    {
                        JSONArray elite_users = (JSONArray) jsonObject.get("elite");
                        Iterator<Long> iterator2 = elite_users.iterator();
                        int eliteUsers;
                        
                        while(iterator2.hasNext())
                        {
                            eliteUsers = (iterator2.next()).intValue();
                            preparedStatement3.setString(1, user_id);
                            preparedStatement3.setInt(2, eliteUsers);
                            preparedStatement3.executeUpdate();
                        }
                    }
                }
            }
        }
        catch(FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        catch(ParseException e)
        {
            e.printStackTrace();
        }
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (preparedStatement1 != null) 
            {
                preparedStatement1.close();
            }
            if (preparedStatement2 != null) 
            {
                preparedStatement2.close();
            }
            if (preparedStatement3 != null) 
            {
                preparedStatement3.close();
            }
 
            if (conn != null) 
            {
                conn.close();
            }
        }
 
    }
    
    //BUSINESS TABLE
    public void Business() throws FileNotFoundException, SQLException, ParseException, IOException {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        PreparedStatement preparedStatement2 = null;
        PreparedStatement preparedStatement3 = null;
        PreparedStatement preparedStatement4 = null;
        PreparedStatement preparedStatement5 = null;
        PreparedStatement preparedStatement6 = null; 
        JSONParser parser = new JSONParser();
        
        String insertBusiness = "INSERT INTO BUSINESS"
        + "(business_id, full_address, open_status," +
                "city, review_count, business_name," + 
                "longitude, state, stars, latitude," +
                "business_type) VALUES"
                + "(?,?,?,?,?,?,?,?,?,?,?)";
        String insertBH = "INSERT INTO BUSINESS_HOURS" + "(business_day, close_hour, open_hour, business_id) VALUES" + "(?,?,?,?)";
        String insertMC = "INSERT INTO MAIN_CATEGORY" + "(main_category_name, business_id) VALUES" + "(?,?)";
        String insertSC = "INSERT INTO SUB_CATEGORY" + "(sub_category_name, business_id) VALUES" + "(?,?)";
        String insertNeighbor = "INSERT INTO NEIGHBORHOOD" + "(landmark, business_id) VALUES" + "(?,?)";
        String insertBA = "INSERT INTO BUSINESS_ATTRIBUTE" + "(attribute_name, business_id) VALUES" + "(?,?)";
 
        try
        {
            conn = connectDB();
            preparedStatement = conn.prepareStatement(insertBusiness);
            preparedStatement2 = conn.prepareStatement(insertBH);
            preparedStatement3 = conn.prepareStatement(insertMC);
            preparedStatement4 = conn.prepareStatement(insertSC);
            preparedStatement5 = conn.prepareStatement(insertNeighbor);
            preparedStatement6 = conn.prepareStatement(insertBA);
            FileReader filereader = new FileReader(BUSINESSPATH);
            
            System.out.println("INSERTING INTO BUSINESS TABLE"); 
            BufferedReader br = new BufferedReader(filereader);
            
            while ((l = br.readLine()) != null) 
            {
            	Object obj = parser.parse(l);
                JSONObject jsonObject = (JSONObject) obj;
                 
                String business_id = (String) jsonObject.get("business_id");
                preparedStatement.setString(1, business_id);
                 
                String b_address = (String) jsonObject.get("full_address");
                preparedStatement.setString(2, b_address);
                
                boolean open_now = (Boolean) jsonObject.get("open");
                int open_status;
                open_status = (open_now) ? 1 : 0;
                preparedStatement.setInt(3, open_status);
                 
                String city = (String) jsonObject.get("city");
                preparedStatement.setString(4, city);
                 
                int review_count = ((Long) jsonObject.get("review_count")).intValue();
                preparedStatement.setInt(5, review_count);
                 
                String b_name = (String) jsonObject.get("name");
                preparedStatement.setString(6, b_name);
                 
                float longitude = ((Double) jsonObject.get("longitude")).floatValue();
                preparedStatement.setFloat(7, longitude);
                 
                String state = (String) jsonObject.get("state");
                preparedStatement.setString(8, state);
                 
                float stars = ((Double) jsonObject.get("stars")).floatValue();
                preparedStatement.setFloat(9, stars);
                 
                float latitude = ((Double) jsonObject.get("latitude")).floatValue();
                preparedStatement.setFloat(10, latitude);
                 
                String type = (String) jsonObject.get("type");
                preparedStatement.setString(11, type);
                 
                preparedStatement.executeUpdate();
                
                //NEIGHBORHOOD TABLE
                if(jsonObject.get("neighborhoods")!=null)
                {
                    JSONArray neighbor = (JSONArray) jsonObject.get("neighborhoods");
                    Iterator<String> iterator = neighbor.iterator();
                    String n_name;

                    while(iterator.hasNext())
                    {
                        n_name = iterator.next();
                        preparedStatement5.setString(1, n_name);
                        preparedStatement5.setString(2, business_id);
                        preparedStatement5.executeUpdate();
                    }
                }
                 
                JSONArray sub_category = (JSONArray) jsonObject.get("categories");
                Iterator<String> iterator;
                iterator = sub_category.iterator();
                String cat;
         
                while(iterator.hasNext())
                {
                    cat = iterator.next();
                    if(cat.equals("Active Life") || cat.equals("Arts & Entertainment") || cat.equals("Automotive") || 
                            cat.equals("Car Rental") || cat.equals("Cafes") || cat.equals("Beauty & Spas") || 
                            cat.equals("Convenience Stores") || cat.equals("Dentists") || cat.equals("Doctors") ||
                            cat.equals("Drugstores") || cat.equals("Department Stores") || cat.equals("Education") ||
                            cat.equals("Event Planning & Services") || cat.equals("Flowers & Gifts") || 
                            cat.equals("Food") || cat.equals("Health & Medical") || cat.equals("Home Services") ||
                            cat.equals("Home & Garden") || cat.equals("Hospitals") || cat.equals("Hotels & Travel") ||
                            cat.equals("Hardware Stores") || cat.equals("Grocery") || cat.equals("Medical Centers") ||
                            cat.equals("Nurseries & Gardening") || cat.equals("Nightlife") || cat.equals("Restaurants") ||
                            cat.equals("Shopping") || cat.equals("Transportation"))
                    {
                        preparedStatement3.setString(1, cat);
                        preparedStatement3.setString(2, business_id);
                        preparedStatement3.executeUpdate();
                    }
                    else
                    {
                        preparedStatement4.setString(1, cat);
                        preparedStatement4.setString(2, business_id);
                        preparedStatement4.executeUpdate();
                    }
                     
                }
                 
                if(jsonObject.get("attributes")!=null)
                {
                    JSONObject jo4 = (JSONObject) jsonObject.get("attributes");
                    for (Object key : jo4.keySet()) 
                    {
                        String str1 = (String)key;
                        Object keyvalue = jo4.get(str1);                    
 
                        if (keyvalue instanceof JSONObject)
                        {
                            JSONObject jsonObject5 = (JSONObject) jo4.get((String) key);
                            for (Object key2 : jsonObject5.keySet())
                            {
                                String str2 = (String)key2;
                                Object keyvalue2 = jsonObject5.get(str2);
                                if (keyvalue2 instanceof Integer)
                                {
                                    String a_value = ((Long) jsonObject5.get(str2)).toString();
                                    str2 = str2 + "_" + a_value;
                                    preparedStatement6.setString(1, str2);
                                    preparedStatement6.setString(2, business_id);
                                    preparedStatement6.executeUpdate();
                                }
                                else if (keyvalue2 instanceof String)
                                {
                                    String a_value = (String) jsonObject5.get(str2);
                                    str2 = str2 + "_" + a_value;
                                    preparedStatement6.setString(1, str2);
                                    preparedStatement6.setString(2, business_id);
                                    preparedStatement6.executeUpdate();
                                }
                                else if (keyvalue2 instanceof Boolean)
                                {
                                    boolean a = (Boolean) jsonObject5.get(str2);
                                    String a_value = String.valueOf(a);
                                    str2 = str2 + "_" + a_value;
                                    preparedStatement6.setString(1, str2);
                                    preparedStatement6.setString(2, business_id);
                                    preparedStatement6.executeUpdate();
                                }
                            }
                        }
                        else
                        {
                            if (keyvalue instanceof Integer)
                            {
                                String a_value = ((Long) jo4.get(str1)).toString();
                                str1 = str1 + "_" + a_value;
                                preparedStatement6.setString(1, str1);
                                preparedStatement6.setString(2, business_id);
                                preparedStatement6.executeUpdate();
                            }
                            else if (keyvalue instanceof String)
                            {
                                String a_value = (String) jo4.get(str1);
                                str1 = str1 + "_" + a_value;
                                preparedStatement6.setString(1, str1);
                                preparedStatement6.setString(2, business_id);
                                preparedStatement6.executeUpdate();
                            }
                            else if (keyvalue instanceof Boolean)
                            {
                                boolean a = (Boolean) jo4.get(str1);
                                String a_value = String.valueOf(a);
                                str1 = str1 + "_" + a_value;
                                preparedStatement6.setString(1, str1);
                                preparedStatement6.setString(2, business_id);
                                preparedStatement6.executeUpdate();
                            } 
                        }
                         
                    }
                }
                 
                if(jsonObject.get("hours")!=null)
                {
                    JSONObject jo2 = (JSONObject) jsonObject.get("hours");
                    for (Object key : jo2.keySet()) 
                    {
                        String str3 = (String)key;                        
                        JSONObject jo3 = (JSONObject) jo2.get(str3);
                        String open_h = (String) jo3.get("open");
                        Float o_h = convert_hour(open_h);
                                               
                        String close_h = (String) jo3.get("close");
                        Float c_h = convert_hour(close_h);
                        preparedStatement2.setString(1, str3);
                        preparedStatement2.setFloat(2, o_h);
                        preparedStatement2.setFloat(3, c_h);
                        preparedStatement2.setString(4, business_id);
                        preparedStatement2.executeUpdate();                                     
                    }
                }
                 
            }
            filereader.close();
        }
        catch(FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        catch(ParseException e)
        {
            e.printStackTrace();
        }
        catch (SQLException e) 
        {
 
            e.printStackTrace();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (preparedStatement != null) 
            {
                preparedStatement.close();
            }
            if (preparedStatement2 != null) 
            {
                preparedStatement2.close();
            }
            if (preparedStatement3 != null) 
            {
                preparedStatement3.close();
            }
            if (preparedStatement4 != null) 
            {
                preparedStatement4.close();
            }
            if (preparedStatement5 != null) 
            {
                preparedStatement5.close();
            }
            if (preparedStatement6 != null) 
            {
                preparedStatement6.close();
            }
 
            if (conn != null) 
            {
                conn.close();
            }
        }
 
    }
    public static Float convert_hour(String a)
    {
        String[] b = a.split(":");
        float c = Float.parseFloat(b[0]);
        float d = Float.parseFloat(b[1]);
        d = d / 100;
        c = c + d;
        return c;
    }
    
    
    //review
    public void Reviews() throws SQLException
    {
        // TODO Auto-generated method stub
        Connection dbConnection = null;
        PreparedStatement preparedStatement = null;
        String insertReviews = "INSERT INTO REVIEWS" +
                "(funny_vote, useful_vote," +
                "cool_vote, user_id, review_id, stars," +
                "review_date, review_message, review_type," + 
                "business_id) VALUES" +
                "(?,?,?,?,?,?,to_date(?,'yyyy-mm-dd'),?,?,?)";
         
        JSONParser parser = new JSONParser();
        try
        {
            dbConnection = connectDB();
            preparedStatement = dbConnection.prepareStatement(insertReviews);
             
            FileReader filereader = new FileReader(REVIEWSPATH);
            BufferedReader br = new BufferedReader(filereader);
            String l;
            int funny_votes;
            int useful_votes;
            int cool_votes;
            while ((l = br.readLine()) != null) 
            {
                Object o = parser.parse(l);
                JSONObject jsonObject = (JSONObject) o;
 
                JSONObject votes = (JSONObject) jsonObject.get("votes");
               
                funny_votes = ((Long) votes.get("funny")).intValue();
                useful_votes = ((Long) votes.get("useful")).intValue();
                cool_votes = ((Long) votes.get("cool")).intValue();
                
                preparedStatement.setInt(1, funny_votes);
                preparedStatement.setInt(2, useful_votes);
                preparedStatement.setInt(3, cool_votes);
                 
                String user_id = (String) jsonObject.get("user_id");
                preparedStatement.setString(4, user_id);
                 
                String review_id = (String) jsonObject.get("review_id");
                preparedStatement.setString(5, review_id);
                 
                int stars = ((Long) jsonObject.get("stars")).intValue();
                preparedStatement.setInt(6, stars);
                
                String date_string = (String) jsonObject.get("date");
                preparedStatement.setString(7, date_string);

                String text = (String) jsonObject.get("text");
                preparedStatement.setString(8, text);
                 
                String type = (String) jsonObject.get("type");
                preparedStatement.setString(9, type);
                 
                String business_id = (String) jsonObject.get("business_id");
                preparedStatement.setString(10, business_id);
                 
                preparedStatement.executeUpdate();
                 
            }
            filereader.close();
        }
        catch(FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        catch(ParseException e)
        {
            e.printStackTrace();
        }
        catch (SQLException e) 
        {
 
            e.printStackTrace();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (preparedStatement != null) 
            {
                preparedStatement.close();
            }
            if (dbConnection != null) 
            {
                dbConnection.close();
            }
        }
 
    }
    
    //checkin
    public void Checkin() throws SQLException {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        String sqlquery = "INSERT INTO CHECKIN"
        + "(checkin_day, checkin_start," +
        "checkin_end, checkin_type, business_id) VALUES"
        + "(?,?,?,?,?)";

        JSONParser parser = new JSONParser();
        try
        {
            conn = connectDB();
            preparedStatement = conn.prepareStatement(sqlquery);
            FileReader filereader = new FileReader(CHECKINPATH);
             
            BufferedReader br = new BufferedReader(filereader);
            
            while ((l = br.readLine()) != null) 
            {
                Object o = parser.parse(l);
                JSONObject jo1 = (JSONObject) o;
                JSONObject jo2 = (JSONObject) jo1.get("checkin_info");
                for (Object key : jo2.keySet()) 
                {
                    String strCi = (String)key;
                    Object keyvalue = jo2.get(strCi);
                    
                    String checkinDay;
                    int checkinStart;
                    int checkinEnd;
                    
                    checkinDay = getDay(strCi);
                    checkinStart = getHour(strCi);
                    checkinEnd = ((Long) keyvalue).intValue();
                     
                    preparedStatement.setString(1, checkinDay);
                    preparedStatement.setInt(2, checkinStart);
                    preparedStatement.setInt(3, checkinEnd);
                    
                }
                String checkinType1 = (String) jo1.get("type");
                preparedStatement.setString(4, checkinType1);
                
                String business_id = (String) jo1.get("business_id");
                preparedStatement.setString(5, business_id);
                preparedStatement.executeUpdate();
    
            }
            filereader.close();
        }
        catch(FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        catch(ParseException e)
        {
            e.printStackTrace();
        }
        catch (SQLException e) 
        {
 
            e.printStackTrace();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (preparedStatement != null) 
            {
                preparedStatement.close();
            }
             
            if (conn != null) 
            {
                conn.close();
            }
        }
 
    }
    
    public static int getHour(String a)
    {
        String[] b = a.split("-");
        int c;
        c = Integer.parseInt(b[0]);
        return c;
    }
     
    public static String getDay(String a)
    {
        String[] b = a.split("-");
        String c = "NULL/ILLEGAL";
         
        if (b[1].equals("0"))
        {
            c = "SUNDAY";
        }
        if (b[1].equals("1"))
        {
            c = "MONDAY";
        }
        if (b[1].equals("2"))
        {
            c = "TUESDAY";
        }
        if (b[1].equals("3"))
        {
            c = "WEDNESDAY";
        }
        if (b[1].equals("4"))
        {
            c = "THURSDAY";
        }
        if (b[1].equals("5"))
        {
            c = "FRIDAY";
        }
        if (b[1].equals("6"))
        {
            c = "SATURDAY";
        }
         
        return c;
    }
    public static Connection connectDB() 
    {
 
        Connection dbConnection = null;
 
        try {
 
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
 
            System.out.println(e.getMessage());
 
        }
 
        try {
 
            dbConnection = DriverManager.getConnection(
            		"jdbc:oracle:thin:@//localhost:1521/tejas", "sys as sysdba", "server");
            return dbConnection;
 
        } catch (SQLException e) {
 
            System.out.println(e.getMessage());
 
        }
 
        return dbConnection;
 
    }    
    
}


