package view;
import view.component.Frame;
import  view.component.ImageButton;
import view.component.Panel;

import javax.swing.*;
import java.util.Objects;

public class HowPanel extends Panel{

    private ImageButton musicButton, homeButton, defButton, termsButton, backButton;
    public HowPanel() {

        super("bg/how-it-works.png");

        musicButton = new ImageButton("button/music-on.png");
        homeButton = new ImageButton("button/home.png");
        defButton = new ImageButton("button/definition.png");
        termsButton = new ImageButton("button/terms.png");
        backButton = new ImageButton("button/back.png");

        musicButton.setBounds(945, 40, 47, 47);
        homeButton.setBounds(1010, 40, 47, 47);
        defButton.setBounds(187, 666, 353, 47);
        termsButton.setBounds(572, 666, 256, 47);
        backButton.setBounds(282, 112, 47, 47);

        backButton.setVisible(false);
        setListeners();

        this.add(musicButton);
        this.add(homeButton);
        this.add(defButton);
        this.add(termsButton);
        this.add(backButton);
    }

    private void setListeners() {
        musicButton.hover("button/music-off-hover.png", "button/music-on.png");
        homeButton.hover("button/home-hover.png", "button/home.png");
        defButton.hover("button/definition-hover.png", "button/definition.png");
        termsButton.hover("button/terms-hover.png", "button/terms.png");
        backButton.hover("button/back-hover.png", "button/back.png");
        listenToButtonClicks();
    }

    private void listenToButtonClicks() {
        defButton.addActionListener(e -> {
            setImage("bg/how-1.png");
            backButton.setVisible(true);
            termsButton.setVisible(true);
            termsButton.setBounds(422, 635, 256, 47);
            defButton.setVisible(false);
        });
        termsButton.addActionListener(e -> {
            setImage("bg/how-2.png");
            backButton.setVisible(true);
            defButton.setVisible(true);
            defButton.setBounds(373, 680, 353, 47);
            termsButton.setVisible(false);
        });
        backButton.addActionListener(e -> {
            setImage("bg/how-it-works.png");
            defButton.setVisible(true);
            termsButton.setVisible(true);
            defButton.setBounds(187, 666, 353, 47);
            termsButton.setBounds(572, 666, 256, 47);
            backButton.setVisible(false);
        });
    }

    public static void main(String[] args) {
        HowPanel m = new HowPanel();
        Frame frame = new Frame("How Panel");
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
