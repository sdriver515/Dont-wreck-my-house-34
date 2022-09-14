package learn.renting.data;

import learn.renting.models.Host;
import learn.renting.models.Reservation;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface ReservationRepository {

    List<Reservation> findContentsOfReservationFileByHostId(String hostId);

    Reservation findReservationByHostIdAndDatesAndGuestId(String hostId, LocalDate startDate, LocalDate endDate, int guestId) throws DataException;

    List<Reservation> findContentsOfAllReservationFiles();

    Map<LocalDate, LocalDate> returnOccupiedDatesOfHost(String hostId) throws DataException//returnFreeDatesOfHost
    ;

//    String returnOccupiedDatesOfHost(String hostId) throws DataException//returnFreeDatesOfHost
//    ;

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
