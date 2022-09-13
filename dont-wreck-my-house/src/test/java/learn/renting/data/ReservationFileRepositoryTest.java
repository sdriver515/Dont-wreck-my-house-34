package learn.renting.data;

import learn.renting.data.HostFileRepository;
import learn.renting.data.ReservationFileRepository;
import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class ReservationFileRepositoryTest {
    private static final String SEED_DIRECTORY_PATH = "./dont-wreck-my-house-data/test-reservations-data-seed";
    private static final String TEST_DIRECTORY_PATH = "./dont-wreck-my-house-data/test-reservations-data";

    //New file repository
    private final ReservationFileRepository repository = new ReservationFileRepository(TEST_DIRECTORY_PATH);

    //Setting up known Good State
    @BeforeEach
    public void setUp() throws IOException {
        Path seedPath = Paths.get(SEED_DIRECTORY_PATH);
        Path testPath = Paths.get(TEST_DIRECTORY_PATH);
        Files.copy(seedPath, testPath, StandardCopyOption.REPLACE_EXISTING);
    }//setUp


}//end
