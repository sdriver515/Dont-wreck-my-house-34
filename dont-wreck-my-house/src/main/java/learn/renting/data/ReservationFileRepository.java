package learn.renting.data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
public class ReservationFileRepository implements ReservationRepository{
    private final String directory;
    private static final String HEADER = "id,start_date,end_date,guest_id,total";
    public ReservationFileRepository(@Value("${ReservationFilePath:./dont-wreck-my-house-data/}") String directory){
        this.directory = directory;
    }//directoryFilepathForReservationFileRepository




}//end
