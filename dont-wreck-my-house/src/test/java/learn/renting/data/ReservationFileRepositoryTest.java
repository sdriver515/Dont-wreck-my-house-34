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

    //READ
    @Test
    void shouldFindById() {
        List<Reservation> reservations = repository.findContentsOfReservationFileByHostId("2e72f86c-b8fe-4265-b4f1-304dea8762db");
        assertEquals(4, reservations.size());
    }//shouldFindById

    @Test
    void shouldReturnFiles(){
        List<Reservation> list = repository.findContentsOfAllReservationFiles();
        assertEquals(1, list.get(0).getId());
        assertNotNull(list);
    }//shouldReturnFiles

    @Test
    void shouldReturnCorrectCostOfStay(){
        List<Reservation> reservations = repository.findContentsOfReservationFileByHostId("2e72f86c-b8fe-4265-b4f1-304dea8762db");
        BigDecimal result = repository.returnCostOfStayAtHost(new Host("2e72f86c-b8fe-4265-b4f1-304dea8762db", "Driver", BigDecimal.valueOf(100), BigDecimal.valueOf(150)), LocalDate.of(2022, 9, 12), LocalDate.of(2022, 9, 17));
        assertNotNull(result);
        assertEquals(BigDecimal.valueOf(550), result);
        result = repository.returnCostOfStayAtHost(new Host("2e72f86c-b8fe-4265-b4f1-304dea8762db", "Driver", BigDecimal.valueOf(100), BigDecimal.valueOf(150)), LocalDate.of(2022, 9, 12), LocalDate.of(2022, 9, 15));
        assertEquals(BigDecimal.valueOf(300), result);
    }//shouldReturnCorrectCostOfStay

    @Test
    void shouldReturnOccupiedDaysOfHost(){
        Map<LocalDate, LocalDate> occupiedDatesOfHost = repository.returnOccupiedDatesOfHost("2e72f86c-b8fe-4265-b4f1-304dea8762db");
//        String occupiedDatesOfHost = repository.returnOccupiedDatesOfHost("2e72f86c-b8fe-4265-b4f1-304dea8762db");
        System.out.println(occupiedDatesOfHost);
        LocalDate date = occupiedDatesOfHost.get(LocalDate.of(2023, 1, 12));
        assertEquals(LocalDate.of(2023, 1, 18), date);
        for (LocalDate key: occupiedDatesOfHost.keySet()){
            System.out.println(key+ " to " + occupiedDatesOfHost.get(key));
        }
    }//shouldReturnOccupiedDaysOfHost

    //UPDATE
//    @Test
//    public void shouldUpdate() throws DataException {
//        Reservation reservation = repository.findContentsOfReservationFileByHostId("2e72f86c-b8fe-4265-b4f1-304dea8762db").get(0);
//        reservation.setId(1);
//        reservation.setStartDateOfStay(LocalDate.of(2002, 4, 4));
//        reservation.setEndDateOfStay(LocalDate.of(2002, 5, 5));
//        reservation.setTotalCost(BigDecimal.valueOf(100));
//
//        Guest guest = new Guest();
//        guest.setId(663);
//
//        reservation.setGuest(guest);
//
//        Host host = new Host();
//        host.setId("2e72f86c-b8fe-4265-b4f1-304dea8762db");
//
//        boolean result = repository.update(reservation, host, guest);
//
//        assertTrue(result);
//
//        assertNotNull(reservation);
//        assertEquals(1, reservation.getId());
//    }//shouldUpdate

    //DELETE

}//end
