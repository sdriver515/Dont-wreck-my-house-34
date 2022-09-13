package learn.renting.data;
import learn.renting.data.HostFileRepository;
import learn.renting.data.ReservationFileRepository;
import learn.renting.models.Reservation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;
import static java.nio.file.StandardCopyOption.*;

import static java.nio.file.LinkOption.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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

}//end
