package learn.renting.data;

import learn.renting.models.Host;
import learn.renting.models.Reservation;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ReservationRepositoryDouble implements ReservationRepository{

    private final ArrayList<Reservation> reservations = new ArrayList<>();

    public ReservationRepositoryDouble() {
        Reservation reservation = new Reservation();
        reservation.setId(1);
        reservation.setStartDateOfStay(LocalDate.of(2002, 5,5));
        reservation.setEndDateOfStay(LocalDate.of(2002, 5, 6));
        reservation.setGuest(GuestRepositoryDouble.GUEST);
        reservation.setTotalCost(BigDecimal.valueOf(200));
        reservations.add(reservation);
    }

    @Override
    public List<Reservation> findContentsOfReservationFileByHostId(String hostId) {
        return null;
    }

    @Override
    public Reservation findReservationByHostIdAndDatesAndGuestId(String hostId, LocalDate startDate, LocalDate endDate, int guestId) throws DataException {
        return null;
    }

    @Override
    public List<Reservation> findContentsOfAllReservationFiles() {
        return null;
    }

    @Override
    public Map<LocalDate, LocalDate> returnOccupiedDatesOfHost(String hostId) throws DataException {
        return null;
    }
//    @Override
//    public String returnOccupiedDatesOfHost(String hostId) throws DataException {
//        return null;
//    }

    @Override
    public BigDecimal returnCostOfStayAtHost(Host host, LocalDate startDate, LocalDate endDate) {
        return null;
    }

    @Override
    public boolean update(Reservation reservation, String hostId, int guestId) throws DataException {
        return false;
    }

    @Override
    public Reservation create(Reservation reservation, String hostId) throws DataException {
        return null;
    }

    @Override
    public boolean deleteByParameters(String hostId, LocalDate startDate, LocalDate endDate, int guestId) throws DataException {
        return false;
    }

    @Override
    public boolean trueIfMatchingParameters(String hostId, int guestId, LocalDate startDate, LocalDate endDate) throws DataException {
        return false;
    }
}//end
