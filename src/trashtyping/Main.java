package trashtyping;

import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author Ben
 */
public class Main {

    public static Scanner scan = new Scanner(System.in);

    public static Timer timer;

    public static void main(String[] args) throws InterruptedException {
        print(ControlCodes.ResetFormatting);
        println("Hello, and welcome to Trash Typing. In this game, you will type away trash and be given a final score based on how fast you were.\n Press Enter to Begin.");
        scan.nextLine();
        println("Once you have completed a section, press Enter to check for mistakes. If there is a mistake, you will be penalized and force to retry.\n\n"
                + "Would you like to play [story] mode or [random] mode?");
        String input = scan.nextLine();
        println(ControlCodes.Clear);
        input = input.toLowerCase();
        switch (input) {
            case "random": {
                randomAdventure();
                break;
            }
            default: {
                normalMode();
                break;
            }
        }
        print("\n" + ControlCodes.ResetFormatting);
        System.exit(0);
    }

    public static void normalMode() throws InterruptedException {
        String[] lines = {
            "You decided today would be a good day to head to the beach. You packed your sunscreen and picnic basket and got in your car. "
            + "The drive to the beach is about half an hour. "
            + "When you arrive, you wave hello to the lifeguard (you two are very good friends) and find a nice spot on the beach to set your things down at. "
            + "You take out your towel and lay it down on the sand. You came pretty early in the day, so you basically have the beach all to yourself. "
            + "You take out your sunscreen and apply it (you don't want to get burned after all!)",
            "After sunbathing for around 10 minutes, you decide to go for a little walk around the beach. "
            + "Heading to your left, you find yourself at the edge of the beach where a pile of rocks lay. "
            + "You discover, laying on the rocks, a bunch of plastic bags and used paper plates and plastic utensils. "
            + "You shake your head before cleaning up the mess. You throw the trash into a trashcan, conveniently located in the parking lot. "
            + "Heading back to your spot on the beach, you decide that you want to go for a swim. "
            + "You make your way to the shoreline and begin to walk into the water. "
            + "Once you are knee-deep, you feel your foot touch something round. It's a glass bottle. You couldn't even see it because it was buried beneath the sand. "
            + "{Imagine if that bottle was broken, any unsuspecting person could have stepped on that and cut themselves} You think to yourself. "
            + "Picking up the bottle, you dispose of it before going back to swimming.",
            "It was a pretty nice swim, the water was just the right temperature. "
            + "You decide to head back to your spot on the beach. The beach has begun to get crowded. "
            + "You stay at the beach for 20 more minutes before deciding to leave. "
            + "You gather your things and begin to head to the parking lot. "
            + "You arrive at your car and get in. You start your car and drive home."
        };
        int[] times = new int[lines.length];
        timer = new Timer();
        for (int i = 0; i < lines.length; i++) {
            timer.start();
            typeLine(lines[i]);
            timer.interrupt();
            times[i] = timer.getTime();
            timer = new Timer();
        }
        print(ControlCodes.Clear);
        println("Total time: " + (sum(times) / 1000));
        for (int i = 0; i < lines.length; i++) {
            println("Section " + (i + 1) + " time: " + (((float) times[i]) / 1000));
        }
    }

    public static void randomAdventure() throws InterruptedException {
        String[] lines;
        lines = generateStory();
        int[] times = new int[lines.length];
        timer = new Timer();
        for (int i = 0; i < lines.length; i++) {
            timer.start();
            typeLine(lines[i]);
            timer.interrupt();
            times[i] = timer.getTime();
            timer = new Timer();
        }
        print(ControlCodes.Clear);
        println("Total time: " + (sum(times) / 1000));
        for (int i = 0; i < lines.length; i++) {
            println("Section " + (i + 1) + " time: " + (((float) times[i]) / 1000));
        }
    }

    public static float sum(int[] arr) {
        float total = 0;
        for (int a : arr) {
            total += a;
        }
        return total;
    }

