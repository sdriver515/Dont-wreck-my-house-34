package learn.renting.data;


import learn.renting.models.Guest;

import java.util.List;

public interface GuestRepository {
    List<Guest> findAll();

    Guest findById(int idOfGuest);

}//end
