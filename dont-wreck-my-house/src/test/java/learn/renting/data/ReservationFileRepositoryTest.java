package learn.renting.data;
import learn.renting.data.HostFileRepository;
import learn.renting.data.ReservationFileRepository;
import learn.renting.models.Guest;
import learn.renting.models.Host;
import learn.renting.models.Reservation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.FileHandler;
import static java.nio.file.StandardCopyOption.*;

import static java.nio.file.LinkOption.*;
import static org.junit.jupiter.api.Assertions.*;

public class ReservationFileRepositoryTest {

    static final String SEED_FILE_PATH = "./dont-wreck-my-house-data/2e72f86c-b8fe-4265-b4f1-304dea8762db-seed.csv";
    static final String TEST_FILE_PATH = "dont-wreck-my-house-data/test-reservations-data/2e72f86c-b8fe-4265-b4f1-304dea8762db.csv";
    static final String TEST_DIR_PATH = "dont-wreck-my-house-data/test-reservations-data";

    ReservationFileRepository repository = new ReservationFileRepository(TEST_DIR_PATH);

    @BeforeEach
    void setup() throws IOException {
        Path seedPath = Paths.get(SEED_FILE_PATH);
        Path testPath = Paths.get(TEST_FILE_PATH);
        Files.copy(seedPath, testPath, StandardCopyOption.REPLACE_EXISTING);
    }//setup

    //CREATE

    @Test
    void shouldAdd() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setStartDateOfStay(LocalDate.of(2022, 11, 11));
        reservation.setEndDateOfStay(LocalDate.of(2022, 11, 12));
        reservation.setTotalCost(BigDecimal.valueOf(500));

        Guest guest = new Guest();
        guest.setId(12);
        reservation.setGuest(guest);

        Host host = new Host();
        host.setId("2e72f86c-b8fe-4265-b4f1-304dea8762db");
        reservation.setHost(host);

        reservation = repository.add(reservation);
        System.out.println(reservation.getGuest().getId());
    }//shouldAdd

    //READ
    @Test
    void shouldFindFileContentsByHostId() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setStartDateOfStay(LocalDate.of(2022, 11, 11));
        reservation.setEndDateOfStay(LocalDate.of(2022, 11, 12));
        reservation.setTotalCost(BigDecimal.valueOf(500));

        Guest guest = new Guest();
        guest.setId(12);
        reservation.setGuest(guest);

        Host host = new Host();
        host.setId("2e72f86c-b8fe-4265-b4f1-304dea8762db");
        reservation.setHost(host);

        List<Reservation> reservations = repository.findByHost(host);
        assertNotNull(reservations);
        assertEquals(4, reservations.size());
        assertEquals(1, reservations.get(0).getId());
        assertEquals(LocalDate.of(2022, 10, 11), reservations.get(0).getStartDateOfStay());
        assertEquals(LocalDate.of(2022, 10, 13), reservations.get(0).getEndDateOfStay());
        assertEquals(663, reservations.get(0).getGuest().getId());
        assertEquals(BigDecimal.valueOf(400.0), reservations.get(0).getTotalCost());
    }//shouldFindFileContentsByHostIds

    @Test
    void shouldNotFindNonExistentFile() throws DataException {
        Host host = new Host();
        host.setId("1");
        List<Reservation> reservations = repository.findByHost(host);
        assertNotNull(reservations);
        assertEquals(0, reservations.size());
    }//shouldNotFindNonExistentFile

    @Test
    void shouldNotFindNullFile() throws DataException {
        List<Reservation> reservations = repository.findByHost(null);
        assertEquals(reservations.size(), 0);
    }//shouldNotFindNullFile

//    @Test
//    void shouldReturnOccupiedDaysOfHost() throws DataException {
//        Map<LocalDate, LocalDate> occupiedDatesOfHost = repository.returnOccupiedDatesOfHost("2e72f86c-b8fe-4265-b4f1-304dea8762db");
//        System.out.println(occupiedDatesOfHost);
//        LocalDate date = occupiedDatesOfHost.get(LocalDate.of(2023, 1, 12));
//        assertEquals(LocalDate.of(2023, 1, 18), date);
//        for (LocalDate key: occupiedDatesOfHost.keySet()){
//            System.out.println(key+ " to " + occupiedDatesOfHost.get(key));
//        }
//    }//shouldReturnOccupiedDaysOfHost

