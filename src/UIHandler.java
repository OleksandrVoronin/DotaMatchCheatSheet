import javax.swing.*;
import java.awt.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

public class UIHandler extends JFrame {

    JButtonLink[] nameButtons = new JButtonLink[10];
    JLabel[] estimatedMMRLabel = new JLabel[10];
    JLabel[] winsLosesLabels = new JLabel[10];
    JLabel[] winsLosesWithMeLabels = new JLabel[10];
    JLabel[] winRateLabels = new JLabel[10];
    JLabel[] kdaLabels = new JLabel[10];
    JLabel[] mostPlayedLabels = new JLabel[10];

    final Color goodStatGreen = new Color(255, 45, 56);
    final Color badStatRed = new Color(57, 204, 103);

    final Color sideColor = new Color(29, 29, 41);
    final Color separatorColor = new Color(30, 30, 42);
    final Color tileA = new Color(45, 47, 65);
    final Color tileB = new Color(38, 40, 58);

    final Color font = new Color(194, 194, 194);

    final Color highlight = new Color(62, 65, 70);

    static Font hugeFont;
    static Font smallFont;

    final String[] SIDE_TEXT = {"Overview - RADIANT", "Overview - DIRE"};
    final String[] headerText = {"Player name:", "Estimated MMR:", "Wins/Loses:", "Win rate:", "KDA:", "Most Played Heroes:"};


    final String NO_DATA = "";

