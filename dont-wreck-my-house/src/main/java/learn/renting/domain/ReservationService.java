package learn.renting.domain;

import learn.renting.data.DataException;
import learn.renting.data.GuestRepository;
import learn.renting.data.HostRepository;
import learn.renting.data.ReservationRepository;
import learn.renting.models.Guest;
import learn.renting.models.Host;
import learn.renting.models.Reservation;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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

    //CREATE
    public Result<Reservation> add(Reservation reservation) throws DataException {
        Result<Reservation> result = validateAdd(reservation);
        if (!result.isSuccess()) {
            return result;
        }
        result.setPayload(reservationRepository.add(reservation));

        return result;
    }//add

    //READ
    public List<Reservation> findByHost(Host host) throws DataException {
        Map<Integer, Guest> guestMap = guestRepository.findAll().stream()
                .collect(Collectors.toMap(g -> g.getId(), g-> g));

        List<Reservation> listResult = reservationRepository.findByHost(host);
        for(Reservation reservation : listResult){
            reservation.setGuest(guestMap.get(reservation.getGuest().getId()));
        }
        return listResult;
    }//findByHost

    //update reservation
    public Result<Reservation> update(Reservation reservation) throws DataException {
        Result<Reservation> result = validateUpdate(reservation);
        if(!result.isSuccess()){
            return result;
        }
        if(!reservationRepository.updateReservation(reservation)){
            result.addErrorMessage("Reservation does not exist.");
        }
        reservationRepository.updateReservation(reservation);//added this
        return  result;
    }//update

