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
    void shouldAdd() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setGuest(GuestRepositoryDouble.GUEST);
        reservation.setHost(HostRepositoryDouble.HOST);
        reservation.setStartDateOfStay(LocalDate.now().plusDays(15));
        reservation.setEndDateOfStay(LocalDate.now().plusDays(20));
        service.returnCostOfStay(reservation);

        Result<Reservation> result = service.add(reservation);
        assertTrue(result.isSuccess());
        assertNotNull(result.getPayload());
        assertEquals(LocalDate.now().plusDays(20), result.getPayload().getEndDateOfStay());
    }//shouldAdd

    @Test
    void shouldNotAddReservationIfDateIsInThePast() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setGuest(GuestRepositoryDouble.GUEST);
        reservation.setHost(HostRepositoryDouble.HOST);
        reservation.setStartDateOfStay(LocalDate.of(2022,8,12));
        reservation.setEndDateOfStay(LocalDate.of(2022,8,13));

        service.returnCostOfStay(reservation);

        Result<Reservation> result = service.add(reservation);
        assertFalse(result.isSuccess());
        assertNull(result.getPayload());
        assertTrue(result.getErrorMessages().get(0).contains("past"));
    }//shouldNotAddReservationIfDateIsInThePast

    //READ
    @Test
    void shouldFindByHostEmail() throws DataException {
        List actual = service.findByHost(HostRepositoryDouble.HOST);
        assertNotNull(actual);
        assertEquals(1, actual.size());
    }//shouldFindByHostEmail

    @Test
    void shouldNotFindByNullHostEmail() throws DataException {
        Host host = new Host();
        host.setEmailOfHost(null);
        List actual = service.findByHost(host);
        assertTrue(actual.isEmpty());
        assertEquals(0, actual.size());
    }//shouldNotFindByNullHostEmail

    @Test
    void shouldNotFindByBlankHostEmail() throws DataException {
        Host host = new Host();
        host.setEmailOfHost(" ");
        List actual = service.findByHost(host);
        assertTrue(actual.isEmpty());
        assertEquals(0, actual.size());
    }//shouldNotFindByBlankHostEmail

    @Test
    void shouldNotFindByNullHost() throws DataException {
        Host host = null;
        List actual = service.findByHost(host);
        assertTrue(actual.isEmpty());
        assertEquals(0, actual.size());
    }//shouldNotFindByBlankHostEmail

    @Test
    void shouldValidateDuplicateDates() throws DataException {
        Result<Reservation> result = new Result<>();
        LocalDate today = LocalDate.now();
        Reservation newReservation = new Reservation(today, today.plusDays(2));
        List<Reservation> existingReservations = new ArrayList<>(List.of());
        service.validateDuplicates(result, newReservation, existingReservations);
        assertTrue(result.isSuccess());

        //yesterday to today
        newReservation = new Reservation(today.minusDays(1), today);
        existingReservations = new ArrayList<>();
        service.validateDuplicates(result, newReservation, existingReservations);
        assertFalse(result.isSuccess());
        assertEquals(1, result.getErrorMessages().size());
        assertEquals("Reservations cannot be made in the past.", result.getErrorMessages().get(0));
    }//shouldValidateDuplicateDates

    @Test
    void shouldNotHaveEndDateBeforeStartDate() throws DataException {
        Result<Reservation> result = new Result<>();
        Reservation newReservation = new Reservation(LocalDate.of(2023, 10,20), LocalDate.of(2023, 10, 19));
        List<Reservation> existingReservations = new ArrayList<>(List.of());
        service.validateDuplicates(result, newReservation, existingReservations);
        assertFalse(result.isSuccess());
        assertEquals(1, result.getErrorMessages().size());
        assertEquals("Start date must be before end date.", result.getErrorMessages().get(0));
    }//shouldNotHaveEndDateBeforeStartDate

    @Test
    void shouldNotBookOnSameDay() throws DataException {
        Result<Reservation> result = new Result<>();
        LocalDate today = LocalDate.now();
        Reservation newReservation = new Reservation(today, today);
        List<Reservation> existingReservations = new ArrayList<>(List.of());
        service.validateDuplicates(result, newReservation, existingReservations);
        assertFalse(result.isSuccess());
    }//shouldNotHaveSameDates

    @Test
    void shouldNotHaveOverlappingDates() throws DataException {
        Result<Reservation> result = new Result<>();
        LocalDate today = LocalDate.now();
        Reservation newReservation = new Reservation(today, today.plusDays(1));
        List<Reservation> existingReservations = new ArrayList<>(List.of(newReservation));
        service.validateDuplicates(result, newReservation, existingReservations);
        assertFalse(result.isSuccess());
        assertTrue(result.getErrorMessages().get(0).contains("overlap"));
    }//shouldNotHaveSameDates

    //UPDATE
    @Test
    void shouldUpdate() throws DataException{
        List<Reservation> all = service.findByHost(HostRepositoryDouble.HOST);
        Reservation toUpdate = new Reservation();
        toUpdate.setEndDateOfStay(LocalDate.of(2023,1,1));
        toUpdate.setEndDateOfStay(LocalDate.of(2023,1,3));
        toUpdate.setHost(HostRepositoryDouble.HOST);
        toUpdate.setGuest(GuestRepositoryDouble.GUEST);
        

    }//shouldUpdate

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
