package learn.renting.data;

import learn.renting.models.Guest;
import learn.renting.models.Host;
import learn.renting.models.Reservation;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
    public List<Reservation> findContentsOfReservationFileByHostId(String id) {
        return null;
    }

    @Override
    public List<Reservation> findContentsOfAllReservationFiles() {
        return null;
    }

    @Override
    public boolean update(Reservation reservation, Host host, Guest guest) throws DataException {
        return false;
    }
}//end
