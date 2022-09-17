package learn.renting.ui;

import learn.renting.data.DataException;
import learn.renting.domain.GuestService;
import learn.renting.domain.HostService;
import learn.renting.domain.ReservationService;
import learn.renting.domain.Result;
import learn.renting.models.Guest;
import learn.renting.models.Host;
import learn.renting.models.Reservation;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

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


    public void run() {
        view.displayHeader("Welcome to Don't Wreck My House!");
        try {
            runAppLoop();
        } catch (DataException ex) {
            view.displayException(ex);
        }
        view.displayHeader("Goodbye.");
    }

    private void runAppLoop() throws DataException {
        MainMenuOption option;
        do {
            option = view.selectMainMenuOption();
            switch (option) {
                case VIEW_RESERVATIONS_BY_HOST:
                    viewByHost();
                    break;
                case MAKE_A_RESERVATION:
                    addReservation();
                    break;
                case EDIT_A_RESERVATION:
                    updateReservation();
                    break;
                case CANCEL_A_RESERVATION:
                    deleteReservation();
                    break;
            }
        } while (option != MainMenuOption.EXIT);
    }

//     top level menu
    private void viewByHost() throws DataException {
        Host host = getHost();
        List<Reservation> reservations = reservationService.findByHost(host);
        view.displayReservations(reservations);
        view.enterToContinue();
    }//viewByHost

    public void addReservation() throws DataException {
        view.displayHeader(MainMenuOption.MAKE_A_RESERVATION.getMessage());
        Host host = getHost();
        if(host == null){
            return;
        }
        Guest guest = getGuest();
        if(guest == null){
            return;
        }
        Reservation reservation = view.makeReservation(guest, host);
        Result<Reservation> result = reservationService.add(reservation);
        if(!result.isSuccess()){//added a "not"
            view.displayStatus(false, result.getErrorMessages());
        } else {
            String successMessage = String.format("Reservation %s added.", reservation.getId());//changed this from the get payload thing
            view.displayStatus(true, successMessage);
        }
    }//addReservation

    public void updateReservation() throws DataException{
        view.displayHeader(MainMenuOption.EDIT_A_RESERVATION.getMessage());
        Host host = getHost();
        Guest guest = getGuest();
        if(host == null){
            return;
        }
        if(guest == null){
            return;
        }
        Reservation reservation = view.putTogetherReservationForUpdating(guest, host);
        Result<Reservation> result = reservationService.update(reservation);
        if(!result.isSuccess()){
            view.displayStatus(false, result.getErrorMessages());
        } else {
            String successMessage = String.format("Your reservation for %s guest with %s host is updated.", reservation.getGuest().getLastNameOfGuest(), reservation.getHost().getId());
            view.displayStatus(true, successMessage);
        }
    }//updateReservation

    public void deleteReservation() throws DataException{
        view.displayHeader(MainMenuOption.CANCEL_A_RESERVATION.getMessage());
        Host host = getHost();
        Guest guest = getGuest();
        if(host == null){
            return;
        }
        if(guest == null){
            return;
        }
        Reservation reservation = view.putTogetherReservationForDeletion(guest, host);
        List<Reservation> reservations = reservationService.findByHost(host);
        Result<Reservation> result = reservationService.delete(reservation, reservations);
        if(!result.isSuccess()){
            view.displayStatus(false, result.getErrorMessages());
        } else {
            String successMessage = String.format("Your reservation for %s guest with %s host is deleted.", reservation.getGuest().getLastNameOfGuest(), reservation.getHost().getId());
            view.displayStatus(true, successMessage);
        }
    }//deleteReservation

    // support methods
    private Guest getGuest() {
        String guestEmail = view.getGuestEmail();
        Guest guest = guestService.findByEmail(guestEmail);
        return guest;
    }//getGuest

    private Host getHost() {
        String hostEmail = view.getHostEmail();
        Host host = hostService.findByEmail(hostEmail);
        return host;
    }//getHost

}//end
