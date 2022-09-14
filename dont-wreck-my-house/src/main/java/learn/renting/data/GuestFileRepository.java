package learn.renting.data;
import learn.renting.models.Guest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class GuestFileRepository implements GuestRepository {
    private final String filePath;
    public GuestFileRepository(@Value("${GuestFilePath:./dont-wreck-my-house-data/guests.csv}") String filePath){
        this.filePath = filePath;
    }//filePathForGuestFileRepository

    //READ
    @Override
    public List<Guest> findAll() {
        ArrayList<Guest> result = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {

            reader.readLine();

            for (String line = reader.readLine(); line != null; line = reader.readLine()) {

                String[] fields = line.split(",", -1);
                if (fields.length == 6) {
                    result.add(deserialize(fields));
                }
            }
        } catch (IOException ex) {
        }
        return result;
    }//findAllGuestsInFile

    @Override
    public Guest findById(int idOfGuest) {
        return findAll().stream()
                .filter(i -> i.getId() == idOfGuest)
                .findFirst()
                .orElse(null);
    }//findById

//    @Override
//    public Guest findByGuestEmail(String emailOfGuest) {
//        return findAllGuestsInFile().stream()
//                .filter(i -> i.getEmailOfGuest().equalsIgnoreCase(emailOfGuest))
//                .findFirst()
//                .orElse(null);
//    }//findById


    //HELPER METHODS//
    private Guest deserialize(String[] fields) {
        Guest result = new Guest();
        result.setId(Integer.parseInt(fields[0]));
        result.setFirstNameOfGuest(fields[1]);
        result.setLastNameOfGuest(fields[2]);
        result.setEmailOfGuest(fields[3]);
        result.setPhoneOfGuest(fields[4]);
        result.setStateOfGuest(fields[5]);
        return result;
    }//deserialize

//    private String serialize(Guest guest) {
//        return String.format("%s,%s,%s,%s,%s,%s",
//                guest.getId(),
//                guest.getFirstNameOfGuest(),
//                guest.getLastNameOfGuest(),
//                guest.getEmailOfGuest(),
//                guest.getPhoneOfGuest(),
//                guest.getStateOfGuest());
//    }//serialize

}//end
