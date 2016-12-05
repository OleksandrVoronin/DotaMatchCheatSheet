import javax.swing.*;
import java.awt.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

public class UIHandler extends JFrame {

    JButtonLink[] nameButtons = new JButtonLink[10];
    JLabel[] winsLosesLabels = new JLabel[10];
    JLabel[] winRateLabels = new JLabel[10];
    JLabel[] kdaLabels = new JLabel[10];
    JLabel[] mostPlayedLabels = new JLabel[10];

    final Color radiantColor = new Color(232, 255, 230);
    final Color direColor = new Color(255, 241, 234);

    final Color goodStatGreen = new Color(255, 45, 56);
    final Color badStatRed = new Color(57, 204, 103);

    final String NO_DATA = " No data. ";

    public UIHandler() {
        super();
        setSize(640, 480);
        setTitle("Dota Match Cheat Sheet");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));


        this.add(initNamesPanel());
        this.add(initWinsLosesPanel());
        this.add(initWinrateLabels());
        this.add(initKDALabels());
        this.add(initMostPlayedLabels());

        this.pack();
    }

    public JPanel initNamesPanel() {
        JPanel names = new JPanel(new GridLayout(10, 1));

        for(int i = 0; i < 10; i++) {
            JButtonLink button = new JButtonLink(NO_DATA);
            button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

            button.setPreferredSize(new Dimension(100, 45));

            button.addActionListener(e -> openPage(button.link));

            if(i < 5)
                button.setBackground(interpolateColor(radiantColor, Color.WHITE, 0.1f));
            else
                button.setBackground(interpolateColor(direColor, Color.WHITE, 0.1f));

            button.setOpaque(true);

            nameButtons[i] = button;
            names.add(button);
        }

        return names;
    }

    public JPanel initWinsLosesPanel() {
        JPanel winsLoses = new JPanel(new GridLayout(10, 1));

        for(int i = 0; i < 10; i++) {
            JLabel label = new JLabel(NO_DATA);
            label.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

            label.setPreferredSize(new Dimension(135, 45));

            if(i < 5)
                label.setBackground(radiantColor);
            else
                label.setBackground(direColor);

            label.setOpaque(true);

            winsLosesLabels[i] = label;
            winsLoses.add(label);
        }

        return winsLoses;
    }

    public JPanel initWinrateLabels() {
        JPanel winRate = new JPanel(new GridLayout(10, 1));

        for(int i = 0; i < 10; i++) {
            JLabel label = new JLabel(NO_DATA);
            label.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

            label.setPreferredSize(new Dimension(120, 45));

            if(i < 5)
                label.setBackground(radiantColor);
            else
                label.setBackground(direColor);

            label.setOpaque(true);

            winRateLabels[i] = label;
            winRate.add(label);
        }

        return winRate;
    }

    public JPanel initKDALabels() {
        JPanel kda = new JPanel(new GridLayout(10, 1));

        for(int i = 0; i < 10; i++) {
            JLabel label = new JLabel(NO_DATA);
            label.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

            label.setPreferredSize(new Dimension(120, 45));

            if(i < 5)
                label.setBackground(radiantColor);
            else
                label.setBackground(direColor);

            label.setOpaque(true);

            kdaLabels[i] = label;
            kda.add(label);
        }

        return kda;
    }

    public JPanel initMostPlayedLabels() {
        JPanel mostPlayed = new JPanel(new GridLayout(10, 1));

        for(int i = 0; i < 10; i++) {
            JLabel label = new JLabel(NO_DATA);
            label.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

            label.setPreferredSize(new Dimension(350, 45));

            if(i < 5)
                label.setBackground(radiantColor);
            else
                label.setBackground(direColor);

            label.setOpaque(true);

            mostPlayedLabels[i] = label;
            mostPlayed.add(label);
        }

        return mostPlayed;
    }

    public void setNameButton(String name, String URL, int index) {
        nameButtons[index].setText(" "  + name + " ");
        nameButtons[index].setLink(URL);
        this.revalidate();
        this.repaint();
    }

    public void setWinsLosesLabel(int wins, int loses, int abandoned, int index) {
        if(wins == 0 && loses == 0)
            winsLosesLabels[index].setText(NO_DATA);
        else {
            String string = " W-L : " + wins + "-" + loses + " ";
            if(abandoned != 0)
                string += "(ab. " + abandoned + ") ";

            winsLosesLabels[index].setText(string);

        }
    }

    public void setWinrateLabel(float winrate, int index) {
        if(!Float.isNaN(winrate)) {
            String winrateString = String.format("%.2f", winrate);
            winRateLabels[index].setForeground(interpolateColor(goodStatGreen, badStatRed, winrate / 100f));
            winRateLabels[index].setText(" " + winrateString + "% win rate ");
        } else {
            winRateLabels[index].setForeground(Color.BLACK);
            winRateLabels[index].setText(NO_DATA);
        }
    }

    public void setKDALabel (float kda, int index) {
        if(!Float.isNaN(kda)) {
            String winrateString = String.format("%.2f", kda);
            kdaLabels[index].setForeground(interpolateColor(goodStatGreen, badStatRed, kda / 6f));
            kdaLabels[index].setText(" " + winrateString + " KDA ");
        } else {
            kdaLabels[index].setForeground(Color.BLACK);
            kdaLabels[index].setText(" No data. ");
        }
    }

    public void setMostPlayed (ArrayList<Parser.HeroPick> list, int index) {
        for(int i = 0; i < Math.min(3, list.size()); i++) {
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
