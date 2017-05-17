
package atmcasestudy;


// Withdrawal.java
// Represents a withdrawal ATM transaction
public class Withdrawal extends Transaction {

    private int amount; // amount to withdraw
    private ATM keypad; // reference to keypad
    private CashDispenser cashDispenser; // reference to cash dispenser
    // constant corresponding to menu option to cancel
    private final static int CANCELED = 6;

    // Withdrawal constructor
    public Withdrawal(int userAccountNumber, ATM atmScreen,
            BankDatabase atmBankDatabase, ATM atmKeypad,
            CashDispenser atmCashDispenser) {
        // initialize superclass variables
        super(userAccountNumber, atmScreen, atmBankDatabase);

        // initialize references to keypad and cash dispenser
        keypad = atmKeypad;
        cashDispenser = atmCashDispenser;
        displayMenuOfAmounts();
    } // end Withdrawal constructor

    // perform transaction
    @Override
    public void execute() {
        boolean cashDispensed = false; // cash was not dispensed yet
        double availableBalance; // amount available for withdrawal
        int[] amounts = { 0, 20, 40, 60, 100, 200 };
        // Getter Methods to bank database and screen
        BankDatabase bankDatabase = getBankDatabase();
        ATM screen = getScreen();

         
         
            // obtain a chosen withdrawal amount from the user 
            amount = 0;
            try {
                amount = Integer.parseInt(keypad.getNumber()); // input account number
            } catch (Exception e) {
            }
             
            // determine how to proceed based on the input value
            switch (amount) {
                case 1: // if the user chose a withdrawal amount 
                case 2: // (i.e., chose option 1, 2, 3, 4 or 5), return the
                case 3: // corresponding amount from amounts array
                case 4:
                case 5:
                    amount = amounts[ amount]; // save user's choice
                    break;
                case CANCELED: // the user chose to cancel
                    amount = CANCELED; // save user's choice
                    break;
                default: // the user did not enter a value from 1-6
                    screen.displayMessageNewLine(
                            "\nInvalid selection. Try again.");
            } // end switch
            
            // check whether user chose a withdrawal amount or canceled
            if (amount != CANCELED) {
                // get available balance of account involved
                availableBalance =
                        bankDatabase.getAvailableBalance(getAccountNumber());

                // check whether the user has enough money in the account 
                if (amount <= availableBalance) {
                    // check whether the cash dispenser has enough money
                    if (cashDispenser.isSufficientCashAvailable(amount)) {
                        // update the account involved to reflect the withdrawal
                        bankDatabase.debit(getAccountNumber(), amount);

                        cashDispenser.dispenseCash(amount); // dispense cash
                        cashDispensed = true; // cash was dispensed

                        // instruct user to take cash
                        screen.displayMessageNewLine("\nYour cash has been"
                                + " dispensed. Please take your cash now.");
                    } // end if
                    else // cash dispenser does not have enough cash
                    {
                        screen.displayMessageNewLine(
                                "\nInsufficient cash available in the ATM."
                                + "\n\nPlease choose a smaller amount.");
                    }
                } // end if
                else // not enough money available in user's account
                {
                    screen.displayMessageNewLine(
                            "\nInsufficient funds in your account."
                            + "\n\nPlease choose a smaller amount.");
                } // end else
            } // end if
            else // user chose cancel menu option 
            {
                screen.displayMessageNewLine("\nCanceling transaction...");
                return; // return to main menu because user canceled
            } // end else
         

    } // end method execute

    // display a menu of withdrawal amounts and the option to cancel;
    // return the chosen amount or 0 if the user chooses to cancel
    private void displayMenuOfAmounts() {
        ATM screen = getScreen(); // get screen reference
            // display the menu
            screen.displayMessageNewLine("\nWithdrawal Menu:");
            screen.displayMessageNewLine("1 - $20");
            screen.displayMessageNewLine("2 - $40");
            screen.displayMessageNewLine("3 - $60");
            screen.displayMessageNewLine("4 - $100");
            screen.displayMessageNewLine("5 - $200");
            screen.displayMessageNewLine("6 - Cancel transaction");

    } // end method displayMenuOfAmounts
} // end class Withdrawal