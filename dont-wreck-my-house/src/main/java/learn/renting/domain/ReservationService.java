package learn.renting.domain;

import learn.renting.data.DataException;
import learn.renting.data.GuestRepository;
import learn.renting.data.HostRepository;
import learn.renting.data.ReservationRepository;
import learn.renting.models.Reservation;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
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

    //Need:
    //find reservation by host

    //CREATE
    public Result<Reservation> add(Reservation reservation) throws DataException {
        reservation.setTotalCost(returnCostOfStay(reservation));
        Result result = validateAdd(reservation);
        if (!result.isSuccess()) {
            return result;
        }
        if (reservation.getId() < 0) {
            result.addErrorMessage("Problems with the ID.");
            return result;
        }
        if(!reservation.getTotalCost().equals(returnCostOfStay(reservation))){
            result.addErrorMessage("The cost is not correct.");
        }
        reservation = reservationRepository.add(reservation);
        result.setPayload(reservation);

        return result;
    }//add

    //update reservation
    public Result<Reservation> update(Reservation reservation) throws DataException {
        reservation.setTotalCost(returnCostOfStay(reservation));
        Result result = validateUpdate(reservation);
        boolean updated = reservationRepository.updateReservation(reservation);
        return  result;
    }//update

    //delete reservation

    //validate add to validate that the result is successful, and it gets the host
    private Result<Reservation> validateAdd(Reservation reservation) throws DataException {

        Result<Reservation> result = validateNulls(reservation);
        if (!result.isSuccess()) {
            return result;
        }
        result = validateDuplicates(reservation);
        if (!result.isSuccess()) {
            return result;
        }
        if(trueIfWithinRange(reservation)){
            result.addErrorMessage("Your reservation would be within a time frame that is already occupied. That is not permitted in our business model.");
        }
        if(!trueIfInFuture(reservation)){
            result.addErrorMessage("This is not in the future. You cannot add reservations to the past.");
        }
        if(trueIfEndDateIsBeforeStartDate(reservation)){
            result.addErrorMessage("Your end date is before your start date.");
        }
        if(trueIfEqualsItself(reservation)){
            result.addErrorMessage("You cannot book only a few hours in one day. Sorry.");
        }

        return result;
    }//validateAdd

    //validate updates
    private Result<Reservation> validateUpdate(Reservation reservation) throws DataException {
        Result<Reservation> result = new Result<>();

        if(!trueIfInFuture(reservation)){
            result.addErrorMessage("You cannot update a reservation set in the past.");
        }
        if(trueIfEndDateIsBeforeStartDate(reservation)){
            result.addErrorMessage("The end date cannot be before the start date.");
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
        if(reservation.getTotalCost()==null){
            result.addErrorMessage("The total cost is null.");
        }
        return result;
    }//validateNulls

    //validate duplicates
    private Result<Reservation> validateDuplicates(Reservation reservation) throws DataException {
        Result<Reservation> result = new Result<>();
        List<Reservation> all = reservationRepository.findByHost(reservation.getHost());
        for(Reservation i : all){
            if (i.getStartDateOfStay().equals(reservation.getStartDateOfStay())){
                result.addErrorMessage("That start date is already occupied.");
            }
            else if(i.getEndDateOfStay().equals(reservation.getEndDateOfStay())){
                result.addErrorMessage("That end date is already occupied.");
            }
             if (i.getStartDateOfStay().equals(reservation.getStartDateOfStay())){
                if(i.getEndDateOfStay().equals(reservation.getEndDateOfStay())){
                    result.addErrorMessage("Actually, both the start and end dates of that are already occupied.");
                }
            }
        }
        return result;
    }//validateDuplicates

    public BigDecimal returnCostOfStay(Reservation reservation){
        BigDecimal standardRateOfHost = reservation.getHost().getStandardRateOfHost();
        BigDecimal weekendRateOfHost = reservation.getHost().getWeekendRateOfHost();
        BigDecimal result = BigDecimal.ONE;
        if(reservation.getStartDateOfStay().isBefore(reservation.getEndDateOfStay()) && (!reservation.getStartDateOfStay().equals(reservation.getStartDateOfStay()))) {
            if (reservation.getStartDateOfStay() == null || reservation.getEndDateOfStay() == null) {
                throw new IllegalArgumentException("Problems here. Something is null.");
            }
            Predicate<LocalDate> isWeekend = date -> date.getDayOfWeek() == DayOfWeek.SATURDAY
                    || date.getDayOfWeek() == DayOfWeek.FRIDAY;
            Predicate<LocalDate> isWeekday = date -> date.getDayOfWeek() == DayOfWeek.SUNDAY
                    || date.getDayOfWeek() == DayOfWeek.MONDAY
                    || date.getDayOfWeek() == DayOfWeek.TUESDAY
                    || date.getDayOfWeek() == DayOfWeek.WEDNESDAY
                    || date.getDayOfWeek() == DayOfWeek.THURSDAY;
            List<LocalDate> weekdays = reservation.getStartDateOfStay().datesUntil(reservation.getEndDateOfStay())
                    .filter(isWeekend.negate()).toList();
            List<LocalDate> weekend = reservation.getStartDateOfStay().datesUntil(reservation.getEndDateOfStay())
                    .filter(isWeekday.negate()).toList();
            int weekdayCount = weekdays.size();
            int weekendCount = weekend.size();
            BigDecimal weekdayCostResult = standardRateOfHost.multiply(BigDecimal.valueOf(weekdayCount));
            BigDecimal weekendCostResult = weekendRateOfHost.multiply(BigDecimal.valueOf(weekendCount));
            result = weekendCostResult.add(weekdayCostResult);
            return result;
        }
        return result;
    }//returnCostOfStayAtHost

    public boolean trueIfIsWeekendRate(Reservation reservation) throws DataException {
        List<Reservation> all = reservationRepository.findByHost(reservation.getHost());
        for(Reservation i : all){
            if(i.getHost().getWeekendRateOfHost().equals(reservation.getHost().getWeekendRateOfHost())){
                return true;
            }
        }
        return false;
    }//trueIfIsWeekendRate

    public boolean trueIfWithinRange(Reservation reservation) throws DataException {
        List<Reservation> all = reservationRepository.findByHost(reservation.getHost());
        for( Reservation i : all){
            if (reservation.getStartDateOfStay().isAfter(i.getStartDateOfStay()) && reservation.getEndDateOfStay().isBefore(i.getEndDateOfStay())){
                return true;
            }}
        return false;
    }//trueIfWithinRange

    public boolean trueIfEqualsItself(Reservation reservation) throws DataException{
        if(reservation.getStartDateOfStay().equals(reservation.getEndDateOfStay())){
            return true;
        }
            return false;
    }//trueIfEqualsItself

    public Map<LocalDate, LocalDate> returnOccupiedDatesOfHost(Reservation reservation) throws DataException {
        List<Reservation> all = reservationRepository.findByHost(reservation.getHost());
        Map<LocalDate, LocalDate> mapWithTimes = new HashMap<>();
        for(Reservation r : all){
            mapWithTimes.put(r.getStartDateOfStay(),
                    r.getEndDateOfStay());
        }
        return mapWithTimes;
    }//returnOccupiedDatesOfHost

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

    public List<Reservation> findFutureReservations(Reservation reservation) throws DataException {
        List<Reservation> all = reservationRepository.findByHost(reservation.getHost());
        List<Reservation> result = new ArrayList<>();
        for(Reservation i : all) {
            if (trueIfInFuture(reservation)) {
                result.add(reservation);
                //removed a writeAll line--would need a helper method
                return result;
            }
        }
        return null;
    }//findFutureReservations

}//end
