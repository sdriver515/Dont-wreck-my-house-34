package learn.renting.domain;

import learn.renting.data.GuestRepositoryDouble;
import learn.renting.data.HostRepositoryDouble;

public class GuestServiceTest {
    GuestService service = new GuestService(new GuestRepositoryDouble());
    //can only test:
    //shouldFindByEmail
    //shouldNotFindBlaankEmail
    //shouldNotFindNullEmail
}//end
