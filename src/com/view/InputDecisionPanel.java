package view;
import view.component.Frame;
import  view.component.ImageButton;
import view.component.Panel;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;

public class InputDecisionPanel extends Panel{
    private final ImageButton fromATextFileButton, userDefinedButton, randomButton, musicButton, homeButton;
    public InputDecisionPanel() {
        super("bg/menu2.gif");



        fromATextFileButton = new ImageButton("button/from-file.png");
        userDefinedButton = new ImageButton("button/user-defined.png");
        randomButton = new ImageButton("button/random.png");

        musicButton = new ImageButton("button/music-on.png");
        homeButton = new ImageButton("button/home.png");

        fromATextFileButton.setBounds(57, 390, 370, 63);
        userDefinedButton.setBounds(57, 490, 373, 76);
        randomButton.setBounds(57, 590, 373, 76);
        musicButton.setBounds(945, 40, 47, 47);
        homeButton.setBounds(1010, 40, 47, 47);

        setListeners();

        ImageIcon background = new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/images/bg/menu.gif")));

        JLabel bgImage = new JLabel();

        bgImage.setBounds(0, 0, 1100, 800);
        bgImage.setIcon(background);
        bgImage.add(fromATextFileButton);
        bgImage.add(userDefinedButton);
        bgImage.add(randomButton);
        bgImage.add(musicButton);
        bgImage.add(homeButton);

        this.add(bgImage);
    }

    private void setListeners() {
        fromATextFileButton.hover("button/from-file-hover.png", "button/from-file.png");
        userDefinedButton.hover("button/user-defined-hover.png", "button/user-defined.png");
        randomButton.hover("button/random-hover.png", "button/random.png");
        musicButton.hover("button/music-off-hover.png", "button/music-on.png");
        homeButton.hover("button/home-hover.png", "button/home.png");
    }

    public ArrayList getDataFromFiles() {
        String resourcePath = "/resources/text/";
        URL resourceUrl = InputDecisionPanel.class.getResource(resourcePath);

        // Convert the URL to a file object
        assert resourceUrl != null;
        File resourceFile = new File(resourceUrl.getPath());
        JFileChooser fileChooser = new JFileChooser(resourceFile);
        fileChooser.setDialogTitle("Select file with processes separated by new line and values separated by comma");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Text files", "txt"));
        int result = fileChooser.showOpenDialog((Component) null);
        if (result != JFileChooser.APPROVE_OPTION) {
            JOptionPane.showMessageDialog(null, "No file selected");
        }

        // Read the input file and store the values in a 2-dimensional array
        String inputFileName = fileChooser.getSelectedFile().getPath();
        ArrayList<int[]> valuesList = new ArrayList<int[]>();
        try (BufferedReader br = new BufferedReader(new FileReader(inputFileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(",");
                if (tokens.length != 3) {
                    JOptionPane.showMessageDialog(null, "Invalid line: " + line);
                    continue;
                }
                int[] values = new int[3];
                for (int i = 0; i < 3; i++) {
                    values[i] = Integer.parseInt(tokens[i]);
                }
                valuesList.add(values);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error reading file: " + e.getMessage());
            return valuesList;
        }
        return valuesList;

    }


    public static void main(String[] args) {
        InputDecisionPanel m = new InputDecisionPanel();
        Frame frame = new Frame("Input Decision Panel");
        frame.add(m);
        frame.setVisible(true);
    }

    public ImageButton getFromATextFileButton() {
        return fromATextFileButton;
    }

    public ImageButton getUserDefinedButton() {
        return userDefinedButton;
    }

    public ImageButton getRandomButton() {
        return randomButton;
    }

    public ImageButton getMusicButton() {
        return musicButton;
    }

    public ImageButton getHomeButton() {
        return homeButton;
    }
}
