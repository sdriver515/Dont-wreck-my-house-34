package learn.renting.domain;

import learn.renting.data.GuestRepository;
import learn.renting.models.Guest;
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
//        validateNulls(email);
        List<Guest> all = repository.findAll();
        for (Guest i : all){
            if(i.getEmailOfGuest().equals(email)){
                return i;
            }
        }
        return null;
    }//findByEmail

//    private Result<Guest> validateNulls(String email) {
//        Result<Guest> result = new Result<>();
//        if (email == null) {
//            result.addErrorMessage("The email is null.");
//            return result;
//        }
//        if(email.equals(" ")){
//            result.addErrorMessage("Nothing is there.");
//            return result;
//        }
//        return result;
//    }//validateNulls
}//end
