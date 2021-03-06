import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
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
    String heroNamesJson = "http://api.steampowered.com/IEconDOTA2_570/GetHeroes/v1?key=48A32B90C04CFC588FCEC89B4AAF2B5C&language=us";
    boolean parseForCmMode = true;
    HashMap<Integer, String> heroNamesHashMap = new HashMap<>();

    String[] rankNames = {"Unranked", "Herald", "Guardian", "Crusader", "Archon", "Legend", "Ancient", "Divine", "Immortal"};

    public Parser(String serverLog, UIHandler uiHandler) {
        this.uiHandler = uiHandler;
        this.serverLog = serverLog;
    }

    public void parse(boolean cm) {
        parseForCmMode = cm;

        initHeroNamesArray();
        ids = parseIDs(getLastMatchString(serverLog));

        for(int i = 0; i < ids.size(); i++) {
            parseProfile(ids.get(i), i);
        }

        uiHandler.revalidateFrame();
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

        try {

            {   /** PARSE PROFILE INFO */
                JSONObject profile = new JSONObject(readJsonFromUrl(
                        "https://api.opendota.com/api/players/" + id));

                String name = "<Anonymous>";
                String path = "https://www.opendota.com/players/" + id + "/matches";
                String rankTier = "";

                if (profile.has("profile")) {
                    name = profile.getJSONObject("profile").getString("personaname");
                    if(profile.has("rank_tier") && !profile.isNull("rank_tier")) {
                        int rankTierInt = profile.getInt("rank_tier");
                        if (rankTierInt / 10 == 0) {
                            rankTier = rankNames[rankTierInt / 10];
                        } else if (rankTierInt / 10 == 8) {
                            rankTier = rankNames[rankTierInt / 10] + " " + profile.getInt("leaderboard_rank");
                        } else {
                            rankTier = rankNames[rankTierInt / 10] + " " + (rankTierInt % 10);
                        }
                    }
                }

                uiHandler.setEstimatedMMR(rankTier, index);
                uiHandler.setNameButton(name, path, index);
            }

            {   /** PARSE MATCH INFO */
                String matchQuery =
                        "https://api.opendota.com/api/players/" + id + "/matches?date=7&significant=1";
                if(parseForCmMode)
                    matchQuery = "https://api.opendota.com/api/players/" + id + "/matches?date=31&significant=1&game_mode=2";
                JSONArray matches = new JSONArray(readJsonFromUrl(matchQuery));

                int wins = 0;
                int loses = 0;

                int killsStat = 0;
                int assistsStat = 0;
                int deathsStat = 0;

                ArrayList<HeroPick> heroPickArrayList = new ArrayList<>();

                for (int i = 0; i < matches.length(); i++) {
                    int playerSlot = matches.getJSONObject(i).getInt("player_slot");
                    boolean radiantWin = matches.getJSONObject(i).getBoolean("radiant_win");

                    killsStat += matches.getJSONObject(i).getInt("kills");
                    assistsStat += matches.getJSONObject(i).getInt("assists");
                    deathsStat += matches.getJSONObject(i).getInt("deaths");

                    if (radiantWin == playerSlot < 50)
                        wins++;
                    else
                        loses++;

                    String hero = heroNamesHashMap.get(matches.getJSONObject(i).getInt("hero_id"));

                    if(hero == null)
                        hero = "Unknown Hero";

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
                }

                Collections.sort(heroPickArrayList);
                uiHandler.setMostPlayed(heroPickArrayList, index);

                float winRate = (float) wins / (float) (wins + loses) * 100;
                float kda = (killsStat + assistsStat) / (float) deathsStat;

                String matchesWithMeQueury =
                        "https://api.opendota.com/api/players/83934359/wl?included_account_id=" + id;
                JSONObject matchesWithMe = new JSONObject(readJsonFromUrl(matchesWithMeQueury));

                uiHandler.setWinsLosesLabel(wins, loses, index);
                uiHandler.setWinsLosesWithMeLabel(matchesWithMe.getInt("win"), matchesWithMe.getInt("lose"), index);
                uiHandler.setWinrateLabel(winRate, index);
                uiHandler.setKDALabel(kda, index);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void initHeroNamesArray() {
        try {
            JSONObject jsonObject = new JSONObject(readJsonFromUrl(heroNamesJson));
            JSONArray heroesArray = jsonObject.getJSONObject("result").getJSONArray("heroes");

            for(int i = 0; i < heroesArray.length(); i++) {
                heroNamesHashMap.put(heroesArray.getJSONObject(i).getInt("id"),
                        heroesArray.getJSONObject(i).getString("localized_name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Couldn't retrieve a hero names list!");
        }
    }

    public static String readJsonFromUrl(String link) {
        URL url;
        URLConnection uc;

        StringBuilder parsedContentFromUrl = new StringBuilder();

        try {
            url = new URL(link);

            uc = url.openConnection();
            uc.addRequestProperty("User-Agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");

            uc.connect();

            uc.getInputStream();
            InputStreamReader reader = new InputStreamReader(uc.getInputStream(), "utf-8");

            int ch;
            while ((ch = reader.read()) != -1) {
                parsedContentFromUrl.append((char) ch);
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return parsedContentFromUrl.toString();
    }
}
