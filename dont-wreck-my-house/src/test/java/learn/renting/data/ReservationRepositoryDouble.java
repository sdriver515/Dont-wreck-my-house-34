package learn.renting.data;

import learn.renting.models.Host;
import learn.renting.models.Reservation;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ReservationRepositoryDouble implements ReservationRepository{

    private final ArrayList<Reservation> reservations = new ArrayList<>();

    public ReservationRepositoryDouble() {
        Reservation reservation = new Reservation();
        reservation.setId(1);
        reservation.setStartDateOfStay(LocalDate.of(2022, 9,20));
        reservation.setEndDateOfStay(LocalDate.of(2022, 9, 22));
        reservation.setGuest(GuestRepositoryDouble.GUEST);
        reservation.setTotalCost(BigDecimal.valueOf(200));
        reservation.setHost(HostRepositoryDouble.HOST);
        reservations.add(reservation);

        Reservation reservationTwo = new Reservation();
        reservationTwo.setStartDateOfStay(LocalDate.of(2023, 8,21));
        reservationTwo.setEndDateOfStay(LocalDate.of(2023, 8, 25));
        reservationTwo.setGuest(GuestRepositoryDouble.OTHERGUEST);
        reservationTwo.setTotalCost(BigDecimal.valueOf(500));
        reservationTwo.setHost(HostRepositoryDouble.OTHERHOST);
        reservations.add(reservationTwo);
    }

    @Override
    public List<Reservation> findByHost(Host host) throws DataException {
        return reservations.stream()
                .filter(i -> i.getHost().getId().equals(host.getId()))
                .collect(Collectors.toList());
    }//findByHost

    @Override
    public Reservation add(Reservation reservation) throws DataException {
        reservation.setId(3);//keep an eye on this
        reservations.add(reservation);
        return reservation;
    }//add

    @Override
    public boolean updateReservation(Reservation reservation) throws DataException {
        return reservation.getId() > 0;
    }//updateReservation

    @Override
    public boolean deleteReservation(Reservation reservation) throws DataException {
        return reservation.getId() != 999;
    }//deleteReservation

}//end
