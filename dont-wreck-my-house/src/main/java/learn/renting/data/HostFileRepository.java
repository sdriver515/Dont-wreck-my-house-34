package learn.renting.data;
import learn.renting.models.Guest;
import learn.renting.models.Host;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class HostFileRepository implements HostRepository {
    private final String filePath;
    public HostFileRepository(@Value("${HostFilePath:./dont-wreck-my-house-data/hosts.csv}") String filePath){
        this.filePath = filePath;
    }//filepathForHostFileRepository

    @Override
    public List<Host> findAll() {
        ArrayList<Host> result = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {

            reader.readLine();

            for (String line = reader.readLine(); line != null; line = reader.readLine()) {

                String[] fields = line.split(",", -1);
                if (fields.length == 10) {
                    result.add(deserialize(fields));
                }
            }
        } catch (IOException ex) {
        }
        return result;
    }//findAll

    @Override
    public Host findByHostId(String id) {
        return findAll().stream()
                .filter(i -> i.getId().equalsIgnoreCase(id))
                .findFirst()
                .orElse(null);
    }//findByHostId

//    @Override
//    public Host findByHostEmail(String emailOfHost) {
//        return findAllHostsInFile().stream()
//                .filter(i -> i.getEmailOfHost().equalsIgnoreCase(emailOfHost))
//                .findFirst()
//                .orElse(null);
//    }//findByIdOfHost

//    public Map<String, BigDecimal> findStandardRateOfHostById(Integer idOfHost){
//        List<Host> host = findAllHostsInFile();
//        Map<String, BigDecimal> map = new HashMap<>();
//        for(Host h:host) {
//            map.put(h.getId(),
//                    h.getStandardRateOfHost());
//        }
//        return map;
//    }//findStandardRateOfHostById

//    public Map<String, BigDecimal> findWeekendRateOfHostById(Integer idOfHost){
//        List<Host> host = findAllHostsInFile();
//        Map<String, BigDecimal> map = new HashMap<>();
//        for(Host h:host) {
//            map.put(h.getId(),
//                    h.getWeekendRateOfHost());
//        }
//        return map;
//    }//findWeekendRateOfHostById

    //HELPER METHODS//
    private Host deserialize(String[] fields) {
        Host result = new Host();
        result.setId(fields[0]);
        result.setLastNameOfHost(fields[1]);
        result.setEmailOfHost(fields[2]);
        result.setPhoneOfHost(fields[3]);
        result.setAddressOfHost(fields[4]);
        result.setCityOfHost(fields[5]);
        result.setStateOfHost(fields[6]);
        result.setPostalCodeOfHost(fields[7]);
        result.setStandardRateOfHost(new BigDecimal(fields[8]));
        result.setWeekendRateOfHost(new BigDecimal(fields[9]));
        return result;
    }//deserialize


}//end
