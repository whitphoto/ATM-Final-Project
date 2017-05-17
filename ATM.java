/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package atmcasestudy;

// ATM.java
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

// Represents an automated teller machine
public class ATM extends JFrame {

    private boolean userAuthenticated; // whether user is authenticated
    private int currentAccountNumber; // current user's account number
    private CashDispenser cashDispenser; // ATM's cash dispenser
    private DepositSlot depositSlot; // ATM's deposit slot
    private BankDatabase bankDatabase; // account information database
    // constants corresponding to main menu options
    private static final int BALANCE_INQUIRY = 1;
    private static final int WITHDRAWAL = 2;
    private static final int DEPOSIT = 3;
    private static final int EXIT = 4;

    // no-argument ATM constructor initializes instance variables
    // start ATM 
    public void run() {

        textArea.setEditable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
        // welcome and authenticate user; perform transactions
      
        displayMessageNewLine("\nWelcome!");
        displayMessage("\nPlease enter your account number: ");
        menuChoice = 1;
        setNumber("");
              
        userAuthenticated = false; // resets ATM
        currentAccountNumber = 0; // resets ATM
       
    } // end method run
    int menuChoice = 0;
    // attempts to authenticate user against database
    // intinalizes variables with default values of zero
    int accountNumber = 0;
    int pin = 0;
    int choice = 0;
    boolean userExited = false; // user has not chosen to exit
    Transaction temp = null; // temporary Transaction variable
    Transaction currentTransaction = null;

    private void perform() {
        switch (menuChoice) {
            case 1:
                try 
                    {
                        accountNumber = Integer.parseInt(getNumber()); // input account number
                    } catch (NumberFormatException e)  //This ensures that the user only inputs numbers
                    {} 
                   menuChoice = 2;
                displayMessage("\nEnter your PIN: "); // prompt for PIN
                setNumber("");
                break;
            case 2:
                try 
                    {
                        pin = Integer.parseInt(getNumber()); // input account number
                    } 
                catch (NumberFormatException e)  //This ensures that the user only inputs numbers
                    {
                    }   
                userAuthenticated = bankDatabase.authenticateUser(accountNumber, pin);
                // check whether authentication succeeded
                if (userAuthenticated) {currentAccountNumber = accountNumber; // save user's account #
                    menuChoice = 3;
                    textArea.setText("");
                    displayMainMenu();
                    // user is now authenticated
                } // end if
                else {
                    this.displayMessageNewLine("\nInvalid account number or PIN. Please try again.");
                    displayMessage("\nPlease enter your account number: ");
                    
                    menuChoice = 1;
                    
                }   setNumber("");
                break;
            case 3:
                textArea.setText("");
                System.out.println(getNumber());
                try {
                    choice = Integer.parseInt(getNumber()); // input account number
                    setNumber("");
                } catch (NumberFormatException e)  //This ensures that the user only inputs numbers
                {
                    setNumber("");
                }
                if (choice < 1 || choice > 4) 
                    {
                        textArea.setText("");
                        this.displayMessageNewLine(
                                "\nYou did not enter a valid selection. Try again.");
                        menuChoice = 3;

                        displayMainMenu();
                    }
                else if (choice == BALANCE_INQUIRY) 
                    {
                        // initialize as new object of chosen type
                        currentTransaction = createTransaction(choice);

                        currentTransaction.execute(); // execute transaction
                        //textArea.setText("");
                        displayMainMenu();
                        menuChoice = 3;
                    } 
                else if (choice == WITHDRAWAL) 
                    {
                        setNumber("");
                        currentTransaction =
                                createTransaction(choice);
                        menuChoice = 4;
                    } 
                else if (choice == DEPOSIT) 
                    {
                        currentTransaction =
                                createTransaction(choice);
                        menuChoice = 5;
                    } 
                else if (choice == EXIT) 
                    {
                        textArea.setText("");
                        displayMessage("\nPlease enter your account number: ");
                        menuChoice = 1;
                    }
                return;
            case 4:
            case 5:
                currentTransaction.execute(); // execute transaction
                menuChoice = 3;
                setNumber("");
                displayMainMenu();
                break;
            default:
                break;
        }//end Switch






    } // end method authenticateUser

