//Matthew Smith
//PE 09

public class BLEquipment extends Equipment{

   public BLEquipment(int equipNum){
      super(equipNum);
   }
   
   public boolean save(BLUser obj){
      if(obj.getAccess() =="Admin" || obj.getAccess() == "Editor"){
         return true;
      }
      return false;
   }
}