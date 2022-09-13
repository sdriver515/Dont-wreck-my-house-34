package learn.renting.models;

import java.math.BigDecimal;

public class Host {
    //FIELDS
    private String idOfHost;
    private String lastNameOfHost;
    private String emailOfHost;
    private BigDecimal standardRateOfHost;
    private BigDecimal weekendRateOfHost;

    //GETTERS AND SETTERS

    public String getIdOfHost() {
        return idOfHost;
    }
    public void setIdOfHost(String idOfHost) {
        this.idOfHost = idOfHost;
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

    //CONSTRUCTORS
    public Host(){}
    public Host(String idOfHost, String lastNameOfHost, String emailOfHost, BigDecimal standardRateOfHost, BigDecimal weekendRateOfHost) {
        this.idOfHost = idOfHost;
        this.lastNameOfHost = lastNameOfHost;
        this.emailOfHost = emailOfHost;
        this.standardRateOfHost = standardRateOfHost;
        this.weekendRateOfHost = weekendRateOfHost;
    }//Guest

}//end
