package learn.renting.domain;

import learn.renting.data.HostRepository;
import learn.renting.models.Host;
import org.springframework.stereotype.Service;

@Service
public class HostService {
    private final HostRepository repository;

    public HostService(HostRepository repository) {
        this.repository = repository;
    }

    public Host findByEmail(String email){
        return null;
    }
}//end
