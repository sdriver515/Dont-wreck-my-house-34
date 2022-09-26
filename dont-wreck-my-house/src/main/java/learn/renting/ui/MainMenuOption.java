package learn.renting.ui;

    public enum MainMenuOption {

        EXIT(0, "Exit", false),
        VIEW_RESERVATIONS_BY_HOST(1,"View Reservations By Host", false),
        ADD_A_RESERVATION(2, "Add a Reservation", false),
        EDIT_A_RESERVATION(3, "Edit a Reservation", false),
        CANCEL_A_RESERVATION(4, "Cancel a Reservation", false);

        private int value;
        private String message;
        private boolean hidden;

        private MainMenuOption(int value, String message, boolean hidden) {
            this.value = value;
            this.message = message;
            this.hidden = hidden;
        }

        public static MainMenuOption fromValue(int value) {
            for (MainMenuOption option : MainMenuOption.values()) {
                if (option.getValue() == value) {
                    return option;
                }
            }
            return EXIT;
        }

        public int getValue() {
            return value;
        }

        public String getMessage() {
            return message;
        }

        public boolean isHidden() {
            return hidden;
        }
    }//end