    public UIHandler() {
        super();

        setSize(1200, 480);
        setTitle("Dota Match Cheat Sheet");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));

        setBackground(tileA);

        this.add(initNamesPanel());
        this.add(initEstimatedMMRLabel());
        this.add(initWinsLosesPanel());
        this.add(initWinsLosesWithMePanel());
        this.add(initWinrateLabels());
        this.add(initKDALabels());
        this.add(initMostPlayedLabels());

        this.pack();
    }

    public JPanel initNamesPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(sideColor);
        JPanel names = new JPanel(new GridLayout(10, 1));

        for(int i = 0; i < 10; i++) {
            JButtonLink button = new JButtonLink(NO_DATA);
            button.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, i == 5 ? font : highlight));

            button.setPreferredSize(new Dimension(120, 45));

            button.addActionListener(e -> openPage(button.link));

            button.setBackground(interpolateColor((i % 2 == 0) ? tileA : tileB, Color.BLACK, 0.05f));
            button.setForeground(font);
            button.setOpaque(true);

            nameButtons[i] = button;
            names.add(button);
        }

        panel.add(names, BorderLayout.CENTER);

        JLabel header = new JLabel("Player name:", SwingConstants.CENTER);
        header.setForeground(font);
        header.setPreferredSize(new Dimension(120, 35));
        panel.add(header, BorderLayout.NORTH);

        return panel;
    }

    public JPanel initEstimatedMMRLabel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(sideColor);
        JPanel estimatedMMR = new JPanel(new GridLayout(10, 1));

        for(int i = 0; i < 10; i++) {
            JLabel label = new JLabel(NO_DATA, SwingConstants.CENTER);
            label.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, i == 5 ? font : highlight));

            label.setPreferredSize(new Dimension(110, 45));

            label.setBackground((i % 2 == 0) ? tileA : tileB);
            label.setForeground(font);
            label.setOpaque(true);

            estimatedMMRLabel[i] = label;
            estimatedMMR.add(label);
        }

        panel.add(estimatedMMR, BorderLayout.CENTER);

        JLabel header = new JLabel("Rank:", SwingConstants.CENTER);
        header.setForeground(font);
        header.setPreferredSize(new Dimension(100, 35));
        panel.add(header, BorderLayout.NORTH);

        return panel;
    }

    public JPanel initWinsLosesPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(sideColor);
        JPanel winsLoses = new JPanel(new GridLayout(10, 1));

        for(int i = 0; i < 10; i++) {
            JLabel label = new JLabel(NO_DATA, SwingConstants.CENTER);
            label.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, i == 5 ? font : highlight));

            label.setPreferredSize(new Dimension(110, 45));

            label.setBackground((i % 2 == 0) ? tileA : tileB);
            label.setForeground(font);
            label.setOpaque(true);

            winsLosesLabels[i] = label;
            winsLoses.add(label);
        }

        panel.add(winsLoses, BorderLayout.CENTER);

        JLabel header = new JLabel("Wins/Loses:", SwingConstants.CENTER);
        header.setForeground(font);
        header.setPreferredSize(new Dimension(100, 35));
        panel.add(header, BorderLayout.NORTH);

        return panel;
    }

    public JPanel initWinsLosesWithMePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(sideColor);
        JPanel winsLoses = new JPanel(new GridLayout(10, 1));

        for(int i = 0; i < 10; i++) {
            JLabel label = new JLabel(NO_DATA, SwingConstants.CENTER);
            label.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, i == 5 ? font : highlight));

            label.setPreferredSize(new Dimension(110, 45));

            label.setBackground((i % 2 == 0) ? tileA : tileB);
            label.setForeground(font);
            label.setOpaque(true);

            winsLosesWithMeLabels[i] = label;
            winsLoses.add(label);
        }

        panel.add(winsLoses, BorderLayout.CENTER);

        JLabel header = new JLabel("With me:", SwingConstants.CENTER);
        header.setForeground(font);
        header.setPreferredSize(new Dimension(100, 35));
        panel.add(header, BorderLayout.NORTH);

        return panel;
    }

    public JPanel initWinrateLabels() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(sideColor);
        JPanel winRate = new JPanel(new GridLayout(10, 1));

        for(int i = 0; i < 10; i++) {
            JLabel label = new JLabel(NO_DATA, SwingConstants.CENTER);
            label.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, i == 5 ? font : highlight));

            label.setPreferredSize(new Dimension(110, 45));

            label.setBackground((i % 2 == 0) ? tileA : tileB);
            label.setForeground(font);
            label.setOpaque(true);

            winRateLabels[i] = label;
            winRate.add(label);
        }

        panel.add(winRate, BorderLayout.CENTER);

        JLabel header = new JLabel("Win rate:", SwingConstants.CENTER);
        header.setForeground(font);
        header.setPreferredSize(new Dimension(100, 35));
        panel.add(header, BorderLayout.NORTH);

        return panel;
    }

    public JPanel initKDALabels() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(sideColor);
        JPanel kda = new JPanel(new GridLayout(10, 1));

        for(int i = 0; i < 10; i++) {
            JLabel label = new JLabel(NO_DATA, SwingConstants.CENTER);
            label.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, i == 5 ? font : highlight));

            label.setPreferredSize(new Dimension(110, 45));

            label.setBackground((i % 2 == 0) ? tileA : tileB);
            label.setForeground(font);
            label.setOpaque(true);

            kdaLabels[i] = label;
            kda.add(label);
        }

        panel.add(kda, BorderLayout.CENTER);

        JLabel header = new JLabel("KDA:", SwingConstants.CENTER);
        header.setForeground(font);
        header.setPreferredSize(new Dimension(100, 35));
        panel.add(header, BorderLayout.NORTH);

        return panel;
    }

    public JPanel initMostPlayedLabels() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(sideColor);
        JPanel mostPlayed = new JPanel(new GridLayout(10, 1));

        for(int i = 0; i < 10; i++) {
            JLabel label = new JLabel(NO_DATA, SwingConstants.CENTER);
            label.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, i == 5 ? font : highlight));

            label.setPreferredSize(new Dimension(450, 45));

            label.setBackground((i % 2 == 0) ? tileA : tileB);
            label.setForeground(font);
            label.setOpaque(true);

            mostPlayedLabels[i] = label;
            mostPlayed.add(label);
        }

        panel.add(mostPlayed, BorderLayout.CENTER);

        JLabel header = new JLabel("Most played heroes:", SwingConstants.CENTER);
        header.setForeground(font);
        header.setPreferredSize(new Dimension(100, 35));
        panel.add(header, BorderLayout.NORTH);

        return panel;
    }

    public void revalidateFrame() {
        this.revalidate();
        this.repaint();
    }

    public void setNameButton(String name, String URL, int index) {
        nameButtons[index].setText(" "  + name + " ");
        nameButtons[index].setLink(URL);

    }

    public void setEstimatedMMR(String mmr, int index) {
        if(mmr.length() != 0) {
            estimatedMMRLabel[index].setText(" " + mmr + " ");
        }
    }

    public void setWinsLosesLabel(int wins, int loses, int index) {
        if(wins == 0 && loses == 0)
            winsLosesLabels[index].setText(NO_DATA);
        else {
            String string = " " + wins + " / " + loses;

            winsLosesLabels[index].setText(string);
        }
    }

    public void setWinsLosesWithMeLabel(int wins, int loses, int index) {
        if(wins == 0 && loses == 0)
            winsLosesWithMeLabels[index].setText(NO_DATA);
        else {
            String string = " " + wins + " / " + loses;

            winsLosesWithMeLabels[index].setText(string);
        }
    }

    public void setWinrateLabel(float winrate, int index) {
        if(!Float.isNaN(winrate)) {
            String winrateString = String.format("%.2f", winrate);
            winRateLabels[index].setForeground(interpolateColor(goodStatGreen, badStatRed, winrate / 100f));
            winRateLabels[index].setText(" " + winrateString + "% ");
        } else {
            winRateLabels[index].setForeground(Color.BLACK);
            winRateLabels[index].setText(NO_DATA);
        }
    }

    public void setKDALabel (float kda, int index) {
        if(!Float.isNaN(kda)) {
            String winrateString = String.format("%.2f", kda);
            kdaLabels[index].setForeground(interpolateColor(goodStatGreen, badStatRed, kda / 6f));
            kdaLabels[index].setText(" " + winrateString + " ");
        } else {
            kdaLabels[index].setForeground(Color.BLACK);
            kdaLabels[index].setText(NO_DATA);
        }
    }

    public void setMostPlayed (ArrayList<Parser.HeroPick> list, int index) {
        for(int i = 0; i < Math.min(5, list.size()); i++) {
            mostPlayedLabels[index].setText(mostPlayedLabels[index].getText() + " " +
                                list.get(i).name + " " + list.get(i).count + "x ");
        }
    }

    public void clearAllText() {
        for(int i = 0; i < winsLosesLabels.length; i++) {
            winRateLabels[i].setText("");
            winsLosesLabels[i].setText("");
            nameButtons[i].setText("");
            kdaLabels[i].setText("");
            mostPlayedLabels[i].setText("");
        }
    }

    private void openPage(URI uri) {
        Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
        if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
            try {
                desktop.browse(uri);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void openPage(URL url) {
        try {
            openPage(url.toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private class JButtonLink extends JButton {
        URL link = null;

        JButtonLink(String name) {
            super(name);
        }

        public void setLink(String link) {
            try {
                this.link = new URL(link);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
    }

    private Color interpolateColor(Color from, Color to, float ratio) {
        int fr = from.getRed();
        int fb = from.getBlue();
        int fg = from.getGreen();

        int tr = to.getRed();
        int tb = to.getBlue();
        int tg = to.getGreen();

        return new Color((int) Math.max(0, Math.min(255, fr + (tr - fr) * ratio)),
                (int) Math.max(0, Math.min(255, fg + (tg - fg) * ratio)),
                (int) Math.max(0, Math.min(255, fb + (tb - fb) * ratio)));
    }

}