//    @Test
//    void shouldReturnFutureReservationsOfHost() throws DataException {
//        Map<LocalDate, LocalDate> futureReservations = repository.returnFutureReservations("2e72f86c-b8fe-4265-b4f1-304dea8762db");
//        assertNotNull(futureReservations);
//        assertEquals(3, futureReservations.size());
//        assertTrue(futureReservations.containsValue(LocalDate.of(2023,1,18)));
//        assertTrue(futureReservations.containsKey(LocalDate.of(2023,1,12)));
//        assertFalse(futureReservations.containsValue(LocalDate.of(2022,9,9)));
//    }//shouldReturnFutureReservationsOfHost

//    @Test
//    void shouldNotReturnPastReservationsOfHost() throws DataException {
//        Map<LocalDate, LocalDate> futureReservations = repository.returnFutureReservations("2e72f86c-b8fe-4265-b4f1-304dea8762db");
//        assertNotNull(futureReservations);
//        LocalDate timeInThePast = futureReservations.get(LocalDate.of(2022, 9, 9));
//        assertNull(timeInThePast);
//    }//shouldNotReturnPastReservationsOfHost

//    @Test
//    void shouldReturnTrueIfDateIsWithinRange() throws DataException{
//        repository.trueIfWithinRange(LocalDate.of(2022, 9,14), LocalDate.of(2022, 9,18), LocalDate.of(2022,9,17));
//    }//shouldReturnTrueIfDateIsWithinRange

//    @Test
//    void shouldReturnFalseIfDateIsNotWithinRange() throws DataException{
//        boolean answer = repository.trueIfWithinRange(LocalDate.of(2022, 9,14), LocalDate.of(2022, 9,18), LocalDate.of(2022,9,30));
//        assertFalse(answer);
//    }//shouldReturnTrueIfDateIsWithinRange

//    @Test
//    void shouldReturnTrueIfDateIsInFuture() throws DataException{
//        boolean answerTrue = repository.trueIfInFuture(LocalDate.of(2022, 9,15));
//        assertTrue(answerTrue);
//    }//shouldReturnTrueIfDateIsInFuture

//    @Test
//    void shouldReturnFalseIfDateIsInPast() throws DataException{
//        boolean answerFalse = repository.trueIfInFuture(LocalDate.of(2022, 9,13));
//        assertFalse(answerFalse);
//    }//shouldReturnFalseIfDateIsInPast

//    @Test
//    void shouldReturnCorrectCostOfStay() throws DataException {
//        List<Reservation> reservations = repository.findContentsOfReservationFileByHostId("2e72f86c-b8fe-4265-b4f1-304dea8762db");
//        BigDecimal result = repository.returnCostOfStayAtHost(new Host("2e72f86c-b8fe-4265-b4f1-304dea8762db", "Driver", BigDecimal.valueOf(100), BigDecimal.valueOf(150)), LocalDate.of(2022, 9, 12), LocalDate.of(2022, 9, 17));
//        assertNotNull(result);
//        assertEquals(BigDecimal.valueOf(550), result);
//        result = repository.returnCostOfStayAtHost(new Host("2e72f86c-b8fe-4265-b4f1-304dea8762db", "Driver", BigDecimal.valueOf(100), BigDecimal.valueOf(150)), LocalDate.of(2022, 9, 12), LocalDate.of(2022, 9, 15));
//        assertEquals(BigDecimal.valueOf(300), result);
//    }//shouldReturnCorrectCostOfStay

