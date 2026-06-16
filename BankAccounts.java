import java.text.NumberFormat;
import java.util.*;
class BankAccount{
  protected String ownerName;
  protected String accountType;
  protected int ownerId;
  private float accountBalance;
  private int accountNumber;
  public BankAccount(String type,String owner,int id,float balance,int number){
    this.accountType=type;
    this.ownerName=owner;
    this.ownerId=id;
    this.accountBalance=balance;
    this.accountNumber=number;
  }
  public String getOwner(){
    return ownerName;
  }
  public int getOwnerId(){
    return ownerId;
  }
  public float getBalance(){
    return accountBalance;
  }
  public int getAccountNumber(){
    return accountNumber;
  }
  public String getAccountType(){
    return accountType;
  }
  public void display(){
    System.out.println("Owner's name: "+getOwner());
   System.out.println("Account number: "+getAccountNumber());
   System.out.println("Account type: "+getAccountType());

  }
  public float deposit(float amount){
    System.out.println("You have deposited "+amount+"shillings");
    accountBalance+=amount;
    return accountBalance;
  }
  public float withdraw(float amount){
    if (amount<=accountBalance){
    System.out.println("You have withdrawn "+NumberFormat.getCurrencyInstance().format(amount)+" shillings");
    accountBalance-=amount;
    }
    else{
      System.out.println("Insufficient funds!");
    }
    return accountBalance;
  }
}
class SavingAccount extends BankAccount{
  public float targetAmount;
  public float interestRates;
  public int period;
  public SavingAccount(float target,float rate,int time,String type,String owner,int id,float balance,int number){
    super(type,owner,id,balance,number);
    this.targetAmount=target;
    this.interestRates=rate;
    this.period=time;
  }
  public float calculateInterest(){
    float currentBalance=getBalance();
    float interest=currentBalance*interestRates/100*period;
    float newBalance=interest+currentBalance;
    float amountOffTarget=targetAmount-newBalance;
    System.out.println("Hello "+getOwner());
    System.out.println("Your interest for this year is "+NumberFormat.getCurrencyInstance().format(interest)+" shillings");
    System.out.println("Your target is "+NumberFormat.getCurrencyInstance().format(targetAmount)+" shillings. Your balance is "+NumberFormat.getCurrencyInstance().format(newBalance)+" shillings.");
    return interest;
  }
}
class LoansAccount extends BankAccount {
    public boolean isInCrb = false;
    private float loanAmount = 0.0f; // Tracks how much is actually owed

    public LoansAccount(String type, String owner, int id, float balance, int number) {
        super(type, owner, id, balance, number);
    }

    // 1. Updated Disbursement: Now tracks the debt
    public void disburseLoan(float amount) {
        if (!isInCrb && amount <= getBalance()) {
            this.loanAmount = amount; // Set the debt
            deposit(amount);          // Give them the cash
            this.isInCrb = true;      // Mark as indebted
            System.out.println("Loan amount of "+NumberFormat.getCurrencyInstance().format(loanAmount)+" disbursed. You now owe: " + NumberFormat.getCurrencyInstance().format(loanAmount));
        } else {
            System.out.println("Eligibility failed.");
        }
    }
    public void repayLoan(float payment) {float extraPayment=0;
      if (loanAmount>0){
        if (payment <= 0) {
            System.out.println("Payment must be positive.");
            return;
        }
        if (payment > loanAmount) {
            extraPayment=payment-loanAmount;
            System.out.println("Note: Payment exceeds debt. Clearing full loan.");
            loanAmount=payment; 
            deposit(extraPayment);
        }
        if (getBalance() >= payment) {
            loanAmount -= payment;
            System.out.println("Repayment successful. Remaining debt: " + loanAmount);

            if (loanAmount <= 0) {
                isInCrb = false;
                System.out.println("Congratulations! Your loan is fully settled.");
            }
        } else {
            System.out.println("Insufficient account balance to make this repayment.");
        }
    }
  }
}

public class Inheritance{
  public static void main(String[] args){
    /*SavingAccount account1 =new SavingAccount(1000000.0f,1.0f,5,"Savings","Timothy",1234,20000.0f,114789);
    account1.display();
    account1.withdraw(3000);
    account1.calculateInterest();*/
  LoansAccount account2=new LoansAccount("Loan","Timothy",1234,20000.0f,114789);
  account2.display();
  account2.disburseLoan(20000.0f);
  account2.repayLoan(10000.0f);
  }
}