    // display the main menu and return an input selection
    private void displayMainMenu() 
        {
            this.displayMessageNewLine("\nMain menu:");
            this.displayMessageNewLine("1 - View my balance");
            this.displayMessageNewLine("2 - Withdraw cash");
            this.displayMessageNewLine("3 - Deposit funds");
            this.displayMessageNewLine("4 - Exit\n");
            this.displayMessage("Enter a choice: ");
        } // end method displayMainMenu

    // return object of specified Transaction subclass
    private Transaction createTransaction(int type) 
        {


            // determine which type of Transaction to create     
            switch (type) 
                {
                    case BALANCE_INQUIRY: // create new BalanceInquiry transaction
                        temp = new BalanceInquiry(currentAccountNumber, this, bankDatabase);
                        break;
                    case WITHDRAWAL: // create new Withdrawal transaction
                        temp = new Withdrawal(currentAccountNumber, this, bankDatabase, this, cashDispenser);
                        break;
                    case DEPOSIT: // create new Deposit transaction
                        temp = new Deposit(currentAccountNumber, this, bankDatabase, this, depositSlot);
                        break;
                } // end switch

        return temp; // return the newly created object
    } // end method createTransaction

    private JTextArea textArea;
    JButton enterButton = new JButton("Enter");


    public ATM() {
        userAuthenticated = false; // user is not authenticated to start
        currentAccountNumber = 0; // no current account number to start


        cashDispenser = new CashDispenser(); // create cash dispenser
        depositSlot = new DepositSlot(); // create deposit slot
        bankDatabase = new BankDatabase(); // create acct info database
        textArea = new JTextArea(20, 10);
        
        ActionListener numberListener = new NumListener();
        String buttonOrder = "1234567890 ";// the space leaves an empty spot 
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 4));
        
        //the following loop populates the grid, it checks for the space first, as 
        //that is the only one that needs an exception. For the space it puts an empty 
        //button, otherwise it adds a button, labels it and attaches a listener
        for (int i = 0; i < buttonOrder.length(); i++) 
            {
                String key = buttonOrder.substring(i, i + 1); 
                //reads the character at the substring position and then increments 
                //the substring position for the next read through
                if (key.equals(" ")) 
                    {
                        buttonPanel.add(new JLabel(""));
                    } 
                else 
                    {
                        JButton button = new JButton(key);
                        button.addActionListener(numberListener);
                        buttonPanel.add(button);
                    }
            }
        buttonPanel.add(enterButton);//adds Enter button after numbers, under 9 due to the blank space
        //Action Listener for Enter button
        CmdListener commandListener = new CmdListener() {};
        enterButton.addActionListener(commandListener);
        
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1));
        panel.setPreferredSize(new Dimension(150, 300));
        JPanel pan = new JPanel();
        pan.setLayout(new BorderLayout(4, 4));
        pan.add(new JScrollPane(textArea), BorderLayout.NORTH);
        pan.add(buttonPanel, BorderLayout.CENTER);
        pan.add(panel, BorderLayout.EAST);
        this.setContentPane(pan);
        this.setTitle("ATM Machine");
        //sets up the applet pannel, declaring sizes, positions and adding a title to the window bar
  
    }
    private String number = "";

    public String getNumber() 
        {
            return number;
        }

    public void setNumber(String number) 
        {
            this.number = number;
        }

    abstract class CmdListener implements ActionListener 
        {

            public void actionPerformed(ActionEvent event) 
            {
                if (event.getSource() == enterButton) 
                {
                    //runs perform() when the enter button is pressed in the ATM
                    perform();
                }
            }
        }

    class NumListener implements ActionListener 
    {
        //when numbered buttons are pushed this captures the number and adds 
        //adds the digit to number, then displays it in the text area.
        public void actionPerformed(ActionEvent event) 
        {
           
            String digit = event.getActionCommand();
            number += digit;
            textArea.setText(textArea.getText() + digit);
        }
    }

    // displays a message 
    public void displayMessage(String message) 
        {
            textArea.setText(textArea.getText() + message);

        } // end method displayMessage

    // displays a message followed by a new line into the text area
    public void displayMessageNewLine(String message) 
        {
            textArea.setText(textArea.getText() + message + "\n");
        } // end method displayMessageNewLine

    // display a dollar amount
    public void displayDollarAmount(double amount) 
        {
            textArea.setText(textArea.getText() + String.format("$%,.2f", amount));
        } // end method displayDollarAmount 
} // end class ATM