package learn.renting.models;

public class Guest {
    //FIELDS
    private int idOfGuest;
    private String firstNameOfGuest;
    private String lastNameOfGuest;
    private String emailOfGuest;
    private String phoneOfGuest;
    private String stateOfGuest;

    //GETTERS AND SETTERS
    public int getIdOfGuest() {
        return idOfGuest;
    }
    public void setIdOfGuest(int idOfGuest) {
        this.idOfGuest = idOfGuest;
    }
    public String getFirstNameOfGuest() {
        return firstNameOfGuest;
    }
    public void setFirstNameOfGuest(String firstNameOfGuest) {
        this.firstNameOfGuest = firstNameOfGuest;
    }
    public String getLastNameOfGuest() {
        return lastNameOfGuest;
    }
    public void setLastNameOfGuest(String lastNameOfGuest) {
        this.lastNameOfGuest = lastNameOfGuest;
    }
    public String getEmailOfGuest() {
        return emailOfGuest;
    }
    public void setEmailOfGuest(String emailOfGuest) {
        this.emailOfGuest = emailOfGuest;
    }
    public String getPhoneOfGuest() {
        return phoneOfGuest;
    }
    public void setPhoneOfGuest(String phoneOfGuest) {
        this.phoneOfGuest = phoneOfGuest;
    }
    public String getStateOfGuest() {
        return stateOfGuest;
    }
    public void setStateOfGuest(String stateOfGuest) {
        this.stateOfGuest = stateOfGuest;
    }

    //CONSTRUCTORS
    public Guest(){}
    public Guest(int idOfGuest, String firstNameOfGuest, String lastNameOfGuest, String emailOfGuest, String phoneOfGuest, String stateOfGuest) {
        this.idOfGuest = idOfGuest;
        this.firstNameOfGuest = firstNameOfGuest;
        this.lastNameOfGuest = lastNameOfGuest;
        this.emailOfGuest = emailOfGuest;
        this.phoneOfGuest = phoneOfGuest;
        this.stateOfGuest = stateOfGuest;
    }//Guest

}//end
