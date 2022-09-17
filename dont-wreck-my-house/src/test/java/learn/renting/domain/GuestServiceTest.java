package learn.renting.domain;

import learn.renting.data.GuestRepositoryDouble;
import learn.renting.data.HostRepositoryDouble;
import learn.renting.models.Guest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GuestServiceTest {
    GuestService service = new GuestService(new GuestRepositoryDouble());

    @Test
    void shouldFindByEmail(){
        Guest actual = service.findByEmail(GuestRepositoryDouble.GUEST.getEmailOfGuest());
        assertNotNull(actual);
        assertEquals(GuestRepositoryDouble.GUEST.getLastNameOfGuest(), actual.getLastNameOfGuest());
    }
    //shouldNotFindBlankEmail
    @Test
    void shouldNotFindBlankEmail(){
        Guest actual = service.findByEmail(" ");
        assertNull(actual);
    }//shouldNotFindBlankEmail

    //shouldNotFindNullEmail
    @Test
    void shouldNotFindNullEmail(){
        Guest actual = service.findByEmail(null);
        assertNull(actual);
    }//shouldNotFindNullEmail

}//end
