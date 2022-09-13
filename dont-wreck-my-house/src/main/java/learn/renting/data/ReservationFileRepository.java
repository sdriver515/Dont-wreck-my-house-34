package learn.renting.data;
import learn.renting.models.Guest;
import learn.renting.models.Host;
import learn.renting.models.Reservation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ReservationFileRepository implements ReservationRepository{
    private final String directory;
    private static final String HEADER = "id,start_date,end_date,guest_id,total";
    public ReservationFileRepository(@Value("${ReservationFilePath:./dont-wreck-my-house-data/reservations}") String directory){
        this.directory = directory;
    }//directoryFilepathForReservationFileRepository

    //CREATE

    //READ
    @Override
    public List<Reservation> findContentsOfReservationFileByHostId(String id) {
        ArrayList<Reservation> result = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(getFilePath(id)))) {

            reader.readLine();

            for (String line = reader.readLine(); line != null; line = reader.readLine()) {

                String[] fields = line.split(",", -1);
                if (fields.length == 5) {
                    result.add(deserialize(fields, id));
                }
            }
        } catch (IOException ex) {
        }
        return result;
    }//findContentsOfReservationFilesByHostId

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

    //UPDATE
    @Override
    public boolean update(Reservation reservation, Host host, Guest guest) throws DataException {
        String hostId = host.getId();
        int guestId = guest.getId();
        List<Reservation> all = findContentsOfReservationFileByHostId(hostId);
        for (int i = 0; i < all.size(); i++) {
            if (all.get(i).getGuest().getId() == (guestId)) {
                all.set(i, reservation);
                writeAll(all, hostId);
                return true;
            }
        }
        return false;
    }//update

    //DELETE


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
                reservation.getGuest().getId(),//do I want it like this?
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

    private void writeAll(List<Reservation> reservations, String id) throws DataException {
        try (PrintWriter writer = new PrintWriter(getFilePath(id))) {

            writer.println(HEADER);

            for (Reservation i : reservations) {
                writer.println(serialize(i));
            }
        } catch (FileNotFoundException ex) {
            throw new DataException(ex);
        }
    }//writeAll


}//end
