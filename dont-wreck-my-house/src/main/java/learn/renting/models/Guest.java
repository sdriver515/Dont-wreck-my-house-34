package learn.renting.models;

import java.util.Objects;

public class Guest {
    //FIELDS
    private int id;
    private String firstNameOfGuest;
    private String lastNameOfGuest;
    private String emailOfGuest;
    private String phoneOfGuest;
    private String stateOfGuest;

    //GETTERS AND SETTERS
    public int getId() {
        return id;
    }
    public void setId(int idOfGuest) {
        this.id = id;
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
    public Guest(int id, String firstNameOfGuest, String lastNameOfGuest, String emailOfGuest, String phoneOfGuest, String stateOfGuest) {
        this.id = id;
        this.firstNameOfGuest = firstNameOfGuest;
        this.lastNameOfGuest = lastNameOfGuest;
        this.emailOfGuest = emailOfGuest;
        this.phoneOfGuest = phoneOfGuest;
        this.stateOfGuest = stateOfGuest;
    }//Guest

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Guest guest = (Guest) o;
        return id == guest.id &&
                Objects.equals(firstNameOfGuest, guest.firstNameOfGuest) &&
                lastNameOfGuest.equals(guest.lastNameOfGuest) &&
                emailOfGuest.equals(guest.emailOfGuest) &&
                phoneOfGuest.equals(guest.phoneOfGuest) &&
                stateOfGuest.equals(guest.stateOfGuest);
    }

    @Override
    public int hashCode() {//may end up deleting this
        return Objects.hash(id, firstNameOfGuest, lastNameOfGuest, emailOfGuest, phoneOfGuest, stateOfGuest);
    }

}//end
