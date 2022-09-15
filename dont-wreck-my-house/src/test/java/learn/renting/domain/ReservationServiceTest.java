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

        reservation.setStartDateOfStay(LocalDate.of(2022,12,12));
        reservation.setEndDateOfStay(LocalDate.of(2022,12,13));

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

        reservation.setStartDateOfStay(LocalDate.of(2021,12,12));
        reservation.setEndDateOfStay(LocalDate.of(2021,12,13));

        reservation.setHost(host);
        reservation.setGuest(guest);

        Result<Reservation> result = service.add(reservation);
        assertFalse(result.isSuccess());
        assertTrue(result.getErrorMessages().get(0).contains("This is not in the future. You cannot add reservations to the past."));
    }//shouldNotAddReservationIfDateIsInThePast

    @Test
    void shouldNotAddReservationIfDatesEqualThemselves() throws DataException {
        Guest guest = guestRepository.findById(444);
        Host host = hostRepository.findByHostId("7546776547654787684");
        Reservation reservation = new Reservation();

        reservation.setStartDateOfStay(LocalDate.of(2022,12,12));
        reservation.setEndDateOfStay(LocalDate.of(2022,12,12));

        reservation.setHost(host);
        reservation.setGuest(guest);

        Result<Reservation> result = service.add(reservation);
        assertFalse(result.isSuccess());
        assertTrue(result.getErrorMessages().get(0).contains("You cannot book only a few hours in one day. Sorry."));
    }//shouldNotAddReservationIfDateIsInThePast

    @Test
    void shouldNotAddReservationIfEndDateIsBeforeStartDate() throws DataException {
        Guest guest = guestRepository.findById(444);
        Host host = hostRepository.findByHostId("7546776547654787684");
        Reservation reservation = new Reservation();

        reservation.setStartDateOfStay(LocalDate.of(2022,12,12));
        reservation.setEndDateOfStay(LocalDate.of(2022,11,13));

        reservation.setHost(host);
        reservation.setGuest(guest);

        Result<Reservation> result = service.add(reservation);
        assertFalse(result.isSuccess());
        assertTrue(result.getErrorMessages().get(0).contains("Your end date is before your start date."));
    }//shouldNotAddReservationIfEndDateIsBeforeStartDate

    @Test
    void shouldNotAddReservationIfDatesOverlapEntirelyWithWhatIsAlreadyThere() throws DataException {
        Guest guest = guestRepository.findById(444);
        Host host = hostRepository.findByHostId("7537");
        Reservation reservation = new Reservation();

        reservation.setStartDateOfStay(LocalDate.of(2022,9,20));
        reservation.setEndDateOfStay(LocalDate.of(2022,9,22));

        reservation.setHost(host);
        reservation.setGuest(guest);

        Result<Reservation> result = service.add(reservation);
        assertFalse(result.isSuccess());
        assertTrue(result.getErrorMessages().get(0).contains("That start date is already occupied."));
        assertTrue(result.getErrorMessages().get(1).contains("Actually, both the start and end dates of that are already occupied."));
    }//shouldNotAddReservationIfDatesOverlapEntirelyWithWhatIsAlreadyThere

    @Test
    void shouldNotAddReservationIfDatesOverlapSlightlyWithWhatIsAlreadyThere() throws DataException {
        Guest guest = guestRepository.findById(444);
        Host host = hostRepository.findByHostId("7537");
        Reservation reservation = new Reservation();

        reservation.setStartDateOfStay(LocalDate.of(2022,9,20));
        reservation.setEndDateOfStay(LocalDate.of(2022,9,24));

        reservation.setHost(host);
        reservation.setGuest(guest);

        Result<Reservation> result = service.add(reservation);
        assertFalse(result.isSuccess());
        assertTrue(result.getErrorMessages().get(0).contains("That start date is already occupied"));

        Guest guestTwo = guestRepository.findById(444);
        Host hostTwo = hostRepository.findByHostId("7537");

        reservation.setId(3);
        reservation.setStartDateOfStay(LocalDate.of(2022,9,21));
        reservation.setEndDateOfStay(LocalDate.of(2022,9,22));
        reservation.setTotalCost(BigDecimal.valueOf(100));

        reservation.setHost(hostTwo);
        reservation.setGuest(guestTwo);

        result = service.add(reservation);
        assertFalse(result.isSuccess());
        assertTrue(result.getErrorMessages().get(0).contains("That end date is already occupied"));
    }//shouldNotAddReservationIfDatesOverlapSlightlyWithWhatIsAlreadyThere


}//end
