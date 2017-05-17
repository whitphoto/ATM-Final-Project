
package atmcasestudy;


// Deposit.java
// Represents a deposit ATM transaction
public class Deposit extends Transaction {

    private double amount; // amount to deposit
    private ATM keypad; // reference to keypad
    private DepositSlot depositSlot; // reference to deposit slot
    private final static int CANCELED = 0; // constant for cancel option

    // Deposit constructor
    public Deposit(int userAccountNumber, ATM atmScreen,
            BankDatabase atmBankDatabase, ATM atmKeypad,
            DepositSlot atmDepositSlot) {
        // initialize superclass variables
        super(userAccountNumber, atmScreen, atmBankDatabase);

        // initialize references to keypad and deposit slot
        keypad = atmKeypad;
        depositSlot = atmDepositSlot;
        promptForDepositAmount(); // get deposit amount from user
    } // end Deposit constructor

    // perform transaction
    @Override
    public void execute() {
        BankDatabase bankDatabase = getBankDatabase(); // Getter Method
        ATM screen = getScreen(); // Getter Method

        amount = 0;
        try {
            amount = Integer.parseInt(keypad.getNumber()); // input account number
        } catch (Exception e) {
        }

        // check whether the user canceled or entered a valid amount
      if ( amount != CANCELED ) 
         amount = ( double ) amount / 100; // return dollar amount 
      
      
        // check whether user entered a deposit amount or canceled
        if (amount != CANCELED) {
            // request deposit envelope containing specified amount
            screen.displayMessage(
                    "\nPlease insert a deposit envelope containing ");
            screen.displayDollarAmount(amount);
            screen.displayMessageNewLine(".");

            // receive deposit envelope
            boolean envelopeReceived = depositSlot.isEnvelopeReceived();

            // check whether deposit envelope was received
            if (envelopeReceived) {
                screen.displayMessageNewLine("\nYour envelope has been "
                        + "received.\nNOTE: The money just deposited will not "
                        + "\nbe available until we verify the amount of any "
                        + "\nenclosed cash and your checks clear.");

                // credit account to reflect the deposit
                bankDatabase.credit(getAccountNumber(), amount);
            } // end if
            else // deposit envelope not received
            {
                screen.displayMessageNewLine("\nYou did not insert an "
                        + "envelope, so the ATM has canceled your transaction.");
            } // end else
        } // end if 
        else // user canceled instead of entering amount
        {
            screen.displayMessageNewLine("\nCanceling transaction...");
        } // end else
    } // end method execute

    // prompt user to enter a deposit amount in cents 
    private void promptForDepositAmount() {
        ATM screen = getScreen(); // Getter Method for screen
        screen.setNumber("");
        screen.displayMessage("\nPlease enter a deposit amount in "
                + "CENTS (or 0 to cancel): ");
    } // end method promptForDepositAmount
} // end class Deposit
