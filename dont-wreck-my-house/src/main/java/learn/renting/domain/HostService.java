package learn.renting.domain;

import learn.renting.data.HostRepository;
import learn.renting.models.Guest;
import learn.renting.models.Host;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HostService {
    private final HostRepository repository;

    public HostService(HostRepository repository) {
        this.repository = repository;
    }

    public Host findByEmail(String email){
//        validate(email);
        List<Host> all = repository.findAll();
        for (Host i : all){
            if(i.getEmailOfHost().equals(email)){
                return i;
            }
        }
        return null;
    }//findByEmail

//    private Result<Host> validate(String email) {
//        Result<Host> result = new Result<>();
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
