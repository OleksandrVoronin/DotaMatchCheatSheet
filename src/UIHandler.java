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
    JLabel[] winRateLabels = new JLabel[10];
    JLabel[] kdaLabels = new JLabel[10];
    JLabel[] mostPlayedLabels = new JLabel[10];

    final Color radiantColor = new Color(245, 255, 245);
    final Color direColor = new Color(255, 245, 245);

    final Color goodStatGreen = new Color(255, 45, 56);
    final Color badStatRed = new Color(57, 204, 103);

    final Color separatorColor = new Color(0, 0, 0, 80);

    final String NO_DATA = "";

    public UIHandler() {
        super();
        setSize(640, 480);
        setTitle("Dota Match Cheat Sheet");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));


        this.add(initNamesPanel());
        this.add(initEstimatedMMRLabel());
        this.add(initWinsLosesPanel());
        this.add(initWinrateLabels());
        this.add(initKDALabels());
        this.add(initMostPlayedLabels());

        this.pack();
    }

    public JPanel initNamesPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel names = new JPanel(new GridLayout(10, 1));

        for(int i = 0; i < 10; i++) {
            JButtonLink button = new JButtonLink(NO_DATA);
            button.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, separatorColor));

            button.setPreferredSize(new Dimension(120, 45));

            button.addActionListener(e -> openPage(button.link));

            if(i < 5)
                button.setBackground(interpolateColor(radiantColor, Color.BLACK, 0.05f));
            else
                button.setBackground(interpolateColor(direColor, Color.BLACK, 0.05f));

            button.setOpaque(true);

            nameButtons[i] = button;
            names.add(button);
        }

        panel.add(names, BorderLayout.CENTER);

        JLabel header = new JLabel("Player name:", SwingConstants.CENTER);
        header.setPreferredSize(new Dimension(120, 35));
        panel.add(header, BorderLayout.NORTH);

        return panel;
    }

    public JPanel initEstimatedMMRLabel() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel estimatedMMR = new JPanel(new GridLayout(10, 1));

        for(int i = 0; i < 10; i++) {
            JLabel label = new JLabel(NO_DATA, SwingConstants.CENTER);
            label.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, separatorColor));

            label.setPreferredSize(new Dimension(110, 45));

            if(i < 5)
                label.setBackground(radiantColor);
            else
                label.setBackground(direColor);

            label.setOpaque(true);

            estimatedMMRLabel[i] = label;
            estimatedMMR.add(label);
        }

        panel.add(estimatedMMR, BorderLayout.CENTER);

        JLabel header = new JLabel("Estimated MMR:", SwingConstants.CENTER);
        header.setPreferredSize(new Dimension(100, 35));
        panel.add(header, BorderLayout.NORTH);

        return panel;
    }

    public JPanel initWinsLosesPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel winsLoses = new JPanel(new GridLayout(10, 1));

        for(int i = 0; i < 10; i++) {
            JLabel label = new JLabel(NO_DATA, SwingConstants.CENTER);
            label.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, separatorColor));

            label.setPreferredSize(new Dimension(110, 45));

            if(i < 5)
                label.setBackground(radiantColor);
            else
                label.setBackground(direColor);

            label.setOpaque(true);

            winsLosesLabels[i] = label;
            winsLoses.add(label);
        }

        panel.add(winsLoses, BorderLayout.CENTER);

        JLabel header = new JLabel("Wins/Loses:", SwingConstants.CENTER);
        header.setPreferredSize(new Dimension(100, 35));
        panel.add(header, BorderLayout.NORTH);

        return panel;
    }

    public JPanel initWinrateLabels() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel winRate = new JPanel(new GridLayout(10, 1));

        for(int i = 0; i < 10; i++) {
            JLabel label = new JLabel(NO_DATA, SwingConstants.CENTER);
            label.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, separatorColor));

            label.setPreferredSize(new Dimension(110, 45));

            if(i < 5)
                label.setBackground(radiantColor);
            else
                label.setBackground(direColor);

            label.setOpaque(true);

            winRateLabels[i] = label;
            winRate.add(label);
        }

        panel.add(winRate, BorderLayout.CENTER);

        JLabel header = new JLabel("Win rate:", SwingConstants.CENTER);
        header.setPreferredSize(new Dimension(100, 35));
        panel.add(header, BorderLayout.NORTH);

        return panel;
    }

    public JPanel initKDALabels() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel kda = new JPanel(new GridLayout(10, 1));

        for(int i = 0; i < 10; i++) {
            JLabel label = new JLabel(NO_DATA, SwingConstants.CENTER);
            label.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, separatorColor));

            label.setPreferredSize(new Dimension(110, 45));

            if(i < 5)
                label.setBackground(radiantColor);
            else
                label.setBackground(direColor);

            label.setOpaque(true);

            kdaLabels[i] = label;
            kda.add(label);
        }

        panel.add(kda, BorderLayout.CENTER);

        JLabel header = new JLabel("KDA:", SwingConstants.CENTER);
        header.setPreferredSize(new Dimension(100, 35));
        panel.add(header, BorderLayout.NORTH);

        return panel;
    }

    public JPanel initMostPlayedLabels() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel mostPlayed = new JPanel(new GridLayout(10, 1));

        for(int i = 0; i < 10; i++) {
            JLabel label = new JLabel(NO_DATA, SwingConstants.CENTER);
            label.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, separatorColor));

            label.setPreferredSize(new Dimension(350, 45));

            if(i < 5)
                label.setBackground(radiantColor);
            else
                label.setBackground(direColor);

            label.setOpaque(true);

            mostPlayedLabels[i] = label;
            mostPlayed.add(label);
        }

        panel.add(mostPlayed, BorderLayout.CENTER);

        JLabel header = new JLabel("Most played heroes:", SwingConstants.CENTER);
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

    public void setEstimatedMMR(int mmr, int index) {
        if(mmr != Integer.MIN_VALUE) {
            estimatedMMRLabel[index].setText(" " + mmr + " ");
        }
    }

    public void setWinsLosesLabel(int wins, int loses, int abandoned, int index) {
        if(wins == 0 && loses == 0)
            winsLosesLabels[index].setText(NO_DATA);
        else {
            String string = " " + wins + " / " + loses + " ";
            if(abandoned != 0)
                string += "(ab. " + abandoned + ") ";

            winsLosesLabels[index].setText(string);

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
