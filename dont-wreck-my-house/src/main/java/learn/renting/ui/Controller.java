package learn.renting.ui;

import learn.renting.data.DataException;
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


//    public void run() {
//        view.displayHeader("Welcome to Don't Wreck My House!");
//        try {
//            runAppLoop();
//        } catch (DataException ex) {
//            view.displayException(ex);
//        }
//        view.displayHeader("Goodbye.");
//    }

//    private void runAppLoop() throws DataException {
//        MainMenuOption option;
//        do {
//            option = view.selectMainMenuOption();
//            switch (option) {
//                case VIEW_RESERVATIONS_BY_HOST:
//                    break;
//                case MAKE_A_RESERVATION:
//                    break;
//                case EDIT_A_RESERVATION:
//                    break;
//                case CANCEL_A_RESERVATION:
//                    break;
//            }
//        } while (option != MainMenuOption.EXIT);
//    }

    // top level menu
//    private void viewByDate() {
//        LocalDate date = view.getForageDate();
//        List<Forage> forages = forageService.findByDateTwo(date);
//        view.displayForages(forages);
//        view.enterToContinue();
//    }//viewByDate
//
//    private void showCategoryValues(){
//        view.showCategoryValues();
//        LocalDate date = view.getForageDate();
//        Map<Integer, BigDecimal> forages = forageService.findByCategoryValues(date);
//        view.displayMapOfCategories(forages);
//        view.enterToContinue();
//    }//showCategoryValues
//
//    private void showItemKilogramPerDayValues(){
//        view.showKilogramPerDay();
//        LocalDate date = view.getForageDate();
//        Map<Integer, BigDecimal> forages = forageService.findKilogramsOfItemsPerDay(date);
//        view.displayMapOfItems(forages);
//        view.enterToContinue();
//    }//showCategoryValues
//
//    private void viewByState() {//added by me
//        String state = view.getForagerState();
//        List<Forager> foragers = foragerService.findByState(state);
//        view.displayForagers(foragers);
//        view.enterToContinue();
//    }//viewByState
//
//    private void viewItems() {
//        view.displayHeader(MainMenuOption.VIEW_ITEMS.getMessage());
//        Category category = view.getItemCategory();
//        List<Item> items = itemService.findByCategory(category);
//        view.displayHeader("Items");
//        view.displayItems(items);
//        view.enterToContinue();
//    }//viewItems
//
//    private void addForage() throws DataException {
//        view.displayHeader(MainMenuOption.ADD_FORAGE.getMessage());
//        Forager forager = getForager();
//        if (forager == null) {
//            return;
//        }
//        Item item = getItem();
//        if (item == null) {
//            return;
//        }
//        Forage forage = view.makeForage(forager, item);
//        Result<Forage> result = forageService.add(forage);
//        if (!result.isSuccess()) {
//            view.displayStatus(false, result.getErrorMessages());
//        } else {
//            String successMessage = String.format("Forage %s created.", result.getPayload().getId());
//            view.displayStatus(true, successMessage);
//        }
//    }//addForage
//
//    private void addItem() throws DataException {
//        Item item = view.makeItem();
//        Result<Item> result = itemService.add(item);
//        if (!result.isSuccess()) {
//            view.displayStatus(false, result.getErrorMessages());
//        } else {
//            String successMessage = String.format("Item %s created.", result.getPayload().getId());
//            view.displayStatus(true, successMessage);
//        }
//    }//addItem
//
//    private void addForager() throws DataException {//I added
//        Forager forager = view.makeForager();
//        Result<Forager> result = foragerService.add(forager);
//        if (!result.isSuccess()) {
//            view.displayStatus(false, result.getErrorMessages());
//        } else {
//            String successMessage = String.format("Item %s created.", result.getPayload().getId());
//            view.displayStatus(true, successMessage);
//        }
//    }//addForager
//
//    private void generate() throws DataException {
//        GenerateRequest request = view.getGenerateRequest();
//        if (request != null) {
//            int count = forageService.generate(request.getStart(), request.getEnd(), request.getCount());
//            view.displayStatus(true, String.format("%s forages generated.", count));
//        }
//    }//generate
//
//    // support methods
//    private Forager getForager() {
//        String lastNamePrefix = view.getForagerNamePrefix();
//        List<Forager> foragers = foragerService.findByLastName(lastNamePrefix);
//        return view.chooseForager(foragers);
//    }//getForager
//
//    private Item getItem() {
//        Category category = view.getItemCategory();
//        List<Item> items = itemService.findByCategory(category);
//        return view.chooseItem(items);
//    }//getItem

}//end
