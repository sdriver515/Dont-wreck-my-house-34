package learn.renting.models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class Reservation {

    //FIELDS
    private int id;
    private LocalDate startDateOfStay;

    private LocalDate endDateOfStay;
    private Guest guest;
    private BigDecimal totalCost;
    private Host host;

    public Reservation(int id, LocalDate startDateOfStay, LocalDate endDateOfStay, Guest guest, BigDecimal totalCost, Host host) {
        this.id = id;
        this.startDateOfStay = startDateOfStay;
        this.endDateOfStay = endDateOfStay;
        this.guest = guest;
        this.totalCost = totalCost;
        this.host = host;
    }

    //CONSTRUCTORS
    public Reservation(){};
//    public Reservation(int id, LocalDate startDateOfStay, LocalDate endDateOfStay, Guest guest, BigDecimal totalCost) {
//        this.id = id;
//        this.startDateOfStay = startDateOfStay;
//        this.endDateOfStay = endDateOfStay;
//        this.guest = guest;
//        this.totalCost = totalCost;
//    }
    public Reservation(LocalDate startDateOfStay, LocalDate endDateOfStay){
        this.startDateOfStay = startDateOfStay;
        this.endDateOfStay = endDateOfStay;
    }

//GETTERS AND SETTERS

    public Host getHost() {
        return host;
    }

    public void setHost(Host host) {
        this.host = host;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getStartDateOfStay() {
        return startDateOfStay;
    }

    public void setStartDateOfStay(LocalDate startDateOfStay) {
        this.startDateOfStay = startDateOfStay;
    }

    public LocalDate getEndDateOfStay() {
        return endDateOfStay;
    }

    public void setEndDateOfStay(LocalDate endDateOfStay) {
        this.endDateOfStay = endDateOfStay;
    }


    public Guest getGuest() {
        return guest;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation reservation = (Reservation) o;
        return id == reservation.id &&
                Objects.equals(startDateOfStay, reservation.startDateOfStay) &&
                Objects.equals(endDateOfStay, reservation.endDateOfStay) &&
                guest == reservation.guest &&
                Objects.equals(totalCost, reservation.totalCost);
    }

    @Override
    public int hashCode() {//may end up deleting this
        return Objects.hash(id, startDateOfStay, endDateOfStay, guest, totalCost);
    }

}//end
