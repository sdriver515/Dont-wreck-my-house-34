package learn.renting.domain;

import learn.renting.data.GuestRepository;
import learn.renting.models.Guest;
import org.springframework.stereotype.Service;

@Service
public class GuestService {
    private final GuestRepository repository;

    public GuestService(GuestRepository repository) {
        this.repository = repository;
    }

    public Guest findByEmail(String email){
        //somethng
        return null;
    }
}//end
