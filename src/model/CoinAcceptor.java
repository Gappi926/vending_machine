package model;

import java.util.Scanner;

public class CoinAcceptor implements MoneyReceiver {
    private int amount;

    public CoinAcceptor(int amount) {
        this.amount = amount;
    }

    @Override
    public int getAmount() {
        return amount;
    }

    @Override
    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public void acceptPayment() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите сумму пополнения:");
        try {
            int payment = Integer.parseInt(scanner.nextLine());
            if (payment > 0) {
                this.amount += payment;
                System.out.println("Принято " + payment + " монетки");
            } else {
                System.out.println("Сумма должна быть положительной");
            }
        } catch (NumberFormatException e) {
            System.out.println("Сумма некоректная");
        }
    }

    @Override
    public String getCurrencyName() {
        return "монетки";
    }
}
