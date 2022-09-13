package learn.renting.data;

import learn.renting.data.GuestFileRepository;
import learn.renting.data.HostFileRepository;
import learn.renting.models.Guest;
import learn.renting.models.Host;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HostFileRepositoryTest {
    private static final String SEED_FILE_PATH = "./dont-wreck-my-house-data/test-hosts-seed.csv";
    private static final String TEST_FILE_PATH = "./dont-wreck-my-house-data/test-hosts.csv";

    //New file repository
    private final HostFileRepository repository = new HostFileRepository(TEST_FILE_PATH);

    //Setting up known Good State
    @BeforeEach
    public void setUp() throws IOException {
        Path seedPath = Paths.get(SEED_FILE_PATH);
        Path testPath = Paths.get(TEST_FILE_PATH);
        Files.copy(seedPath, testPath, StandardCopyOption.REPLACE_EXISTING);
    }//setUp

    @Test
    void shouldFindAllHosts() {
        List<Host> all = repository.findAllHostsInFile();
        assertEquals(4, all.size());
    }//shouldFindAll



}//end
