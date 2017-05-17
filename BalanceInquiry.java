
package atmcasestudy;


// BalanceInquiry.java
// Represents a balance inquiry ATM transaction

public class BalanceInquiry extends Transaction
{
   // BalanceInquiry constructor
   public BalanceInquiry( int userAccountNumber, ATM atmScreen, BankDatabase atmBankDatabase )
        {
           super( userAccountNumber, atmScreen, atmBankDatabase );
        } // end BalanceInquiry constructor

   // performs the transaction
   @Override
   public void execute()
        {
           // Getter Methods to bank database and screen
           BankDatabase bankDatabase = getBankDatabase();
           ATM screen = getScreen();

           // get the available balance for the account involved
           double availableBalance = bankDatabase.getAvailableBalance( getAccountNumber() );

           // get the total balance for the account involved
           double totalBalance = bankDatabase.getTotalBalance( getAccountNumber() );

           // display the balance information on the screen
           screen.displayMessageNewLine( "\nBalance Information:" );
           screen.displayMessage( " - Available balance: " ); 
           screen.displayDollarAmount( availableBalance );
           screen.displayMessage( "\n - Total balance:     " );
           screen.displayDollarAmount( totalBalance );
           screen.displayMessageNewLine( "" );
        } // end method execute
} // end class BalanceInquiry