//    public Result<Reservation> updateByGUEST(Reservation reservation) throws DataException {
//        Result<Reservation> result = validateUpdate(reservation);
//        if(!result.isSuccess()){
//            return result;
//        }
//        if(!reservationRepository.updateReservationByGuest(reservation)){
//            result.addErrorMessage("Reservation does not exist.");
//        }
//        return  result;
//    }//updateByGUEST

    //delete reservation
    public Result<Reservation> delete(Reservation reservation, List<Reservation> reservations) throws DataException {
        Result<Reservation> result = new Result<>();
        for(Reservation r : reservations){
            if(r.getEndDateOfStay().isBefore(LocalDate.now())){
                result.addErrorMessage("Past reservations cannot be deleted.");
                return result;
            }
        }
        if(!reservationRepository.deleteReservation(reservation)){
            result.addErrorMessage("Reservation does not exist.");
            return result;
        }
        reservationRepository.deleteReservation(reservation);//added this
        return result;
    }//delete

    //HELPER METHODS

    private Result<Reservation> validateAdd(Reservation reservation) throws DataException {
        Result<Reservation> result = validateNulls(reservation);
        if (!result.isSuccess()) {
            return result;
        }
        Host host = reservation.getHost();

        validateDuplicates(result, reservation, reservationRepository.findByHost(host));
        return result;
    }//validateAdd

    private Result<Reservation> validateUpdate(Reservation reservation) throws DataException {
        Result<Reservation> result = validateNulls(reservation);
        if (!result.isSuccess()) {
            return result;
        }
        if(!trueIfInFuture(reservation)){
            result.addErrorMessage("You cannot update a reservation set in the past.");
        }
        if(trueIfEndDateIsBeforeStartDate(reservation)){
            result.addErrorMessage("Your end date is before your start date.");
        }
        if(trueIfEqualsItself(reservation)){
            result.addErrorMessage("You cannot book only a few hours in one day. Sorry.");
        }
        if(trueIfHostDoesNotExist(reservation)){
            result.addErrorMessage("This host does not seem to exist.");
        }
        if(trueIfGuestDoesNotExist(reservation)){
            result.addErrorMessage("This guest does not seem to exist.");
        }
        return result;
    }//validateUpdate

    private Result<Reservation> validateNulls(Reservation reservation) {
        Result<Reservation> result = new Result<>();
        if (reservation == null) {
            result.addErrorMessage("There is no reservation... It is null.");
            return result;
        }
        if (reservation.getHost()==null){
            result.addErrorMessage("The host is null.");
        }
        if(reservation.getGuest()==null){
            result.addErrorMessage("The guest is null.");
        }
        if (reservation.getStartDateOfStay() == null) {
            result.addErrorMessage("There must be a start date. It is currently null.");
        }
        if (reservation.getEndDateOfStay() == null) {
            result.addErrorMessage("There must be an end date. It is currently null.");
        }
        Guest guest = reservation.getGuest();
        Guest guestFromDatabase = guestRepository.findById(guest.getId());
        if(guestFromDatabase == null){
            result.addErrorMessage("Guest does not exist.");
        }
        if(!guestFromDatabase.getEmailOfGuest().equalsIgnoreCase(guest.getEmailOfGuest())){
            result.addErrorMessage("This is not the correct email.");
        }
        return result;
    }//validateNulls

    public void validateDuplicates(Result<Reservation> result, Reservation newReservation, List<Reservation> existingReservations) throws DataException {
        for(Reservation existingReservation : existingReservations){
            if(newReservation.getStartDateOfStay().isBefore(existingReservation.getStartDateOfStay()) && newReservation.getEndDateOfStay().isAfter(existingReservation.getStartDateOfStay())) {
                result.addErrorMessage("Reservations cannot overlap.");
                return;
            }
            if(newReservation.getStartDateOfStay().isBefore(existingReservation.getEndDateOfStay()) && newReservation.getEndDateOfStay().isAfter(existingReservation.getStartDateOfStay())){
                result.addErrorMessage("Reservations cannot overlap.");
                return;
            }
            if(newReservation.getStartDateOfStay().isAfter(existingReservation.getStartDateOfStay()) && newReservation.getEndDateOfStay().isBefore(existingReservation.getEndDateOfStay()) || newReservation.getEndDateOfStay().isEqual(existingReservation.getEndDateOfStay())){
                result.addErrorMessage("Reservations cannot overlap.");
                return;
            }
        }
        if(newReservation.getStartDateOfStay().isBefore(LocalDate.now())){
            result.addErrorMessage("Reservations cannot be made in the past.");
        }
        if(newReservation.getStartDateOfStay().isAfter(newReservation.getEndDateOfStay())){
            result.addErrorMessage("Start date must be before end date.");
        }
        if(newReservation.getStartDateOfStay().isEqual(newReservation.getEndDateOfStay())){//I added
            result.addErrorMessage("Start and end date cnanot be the same.");
        }
    }//validateDuplicates

    public void returnCostOfStay(Reservation reservation){
        LocalDate day = reservation.getStartDateOfStay();
        BigDecimal totalCost = BigDecimal.ZERO;
        do{
            totalCost = trueIfIsWeekendRate(day)?
                    totalCost.add(reservation.getHost().getWeekendRateOfHost()):
                    totalCost.add(reservation.getHost().getStandardRateOfHost());
            day = day.plusDays(1);
        }while(day.isBefore(reservation.getEndDateOfStay()));
        reservation.setTotalCost(totalCost);
    }//returnCostOfStayAtHost

    private boolean trueIfIsWeekendRate(LocalDate dayOfWeek) {
       DayOfWeek day = DayOfWeek.of(dayOfWeek.get(ChronoField.DAY_OF_WEEK));
       return day == DayOfWeek.FRIDAY || day == DayOfWeek.SATURDAY;
    }//trueIfIsWeekendRate

    private boolean trueIfEqualsItself(Reservation reservation) throws DataException{
        if(reservation.getStartDateOfStay().equals(reservation.getEndDateOfStay())){
            return true;
        }
            return false;
    }//trueIfEqualsItself

    private boolean trueIfHostDoesNotExist(Reservation reservation) throws DataException{
        if(!reservation.getHost().equals(hostRepository.findByHostId(reservation.getHost().getId()))){
            return true;
        }
        return false;
    }//trueIfHostDoesNotExist

    private boolean trueIfGuestDoesNotExist(Reservation reservation) throws DataException{
        if(!reservation.getGuest().equals(guestRepository.findById(reservation.getGuest().getId()))){
            return true;
        }
        return false;
    }//trueIfGuestDoesNotExist

    public boolean trueIfInFuture(Reservation reservation){
        LocalDate dateNow = LocalDate.now();
        if (reservation.getStartDateOfStay().isAfter(dateNow)){
            return true;
        }
        return false;
    }//trueIfInFuture

    public boolean trueIfEndDateIsBeforeStartDate(Reservation reservation) throws DataException {
        if(reservation.getEndDateOfStay().isBefore(reservation.getStartDateOfStay())){
            return true;
        }
        return false;
    }//trueIfEndDateIsBeforeStartDates

//    public Map<LocalDate, LocalDate> displayOccupiedDatesOfHost(Reservation reservation) throws DataException {
//        List<Reservation> all = reservationRepository.findByHost(reservation.getHost());
//        Map<LocalDate, LocalDate> mapWithTimes = new HashMap<>();
//        for(Reservation r : all){
//            mapWithTimes.put(r.getStartDateOfStay(),
//                    r.getEndDateOfStay());
//        }
//        return mapWithTimes;
//    }//displayOccupiedDatesOfHost

}//end
