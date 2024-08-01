package myPackage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

  public static void main(String[] args) throws IOException {
    User user = null;

    // Condition to break out of loop
    boolean loopActive = true;
    while (loopActive) {
      if (user == null) {
        try {
          user = createAccountOrSignIn();
        } catch (Exception e) {
          System.out.println("Error creating your account! Please try again.");
        }
        continue;
      }

      // Call displayOptions
      displayOptions(user);

      /* ===== --- IMPLEMENTATION OF DISPLAY OPTIONS --- ===== */
      BufferedReader reader = new BufferedReader(
        new InputStreamReader(System.in)
      );
      String response = reader.readLine();

      switch (response) {
        case "1":
          boolean depositSuccessful = false;
          while (!depositSuccessful) {
            System.out.println("How much would you like to deposit?");
            String amountStr = reader.readLine();
            Float amount = isValidFloat(amountStr);

            if (amount != null) {
              user.deposit(amount);
              displayAccountBalance(user);
              depositSuccessful = true;
            } else {
              System.out.println(
                "You entered an invalid amount. Please try again."
              );
            }
          }
          break;
        case "2":
          boolean withdrawalSuccessful = false;
          while (!withdrawalSuccessful) {
            System.out.println("How much would you like to withdraw?");
            String amountStr = reader.readLine();
            Float amount = isValidFloat(amountStr);

            if (amount != null) {
              user.withdraw(amount);
              displayAccountBalance(user);
              withdrawalSuccessful = true;
            } else {
              System.out.println(
                "You entered an invalid amount. Please try again."
              );
            }
          }
          break;
        case "3":
          displayAccountBalance(user);
          break;
        case "4":
          displayAccountNumber(user);
          break;
        case "5":
          // Accept current PIN for check
          System.out.println("Enter current PIN");
          String testPin = reader.readLine();

          // Check if PIN provided matches active PIN
          if (testPin.equals(user.getPinStr())) {
            System.out.println("Check successful");

            // Accept new PIN
            System.out.println("Enter new PIN");
            String newPin = reader.readLine();

            // Check if new PIN is an integer
            Integer validPin = isValidInteger(newPin);
            if (validPin != null) {
              String pinStr = validPin.toString(); // Convert back to string to be able to save in string variable in User
              user.setPinStr(pinStr);
            } else {
              System.out.println(
                "Invalid PIN. Enter 4-digit numbers to set PIN"
              );
            }
          } else {
            System.out.println(
              "The PIN you entered is invalid! Can't reset PIN."
            );
          }
          break;
        case "6":
          loopActive = false;
          break;
        default:
          System.out.println(
            "You selected an invalid option. Please try again."
          );
          break;
      }
    }
  }

  /*
	====================== -- ONBOARDING USER -- ======================
	*/
  private static User createAccountOrSignIn() throws IOException {
    System.out.println("Welcome to the Bank of Java");
    System.out.println("Let's get started and create an account for you");

    /* ===== --- ACCEPT USER CREDENTIALS --- ===== */
    BufferedReader reader = new BufferedReader(
      new InputStreamReader(System.in)
    );

    // First name
    System.out.println("What is your first name?");
    String firstName = "";

    while (true) {
      firstName = reader.readLine();
      if (firstName != null && !firstName.isEmpty()) {
        break;
      } else {
        System.out.println("This field can't be empty. Enter your first name");
      }
    }

    // Last name
    System.out.println("What is your last name?");
    String lastName = "";

    while (true) {
      lastName = reader.readLine();
      if (lastName != null && !lastName.isEmpty()) {
        break;
      } else {
        System.out.println("This field can't be empty. Enter your last name");
      }
    }

    // Email
    System.out.println("What is your email?");
    String email = "";

    while (true) {
      email = reader.readLine();
      if (email != null && !email.isEmpty()) {
        break;
      } else {
        System.out.println("This field can't be empty. Enter your email");
      }
    }

    // Set PIN
    String pinStr = null;
    boolean isValidPin = false;
    while (!isValidPin) {
      System.out.println("Enter 4-digit numbers to set PIN");
      pinStr = reader.readLine();

      // Check if all characters are digits
      if (pinStr.matches("\\d+")) {
        // Check if PIN is 4 digits
        if (pinStr.length() == 4) {
          Integer pin = isValidInteger(pinStr);

          // Check if pin if is null
          if (pin == null) {
            System.out.println("This field can't be blank");
          } else {
            isValidPin = true;
          }
        } else {
          System.out.println("PIN must be 4 digits. Please try again.");
        }
      } else {
        System.out.println(
          "You entered a non-digit character. Please enter valid characters."
        );
      }
    }

    /* ===== --- CREATE NEW USER and pass in neccessary arguments accepted previously --- ===== */
    User user = new User(firstName, lastName, email, pinStr, 0f);

    /* ===== --- ACCEPT INITIAL DEPOSIT --- ===== */
    System.out.println(
      "Would you like to make an initial deposit? Type yes if you want to"
    );
    String response = reader.readLine();

    if (response.equalsIgnoreCase("yes")) {
      System.out.println("Great! How much would you deposit?");
      String amountStr = reader.readLine();

      Float amount = isValidFloat(amountStr);
      if (amountStr != null) {
        user.deposit(amount);
        displayAccountBalance(user);
      } else {
        System.out.println("Invalid amount! Enter a valid amount to continue");
      }
    }
    return user;
  }

  /* ==== ---- HELPER/UTILITY METHODS ---- ==== */
  // Integer type checker
  public static Integer isValidInteger(String value) {
    try {
      return Integer.parseInt(value);
    } catch (Exception e) {
      return null;
    }
  }

  // Float type checker
  public static Float isValidFloat(String value) {
    try {
      return Float.parseFloat(value);
    } catch (Exception e) {
      return null;
    }
  }

  /* ==== ---- PUBLIC METHODS FROM USER CLASS ---- ==== */
  // Display account balance
  public static void displayAccountBalance(User user) {
    System.out.println("Your account balance is $" + user.getBalance());
  }

  // Display account number
  public static void displayAccountNumber(User user) {
    System.out.println("Your account number is " + user.getAccountNumber());
  }

  /* ======== ------- OPTIONS ------- ======== */
  public static void displayOptions(User user) {
    System.out.println(
      "Hello, " +
      user.getFirstName() +
      ". What can the Bank of Java do for you?"
    );
    System.out.println("1. Deposit");
    System.out.println("2. Withdraw");
    System.out.println("3. Check account balance");
    System.out.println("4. Check account number");
    System.out.println("5. Rest PIN");
    System.out.println("6. Exit");
    System.out.println("Select appropriate option to continue\n\n");
  }
}
