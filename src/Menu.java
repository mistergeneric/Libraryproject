import java.util.Scanner;

public class Menu {
    private Connector connector;

    public void signIn() {
        System.out.println("Hi, please sign in");
        Scanner scanner = new Scanner(System.in);
        System.out.println("Username:");
        String username = scanner.nextLine();
        System.out.println("Password:");
        String password = scanner.nextLine();
        connector = new Connector(username, password);
        mainMenu();
    }

    public void mainMenu() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Library system");

        while(true) {
            System.out.println("Choose an option");
            System.out.println("1. Insert data");
            System.out.println("2. Query data");
            System.out.println("3. Quit");
            String choice = scanner.nextLine();
            chooseMenu(choice);
        }
    }

    private void chooseMenu(String choice) {
        switch (choice) {
            case "1":
                insertMenu();
                break;
            case "2":
                queryMenu();
                break;
            case "3":
                System.out.println("Goodbye");
                System.exit(1);
            default:
                System.out.println("Command not understood, try again.");
                break;
        }
    }

    private void queryMenu() {
        System.out.println("Running query, members with loans:");
        connector.queryLoanedMembers();
    }

    private void insertMenu() {
        System.out.println("Insert what??");
    }
}