    public static void typeLine(String line) throws InterruptedException {
        println(ControlCodes.Clear + ControlCodes.YELLOW + line);
        print(ControlCodes.ResetCursor + ControlCodes.ResetFormatting + ControlCodes.GREEN);
        String input = scan.nextLine();
        if (!line.equals(input)) {
            //improper timing.
            int mistakeCount = showMistake(line, input);
            println("You made a mistake: +3s x " + mistakeCount);
            timer.addTime((mistakeCount * 3000) - 3000);
            Thread.sleep(3000);
        }
    }

    public static int showMistake(String line, String input) {
        int mistakes = 0;
        timer.togglePaused(true);
        //compare each letter, and mark the incorrect ones with red.
        StringBuilder sb = new StringBuilder();
        char[] lineArray = line.toCharArray();
        char[] inputArray = input.toCharArray();
        int index = 0;
        if (inputArray.length >= lineArray.length) {
            for (int i = 0; i < lineArray.length; i++) {
                if (inputArray[i] != lineArray[i]) {
                    sb.append(ControlCodes.RED_BACKGROUND);
                    mistakes++;
                }
                sb.append(inputArray[i]);
                sb.append(ControlCodes.ResetFormatting);
                index = i;
            }
        } else {
            index = Integer.MAX_VALUE;
            for (int i = 0; i < inputArray.length; i++) {
                if (inputArray[i] != lineArray[i]) {
                    sb.append(ControlCodes.RED_BACKGROUND);
                    mistakes++;
                }
                sb.append(inputArray[i]);
                sb.append(ControlCodes.ResetFormatting);
            }
            for (int i = inputArray.length; i < lineArray.length; i++) {
                sb.append(ControlCodes.RED_BACKGROUND);
                sb.append(lineArray[i]);
                mistakes++;
                sb.append(ControlCodes.ResetFormatting);
            }
        }
        if (index < inputArray.length - 1) {
            sb.append(ControlCodes.RED_BACKGROUND);
            for (int i = index + 1; i < inputArray.length; i++) {
                sb.append(inputArray[i]);
                mistakes++;
            }
            sb.append(ControlCodes.ResetFormatting);
        }
        println(ControlCodes.ResetCursor + sb.toString());
        timer.togglePaused(false);
        return mistakes;
    }

    public static String[] generateStory() {
        Random rand = new Random();
        String[] arr = new String[rand.nextInt(3) + 1];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = generateLine();
        }
        return arr;
    }

    public static String[] trash = {
        "soda can",
        "plastic bag",
        "glass bottle",
        "candy wrapper",
        "half-eatten chicken leg",
        "rotten apple",
        "broken glass",
        "newspaper",
        "needle",
        "tin can",
        "battery",
        "fish skeleton",
        "unnecessarily long string in order to make this more difficult"
    };

    public static String[] scenario = {
        "You are walking on the shore line, and you spot ",
        "You are walking down the pier and you see ",
        "You are building a sand castle when you see ",
        "You are sun bathing, and you see in the distance ",
        "You spot a bird trying to eat ",
        "You and a friend are eating. Your friend suddenly points at a turtle eating "
    };

    public static String generateLine() {
        StringBuilder sb = new StringBuilder();
        Random rand = new Random();
        int length = rand.nextInt(2) + 3;
        int count = 0;
        while (count < length) {
            sb.append(generateSentance());
            count++;
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    public static String generateSentance() {
        Random rand = new Random();
        int s = rand.nextInt(scenario.length - 1);
        int t = rand.nextInt(trash.length - 1);
        int quantity = rand.nextInt(5) + 1;
        return scenario[s] + quantity + " " + trash[t] + (quantity > 1 ? "s" : "") + ". You pick the trash up. ";
    }

    public static void println(String str) {
        System.out.println(str);
    }

    public static void print(String str) {
        System.out.print(str);
    }

}
