package learn.renting.domain;

import learn.renting.data.*;
import learn.renting.models.Guest;
import learn.renting.models.Host;
import learn.renting.models.Reservation;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ReservationServiceTest {
    ReservationRepository reservationRepository = new ReservationRepositoryDouble();
    GuestRepository guestRepository = new GuestRepositoryDouble();
    HostRepository hostRepository = new HostRepositoryDouble();

    ReservationService service = new ReservationService(reservationRepository, guestRepository, hostRepository);
    final Host host = new Host("e4ea4932-6f04-4bc6-a23f-24fac8fb662b", "Larsen", "larsen@coastalproperties.ca",
            "(973) 4225678", "45 Test Drive", "Santa Barbara", "CA", "93107",
            BigDecimal.valueOf(340), BigDecimal.valueOf(500));
    final Guest guest = new Guest(1,"Mabel","Testwoman","mabel@hardrockcafe.net","(201) 8985555","ID");

    private final ArrayList<Reservation> reservations = new ArrayList<>();

    //CREATE
    @Test
    void shouldAddReservation() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setGuest(GuestRepositoryDouble.GUEST);
        reservation.setHost(HostRepositoryDouble.HOST);
        reservation.setStartDateOfStay(LocalDate.of(2022,12,12));
        reservation.setEndDateOfStay(LocalDate.of(2022,12,13));

        service.returnCostOfStay(reservation);

        Result<Reservation> result = service.add(reservation);
        assertTrue(result.isSuccess());
        assertNotNull(result.getPayload());
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
        assertTrue(result.getErrorMessages().get(0).contains("That start date is a duplicate."));
        assertTrue(result.getErrorMessages().get(1).contains("Actually, both the start and end dates of that are duplicates."));
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
        assertTrue(result.getErrorMessages().get(0).contains("That start date is a duplicate."));

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
        assertTrue(result.getErrorMessages().get(0).contains("That end date is a duplicate."));
    }//shouldNotAddReservationIfDatesOverlapSlightlyWithWhatIsAlreadyThere

    @Test
    void shouldNotAddReservationIfHostDoesNotExist() throws DataException {
        Guest guest = guestRepository.findById(444);
        Host host = new Host();
        host.setId("4eawrn32");

        Reservation reservation = new Reservation();

        reservation.setStartDateOfStay(LocalDate.of(2022,12,10));
        reservation.setEndDateOfStay(LocalDate.of(2022,12,12));

        reservation.setHost(host);
        reservation.setGuest(guest);

        Result<Reservation> result = service.add(reservation);
        assertFalse(result.isSuccess());
        assertTrue(result.getErrorMessages().get(0).contains("This host does not seem to exist."));
    }//shouldNotAddReservationIfHostDoesNotExist

    //READ
//    @Test
//    void shouldRead(){
//        Reservation reservation = service.findByHost(
//    }

    //UPDATE

//    @Test
//    void shouldUpdate() throws DataException{
//        Guest guest = guestRepository.findById(123);
//        Host host = hostRepository.findByHostId("7537");
//        Reservation reservationUpdate = new Reservation();
//        reservationUpdate.setId(1);
//        reservationUpdate.setHost(host);
//        reservationUpdate.setGuest(guest);
//        reservationUpdate.setStartDateOfStay(LocalDate.of(2022,9,20));
//        reservationUpdate.setEndDateOfStay(LocalDate.of(2022,9,24));
//
//        Result<Reservation> result = service.update(reservationUpdate);
//        System.out.println(result.getPayload());//is null
//    }//shouldUpdate

    //DELETE
//    @Test
//    void shouldDelete() throws DataException{
//        Guest guest = guestRepository.findById(123);
//        Host host = hostRepository.findByHostId("7537");
//        Reservation reservation = new Reservation();
//        reservation.setId(1);
//        reservation.setGuest(guest);
//        reservation.setHost(host);
//
//        Result<Reservation> result = service.delete(reservation);
//        assertTrue(result.isSuccess());
//    }//shouldDelete


}//end
