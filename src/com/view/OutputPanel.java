package view;

import view.component.Frame;
import view.component.ImageButton;
import view.component.Panel;
import view.component.Label;
import model.CustomTableModel;

public class OutputPanel extends Panel{
    private ImageButton musicButton;
    private ImageButton homeButton;
    public OutputPanel() {
        super("bg/output-panel-bg.png");

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
        OutputPanel m = new OutputPanel();
        Frame frame = new Frame("Output Panel");
        frame.add(m);
        frame.setVisible(true);
    }

}

