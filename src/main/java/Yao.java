import java.util.Scanner;

public class Yao {

    public static void main(String[] args) {
        String line = "____________________________________________________________";
        String banner = " __   __            \n"
                + " \\ \\ / /_ _  ___   \n"
                + "  \\ V / _` |/ _ \\  \n"
                + "   | | (_| | (_) | \n"
                + "   |_|\\__,_|\\___/  \n";

        System.out.println(line);
        System.out.print(banner);
        System.out.println("Hello! I'm Yao.");
        System.out.println("What can I do for you?");
        System.out.println(line);

        Scanner scanner = new Scanner(System.in);
        while (true) {
            String command = scanner.nextLine();
            System.out.println(line);
            if (command.equals("bye")) {
                System.out.println("Bye. Hope to see you again soon!");
                System.out.println(line);
                break;
            }
            System.out.println(command);
            System.out.println(line);
        }
    }
}
