package model;

public interface MoneyReceiver {
    int getAmount();
    void setAmount(int amount);
    void acceptPayment();
    String getCurrencyName();
}
