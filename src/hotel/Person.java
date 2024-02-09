package hotel;

public class Person {
    private String firstName;
    private String surname;
    private String creditCardNumber;
    
    public void setFirstName(String receivedFirstName){
        firstName = receivedFirstName;
    }
    
    public void setSurname(String receivedSurname){
        surname = receivedSurname;
    }
    
    public void setCreditCardNumber(String receivedCreditCardNumber){
        creditCardNumber = receivedCreditCardNumber;
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
}
