package learn.renting.data;

import learn.renting.models.Guest;
import learn.renting.models.Host;
import learn.renting.models.Reservation;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public interface ReservationRepository {

    List<Reservation> findContentsOfReservationFileByHostId(String hostId);

    Reservation findReservationByHostIdAndDatesAndGuestId(String hostId, LocalDate startDate, LocalDate endDate, int guestId) throws DataException;

    List<Reservation> findContentsOfAllReservationFiles();

    LocalDate returnFreeDatesOfHost(Reservation reservation, String hostId) throws DataException//returnFreeDatesOfHost
    ;

    BigDecimal returnCostOfStayAtHost(Host host, LocalDate startDate, LocalDate endDate)//returnCostOfStayAtHost
    ;

    //UPDATE
    boolean update(Reservation reservation, String hostId, int guestId) throws DataException;

    Reservation create(Reservation reservation, String hostId) throws DataException//create
    ;

    //DELETE
    boolean deleteByParameters(String hostId, LocalDate startDate, LocalDate endDate, int guestId) throws DataException//deleteByParameters
    ;

    boolean trueIfMatchingParameters(String hostId, int guestId, LocalDate startDate, LocalDate endDate) throws DataException//trueIfMatchingParameters
    ;
}//end
