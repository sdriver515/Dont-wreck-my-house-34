package learn.renting.domain;

import learn.renting.data.GuestRepositoryDouble;
import learn.renting.data.HostRepositoryDouble;
import learn.renting.models.Guest;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GuestServiceTest {
    GuestService service = new GuestService(new GuestRepositoryDouble());

    @Test
    void shouldFindByEmail(){
        Guest actual = service.findByEmail(GuestRepositoryDouble.GUEST.getEmailOfGuest());
        assertNotNull(actual);
        assertEquals(GuestRepositoryDouble.GUEST.getLastNameOfGuest(), actual.getLastNameOfGuest());
    }//shouldFindByEmail

    @Test
    void shouldNotFindBlankEmail(){
        Guest actual = service.findByEmail(" ");
        assertNull(actual);
    }//shouldNotFindBlankEmail

    @Test
    void shouldNotFindNullEmail(){
        Guest actual = service.findByEmail(null);
        assertNull(actual);
    }//shouldNotFindNullEmail

    @Test
    void shouldFindAll(){
        List<Guest> list = service.findAllGuests();
        assertNotNull(list);
        assertEquals(2, list.size());
    }//shouldFindAll

    @Test
    void shouldNotFindWrongListOfGuests(){
        List<Guest> list = service.findAllGuests();
        assertNotEquals(6, list.size());
    }//shouldNotFindWrongListOfGuests

}//end
