package learn.renting.data;

import learn.renting.models.Guest;
import learn.renting.models.Host;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class HostRepositoryDouble implements HostRepository{
    public final static Host HOST = new Host("7537", "Driver", "kdriver@verizon.net", "410-808-5299", "2128 Thomas Run Road", "Darlington", "MD", 21034, BigDecimal.valueOf(150), BigDecimal.valueOf(200));
    public final static Host OTHERHOST = new Host("7546776547654787684", "Spieker", "mespieker@hotmail.com", "410-808-5299", "2128 Thomas Run Road", "Darlington", "MD", 21034, BigDecimal.valueOf(100), BigDecimal.valueOf(200));
    private final ArrayList<Host> hosts = new ArrayList<>();
    public HostRepositoryDouble() {
        hosts.add(HOST);
        hosts.add(OTHERHOST);
    }

    @Override
    public List<Host> findAll() {
        return hosts;
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
