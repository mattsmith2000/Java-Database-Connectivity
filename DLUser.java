//Matthew Smith
//PE 09

import java.sql.*;
import java.util.ArrayList;

public class DLUser{
   private String userName;
   private String password;
   private String name;
   private String access;  
   
   private DLUser(){
   
   }
   
   public DLUser(String userName, String password){
      this.userName = userName;
      this.password = password;
   }       
   
   private void setUser(String userName){
      this.userName = userName;
   }
   
   private String getUser(){
      return userName;
   }

   private void setPass(String password){
      this.password = password;
   }

   private String getPass(){
      return password;
   }
   
   public String name(){
      return name;
   }
   
   public String getAccess(){
      return access;
   }
   
   public boolean login(MySQLDatabase obj){
      //mySQL query
      String loginQuery = "SELECT name, access FROM users"
      + " WHERE username = ? AND password = ?;";
      ArrayList values = new ArrayList<String>();
      values.add(userName);
      values.add(password);
      
      try{
         //connect to database
         obj.connect();
         //execute query and get result
        
         ArrayList<ArrayList<String>> result = obj.getData(loginQuery, values);
          
         //close database
         obj.close();
         
         //if there is one result set name and password then return true
         //size of 2 because we need to skip over headings in result
         if(result.size()==2){
            name = result.get(1).get(0);
            password = result.get(1).get(1);
            return true;
         }
      }
      catch(DLException dle){
         
      }
      return false;
   }
}