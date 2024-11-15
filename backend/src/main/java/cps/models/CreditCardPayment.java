package cps.models;

import java.time.LocalDate;

public class CreditCardPayment extends Payment {
  private String cardNumber;
  private String cardHoldername;
  private LocalDate expirationDate;
  private String cvv;

  public CreditCardPayment(int clientId, int deliveryId, double amount, String cardNumber, String cardHolderName,
      LocalDate expirationDate,
      String cvv) {
    super(clientId, deliveryId, amount);
    this.cardNumber = cardNumber;
    this.expirationDate = expirationDate;
    this.cvv = cvv;
  }

  private String getLastFourDigits() {
    if (this.cardNumber != null && this.cardNumber.length() >= 4) {
      return this.cardNumber.substring(this.cardNumber.length() - 4);
    } else {
      return "";
    }
  }

  public String getCreditCardInfo() {
    return String.format("Credit card ending in %s", this.getLastFourDigits());
  }

  public String getCardNumber() {
    return this.cardNumber;
  }

  public String getCardHolderName() {
    return this.cardHoldername;
  }

  public LocalDate getExpirationDate() {
    return this.expirationDate;
  }

  public String getCVV() {
    return this.cvv;
  }
}
