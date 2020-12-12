import java.util.Scanner;

public class Menu {
    private Connector connector;
    private static final String[] queryKeywords = {"book", "dvd"};
    private static final String[] memberAttributes = {"memberId", "firstName", "lastName", "gender"};
    private Scanner scanner;

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

    /**
     * Only for testing
     * Allows pw and username to be put into vm arguments.
     */
    public void signIn(String[] args) {
        connector = new Connector(args[0], args[1]);
        mainMenu();
    }

    public void mainMenu() {
        scanner = new Scanner(System.in);
        scanner.reset();

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
        System.out.println("Would you like to find member's loaning books or dvds");
        String userInput = getInput("^[Bb]ooks|[Dd]vds$", "query");
        System.out.println("Running query...\n\n");
        connector.queryLoanedMembers(userInput.replace("s", ""));
    }

    private void insertMenu() {
        System.out.println("Please enter the data of the member you wish to add ot the system:");
        connector.insert(getInsertData());
    }

    private String[] getInsertData() {
        String[] values = new String[4];
        int i = 0;
        for (String ma : memberAttributes) {
            System.out.println("Please Enter "  + ma);
            if (ma.toLowerCase().contains("id")) {
                values[i] = getInput("^[0-9]{1,7}$", ma);
            } else if (ma.toLowerCase().contains("name")) {
                values[i] = getInput("^[a-zA-z]{2,15}$", ma);
            } else {
                values[i] = getInput("^[Mm]{1}|[Ff]{1}|[Oo]{1}$", ma);
            }
            i++;
        }
        return values;
    }

    private String getInput(String regex, String errorKey) {
        boolean looping = true;
        String validInput = null;
        while(looping) {
            String temp = null;
            if (scanner.hasNext()) {
                temp = scanner.next();
            }
            if(temp!=null&&temp.matches(regex)){
                validInput = temp;
                looping = false;
            } else {
                System.out.println(ErrorResponses.getErrors().get(errorKey));
            }
        }
        return validInput;
    }
}
