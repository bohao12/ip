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
        String[] tasks = new String[100];
        int taskCount = 0;

        while (true) {
            String command = scanner.nextLine();
            System.out.println(line);
            if (command.equals("bye")) {
                System.out.println("Bye. Hope to see you again soon!");
                System.out.println(line);
                break;
            } else if (command.equals("list")) {
                for (int i = 0; i < taskCount; i++) {
                    System.out.println((i + 1) + ". " + tasks[i]);
                }
                System.out.println(line);
            } else {
                tasks[taskCount] = command;
                taskCount++;
                System.out.println("added: " + command);
                System.out.println(line);
            }
        }
    }
}
