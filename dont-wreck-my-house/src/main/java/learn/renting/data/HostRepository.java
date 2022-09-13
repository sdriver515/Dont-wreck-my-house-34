package learn.renting.data;

import learn.renting.models.Host;

import java.util.List;

public interface HostRepository {

    List<Host> findAllHostsInFile();//findAllHostsInFile


    Host findByHostId(String idOfHost)//findByIdOfHost
    ;

    Host findByHostEmail(String emailOfHost)//findByIdOfHost
    ;
}//end
