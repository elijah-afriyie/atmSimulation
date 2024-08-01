package myPackage;

import java.util.Random;

public class User {
	// Private fields
	private String firstName;
	private String lastName;
	private String email;
	private String pinStr;
	private String accountNumber;
	private Float balance;

	// Constructor
	public User(String firstName, String lastName, String email, String pinStr, Float balance) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.pinStr = pinStr;
		this.balance = balance;
	}

	/* =====--- Getters and setters ---===== */
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPinStr() {
		return pinStr;
	}

	public void setPinStr(String pinStr) {
		while (true) {
			if (pinStr != null && pinStr.matches("\\d+")) {
				this.pinStr = pinStr;
				System.out.println("PIN successfully changed");
			} else {
				System.out.println("PIN characters must be numeric!");
			}
			break;
		}
	}

	// Deposit method
	public Float deposit(Float amount) {
		if (amount > 0) {
			balance += amount;
		} else {
			System.out.println("You entered an invalid amount! Please try again later.");
		}
		return balance;
	}

	// Withdraw method
	public Float withdraw(Float amount) {
		if (amount > 0){
			if (amount <= balance) {
				balance -= amount;
			} else {
				System.out.println("You don't have enough money to complete the transaction! Please try again later.");
			}
		} else {
			System.out.println("You entered an invalid amount! Please try again later.");
		}
		return balance;
	}

	// Check account method
	public Float getBalance() {
		return balance;
	}

	// Generate account number (12 digits, starting with two alphabets)
	public String generateAccountNumber() {
		String prefix = "AA";
		Random random = new Random();
		StringBuilder sb = new StringBuilder(prefix);

		for (int i = 0; i < 10; i++) {
			int digit = random.nextInt(10);
			sb.append(digit);
		}
			this.accountNumber = sb.toString();
			return accountNumber;
	}

	// Retrieve account number
	public String getAccountNumber() {
		return generateAccountNumber();
	}
}
