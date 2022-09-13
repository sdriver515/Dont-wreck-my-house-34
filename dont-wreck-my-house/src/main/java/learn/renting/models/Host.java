package learn.renting.models;

import java.math.BigDecimal;

public class Host {
    //FIELDS
    private String id;
    private String lastNameOfHost;
    private String emailOfHost;
    private String phoneOfHost;
    private String addressOfHost;
    private String cityOfHost;

    private String stateOfHost;
    private int postalCodeOfHost;
    private BigDecimal standardRateOfHost;
    private BigDecimal weekendRateOfHost;

    //GETTERS AND SETTERS

    public String getId() {
        return id;
    }
    public void setId(String idOfHost) {
        this.id = id;
    }
    public String getLastNameOfHost() {
        return lastNameOfHost;
    }
    public void setLastNameOfHost(String lastNameOfHost) {
        this.lastNameOfHost = lastNameOfHost;
    }
    public String getEmailOfHost() {
        return emailOfHost;
    }
    public void setEmailOfHost(String emailOfHost) {
        this.emailOfHost = emailOfHost;
    }
    public BigDecimal getStandardRateOfHost() {
        return standardRateOfHost;
    }
    public void setStandardRateOfHost(BigDecimal standardRateOfHost) {
        this.standardRateOfHost = standardRateOfHost;
    }
    public BigDecimal getWeekendRateOfHost() {
        return weekendRateOfHost;
    }
    public void setWeekendRateOfHost(BigDecimal weekendRateOfHost) {
        this.weekendRateOfHost = weekendRateOfHost;
    }
    public String getPhoneOfHost() {
        return phoneOfHost;
    }
    public void setPhoneOfHost(String phoneOfHost) {
        this.phoneOfHost = phoneOfHost;
    }
    public String getAddressOfHost() {
        return addressOfHost;
    }
    public void setAddressOfHost(String addressOfHost) {
        this.addressOfHost = addressOfHost;
    }
    public String getCityOfHost() {
        return cityOfHost;
    }
    public void setCityOfHost(String cityOfHost) {
        this.cityOfHost = cityOfHost;
    }
    public String getStateOfHost() {
        return stateOfHost;
    }

    public void setStateOfHost(String stateOfHost) {
        this.stateOfHost = stateOfHost;
    }
    public int getPostalCodeOfHost() {
        return postalCodeOfHost;
    }
    public void setPostalCodeOfHost(int postalCodeOfHost) {
        this.postalCodeOfHost = postalCodeOfHost;
    }

    //CONSTRUCTORS
    public Host(){}
    public Host(String id, String lastNameOfHost, String emailOfHost, String phoneOfHost, String addressOfHost, String cityOfHost, String stateOfHost, int postalCodeOfHost, BigDecimal standardRateOfHost, BigDecimal weekendRateOfHost) {
        this.id = id;
        this.lastNameOfHost = lastNameOfHost;
        this.emailOfHost = emailOfHost;
        this.phoneOfHost = phoneOfHost;
        this.addressOfHost = addressOfHost;
        this.cityOfHost = cityOfHost;
        this.stateOfHost = stateOfHost;
        this.postalCodeOfHost = postalCodeOfHost;
        this.standardRateOfHost = standardRateOfHost;
        this.weekendRateOfHost = weekendRateOfHost;
    }//Guest

}//end
