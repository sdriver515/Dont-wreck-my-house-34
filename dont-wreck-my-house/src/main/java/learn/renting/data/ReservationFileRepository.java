package learn.renting.data;
import learn.renting.models.Guest;
import learn.renting.models.Host;
import learn.renting.models.Reservation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Paths;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class ReservationFileRepository implements ReservationRepository{
    private final String directory;
    private static final String HEADER = "id,start_date,end_date,guest_id,total";
    public ReservationFileRepository(@Value("${ReservationFilePath:./dont-wreck-my-house-data/reservations}") String directory){
        this.directory = directory;
    }//directoryFilepathForReservationFileRepository

    //CREATE
    @Override
    public Reservation create(Reservation reservation, String hostId) throws DataException {
        List<Reservation> all = findContentsOfReservationFileByHostId(hostId);
        if (reservation != null){
        int nextId = getNextId(all);
        reservation.setId(nextId);
        all.add(reservation);
        writeAll(all, hostId);
        return reservation;
        }
        return null;
    }//create

    //READ
    @Override
    public List<Reservation> findContentsOfReservationFileByHostId(String hostId) throws DataException {
        ArrayList<Reservation> result = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(getFilePath(hostId)))) {

            reader.readLine();

            for (String line = reader.readLine(); line != null; line = reader.readLine()) {

                String[] fields = line.split(",", -1);
                if (fields.length == 5) {
                    result.add(deserialize(fields, hostId));
                }
            }
        } catch (IOException ex) {
        }
        return result;
    }//findContentsOfReservationFilesByHostId

    public Reservation findReservationByHostIdAndDatesAndGuestId(String hostId, LocalDate startDate, LocalDate endDate, int guestId) throws DataException {
        List<Reservation> all = findContentsOfReservationFileByHostId(hostId);
        for(Reservation reservation : all){
            if(reservation.getStartDateOfStay().equals(startDate))
                if(reservation.getEndDateOfStay() == endDate)
                    if(reservation.getGuest().getId() == guestId){
                        return reservation;
                    }
        }
        return null;
    }//findReservationByHostIdAndDatesAndGuestId

