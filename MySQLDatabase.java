//Matthew Smith
//PE 09

import java.sql.*;
import java.util.ArrayList;


public class MySQLDatabase{
  
   private String uri;
   private String driver;
   private String user;
   private String password;
   private Connection conn;
   private ResultSetMetaData meta;
  
   public MySQLDatabase(String uri, String driver, String user, String password){
      this.uri = uri;
      this.driver = driver;
      this.user = user;
      this.password = password;
      conn = null;
   }
     
   public boolean connect() throws DLException{
      
      // Load the driver
      try {
         Class.forName(driver);
         //System.out.println("Driver loaded");
      }
      catch(ClassNotFoundException cnfe ){
         //System.out.println("Cannot find or load driver: "+ driver );
         ArrayList list = new ArrayList<String>();
         list.add("Error finding driver");
         throw new DLException(cnfe, list);
      }
      catch(Exception e){
         throw new DLException(e);
      }
      // Open the MySQL database connection
      try{
         conn = DriverManager.getConnection(uri, user, password);
         //System.out.println("MySQL database open");
      }
      catch(SQLException sqle ){
         //System.out.println("Could not connect to db: ");
         ArrayList list = new ArrayList<String>();
         list.add("Error connecting to database");
         throw new DLException(sqle, list);
      }
      catch(Exception e){
         throw new DLException(e);
      }
      return true;
   }
   
   public boolean close() throws DLException{
      // Close the database connection
      try{
         conn.close();
        //System.out.println("MySQL Database closed");
      }
      catch(SQLException sqle ){
         //System.out.println("Could not close MySQL Database");
         ArrayList list = new ArrayList<String>();
         list.add("Error disconnecting from database");
         throw new DLException(sqle, list);
      }
      catch(Exception e){
         throw new DLException(e);
         
      }
     //System.out.println("Done!");
      return true;
   }
   
   
   
   
   //used for SELECT ONLY
   public ArrayList<ArrayList<String>> getData(String mySQL) throws DLException{
      ArrayList<ArrayList<String>> list = new ArrayList();
      try{
         Statement stmnt = conn.createStatement();
         ResultSet result = stmnt.executeQuery(mySQL);
         meta = result.getMetaData();
         int fields = meta.getColumnCount();
         
         int rowCount = 0;
         while(result.next()){
            ArrayList row = new ArrayList<String>();
            for(int i=1; i<=fields; i++){
               row.add(result.getString(i));
            }
            rowCount++;
            list.add(row);
         }
      }
      catch(SQLException sqle){
         //System.out.println("getData SQL exception!");
         ArrayList listt = new ArrayList<String>();
         listt.add("getData failure");
         throw new DLException(sqle, listt);
      }
      catch(Exception e){
         //System.out.println("getData error" + e.getMessage());
         throw new DLException(e);
      }
      return list;
   }   
   //This getData will return the column headings based upon the value of boolean
   public ArrayList<ArrayList<String>> getData(String mySQL, boolean nameVal) throws DLException{
      ArrayList list = new ArrayList<ArrayList<String>>();
      try{
         list = getData(mySQL);
         if(nameVal == false){
            return list;
         }
         else{
            int cols = 0;
            cols = meta.getColumnCount();
            int m = 1;
            ArrayList colNames = new ArrayList<String>();
            while(m <= cols){
               colNames.add(meta.getColumnName(m));
               m++;
            }
         list.add(0, colNames);
         }
      }
      catch(SQLException sqle){
         ArrayList list2 = new ArrayList<String>();
         list2.add("getData error");
         throw new DLException(sqle, list2);
      }
      catch(Exception e){
         throw new DLException(e);
      }
      return list;
   }
   //prepares a mySQL statement
   public PreparedStatement prepare(String query, ArrayList list) throws DLException{
      PreparedStatement statement;
      try{
         statement = conn.prepareStatement(query);
         for(int i=0; i<list.size(); i++){
            statement.setString(i+1, list.get(i).toString());
         }     
      }
      catch(SQLException sqle){
         ArrayList exceptionList = new ArrayList<String>();
         exceptionList.add("Error preparing statement");
         throw new DLException(sqle, exceptionList);
      }
      catch(Exception e){
         throw new DLException(e);
      }
      return statement;
   }
   
