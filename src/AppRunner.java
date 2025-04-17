import enums.ActionLetter;
import model.*;
import util.UniversalArray;
import util.UniversalArrayImpl;
import java.util.Scanner;

public class AppRunner {

    private final UniversalArray<Product> products = new UniversalArrayImpl<>();

    private final MoneyReceiver moneyReceiver;

    private static boolean isExit = false;

    private AppRunner(MoneyReceiver moneyReceiver) {
        products.addAll(new Product[]{
                new Water(ActionLetter.B, 20),
                new CocaCola(ActionLetter.C, 50),
                new Soda(ActionLetter.D, 30),
                new Snickers(ActionLetter.E, 80),
                new Mars(ActionLetter.F, 80),
                new Pistachios(ActionLetter.G, 130)
        });
        this.moneyReceiver = moneyReceiver;
    }

    private static MoneyReceiver selectMoneyReceiver() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Выберите тип приемника денег:");
        System.out.println("1 - Монетки");
        System.out.println("2 - Банковская карта");

        String choice = scanner.nextLine();
        switch (choice) {
            case "1":
                return new CoinAcceptor(100);
            case "2":
                return new CardAcceptor(100);
            default:
                System.out.println("Используется приемник монеток по умолчанию");
                return new CoinAcceptor(100);
        }
    }

    public static void run() {
        MoneyReceiver receiver = selectMoneyReceiver();
        AppRunner app = new AppRunner(receiver);
        while (!isExit) {
            app.startSimulation();
        }
    }

    private void startSimulation() {
        print("В автомате доступны:");
        showProducts(products);

        print("Доступно средств (" + moneyReceiver.getCurrencyName() + "): " + moneyReceiver.getAmount());
        print(" a - Пополнить баланс");

        UniversalArray<Product> allowProducts = new UniversalArrayImpl<>();
        allowProducts.addAll(getAllowedProducts().toArray());
        chooseAction(allowProducts);

    }

    private UniversalArray<Product> getAllowedProducts() {
        UniversalArray<Product> allowProducts = new UniversalArrayImpl<>();
        for (int i = 0; i < products.size(); i++) {
            if (moneyReceiver.getAmount() >= products.get(i).getPrice()) {
                allowProducts.add(products.get(i));
            }
        }
        return allowProducts;
    }

    private void chooseAction(UniversalArray<Product> products) {
        showActions(products);
        print(" h - Выйти");
        String action = fromConsole().substring(0, 1);

        if ("a".equalsIgnoreCase(action)) {
            moneyReceiver.acceptPayment();
            return;
        }

        try {
            for (int i = 0; i < products.size(); i++) {
                if (products.get(i).getActionLetter().equals(ActionLetter.valueOf(action.toUpperCase()))) {
                    moneyReceiver.setAmount(moneyReceiver.getAmount() - products.get(i).getPrice());
                    print("Вы купили " + products.get(i).getName());
                    break;
                } else if ("h".equalsIgnoreCase(action)) {
                    isExit = true;
                    break;
                }
            }
        } catch (IllegalArgumentException e) {
            print("Недопустимая буква. Попробуйте еще раз.");
            chooseAction(products);
        }
    }

    private void showActions(UniversalArray<Product> products) {
        for (int i = 0; i < products.size(); i++) {
            print(String.format(" %s - %s", products.get(i).getActionLetter().getValue(), products.get(i).getName()));
        }
    }

    private String fromConsole() {
        return new Scanner(System.in).nextLine();
    }

    private void showProducts(UniversalArray<Product> products) {
        for (int i = 0; i < products.size(); i++) {
            print(products.get(i).toString());
        }
    }

    private void print(String msg) {
        System.out.println(msg);
    }
}
