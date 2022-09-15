package learn.renting.domain;

import learn.renting.data.HostRepositoryDouble;

public class HostServiceTest {
    HostService service = new HostService(new HostRepositoryDouble());
    //can only test:
    //shouldFindByEmail
    //shouldNotFindBlaankEmail
    //shouldNotFindNullEmail
}//end
