package learn.renting.data;

import learn.renting.models.Guest;
import learn.renting.models.Host;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class HostRepositoryDouble implements HostRepository{
    public final static Host HOST = new Host();
    private final ArrayList<Host> hosts = new ArrayList<>();
    public HostRepositoryDouble() {
        hosts.add(HOST);
    }

    @Override
    public List<Host> findAll() {
        ArrayList<Host> all = new ArrayList<>();
        all.add(new Host("7546776547654787684", "Spieker", "mespieker@hotmail.com", "410-808-5299", "2128 Thomas Run Road", "Darlington", "MD", 21034, BigDecimal.valueOf(100), BigDecimal.valueOf(200)));
        return all;
    }//findAll

    @Override
    public Host findByHostId(String idOfHost) {
        for(Host host : findAll()){
            if(host.getId() == idOfHost){
                return host;
            }
        }
        return null;
    }//findByHostId
}//end
