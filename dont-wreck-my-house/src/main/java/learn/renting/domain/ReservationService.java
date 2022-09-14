package learn.renting.domain;

import learn.renting.data.DataException;
import learn.renting.data.GuestRepository;
import learn.renting.data.HostRepository;
import learn.renting.data.ReservationRepository;
import learn.renting.models.Reservation;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final GuestRepository guestRepository;
    private final HostRepository hostRepository;

    public ReservationService(ReservationRepository reservationRepository, GuestRepository guestRepository, HostRepository hostRepository) {
        this.reservationRepository = reservationRepository;
        this.guestRepository = guestRepository;
        this.hostRepository = hostRepository;
    }

    //Need:
    //find reservation by host
    //add reservation

    //CREATE
//    public ReservationResult add(Reservation reservation) throws DataException {
//        ReservationResult result = validateAdd(reservation);
//        if (!result.isSuccess()) {
//            return result;
//        }
//        if (reservation.getId() < 0) {
//            result.addMessage("Problems with the ID.");
//            return result;
//        }
//        if (trueIfMatchingParameters(reservation) {
//            result.addMessage("Please make sure this doesn't already exist.");
//            return result;
//        }
//        reservation = reservationRepository.add(reservation);
//        result.setReservation(reservation);
//        return result;
//    }//create

    //update reservation

    //delete reservation

    //validate updates

    //validate method to validate null, etc.

    //validate add to validate that the result is successful, and it gets the host

    //validate duplicates

    //calculate total

    //helper method "is weekend rate" to return a boolean
    public boolean trueIfIsWeekendRate(Reservation reservation) throws DataException {
        List<Reservation> all = reservationRepository.findByHost(reservation.getHost());
        for(Reservation i : all){
            if(i.getHost().getWeekendRateOfHost().equals(reservation.getHost().getWeekendRateOfHost())){
                return true;
            }
        }
        return false;
    }//trueIfIsWeekendRate

}//end
