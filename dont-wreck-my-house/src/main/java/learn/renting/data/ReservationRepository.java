package learn.renting.data;

import learn.renting.models.Guest;
import learn.renting.models.Host;
import learn.renting.models.Reservation;

import java.util.ArrayList;
import java.util.List;

public interface ReservationRepository {

    List<Reservation> findContentsOfReservationFileByHostId(String id)//findContentsReservationFileByHostId
    ;

    List<Reservation> findContentsOfAllReservationFiles()//findContentsOfAllReservationFiles
    ;

    //UPDATE
    boolean update(Reservation reservation, Host host, Guest guest) throws DataException//update
    ;
}//end
