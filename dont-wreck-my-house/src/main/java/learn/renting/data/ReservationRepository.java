package learn.renting.data;

import learn.renting.models.Host;
import learn.renting.models.Reservation;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface ReservationRepository {
    List<Reservation> findByHost(Host host) throws DataException;
    Reservation add(Reservation reservation)throws DataException;
    boolean updateReservation(Reservation reservation) throws DataException;

    boolean updateReservationByGuest(Reservation reservation) throws DataException//updateReservationByGuest
    ;

    boolean deleteReservation(Reservation reservation) throws DataException;

}//end
