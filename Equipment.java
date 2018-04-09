//Matthew Smith
//PE 09

import java.util.ArrayList;
public class Equipment{

   private int equipmentId;
   private int equipmentCapacity;
   private String equipmentDescription;
   private String equipmentName;

   //default constructor
   public Equipment(){

   }

   public Equipment(int equipmentId){
      this.equipmentId = equipmentId;
   }

   public Equipment(int equipmentId, int equipmentCapacity, String equipmentDescription, String equipmentName){
      this.equipmentId = equipmentId;
      this.equipmentCapacity = equipmentCapacity;
      this.equipmentDescription = equipmentDescription;
      this.equipmentName = equipmentName;
   }


   public void setId(int equipmentId){
      this.equipmentId = equipmentId;
   }

   public int getId(){
      return equipmentId;
   }
   
   public void setCapacity(int equipmentCapacity){
      this.equipmentCapacity = equipmentCapacity;
   }
   
   public int getCapacity(){
      return equipmentCapacity;
   }
   
   public void setName(String equipmentName){
      this.equipmentName = equipmentName;
   }
   
   public String getName(){
      return equipmentName;
   }
   
   public void setDescription(String equipmentDescription){
      this.equipmentDescription = equipmentDescription;
   }
   
   public String getDescription(){
      return equipmentDescription;
   }
   public ArrayList<ArrayList<String>> fetch(MySQLDatabase obj){
      //String fetchQuery2 = "SELECT EquipmentName, EquipmentDescription, EquipmentCapacity FROM equipment"
      //+ " WHERE equipID =" + equipmentId +";";
      String fetchQuery2 = "SELECT EquipmentName, EquipmentDescription, EquipmentCapacity FROM equipment"
      + " WHERE equipID = ?;";
      ArrayList statementValues = new ArrayList<String>();
      
      statementValues.add(equipmentId);
      ArrayList resultList = new ArrayList<ArrayList<String>>();

      try{
         obj.connect();
         resultList =obj.getData(fetchQuery2, statementValues);
         obj.close();
      
      }
      catch(DLException dle){
      
      }
      return resultList;
   }

   public void put(MySQLDatabase obj){
      //String putQuery = "UPDATE equipment SET EquipmentName=" + '"' +equipmentName + '"' 
      //+ ", EquipmentDescription="+ '"' +equipmentDescription + '"'
      //+ ", EquipmentCapacity=" + equipmentCapacity
      //+ " WHERE equipID=" + equipmentId + ";"; 
      String putQuery = "UPDATE equipment SET EquipmentName=" +"?" 
      + ", EquipmentDescription="+ "?" 
      + ", EquipmentCapacity=" + "?"
      + " WHERE equipID= ?;"; 
      ArrayList statementValues = new ArrayList<String>();
      statementValues.add(equipmentName + "");
      statementValues.add(equipmentDescription + "");
      statementValues.add(equipmentCapacity + "");
      statementValues.add(equipmentId + "");
      try{
         obj.connect();
         obj.setData(putQuery, statementValues);
         obj.close();
      }
      catch(DLException dle){
      
      }
   }   

   public void post(MySQLDatabase obj){
      //String postQuery = "INSERT INTO equipment"
      //+ " VALUES(" + equipmentId + "," + '"'+ equipmentName+ '"' + "," + '"' +equipmentDescription + '"' + "," + equipmentCapacity + ");";
      String postQuery = "INSERT INTO equipment"
      + " VALUES(?, " + "?, "+"?, " + "?);";
      ArrayList statementValues = new ArrayList<String>();
      statementValues.add(equipmentId + "");
      statementValues.add(equipmentName + "");
      statementValues.add(equipmentDescription + "");
      statementValues.add(equipmentCapacity + "");
      try{
         obj.connect();
         obj.setData(postQuery, statementValues); 
         obj.close();
      }
      catch(DLException dle){
      
      }
   }

   public void delete(MySQLDatabase obj){
      //String deleteQuery = "DELETE FROM equipment"
      //+" WHERE equipId=" + equipmentId;
       String deleteQuery = "DELETE FROM equipment"
      +" WHERE equipId= ?;";
      ArrayList statementValues = null;
      statementValues.add(equipmentId);
      try{
         obj.connect();
         obj.setData(deleteQuery, statementValues);
         obj.close();
      }
      catch(DLException dle){
      
      }
   }
   
   public void swap(DLUser user, MySQLDatabase obj, int swapValue){
      if(user.getAccess() != "Admin"){
         return;
      }
      
      try{
         obj.connect();
         obj.startTrans();
         
         String getNameQuery = "SELECT EquipmentName FROM equipment"
         +" WHERE equipId = ?;";
         
         //get name of current id loaded into object
         ArrayList currentValueList = new ArrayList<String>();
         currentValueList.add(equipmentId +"");
         ArrayList<ArrayList<String>> returnedCurrent = obj.getData(getNameQuery, currentValueList);
         ArrayList<String> resultCurrent = returnedCurrent.get(1);
         String currentName = resultCurrent.get(0);
         
         //get name of swapValue id
         ArrayList swapValueList = new ArrayList<String>();
         swapValueList.add(swapValue +"");
         ArrayList<ArrayList<String>> returnedSwap = obj.getData(getNameQuery, swapValueList);
         ArrayList<String> resultSwap = returnedSwap.get(1);
         String newName = resultSwap.get(0);
         
         //System.out.println("newName = " + newName);
         
         String setNameQuery = "UPDATE equipment SET equipmentName = ?"
         + " WHERE equipId = ?;";
         //change the name of the current object loaded into the class
         ArrayList changeCurrentName = new ArrayList<String>();
         changeCurrentName.add(newName);
         changeCurrentName.add(equipmentId);
         //executes query and gets the number of rows changed
         int result1 = obj.executeStmt(setNameQuery, changeCurrentName);
         
         //System.out.println(result1);
         
         //change the name of the equipment with the ID matching with the input swapValue
         ArrayList changeOtherName = new ArrayList<String>();
         
        
         
         changeOtherName.add(currentName);
         changeOtherName.add(swapValue);
         //executes query and gets the number of rows changed
         int result2 = obj.executeStmt(setNameQuery, changeOtherName);
         
         //System.out.println(result2);
         
         //if the number of rows changed matches the expected amount commit is set to true
         //otherwise commit is false and the transaction is rolled back then ends
         boolean commit = false;
         if(result1 == 1 && result2 == 1){
            commit = true;
         }
         else{
            obj.rollbackTrans();
         }
         obj.endTrans(commit);
         obj.close();
      }
      catch(DLException dle){
      
      }
         
   }
}