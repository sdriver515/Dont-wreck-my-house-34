package learn.renting.ui;

import learn.renting.domain.GuestService;
import learn.renting.domain.HostService;
import learn.renting.domain.ReservationService;
import org.springframework.stereotype.Component;

@Component
public class Controller {
    private final View view;
    private final GuestService guestService;
    private final HostService hostService;
    private final ReservationService reservationService;

    public Controller(View view, GuestService guestService, HostService hostService, ReservationService reservationService){
        this.view = view;
        this.guestService = guestService;
        this.hostService = hostService;
        this.reservationService = reservationService;
    }//Controller

    public void run(){
    }//run

}//end
