//Matthew Smith
//PE 09
import java.util.*;

public class RunDatabase{

   public static void main(String [] args){
      //mysql connection info
      String Myuri = "jdbc:mysql://127.0.0.1/travel";
      String Mydriver = "com.mysql.jdbc.Driver";
      String Myuser = "root";
      String Mypassword = "student";
      //creation of mysql object
      MySQLDatabase mysql = new MySQLDatabase(Myuri, Mydriver, Myuser, Mypassword);
      
      BLUser generalUser = new BLUser("jpc462", "dpdh");
      
      
      if(generalUser.login(mysql)){
         System.out.println("--EQUIPMENT1--");
         Equipment equip1 = new Equipment(568);
         System.out.println(equip1.fetch(mysql).get(1));
         equip1.swap(generalUser, mysql, 896);
         System.out.println("--EQUIPMENT1 AFTER TRYING TO SWAP--");
         System.out.println(equip1.fetch(mysql).get(1));
      }
      
      
      
   
   }
}