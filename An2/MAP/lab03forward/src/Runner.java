package socialnetwork;

import socialnetwork.ui.UI;

/**
 * Runner class models a Runner
 */
public class Runner {
    /**
     * ui member
     */
    UI ui;

    /**
     * Constructor for Runner
     */
    public Runner() {
        ui = new UI();
    }

    /**
     * run method - main method
     */
    public void run() {
        ui.start();
    }
}
