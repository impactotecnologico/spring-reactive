package net.impactotecnologico.reactivos.event;

public class PagosEvent {

  private String id;
  private String creditCardNumber;
  private String amount;
  private String gateway;

  public PagosEvent() {
  }

    public PagosEvent(String id, String creditCardNumber, String amount, String gateway) {
    this.id = id;
    this.creditCardNumber = creditCardNumber;
    this.amount = amount;
        this.gateway = gateway;
  }

  public String getId() {
    return id;
  }

  public String getCreditCardNumber() {
    return creditCardNumber;
  }

  public String getAmount() {
    return amount;
  }

  public String getGateway() {
    return gateway;
  }
}
