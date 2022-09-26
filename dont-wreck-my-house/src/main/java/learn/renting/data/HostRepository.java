package learn.renting.data;

import learn.renting.models.Host;

import java.util.List;

public interface HostRepository {

    List<Host> findAll();

    Host findByHostId(String id);

}//end
