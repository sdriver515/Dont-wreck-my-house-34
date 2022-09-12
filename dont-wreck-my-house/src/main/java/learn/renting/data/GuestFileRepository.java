package learn.renting.data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
public class GuestFileRepository implements GuestRepository {
    private final String filePath;
    public GuestFileRepository(@Value("${GuestFilePath:./dont-wreck-my-house-data/guests.csv}") String filePath){
        this.filePath = filePath;
    }//filePathForGuestFileRepository



}//end