//    public List<Reservation> findFutureReservationsByHostIdAndDate(String hostId, LocalDate startDate) throws DataException {
//        List<Reservation> all = findContentsOfReservationFileByHostId(hostId);
//        List<Reservation> result = new ArrayList<>();
//        for(Reservation reservation : all) {
//            if (reservation.getStartDateOfStay().equals(startDate)) {
//                result.add(reservation);
//                writeAll(result, hostId);
//                return result;
//            }
//        }
//        return null;
//    }//findFutureReservationsByHostIdAndDate

    @Override
    public List<Reservation> findContentsOfAllReservationFiles() {
        File[] filesList = returnDirectoryFilesList();
        List<Reservation> result = new ArrayList<>();
        int count = 0;

        for(int i = 0; i< filesList.length; i++){
            String fileName = "./dont-wreck-my-house-data/reservations/" + (filesList[i].getName());
            do{
                try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
                    reader.readLine();
                    for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                        String[] fields = line.split(",", -1);
                        if (fields.length == 5) {
                            result.add(deserializeReservationFile(fields));
                            count ++;
                        }
                    }
                } catch (IOException ex) {
                } }while (count < result.size());
        }return result;
    }//findContentsOfAllReservationFiles

    @Override
    public Map<LocalDate, LocalDate> returnOccupiedDatesOfHost(String hostId) throws DataException {
        List<Reservation> all = findContentsOfReservationFileByHostId(hostId);
        Map<LocalDate, LocalDate> mapWithTimes = new HashMap<>();
        for(Reservation r : all){
            mapWithTimes.put(r.getStartDateOfStay(),
                    r.getEndDateOfStay());
        }
        return mapWithTimes;
    }//returnOccupiedDatesOfHost

    @Override
    public Map<LocalDate, LocalDate> returnFutureReservations(String hostId) throws DataException {
        Map<LocalDate, LocalDate> map = returnOccupiedDatesOfHost(hostId);
        Map<LocalDate, LocalDate> result = new HashMap<>();

            for (LocalDate key : map.keySet()) {
                if(trueIfInFuture(key)){
                    result.put(key, map.get(key));
                }
            }
            return result;
    }//returnFutureReservations

    @Override
    public boolean trueIfWithinRange(LocalDate startDate, LocalDate endDate, LocalDate inputDate){
            if (inputDate.isAfter(startDate) && inputDate.isBefore(endDate)){
                return true;
            }
        return false;
    }//trueIfWithinRange

    @Override
    public boolean trueIfInFuture(LocalDate startDate){
        LocalDate dateNow = LocalDate.now();
        if (startDate.isAfter(dateNow)){
            return true;
        }
        return false;
    }//trueIfInFuture

    @Override
    public BigDecimal returnCostOfStayAtHost(Host host, LocalDate startDate, LocalDate endDate){
        int weekendCount = 0;
        int weekdayCount = 0;
        BigDecimal standardRateOfHost = host.getStandardRateOfHost();
        BigDecimal weekendRateOfHost = host.getWeekendRateOfHost();
        BigDecimal result = BigDecimal.ONE;

        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Problems here. Something is null.");
        }
        Predicate<LocalDate> isWeekend = date -> date.getDayOfWeek() == DayOfWeek.SATURDAY
                || date.getDayOfWeek() == DayOfWeek.FRIDAY;

        Predicate<LocalDate> isWeekday = date -> date.getDayOfWeek() == DayOfWeek.SUNDAY
                || date.getDayOfWeek() == DayOfWeek.MONDAY
                || date.getDayOfWeek() == DayOfWeek.TUESDAY
                || date.getDayOfWeek() == DayOfWeek.WEDNESDAY
                || date.getDayOfWeek() == DayOfWeek.THURSDAY;

        List<LocalDate> weekdays = startDate.datesUntil(endDate)
                .filter(isWeekend.negate())
                .collect(Collectors.toList());

        List<LocalDate> weekend = startDate.datesUntil(endDate)
                .filter(isWeekday.negate())
                .collect(Collectors.toList());

        weekdayCount=weekdays.size();
        weekendCount=weekend.size();

        BigDecimal weekdayCostResult = standardRateOfHost.multiply(BigDecimal.valueOf(weekdayCount));
        BigDecimal weekendCostResult = weekendRateOfHost.multiply(BigDecimal.valueOf(weekendCount));
        result = weekendCostResult.add(weekdayCostResult);
        return  result;
    }//returnCostOfStayAtHost

    //UPDATE
    @Override
    public boolean update(Reservation reservation, String hostId, int guestId) throws DataException {
        List<Reservation> all = findContentsOfReservationFileByHostId(hostId);
        if (reservation != null){
            for(int i = 0; i < all.size(); i++) {
                if (all.get(i).getGuest().getId() == guestId) {
                    all.set(i, reservation);
                    writeAll(all, hostId);
                    return true;
                }
            }
        }
        return false;
    }//update

    //DELETE
    @Override
    public boolean deleteByParameters(String hostId, LocalDate startDate, LocalDate endDate, int guestId) throws DataException {
        List<Reservation> all = findContentsOfReservationFileByHostId(hostId);
        for(int i = 0; i < all.size(); i++){
            if(all.get(i).getStartDateOfStay().equals(startDate))
                if(all.get(i).getEndDateOfStay().equals(endDate))
                    if(all.get(i).getGuest().getId() == guestId){
                        all.remove(i);
                        writeAll(all, hostId);
                        return true;
                    }
        }
        return false;
    }//deleteByParameters

    //HELPER METHODS

    private String getFilePath(String id) {
        return Paths.get(directory, id + ".csv").toString();
    }//getFilePath

    private File[] returnDirectoryFilesList() {
        File directoryPath = new File("./dont-wreck-my-house-data/reservations");
        File directoryFilesList[] = directoryPath.listFiles();
        return directoryFilesList;
    }//returnDirectoryFilesList

    private String serialize(Reservation reservation) {
        return String.format("%s,%s,%s,%s,%s",
                reservation.getId(),
                reservation.getStartDateOfStay(),
                reservation.getEndDateOfStay(),
                reservation.getGuest().getId(),
                reservation.getTotalCost());
    }//serialize

    private Reservation deserializeReservationFile(String[] fields) {
        Reservation result = new Reservation();
        result.setId(Integer.parseInt(fields[0]));
        result.setStartDateOfStay(LocalDate.parse((fields[1])));
        result.setEndDateOfStay(LocalDate.parse((fields[2])));
        result.setTotalCost(new BigDecimal((fields[4])));

        Guest guest = new Guest();
        guest.setId(Integer.parseInt(fields[3]));
        result.setGuest(guest);

        return result;
    }//deserializeForageFile

    private Reservation deserialize(String[] fields, String id) {
        Reservation result = new Reservation();
        result.setId(Integer.parseInt(fields[0]));
        result.setStartDateOfStay(LocalDate.parse((fields[1])));
        result.setEndDateOfStay(LocalDate.parse((fields[2])));
        result.setTotalCost(new BigDecimal((fields[4])));

        Guest guest = new Guest();
        guest.setId(Integer.parseInt(fields[3]));
        result.setGuest(guest);

        return result;
    }//deserialize

    private void writeAll(List<Reservation> reservations, String hostId) throws DataException {
        try (PrintWriter writer = new PrintWriter(getFilePath(hostId))) {

            writer.println(HEADER);

            for (Reservation i : reservations) {
                writer.println(serialize(i));
            }
        } catch (FileNotFoundException ex) {
            throw new DataException(ex);
        }
    }//writeAll

    private int getNextId(List<Reservation> reservations){
        int maxId = 0;
        for(Reservation reservation : reservations){
            if(maxId < reservation.getId()){
                maxId = reservation.getId();
            }
        }
        return maxId+1;
    }//getNextId

    @Override
    public boolean trueIfMatchingParameters(String hostId, int guestId, LocalDate startDate, LocalDate endDate) throws DataException {
        List<Reservation> all = findContentsOfReservationFileByHostId(hostId);
        for(Reservation reservation : all){
            if(reservation.getStartDateOfStay().equals(startDate))
                if(reservation.getEndDateOfStay().equals(endDate))
                    if(reservation.getGuest().getId() == guestId){
                        return true;
                    }
        }
        return false;
    }//trueIfMatchingParameters

}//end
