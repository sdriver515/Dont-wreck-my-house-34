package learn.renting.data;
import learn.renting.models.Host;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Repository
public class HostFileRepository implements HostRepository {
    private final String filePath;
    public HostFileRepository(@Value("${HostFilePath:./dont-wreck-my-house-data/hosts.csv}") String filePath){
        this.filePath = filePath;
    }//filepathForHostFileRepository

    @Override
    public List<Host> findAllHostsInFile() {
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
    }//findAllHostsInFile

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
        result.setPostalCodeOfHost(Integer.parseInt(fields[7]));
        result.setStandardRateOfHost(new BigDecimal(fields[8]));
        result.setWeekendRateOfHost(new BigDecimal(fields[9]));
        return result;
    }//deserialize

    private String serialize(Host host) {
        return String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s",
                host.getId(),
                host.getLastNameOfHost(),
                host.getEmailOfHost(),
                host.getPhoneOfHost(),
                host.getAddressOfHost(),
                host.getCityOfHost(),
                host.getStateOfHost(),
                host.getPostalCodeOfHost(),
                host.getStandardRateOfHost(),
                host.getWeekendRateOfHost());
    }//serialize





}//end
