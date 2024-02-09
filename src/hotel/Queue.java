package hotel;

public class Queue {
    private String firstName = "E";
    private String surname = "E";
    private String creditCardNumber = "E";
    private int numberOfGuests = 0;
    
    public void setFirstName(String receivedFirstName){
        firstName = receivedFirstName;
    }
    
    public void setSurname(String receivedSurname){
        surname = receivedSurname;
    }
    
    public void setCreditCardNumber(String receivedCreditCardNumber){
        creditCardNumber = receivedCreditCardNumber;
    }
    
    public void setNumberOfGuests(int receivedNumberOfGuests){
        numberOfGuests = receivedNumberOfGuests;
    }
    
    public String getFirstName(){
        return firstName;
    }
    
    public String getSurname(){
        return surname;
    }
     
    public String getCreditCardNumber(){
        return creditCardNumber;
    }
    
    public int getNumberOfGuests(){
       return numberOfGuests;
   }
}
