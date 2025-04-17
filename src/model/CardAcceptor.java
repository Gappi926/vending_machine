package model;

import java.util.Scanner;

public class CardAcceptor implements MoneyReceiver {
    private int amount;

    public CardAcceptor(int amount) {
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
        System.out.println("Введите номер карты:");
        String cardNumber = scanner.nextLine();

        System.out.println("Введите одноразовый пароль:");
        String password = scanner.nextLine();

        System.out.println("Введите сумму списания:");
        try {
            int payment = Integer.parseInt(scanner.nextLine());
            if (payment > 0) {
                this.amount += payment;
                System.out.println("Оплата " + payment + " прошла");
            } else {
                System.out.println("Сумма должна быть положительной");
            }
        } catch (NumberFormatException e) {
            System.out.println("Сумма некоректная");
        }
    }

    @Override
    public String getCurrencyName() {
        return "карточный платеж";
    }
}
