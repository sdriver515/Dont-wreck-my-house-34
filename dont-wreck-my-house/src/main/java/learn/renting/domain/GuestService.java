package learn.renting.domain;

import learn.renting.data.GuestRepository;
import learn.renting.models.Guest;
import learn.renting.models.Host;
import learn.renting.models.Reservation;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GuestService {
    private final GuestRepository repository;

    public GuestService(GuestRepository repository) {
        this.repository = repository;
    }

    public Guest findByEmail(String email){
        List<Guest> all = repository.findAll();
        for (Guest i : all){
            if(i.getEmailOfGuest().equals(email)){
                return i;
            }
        }
        return null;
    }//findByEmail

    public List<Guest> findAllGuests(){
        List<Guest> all = repository.findAll();
        return all;
    }//findAllGuests

}//end
