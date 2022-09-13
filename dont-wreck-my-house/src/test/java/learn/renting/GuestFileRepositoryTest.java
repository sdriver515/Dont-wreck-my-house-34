package learn.renting;

import learn.renting.data.GuestFileRepository;
import learn.renting.models.Guest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GuestFileRepositoryTest {
    private static final String SEED_FILE_PATH = "./dont-wreck-my-house-data/test-guests-seed.csv";
    private static final String TEST_FILE_PATH = "./dont-wreck-my-house-data/test-guests.csv";

    //New file repository
    private final GuestFileRepository repository = new GuestFileRepository(TEST_FILE_PATH);

    //Setting up known Good State
    @BeforeEach
    public void setUp() throws IOException {
        Path seedPath = Paths.get(SEED_FILE_PATH);
        Path testPath = Paths.get(TEST_FILE_PATH);
        Files.copy(seedPath, testPath, StandardCopyOption.REPLACE_EXISTING);
    }//setUp

    @Test
    void shouldFindAllGuests() {
        List<Guest> all = repository.findAllGuestsInFile();
        assertEquals(4, all.size());
    }//shouldFindAllGuests
}//end
