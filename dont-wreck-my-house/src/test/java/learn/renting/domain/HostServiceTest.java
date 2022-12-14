package learn.renting.domain;

import learn.renting.data.GuestRepositoryDouble;
import learn.renting.data.HostRepositoryDouble;
import learn.renting.models.Guest;
import learn.renting.models.Host;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNull;

public class HostServiceTest {
    HostService service = new HostService(new HostRepositoryDouble());
    //can only test:
    //shouldFindByEmail
    @Test
    void shouldFindByEmail(){
        Host actual = service.findByEmail(HostRepositoryDouble.HOST.getEmailOfHost());
        assertNotNull(actual);
        assertEquals(HostRepositoryDouble.HOST.getLastNameOfHost(), actual.getLastNameOfHost());
    }//shouldFindByEmail
    @Test
    void shouldNotFindBlankEmail(){
        Host actual = service.findByEmail(" ");
        assertNull(actual);
    }//shouldNotFindBlankEmail

    @Test
    void shouldNotFindNullEmail(){
        Host actual = service.findByEmail(null);
        assertNull(actual);
    }//shouldNotFindNullEmail

    @Test
    void shouldFindAll(){
        List<Host> list = service.findAllHosts();
        assertNotNull(list);
        assertEquals(2, list.size());
    }//shouldFindAll

    @Test
    void shouldNotFindWrongListOfHosts(){
        List<Host> list = service.findAllHosts();
        assertNotEquals(6, list.size());
    }//shouldNotFindWrongListOfHosts

}//end
