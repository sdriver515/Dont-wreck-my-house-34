package learn.renting.data;
import learn.renting.models.Guest;
import learn.renting.models.Host;
import learn.renting.models.Reservation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.*;

@Repository
public class ReservationFileRepository implements ReservationRepository{
    private final String directory;
    private static final String HEADER = "id,start_date,end_date,guest_id,total";
    public ReservationFileRepository(@Value("${ReservationFilePath:./dont-wreck-my-house-data/reservations}") String directory){
        this.directory = directory;
    }//directoryFilepathForReservationFileRepository

    //CREATE
    @Override
    public Reservation add(Reservation reservation) throws DataException {
        List<Reservation> all = findByHost(reservation.getHost());
        int nextId = getNextId(all);
        reservation.setId(nextId);
        all.add(reservation);
        writeAll(all, reservation.getHost());
        return reservation;
    }//add

    //READ
    @Override
    public List<Reservation> findByHost(Host host) throws DataException {
        ArrayList<Reservation> result = new ArrayList<>();
        if (host==null||host.getId().isBlank()||host.getId()==null){
            return result;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(getFilePath(host)))) {

            reader.readLine();

            for (String line = reader.readLine(); line != null; line = reader.readLine()) {

                String[] fields = line.split(",", -1);
                if (fields.length == 5) {
                    result.add(deserialize(fields, host));
                }
            }
        } catch (IOException ex) {
        }
        return result;
    }//findByHostId

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

//    @Override
//    public List<Reservation> findContentsOfAllReservationFiles() {
//        File[] filesList = returnDirectoryFilesList();
//        List<Reservation> result = new ArrayList<>();
//        int count = 0;
//
//        for(int i = 0; i< filesList.length; i++){
//            String fileName = "./dont-wreck-my-house-data/reservations/" + (filesList[i].getName());
//            do{
//                try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
//                    reader.readLine();
//                    for (String line = reader.readLine(); line != null; line = reader.readLine()) {
//                        String[] fields = line.split(",", -1);
//                        if (fields.length == 5) {
//                            result.add(deserializeReservationFile(fields));
//                            count ++;
//                        }
//                    }
//                } catch (IOException ex) {
//                } }while (count < result.size());
//        }return result;
//    }//findContentsOfAllReservationFiles

//    @Override
//    public Map<LocalDate, LocalDate> returnOccupiedDatesOfHost(String hostId) throws DataException {
//        List<Reservation> all = findContentsOfReservationFileByHostId(hostId);
//        Map<LocalDate, LocalDate> mapWithTimes = new HashMap<>();
//        for(Reservation r : all){
//            mapWithTimes.put(r.getStartDateOfStay(),
//                    r.getEndDateOfStay());
//        }
//        return mapWithTimes;
//    }//returnOccupiedDatesOfHost

//    @Override
//    public Map<LocalDate, LocalDate> returnFutureReservations(String hostId) throws DataException {
//        Map<LocalDate, LocalDate> map = returnOccupiedDatesOfHost(hostId);
//        Map<LocalDate, LocalDate> result = new HashMap<>();
//
//            for (LocalDate key : map.keySet()) {
//                if(trueIfInFuture(key)){
//                    result.put(key, map.get(key));
//                }
//            }
//            return result;
//    }//returnFutureReservations


    //UPDATE
    @Override
    public boolean updateReservation(Reservation reservation) throws DataException {
        List<Reservation> all = findByHost(reservation.getHost());
            for(int i = 0; i < all.size(); i++) {
                if (all.get(i).getId() == reservation.getId()) {
                    all.set(i, reservation);
                    writeAll(all, reservation.getHost());
                    return true;
                }
            }
        return false;
    }//update

    //DELETE
    @Override
    public boolean deleteReservation(Reservation reservation) throws DataException {
        List<Reservation> all = findByHost(reservation.getHost());
        for(int i = 0; i < all.size(); i++){
            if(all.get(i).getId()== reservation.getId()) {
                        all.remove(i);
                        writeAll(all, reservation.getHost());
                        return true;
                    }
        }
        return false;
    }//deleteReservation

    //HELPER METHODS

    private String getFilePath(Host host) {
        return Paths.get(directory, host.getId() + ".csv").toString();
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

    private Reservation deserialize(String[] fields, Host host) {
        Reservation result = new Reservation();
        result.setId(Integer.parseInt(fields[0]));
        result.setHost(host);
        result.setStartDateOfStay(LocalDate.parse((fields[1])));
        result.setEndDateOfStay(LocalDate.parse((fields[2])));
        result.setTotalCost(BigDecimal.valueOf(Double.parseDouble(fields[4])));

        Guest guest = new Guest();
        result.setGuest(guest);

        guest.setId(Integer.parseInt(fields[3]));

        return result;
    }//deserialize

    private void writeAll(List<Reservation> reservations, Host host) throws DataException {
        try (PrintWriter writer = new PrintWriter(getFilePath(host))) {

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

}//end
