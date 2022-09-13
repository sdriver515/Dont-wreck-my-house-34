package learn.renting.data;


import learn.renting.models.Guest;

import java.util.List;

public interface GuestRepository {
    List<Guest> findAllGuestsInFile();//findAllGuestsInFile


    Guest findByGuestId(int idOfGuest)//findById
    ;

    Guest findByGuestEmail(String emailOfGuest)//findById
    ;
}//end
