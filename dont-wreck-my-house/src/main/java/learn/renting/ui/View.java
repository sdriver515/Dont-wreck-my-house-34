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
        displayHeader(MainMenuOption.MAKE_A_RESERVATION.getMessage());
        return io.readString("Select a Host Email: ");
    }//getHostEmail

    public String getGuestEmail() {//
        displayHeader(MainMenuOption.MAKE_A_RESERVATION.getMessage());
        return io.readString("Select a Guest Email: ");
    }//getGuestEmail

    public Host chooseHost(List<Host> hosts) {
        if (hosts.size() == 0) {
            io.println("No hosts found");
            return null;
        }//chooseHost

        int index = 1;
        for (Host host : hosts.stream().limit(25).collect(Collectors.toList())) {
            io.printf("%s: %s %n", index++, host.getLastNameOfHost());
        }
        index--;

        if (hosts.size() > 25) {
            io.println("More than 25 hosts found. Showing first 25. Please refine your search.");
        }
        io.println("0: Exit");
        String message = String.format("Select a host by their index [0-%s]: ", index);

        index = io.readInt(message, 0, index);
        if (index <= 0) {
            return null;
        }
        return hosts.get(index - 1);
    }//chooseHost

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
    }

    public Forage makeForage(Forager forager, Item item) {
        Forage forage = new Forage();
        forage.setForager(forager);
        forage.setItem(item);
        forage.setDate(io.readLocalDate("Forage date [MM/dd/yyyy]: "));
        String message = String.format("Kilograms of %s: ", item.getName());
        forage.setKilograms(io.readDouble(message, 0.001, 250.0));
        return forage;
    }//makeForage

    public Item makeItem() {
        displayHeader(MainMenuOption.ADD_ITEM.getMessage());
        Item item = new Item();
        item.setCategory(getItemCategory());
        item.setName(io.readRequiredString("Item Name: "));
        item.setDollarPerKilogram(io.readBigDecimal("$/Kg: ", BigDecimal.ZERO, new BigDecimal("7500.00")));
        return item;
    }//makeItem

    public Forager makeForager() {
        displayHeader(MainMenuOption.ADD_FORAGER.getMessage());
        Forager forager = new Forager();
        forager.setFirstName(io.readRequiredString("Forager's first name: "));
        forager.setLastName(io.readRequiredString("Forager's last name: "));
        forager.setState(io.readRequiredString("Forager's state: "));
        return forager;
    }//makeForager

    public void showCategoryValues(){//I added
        displayHeader(MainMenuOption.REPORT_CATEGORY_VALUE.getMessage());
    }//showCategoryValues

    public void showKilogramPerDay(){//I added
        displayHeader(MainMenuOption.REPORT_KG_PER_ITEM.getMessage());
    }//showCategoryValues

    public GenerateRequest getGenerateRequest() {
        displayHeader(MainMenuOption.GENERATE.getMessage());
        LocalDate start = io.readLocalDate("Select a start date [MM/dd/yyyy]: ");
        if (start.isAfter(LocalDate.now())) {
            displayStatus(false, "Start date must be in the past.");
            return null;
        }

        LocalDate end = io.readLocalDate("Select an end date [MM/dd/yyyy]: ");
        if (end.isAfter(LocalDate.now()) || end.isBefore(start)) {
            displayStatus(false, "End date must be in the past and after the start date.");
            return null;
        }

        GenerateRequest request = new GenerateRequest();
        request.setStart(start);
        request.setEnd(end);
        request.setCount(io.readInt("Generate how many random forages [1-500]?: ", 1, 500));
        return request;
    }

    public void enterToContinue() {
        io.readString("Press [Enter] to continue.");
    }

    // display only
    public void displayHeader(String message) {
        io.println("");
        io.println(message);
        io.println("=".repeat(message.length()));
    }

    public void displayException(Exception ex) {
        displayHeader("A critical error occurred:");
        io.println(ex.getMessage());
    }

    public void displayStatus(boolean success, String message) {
        displayStatus(success, List.of(message));
    }

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

    public void displayMapOfCategories(Map<Integer, BigDecimal> map) {
        for (Map.Entry<Integer, BigDecimal> entry : map.entrySet()) {
            System.out.println(entry.getKey() + ":$" + entry.getValue().toString());
        }
    }//displayMapOfCategories

    public void displayOccupiedDatesOfHost(Map<LocalDate, LocalDate> map) {
//        List<Reservation> all = reservationRepository.findByHost(reservation.getHost());
//        Map<LocalDate, LocalDate> mapWithTimes = new HashMap<>();
//        for(Reservation r : all){
//            mapWithTimes.put(r.getStartDateOfStay(),
//                    r.getEndDateOfStay());
//        }
//        return mapWithTimes;
    }//displayOccupiedDatesOfHost

    public void displayMapOfItems(Map<Integer, BigDecimal> map) {
        for (Map.Entry<Integer, BigDecimal> entry : map.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue().toString());
        }
    }//displayMapOfItems

}//end