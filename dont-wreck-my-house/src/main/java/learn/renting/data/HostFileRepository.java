package learn.renting.data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
public class HostFileRepository implements HostRepository {
    private final String filePath;
    public HostFileRepository(@Value("${HostFilePath:./dont-wreck-my-house-data/hosts.csv}") String filePath){
        this.filePath = filePath;
    }//filepathForHostFileRepository
}//end
