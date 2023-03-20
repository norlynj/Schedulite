package view;
import view.component.Frame;
import  view.component.ImageButton;
import view.component.Panel;

import javax.swing.*;
import java.util.Objects;

public class MenuPanel extends Panel{


    private Panel menu;
    private ImageButton getStartedButton;
    private ImageButton howItWorksButton;
    private ImageButton exitButton;
    private ImageButton infoButton;

    public MenuPanel(){
        super("bg/menu.png");

        getStartedButton = new ImageButton("button/get-started.png");
        howItWorksButton = new ImageButton("button/how.png");
        exitButton = new ImageButton("button/quit.png");
        infoButton = new ImageButton("button/info.png");

        getStartedButton.setBounds(57, 400, 373, 76);
        howItWorksButton.setBounds(57, 510, 373, 76);
        exitButton.setBounds(57, 620, 373, 76);
        infoButton.setBounds(1006, 35, 47, 47);

        setListeners();

        ImageIcon background = new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/images/bg/menu.gif")));

        JLabel bgImage = new JLabel();

        bgImage.setBounds(0, 0, 1100, 800);
        bgImage.setIcon(background);
        bgImage.add(getStartedButton);
        bgImage.add(howItWorksButton);
        bgImage.add(exitButton);
        bgImage.add(infoButton);

        this.add(bgImage);

    }

    private void setListeners(){
        getStartedButton.hover("button/get-started-hover.png", "button/get-started.png");
        howItWorksButton.hover("button/how-hover.png", "button/how.png");
        exitButton.hover("button/quit-hover.png", "button/quit.png");
        infoButton.hover("button/info-hover.png", "button/info.png");

    }

    public static void main(String[] args) {
        MenuPanel m = new MenuPanel();
        Frame frame = new Frame("Menu Panel");
        frame.add(m);
        frame.setVisible(true);
    }

    public Panel getMenu() {
        return menu;
    }

    public ImageButton getGetStartedButton() {
        return getStartedButton;
    }

    public ImageButton getHowItWorksButton() {
        return howItWorksButton;
    }

    public ImageButton getExitButton() {
        return exitButton;
    }

    public ImageButton getInfoButton() {
        return infoButton;
    }
}
