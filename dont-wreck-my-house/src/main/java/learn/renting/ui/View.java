package learn.renting.ui;

import learn.renting.models.Guest;
import learn.renting.models.Host;
import learn.renting.models.Reservation;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

@Component
public class View {

    private final ConsoleIO io;

    public View(ConsoleIO io) {
        this.io = io;
    }

    public MainMenuOption selectMainMenuOption() {
        displayHeader("Main Menu");
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (MainMenuOption option : MainMenuOption.values()) {
            if (!option.isHidden()) {
                io.printf("%s. %s%n", option.getValue(), option.getMessage());
            }
            min = Math.min(min, option.getValue());
            max = Math.max(max, option.getValue());
        }

        String message = String.format("Select [%s-%s]: ", min, max);
        return MainMenuOption.fromValue(io.readInt(message, min, max));
    }//selectMainMenuOption

    public String getHostEmail() {//
        return io.readString("Enter a Host Email: ");
    }//getHostEmail

    public String getGuestEmail() {//
        return io.readString("Enter a Guest Email: ");
    }//getGuestEmail

    public Reservation putTogetherReservationForAdding(Guest guest, Host host) {
        Reservation reservation = new Reservation();
        reservation.setHost(host);
        reservation.setId(reservation.getId());
        reservation.setStartDateOfStay(io.readLocalDate("Start date [MM/dd/yyyy]: "));
        reservation.setEndDateOfStay(io.readLocalDate("End date [MM/dd/yyyy]: "));
        reservation.setGuest(guest);
        io.println("");
        return reservation;
    }//putTogetherReservationForAdding

    public Reservation putTogetherReservationForUpdating(Guest guest, Host host){
        Reservation update = new Reservation();
        update.setGuest(guest);
        update.setHost(host);
        update.setId(io.readInt("Choose a reservation # to update: "));
        update.setStartDateOfStay(io.readLocalDate("Start date [MM/dd/yyyy]: "));
        update.setEndDateOfStay(io.readLocalDate("End date [MM/dd/yyyy]: "));
        io.println("");
        return update;
    }//putTogetherReservationForUpdating

    public Reservation putTogetherReservationForDeletion(Guest guest, Host host){
        Reservation toDelete = new Reservation();
        toDelete.setGuest(guest);
        toDelete.setHost(host);
        toDelete.setId(io.readInt("Choose a reservation # to delete: "));
        io.println("");
        return toDelete;
    }//putTogetherReservationForDeletion

    public void enterToContinue() {
        io.readString("Press [Enter] to continue.");
    }//enterToContinue
    public String moveForward(BigDecimal amount){
        System.out.printf("We think this will cost $%s%n", amount);
        String answer = io.readString("Are you ok with this amount? Yes or no? Type it here: ");
        io.println("");
        return answer;
    }//moveForward

    public String viewAllHostsYesOrNo(){
        System.out.println("Do you want to view a list all hosts in the database?");
        String answer = io.readString("Enter yes or no: ");
        io.println("");
        return answer;
    }//viewAllHostsYesOrNo

    public String viewAllGuestsYesOrNo(){
        System.out.println("Do you want to view a list all guests in the database?");
        String answer = io.readString("Enter yes or no: ");
        io.println("");
        return answer;
    }//viewAllGuestsYesOrNo

    // display only
    public void displayHeader(String message) {
        io.println("");
        io.println(message);
        io.println("=".repeat(message.length()));
    }//displayHeader

    public void displayException(Exception ex) {
        displayHeader("A critical error occurred:");
        io.println(ex.getMessage());
    }//displayExemption

    public void displayStatus(boolean success, String message) {
        displayStatus(success, List.of(message));
    }//displayStatus

    public void displayStatus(boolean success, List<String> messages) {
        displayHeader(success ? "Success" : "Error");
        for (String message : messages) {
            io.println(message);
        }
    }//displayStatus

    public void displayReservations(List<Reservation> reservations) {
        io.println("");
        io.println("* ALL RESERVATION INFO *");
        if (reservations == null || reservations.isEmpty()) {
            io.println("No reservations found.");
            io.println("");
            return;
        }
        for (Reservation reservation : reservations) {
            io.printf("Reservation# %s: %s to %s, Guest ID %s / %s, $%s %n",
                    reservation.getId(),
                    reservation.getStartDateOfStay(),
                    reservation.getEndDateOfStay(),
                    reservation.getGuest().getId(),
                    reservation.getGuest().getEmailOfGuest(),
                    reservation.getTotalCost());
        }
        io.println("");
    }//displayReservations

    public void displayFutureReservations(List<Reservation> reservations) {
        io.println("");
        io.println("* FUTURE RESERVATION INFO *");
        if (reservations == null || reservations.isEmpty()) {
            io.println("No reservations found.");
            io.println("");
            return;
        }
        for (Reservation reservation : reservations) {
            if(reservation.getStartDateOfStay().isAfter(LocalDate.now())){
                io.printf("Reservation# %s: %s to %s, Guest ID %s / %s, $%s %n",
                        reservation.getId(),
                        reservation.getStartDateOfStay(),
                        reservation.getEndDateOfStay(),
                        reservation.getGuest().getId(),
                        reservation.getGuest().getEmailOfGuest(),
                        reservation.getTotalCost());
            }
            io.println("");
        }
    }//displayFutureReservations

    public void displayGuests(List<Guest> guests) {

        if (guests.size() == 0) {
            io.println("No guests found");
        }

        for (Guest guest : guests) {
            io.printf("%s: %s, %s, %s %n",
                    guest.getId(),
                    guest.getLastNameOfGuest(),
                    guest.getEmailOfGuest(),
                    guest.getStateOfGuest());
        }
        io.println("");
    }//displayGuests

    public void displayHosts(List<Host> hosts) {

        if (hosts.size() == 0) {
            io.println("No hosts found");
        }

        for (Host host : hosts) {
            io.printf("ID %s: %s, %s, $%s, $%s %n",
                    host.getId(),
                    host.getLastNameOfHost(),
                    host.getEmailOfHost(),
                    host.getStandardRateOfHost(),
                    host.getWeekendRateOfHost());
        }
        io.println("");
    }//displayHosts

}//end