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
