package view;

import view.component.Frame;
import view.component.ImageButton;
import view.component.Panel;

import javax.swing.*;
import java.util.Objects;

public class InputPanel extends Panel {
    private ImageButton musicButton;
    private ImageButton homeButton;

    public InputPanel() {

        super("bg/temp-input-bg.png");

        musicButton = new ImageButton("button/music-on.png");
        homeButton = new ImageButton("button/home.png");

        musicButton.setBounds(945, 40, 47, 47);
        homeButton.setBounds(1010, 40, 47, 47);

        setListeners();





        this.add(musicButton);
        this.add(homeButton);

    }

    private void setListeners() {
        musicButton.hover("button/music-off-hover.png", "button/music-on.png");
        homeButton.hover("button/home-hover.png", "button/home.png");
    }

    public static void main(String[] args) {
        InputPanel m = new InputPanel();
        Frame frame = new Frame("Input Panel");
        frame.add(m);
        frame.setVisible(true);
    }

    public ImageButton getMusicButton() {
        return musicButton;
    }

    public ImageButton getHomeButton() {
        return homeButton;
    }
}

