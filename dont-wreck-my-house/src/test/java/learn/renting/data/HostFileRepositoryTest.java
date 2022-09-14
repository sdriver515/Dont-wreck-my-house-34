package learn.renting.data;

import learn.renting.data.GuestFileRepository;
import learn.renting.data.HostFileRepository;
import learn.renting.models.Guest;
import learn.renting.models.Host;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
        List<Host> all = repository.findAll();
        assertEquals(4, all.size());
    }//shouldFindAll

    @Test
    void shouldReturnHost(){
        assertNotNull(repository.findByHostId("3edda6bc-ab95-49a8-8962-d50b53f84b15"));
        Host host = repository.findByHostId("3edda6bc-ab95-49a8-8962-d50b53f84b15");
        assertNotNull(host);
        assertEquals("3edda6bc-ab95-49a8-8962-d50b53f84b15", host.getId());
        assertEquals("Yearnes", host.getLastNameOfHost());
        assertEquals("eyearnes0@sfgate.com", host.getEmailOfHost());
        assertEquals("(806) 1783815", host.getPhoneOfHost());
        assertEquals("3 Nova Trail", host.getAddressOfHost());
        assertEquals("Amarillo", host.getCityOfHost());
        assertEquals("TX", host.getStateOfHost());
        assertEquals(79182, host.getPostalCodeOfHost());
        assertEquals(BigDecimal.valueOf(340), host.getStandardRateOfHost());
        assertEquals(BigDecimal.valueOf(425), host.getWeekendRateOfHost());
    }//shouldReturnHost

    @Test
    void shouldNotReturnNonExistentHost(){
        assertNull(repository.findByHostId("3edda6b"));
    }//shouldNotReturnNonExistentHost

}//end
