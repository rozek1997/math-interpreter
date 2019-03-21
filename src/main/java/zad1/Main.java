package zad1;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in, "utf-8");
        Memory memory = new Memory();
        Parser parser=new Parser(memory);
        String input;


        while (scanner.hasNextLine()) {
            input = scanner.nextLine();
            if (input.equals("bye")) break;
            if (!input.equals("")){
                try {
                    parser.parseInput(input);

                } catch (ParsingException | NotFoundException e) {
                    System.out.println(e.getMessage());
                }
            }

        }

        scanner.close();

    }
}
