package hotel;

public class Room {
   private String nameOfPaying;
   private int numberOfGuests;
   
   public void setName(String receivedName){
       nameOfPaying = receivedName;    
   }
   
   public void setNumberOfGuests(int receivedNumberOfGuests){
       numberOfGuests = receivedNumberOfGuests;
   }
   
   public String getFirstName(){
       return nameOfPaying;
   }
   
   public int getNumberOfGuests(){
       return numberOfGuests;
   }
}
