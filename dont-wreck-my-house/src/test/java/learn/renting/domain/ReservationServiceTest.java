package learn.renting.domain;

import learn.renting.data.*;
import learn.renting.models.Guest;
import learn.renting.models.Host;
import learn.renting.models.Reservation;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class ReservationServiceTest {
    ReservationRepository reservationRepository = new ReservationRepositoryDouble();
    GuestRepository guestRepository = new GuestRepositoryDouble();
    HostRepository hostRepository = new HostRepositoryDouble();

    ReservationService service = new ReservationService(reservationRepository, guestRepository, hostRepository);

    //CREATE
    @Test
    void shouldAddReservation() throws DataException {
        Guest guest = guestRepository.findById(444);
        Host host = hostRepository.findByHostId("7546776547654787684");
        Reservation reservation = new Reservation();

        reservation.setId(2);
        reservation.setStartDateOfStay(LocalDate.of(2022,12,12));
        reservation.setEndDateOfStay(LocalDate.of(2022,12,13));
        reservation.setTotalCost(BigDecimal.valueOf(100));

        reservation.setHost(host);
        reservation.setGuest(guest);

        Result<Reservation> result = service.add(reservation);
        assertTrue(result.isSuccess());
        assertEquals(result.getPayload().getId(), reservation.getId());
    }//shouldAddReservation

    @Test
    void shouldNotAddReservationIfDateIsInThePast() throws DataException {
        Guest guest = guestRepository.findById(444);
        Host host = hostRepository.findByHostId("7546776547654787684");
        Reservation reservation = new Reservation();

        reservation.setId(2);
        reservation.setStartDateOfStay(LocalDate.of(2021,12,12));
        reservation.setEndDateOfStay(LocalDate.of(2021,12,13));
        reservation.setTotalCost(BigDecimal.valueOf(100));

        reservation.setHost(host);
        reservation.setGuest(guest);

        Result<Reservation> result = service.add(reservation);
        assertFalse(result.isSuccess());
        //need to figure out error message
    }//shouldNotAddReservationIfDateIsInThePast

    @Test
    void shouldNotAddReservationIfStartDateIsAfterEndDate() throws DataException {
        Guest guest = guestRepository.findById(444);
        Host host = hostRepository.findByHostId("7546776547654787684");
        Reservation reservation = new Reservation();

        reservation.setId(2);
        reservation.setStartDateOfStay(LocalDate.of(2021,12,12));
        reservation.setEndDateOfStay(LocalDate.of(2021,11,13));
        reservation.setTotalCost(BigDecimal.valueOf(100));

        reservation.setHost(host);
        reservation.setGuest(guest);

        Result<Reservation> result = service.add(reservation);
        assertFalse(result.isSuccess());
    }//shouldNotAddReservationIfStartDateIsAfterEndDate

//    @Test
//    void shouldNotAddReservationIfDatesOverlapWithWhatIsAlreadyThere() throws DataException {
//        Guest guest = guestRepository.findById(444);
//        Host host = hostRepository.findByHostId("7546776547654787684");
//        Reservation reservation = new Reservation();
//
//        reservation.setId(2);
//        reservation.setStartDateOfStay(LocalDate.of(2022,9,20));
//        reservation.setEndDateOfStay(LocalDate.of(2022,9,21));
//        reservation.setTotalCost(BigDecimal.valueOf(100));
//
//        reservation.setHost(host);
//        reservation.setGuest(guest);
//
//        Result<Reservation> result = service.add(reservation);
//        assertFalse(result.isSuccess());
//        System.out.println(result.getErrorMessages());
//    }//shouldNotAddReservationIfDatesOverlapWithWhatIsAlreadyThere


}//end
