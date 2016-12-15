public class Main {

    public static void main(String[] args) {
        String serverLog = "";

        if(args.length != 1) {
            System.out.println("Invalid arguments");
            System.exit(1);
        } else {
            System.out.println("Server log: " + args[0]);
            serverLog = args[0];
        }

        serverLog += "/server_log.txt";

        {
            UIHandler window = new UIHandler();
            Parser parser = new Parser(serverLog, window);
            window.setVisible(true);

            parser.parse(false);
        }
    }


}
