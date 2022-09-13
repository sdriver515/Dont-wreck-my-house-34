package learn.renting.data;

import learn.renting.models.Guest;

import java.util.ArrayList;

public class GuestRepositoryDouble {
    public final static Guest GUEST = new Guest(123, "Mathias", "Spieker", ".", ".", ".");
    private final ArrayList<Guest> guests = new ArrayList<>();

    public GuestRepositoryDouble() {
        guests.add(GUEST);
    }


}//end
