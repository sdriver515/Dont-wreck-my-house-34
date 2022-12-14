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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                case ADD_A_RESERVATION:
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

    //METHODS
    private void viewByHost() throws DataException {
        view.displayHeader(MainMenuOption.VIEW_RESERVATIONS_BY_HOST.getMessage());

        String yesOrNo = view.viewAllHostsYesOrNo();
        if (yesOrNo.equalsIgnoreCase("yes")){
            List<Host> hosts = hostService.findAllHosts();
            view.displayHeader("All Hosts in Database: ");
            view.displayHosts(hosts);
        }
        Host host = getHost();
        if(host == null){
            view.displayHeader("This host does not exist.");
            return;
        } else {
            List<Reservation> reservations = reservationService.findByHost(host);
            view.displayReservations(reservations);
        }
        view.enterToContinue();
    }//viewByHost

    public void addReservation() throws DataException {
        view.displayHeader(MainMenuOption.ADD_A_RESERVATION.getMessage());

        String answer = null;
        Reservation reservation;

        yesOrNoForHosts();
        Host host = getHost();
        if(host == null){
            view.displayHeader("This host does not exist.");
            return;
        }
        List<Reservation> reservations = reservationService.findByHost(host);

        view.displayFutureReservations(reservations);

        yesOrNoForGuests();
        Guest guest = getGuest();
        if(guest == null){
            view.displayHeader("This guest does not exist.");
            return;
        }

        do {
            reservation = view.putTogetherReservationForAdding(guest, host);
            BigDecimal totalCost = reservationService.returnCostOfStay(reservation);
            answer = view.moveForward(totalCost);
        } while (answer.equalsIgnoreCase("no"));

        Result<Reservation> result = reservationService.add(reservation);

        if(!result.isSuccess()){
            view.displayStatus(false, result.getErrorMessages());
        } else {
            String successMessage = String.format("Reservation %s added.", reservation.getId());
            view.displayStatus(true, successMessage);
        }
    }//addReservation

    public void updateReservation() throws DataException{
        view.displayHeader(MainMenuOption.EDIT_A_RESERVATION.getMessage());

        String answer = null;
        Reservation reservation;

        yesOrNoForHosts();
        Host host = getHost();

        List<Reservation> reservations = reservationService.findByHost(host);
        view.displayFutureReservations(reservations);

        yesOrNoForGuests();
        Guest guest = getGuest();

        if(host == null){
            view.displayHeader("This host does not exist.");
            return;
        }
        if(guest == null){
            view.displayHeader("This guest does not exist.");
            return;
        }

        do{
        reservation = view.putTogetherReservationForUpdating(guest, host);
        BigDecimal totalCost = reservationService.returnCostOfStay(reservation);
        answer = view.moveForward(totalCost);
        } while (answer.equalsIgnoreCase("no"));

        Result<Reservation> result = reservationService.update(reservation);

        if(!result.isSuccess()){
            view.displayStatus(false, result.getErrorMessages());
        } else {
            String successMessage = String.format("Your reservation for Ms. or Mr. %s, reservation #%s, is updated.", reservation.getGuest().getLastNameOfGuest(), reservation.getId());
            view.displayStatus(true, successMessage);
        }
    }//updateReservation

    public void deleteReservation() throws DataException{
        view.displayHeader(MainMenuOption.CANCEL_A_RESERVATION.getMessage());
        yesOrNoForHosts();

        Host host = getHost();
        if(host == null){
            view.displayHeader("This host does not exist.");
            return;
        }
        List<Reservation> reservations = reservationService.findByHost(host);
        view.displayFutureReservations(reservations);

        yesOrNoForGuests();
        Guest guest = getGuest();
        if(guest == null){
            view.displayHeader("This guest does not exist.");
            return;
        }

        Reservation reservation = view.putTogetherReservationForDeletion(guest, host);

        Result<Reservation> result = reservationService.delete(reservation, reservations);
        if(!result.isSuccess()){
            view.displayStatus(false, result.getErrorMessages());
        } else {
            String successMessage = String.format("Your reservation for Ms. or Mr. %s, reservation #%s, is deleted.", reservation.getGuest().getLastNameOfGuest(), reservation.getId());
            view.displayStatus(true, successMessage);
        }
    }//deleteReservation

    // HELPER METHODS
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

    private void yesOrNoForHosts(){
        String yesOrNo = view.viewAllHostsYesOrNo();
        if(yesOrNo.equalsIgnoreCase("yes")){
            List<Host> hosts = hostService.findAllHosts();
            view.displayHeader("All Hosts in Database: ");
            view.displayHosts(hosts);
        }
    }//yesOrNoForHosts

    private void yesOrNoForGuests(){
        String yesOrNo = view.viewAllGuestsYesOrNo();
        if(yesOrNo.equalsIgnoreCase("yes")){
            List<Guest> guests = guestService.findAllGuests();
            view.displayHeader("All Guests in Database: ");
            view.displayGuests(guests);
        }
    }//yesOrNoForGuests

}//end
