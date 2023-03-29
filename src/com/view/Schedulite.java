package view;
import view.component.AudioPlayer;
import  view.component.ImageButton;
import  view.component.Frame;
import  view.component.Panel;

import java.awt.*;

public class Schedulite {
    private Frame frame;
    private MenuPanel menuPanel;
    private HowPanel howPanel;
    private InputDecisionPanel inputDecisionPanel;
    private InputPanel inputPanel;
    private MainPanel mainPanel;
    private Panel contentPane;
    private CardLayout cardLayout;

    private AudioPlayer audio;

    public Schedulite(){
        audio = new AudioPlayer("bgmusic.wav");
        audio.play();
        audio.loop();
        frame = new Frame("Schedulite");

        // create Panels
        menuPanel = new MenuPanel();
        howPanel = new HowPanel();
        mainPanel = new MainPanel();
        inputDecisionPanel = new InputDecisionPanel();
        inputPanel = new InputPanel();

        // setup the content pane and card layout
        contentPane = new Panel(true, "bg/menu.png");
        cardLayout = new CardLayout();
        contentPane.setLayout(cardLayout);

        // add the panels to the content pane
        contentPane.add(menuPanel, "menuPanel");
        contentPane.add(howPanel, "howPanel");

        contentPane.add(mainPanel, "mainPanel");
        contentPane.add(inputDecisionPanel, "inputDecisionPanel");
        contentPane.add(inputPanel, "inputPanel");

        listenToMenu();
        listenToInput();
        listenToInputDecision();
        listenToHow();

        frame.add(contentPane);
        frame.pack();
        frame.setVisible(true);
    }

    public void listenToMenu() {
        menuPanel.getGetStartedButton().addActionListener(e -> cardLayout.show(contentPane, "inputDecisionPanel" ));
        menuPanel.getHowItWorksButton().addActionListener(e -> cardLayout.show(contentPane, "howPanel" ));
        menuPanel.getExitButton().addActionListener(e -> System.exit(0));
    }

    public void listenToInputDecision(){
        inputDecisionPanel.getFromATextFileButton();
        inputDecisionPanel.getUserDefinedButton().addActionListener(e -> cardLayout.show(contentPane, "inputPanel" ));
        inputDecisionPanel.getRandomButton();
        inputDecisionPanel.getMusicButton();
        inputDecisionPanel.getHomeButton().addActionListener(e -> cardLayout.show(contentPane, "menuPanel" ));
    }

    public void listenToHow(){
        howPanel.getMusicButton();
        howPanel.getHomeButton().addActionListener(e -> cardLayout.show(contentPane, "menuPanel" ));
    }

    public void listenToInput(){
        inputPanel.getMusicButton();
        inputPanel.getHomeButton().addActionListener(e -> cardLayout.show(contentPane, "menuPanel" ));
    }

}
