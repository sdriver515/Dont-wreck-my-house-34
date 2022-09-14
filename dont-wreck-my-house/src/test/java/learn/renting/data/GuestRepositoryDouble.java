package learn.renting.data;

import learn.renting.models.Guest;

import java.util.ArrayList;
import java.util.List;

public class GuestRepositoryDouble implements GuestRepository{
    public final static Guest GUEST = new Guest(123, "Mathias", "Spieker", ".", ".", ".");
    private final ArrayList<Guest> guests = new ArrayList<>();

    public GuestRepositoryDouble() {
        guests.add(GUEST);
    }


    @Override
    public List<Guest> findAll() {
        ArrayList<Guest> all = new ArrayList<>();
        all.add(new Guest(444, "Sarah", "Driver", "sdriver@udel.edu", "410-808-5299", "MD"));
        return all;
    }//findAll

    @Override
    public Guest findById(int idOfGuest) {
        for(Guest guest : findAll()){
            if (guest.getId()==idOfGuest){
                return guest;
            }
        }
        return null;
    }//findById

}//end
