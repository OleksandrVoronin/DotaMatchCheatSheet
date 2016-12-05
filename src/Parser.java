import org.jsoup.Jsoup;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Parser {

    public class HeroPick implements Comparable {
        String name;
        int count = 1;

        public HeroPick (String name) {
            this.name = name;
        }

        @Override
        public int compareTo(Object o) {
            if(o instanceof  HeroPick) {
                HeroPick other = (HeroPick) o;

                if(count > other.count) {
                    return -1;
                } else if (count < other.count) {
                    return 1;
                } else {
                    return name.compareTo(other.name);
                }
            } else {
                return 0;
            }
        }
    }

    UIHandler uiHandler;
    ArrayList<String> ids = new ArrayList();
    String serverLog;

    public Parser(String serverLog, UIHandler uiHandler) {
        this.uiHandler = uiHandler;
        this.serverLog = serverLog;
    }

    public void parse() {
        ids = parseIDs(getLastMatchString(serverLog));

        for(int i = 0; i < ids.size(); i++) {
            parseProfile(ids.get(i), i);
        }
        //ids.forEach(parser::parseProfile);
    }

    String getLastMatchString(String path) {
        String lastMatch = "";
        Scanner fileScanner;

        try {
            fileScanner = new Scanner(new FileInputStream(path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }

        while(fileScanner.hasNextLine()) {
            String line = fileScanner.nextLine();
            if(line.contains("Lobby") && line.contains("DOTA_GAMEMODE")) {
                lastMatch = line;
            }
        }

        return lastMatch;
    }

    ArrayList<String> parseIDs(String matchInfo) {
        ArrayList<String> ids = new ArrayList<>();

        String importantInfo = matchInfo.split("\\)")[0];

        String[] idBits = importantInfo.split("U:1:");

        uiHandler.clearAllText();

        for(int i = 1; i < idBits.length; i++) {
            ids.add(idBits[i].split("\\]")[0]);
        }

        return ids;
    }

    void parseProfile(String id, int index) {
        //System.out.println("ID: " + id);
        String path = "https://www.dotabuff.com/players/" + id + "/matches?date=week";
        //System.out.println(path);

        try {
            String dotabuffPageSource = getURLSource(path);

            int wins = dotabuffPageSource.split("Won Match").length - 1;
            int loses = dotabuffPageSource.split("Lost Match").length - 1;
            int abandoned = dotabuffPageSource.split("Abandoned").length - 1;

            float winRate = (float) wins / (float) (wins + loses) * 100;
            String name = dotabuffPageSource.split("<title>")[1].split(" - ")[0];

            uiHandler.setNameButton(name, path, index);
            uiHandler.setWinsLosesLabel(wins, loses, abandoned, index);
            uiHandler.setWinrateLabel(winRate, index);


            try {
                float kda = Float.parseFloat(dotabuffPageSource.split("class=\"color-stat-kda\">")[1].split("</span>")[0]);
                uiHandler.setKDALabel(kda, index);
            } catch (Exception e) {
                uiHandler.setKDALabel(Float.NaN, index);
            }

            String[] heroPicks = dotabuffPageSource.split("</a><span class=");
            ArrayList<HeroPick> heroPickArrayList = new ArrayList<>();

            for(int i = 0; i < heroPicks.length - 1; i++) {
                try {
                    String hero = heroPicks[i].split(">")[heroPicks[i].split(">").length - 1];
                    boolean firstTimePicked = true;

                    for(HeroPick hp : heroPickArrayList) {
                        if (hp.name.contains(hero)) {
                            hp.count++;
                            firstTimePicked = false;
                        }
                    }

                    if(firstTimePicked) {
                        heroPickArrayList.add(new HeroPick(hero));
                    }

                } catch (Exception e) {
                    System.out.println("Something went wrong");
                }
            }

            Collections.sort(heroPickArrayList);
            uiHandler.setMostPlayed(heroPickArrayList, index);

            /*if(wins + loses == 0)
                System.out.println(name + " - NO DATA");
            else
                System.out.format(name + " - " + "W: " + wins + "; L: " + loses + " (%.2f percent)\n", winRate);*/
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Connection issue...");
        }
    }

    static String getURLSource(String profileurl) throws IOException {
        String fakeAgent = "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:5.0) Gecko/20100101 Firefox/5.0";

        String source = Jsoup.connect(profileurl).userAgent(fakeAgent).timeout(0).get().html();

        return source;
    }
}
