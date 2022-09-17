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

        String message = String.format("Select [%s-%s]: ", min, max - 1);
        return MainMenuOption.fromValue(io.readInt(message, min, max));
    }

    public String viewReservationsByHost() {
        displayHeader(MainMenuOption.VIEW_RESERVATIONS_BY_HOST.getMessage());
        return io.readRequiredString("Select a host email: ");
    }//viewReservationsByHost

    public String getHostEmail() {//
        return io.readString("Select a Host Email: ");
    }//getHostEmail

    public String getGuestEmail() {//
        return io.readString("Select a Guest Email: ");
    }//getGuestEmail

    public Guest chooseGuest(List<Guest> guests) {

        displayGuests(guests);

        if (guests.size() == 0) {
            return null;
        }

        int guestId = io.readInt("Select a guest ID: ");
        Guest guest = guests.stream()
                .filter(i -> i.getId() == guestId)
                .findFirst()
                .orElse(null);

        if (guest == null) {
            displayStatus(false, String.format("No guest with ID %s found.", guestId));
        }

        return guest;
    }//chooseGuest

    public Host chooseHost(List<Host> hosts) {

        displayHosts(hosts);

        if (hosts.size() == 0) {
            return null;
        }

        String hostId = io.readString("Select a host ID: ");
        Host host = hosts.stream()
                .filter(i -> i.getId() == hostId)
                .findFirst()
                .orElse(null);

        if (host == null) {
            displayStatus(false, String.format("No host with ID %s found.", hostId));
        }

        return host;
    }//chooseHost

    public Reservation makeReservation(Guest guest, Host host) {
        Reservation reservation = new Reservation();
        reservation.setHost(host);
        reservation.setId(reservation.getId());
        reservation.setStartDateOfStay(io.readLocalDate("Start date [MM/dd/yyyy]: "));
        reservation.setEndDateOfStay(io.readLocalDate("End date [MM/dd/yyyy]: "));
        reservation.setGuest(guest);
        return reservation;
    }//makeReservation

    public Reservation putTogetherReservationForUpdating(Guest guest, Host host){
        Reservation update = new Reservation();
        update.setGuest(guest);
        update.setHost(host);
        update.setId(io.readInt("Choose a reservation ID to update: "));
        update.setStartDateOfStay(io.readLocalDate("Start date [MM/dd/yyyy]: "));
        update.setEndDateOfStay(io.readLocalDate("End date [MM/dd/yyyy]: "));
        return update;
    }//putTogetherReservationForUpdating

    public Reservation putTogetherReservationForDeletion(Guest guest, Host host){
        Reservation toDelete = new Reservation();
        toDelete.setGuest(guest);
        toDelete.setHost(host);
        toDelete.setId(io.readInt("Choose a reservation ID to delete: "));
        return toDelete;
    }//putTogetherReservationForDeletion

    public void showReservationsByHost(){
        displayHeader(MainMenuOption.VIEW_RESERVATIONS_BY_HOST.getMessage());
    }//showReservationsByHost

    public void makeReservation(){
        displayHeader(MainMenuOption.ADD_A_RESERVATION.getMessage());
    }//makeReservation

    public void enterToContinue() {
        io.readString("Press [Enter] to continue.");
    }//enterToContinue
    public void moveForward(BigDecimal amount){
        System.out.println(amount);
        io.readString("Are you ok with this amount? Yes or no? Type it here: ");
    }//moveForward

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
        if (reservations == null || reservations.isEmpty()) {
            io.println("No reservations found.");
            return;
        }
        for (Reservation reservation : reservations) {
            io.printf("%s: %s to %s, Guest ID# %s, $%s%n",
                    reservation.getId(),
                    reservation.getStartDateOfStay(),
                    reservation.getEndDateOfStay(),
                    reservation.getGuest().getId(),
                    reservation.getTotalCost());
        }
    }//displayReservations

    public void displayFutureReservations(List<Reservation> reservations) {
        if (reservations == null || reservations.isEmpty()) {
            io.println("No reservations found.");
            return;
        }
        for (Reservation reservation : reservations) {
            if(reservation.getStartDateOfStay().isAfter(LocalDate.now())){
                io.printf("%s: %s to %s, Guest ID# %s, $%s%n",
                        reservation.getId(),
                        reservation.getStartDateOfStay(),
                        reservation.getEndDateOfStay(),
                        reservation.getGuest().getId(),
                        reservation.getTotalCost());
            }
        }
    }//displayFutureReservations

    public void displayGuests(List<Guest> guests) {

        if (guests.size() == 0) {
            io.println("No guests found");
        }

        for (Guest guest : guests) {
            io.printf("%s: %s, %s, %s $/kg%n",
                    guest.getId(),
                    guest.getLastNameOfGuest(),
                    guest.getEmailOfGuest(),
                    guest.getStateOfGuest());
        }
    }//displayGuests

    public void displayHosts(List<Host> hosts) {

        if (hosts.size() == 0) {
            io.println("No hosts found");
        }

        for (Host host : hosts) {
            io.printf("%s: %s, %s, %s, %s, %s %n",
                    host.getId(),
                    host.getLastNameOfHost(),
                    host.getEmailOfHost(),
                    host.getStandardRateOfHost(),
                    host.getWeekendRateOfHost());
        }
    }//displayGuests


}//end