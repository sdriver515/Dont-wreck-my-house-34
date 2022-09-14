package learn.renting.data;

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

import static org.junit.jupiter.api.Assertions.*;

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
        List<Guest> all = repository.findAll();
        assertNotNull(all);
        assertEquals(4, all.size());
        Guest guest = all.get(0);
        assertEquals(1, guest.getId());
        assertEquals("Sullivan", guest.getFirstNameOfGuest());
        assertEquals("Lomas", guest.getLastNameOfGuest());
        assertEquals("slomas0@mediafire.com", guest.getEmailOfGuest());
        assertEquals("(702) 7768761", guest.getPhoneOfGuest());
        assertEquals("NV", guest.getStateOfGuest());
    }//shouldFindAllGuests

    @Test
    void shouldFindGuestById(){
        Guest guest = repository.findById(1);
        assertEquals(1, guest.getId());
        assertEquals("Sullivan", guest.getFirstNameOfGuest());
        assertEquals("Lomas", guest.getLastNameOfGuest());
        assertEquals("slomas0@mediafire.com", guest.getEmailOfGuest());
        assertEquals("(702) 7768761", guest.getPhoneOfGuest());
        assertEquals("NV", guest.getStateOfGuest());
    }//shouldFindGuestById

    @Test
    void shouldNotFindNonExistentGuestId(){
        Guest guest = repository.findById(10000);
        assertNull(guest);
    }//shouldNotFindNonExistentGuestId

}//end