   //calls prepare and returns a result
   public ArrayList<ArrayList<String>> getData(String mySQL, ArrayList inputValues) throws DLException{
      ArrayList list = new ArrayList<ArrayList<String>>();
      //gets the prepared statement
      PreparedStatement statement = prepare(mySQL, inputValues);
      
      try{
         //executes the prepared statement
         ResultSet result = statement.executeQuery();
         meta = result.getMetaData();
         //writes the result to an arraylist
         int fields = meta.getColumnCount();
         int rowCount = 0;
         while(result.next()){
            ArrayList row = new ArrayList<String>();
            for(int i=1; i<=fields; i++){
               row.add(result.getString(i));
            }
            rowCount++;
            list.add(row);
         }
         //inserts the collumn names into the first slot of the arraylist
         int cols = meta.getColumnCount();
         int m = 1;
         ArrayList colNames = new ArrayList<String>();
         while(m <= cols){
            colNames.add(meta.getColumnName(m));
            m++;
         }
         list.add(0, colNames);

      }
      catch(SQLException sqle){
         ArrayList list2 = new ArrayList<String>();
         list2.add("getData failure");
         throw new DLException(sqle, list2);
         
      }
      catch(Exception e){
         throw new DLException(e);
      }
      return list;
   }
   
   //calls executeStmt and returns true if the query was properly executed
   public boolean setData(String mySQL, ArrayList<String> inputValues) throws DLException{
      int rows = executeStmt(mySQL, inputValues);
      if(rows != -1){
         return true;
      }
      return false;
   }
   
   //calls prepare and returns the number of rows changed. Returns -1 if there was an error
   public int executeStmt(String mySQL, ArrayList<String> list) throws DLException{
      int rows = -1;
      try{
         PreparedStatement statement = prepare(mySQL, list);
         rows = statement.executeUpdate();
         
      }
      catch(SQLException sqle){
         ArrayList eList = new ArrayList<String>();
         list.add("Execution failure");
         throw new DLException(sqle, eList);
      }
      catch(Exception e){
         throw new DLException(e);
      }
      return rows;
   
   } 
   //used for UPDATE, DELETE AND INSERT      
   public boolean setData(String mySQL) throws DLException{
      try{
      Statement stmnt = conn.createStatement();
      
      stmnt.executeUpdate(mySQL);
      }
      catch(SQLException sqle){
         //System.out.println("setData SQL exception!");
         ArrayList list = new ArrayList<String>();
         list.add("setData failure");
         sqle.printStackTrace();
         throw new DLException(sqle, list);
      }
      catch(Exception e){
         //System.out.println("setData Error" + e.getMessage());
        
        throw new DLException(e);
         
      }
      return true;
   }
   //Used for debugging!
   public void descTable(String mySQL){
      
      try{
         connect();
         Statement stmnt = conn.createStatement();
         ResultSet result = stmnt.executeQuery(mySQL);
         
         ResultSetMetaData meta = result.getMetaData();
         int colNum = meta.getColumnCount();
         System.out.println("There were " + colNum + " fields.");
         
         int n = 1;
         while(n<=colNum){
            System.out.println(meta.getColumnName(n) + " " + meta.getColumnTypeName(n));
            n++;
         }
         
         int m = 1;
         while(m <= colNum){
            System.out.format("%18s", meta.getColumnName(m));
            m++;
         }
         System.out.println(" ");
         int rowCount = 0;
         while(result.next()){
            String line = "";
            for(int i=1; i<=colNum; i++){
               System.out.format("%18s", result.getString(i));
            }
            System.out.println("\n");
            rowCount++;
         }
         close();
      }
       
      catch(Exception e){
         e.printStackTrace();
      }
   }
   //starts the transaction
   public void startTrans() throws DLException{
      try{
         conn.setAutoCommit(false);
      }
      catch(Exception e){
         throw new DLException(e);
      }
   }
   //ends the transaction
   public void endTrans(boolean commitValue) throws DLException{
      try{
         if(commitValue){
            conn.commit();
         }
         
         conn.setAutoCommit(true);
      }
      catch(Exception e){
         throw new DLException(e);
      }
   }
   //rolls back the transaction
   public void rollbackTrans() throws DLException{
      try{
         conn.rollback();
      }
      catch(Exception e){
         throw new DLException(e);
      }
   }
}

