//Matthew Smith
//PE 09
import java.util.ArrayList;
import java.io.*;
import java.util.Calendar;
import java.text.SimpleDateFormat;

public class DLException extends Exception{

   public DLException(Exception e){
      
      log("Could not complete operation");
   }

   public DLException(Exception e, ArrayList<String> list){
      
      
      
      log(list.get(0));
   }

   private void log(String logMessage){
      String time = new SimpleDateFormat("yyyy, MM, dd H:H:mm:ss").format(Calendar.getInstance().getTime());
      
      try{
         FileWriter stream = new FileWriter("ErrorLog.txt", true);
         BufferedWriter writer = new BufferedWriter(stream);
         
         writer.write(time +" "+ logMessage);
         writer.newLine();
         writer.close();
      }
      catch(Exception e){
         System.out.println("Logging error");
      }
   }
}