//    @Test
//    void shouldNotReturnIncorrectCostOfStay() throws DataException {
//        List<Reservation> reservations = repository.findContentsOfReservationFileByHostId("2e72f86c-b8fe-4265-b4f1-304dea8762db");
//        BigDecimal result = repository.returnCostOfStayAtHost(new Host("2e72f86c-b8fe-4265-b4f1-304dea8762db", "Driver", BigDecimal.valueOf(100), BigDecimal.valueOf(150)), LocalDate.of(2022, 9, 12), LocalDate.of(2022, 9, 17));
//        assertNotNull(result);
//        assertNotEquals(BigDecimal.valueOf(600), result);
//    }//shouldNotReturnIncorrectCostOfStay

    //UPDATE
    @Test
    public void shouldUpdate() throws DataException {
        Reservation update = new Reservation();
        update.setId(2);
        update.setStartDateOfStay(LocalDate.of(2002, 4, 4));
        update.setEndDateOfStay(LocalDate.of(2002, 5, 5));
        update.setTotalCost(BigDecimal.valueOf(100));

        Guest guest = new Guest();
        guest.setId(136);
        update.setGuest(guest);

        Host host = new Host();
        host.setId("2e72f86c-b8fe-4265-b4f1-304dea8762db");
        update.setHost(host);

        boolean result = repository.updateReservation(update);
        assertTrue(result);
        assertNotNull(update);
    }//shouldUpdate

    @Test
    public void shouldNotUpdateIfHostDoesNotExist() throws DataException {
        Reservation update = new Reservation();
        update.setId(2);
        update.setStartDateOfStay(LocalDate.of(2002, 4, 4));
        update.setEndDateOfStay(LocalDate.of(2002, 5, 5));
        update.setTotalCost(BigDecimal.valueOf(100));

        Guest guest = new Guest();
        guest.setId(136);
        update.setGuest(guest);

        Host host = new Host();
        host.setId("23");

        boolean result = repository.updateReservation(update);
        assertFalse(result);
    }//shouldNotUpdateIfHostDoesNotExist

    @Test
    public void shouldNotUpdateIfGuestIdDoesNotExist() throws DataException {
        Reservation update = new Reservation();
        update.setId(2);
        update.setStartDateOfStay(LocalDate.of(2002, 4, 4));
        update.setEndDateOfStay(LocalDate.of(2002, 5, 5));
        update.setTotalCost(BigDecimal.valueOf(100));

        Guest guest = new Guest();
        guest.setId(1);
        update.setGuest(guest);

        Host host = new Host();
        host.setId("2e72f86c-b8fe-4265-b4f1-304dea8762db");

        boolean result = repository.updateReservation(update);
        assertFalse(result);
    }//shouldNotUpdateIfGuestIdDoesNotExist

    //DELETE
    @Test
    public void shouldDeleteExistingReservation() throws DataException {
        Reservation reservationExisting = new Reservation();
        reservationExisting.setId(2);
        reservationExisting.setStartDateOfStay(LocalDate.of(2022, 10, 11));
        reservationExisting.setEndDateOfStay(LocalDate.of(2022, 10, 13));
        reservationExisting.setTotalCost(BigDecimal.valueOf(400));

        Guest guest = new Guest();
        guest.setId(663);
        reservationExisting.setGuest(guest);

        Host host = new Host();
        host.setId("2e72f86c-b8fe-4265-b4f1-304dea8762db");
        reservationExisting.setHost(host);

        boolean result = repository.deleteReservation(reservationExisting);
        assertTrue(result);
        List<Reservation> all = repository.findByHost(host);
        assertEquals(3, all.size());
    }//shouldDeleteExistingReservation

    @Test
    public void shouldNotDeleteNonExistentReservation() throws DataException {
        Reservation reservationFake = new Reservation();
        reservationFake.setId(2);
        reservationFake.setStartDateOfStay(LocalDate.of(2022, 10, 11));
        reservationFake.setEndDateOfStay(LocalDate.of(2022, 10, 13));
        reservationFake.setTotalCost(BigDecimal.valueOf(400));

        Guest guest = new Guest();
        guest.setId(6);
        reservationFake.setGuest(guest);

        Host host = new Host();
        host.setId("272f82db");
        reservationFake.setHost(host);

        boolean result = repository.deleteReservation(reservationFake);
        assertFalse(result);
        List<Reservation> all = repository.findByHost(host);
        assertEquals(0, all.size());
    }//shouldNotDeleteNonExistentReservation

}//end
