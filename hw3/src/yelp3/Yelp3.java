/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yelp3;

import java.io.IOException;
import java.sql.SQLException;
import org.json.simple.parser.ParseException;

/**
 *
 * @author tejas
 */
public class Yelp3 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException, ParseException, IOException {
        // TODO code application logic here
        populateTables p = new populateTables();
                System.out.println("STARTING TO POPULATE");
                System.out.println("INSERTING INTO YELP_USER");
                p.YelpUser();
                System.out.println("YELP_USER COMPLETED");
                System.out.println("INSERTING INTO BUSINESS");
                p.Business();
                System.out.println("BUSINESS COMPLETED");
                System.out.println("INSERTING INTO REVIEWS");
                p.Reviews();
                System.out.println("REVIEWS COMPLETED");
                System.out.println("INSERTING INTO CHECKIN");
                p.Checkin();
                System.out.println("CHECKIN COMPLETED");
                System.out.println("FINISHED");
    }
    
}


                

