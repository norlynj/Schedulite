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
    private MainPanel mainPanel;
    private Panel contentPane;
    private CardLayout cardLayout;

    public Schedulite(){
        frame = new Frame("Schedulite");

        // create Panels
        menuPanel = new MenuPanel();
        howPanel = new HowPanel();
        mainPanel = new MainPanel();
        inputDecisionPanel = new InputDecisionPanel();

        // setup the content pane and card layout
        contentPane = new Panel(true, "bg/menu.png");
        cardLayout = new CardLayout();
        contentPane.setLayout(cardLayout);

        // add the panels to the content pane
        contentPane.add(menuPanel, "menuPanel");
        contentPane.add(howPanel, "howPanel");

        contentPane.add(mainPanel, "mainPanel");
        contentPane.add(inputDecisionPanel, "inputDecisionPanel");

        listenToMenu();
        listenToInputDecision();

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
        inputDecisionPanel.getUserDefinedButton();
        inputDecisionPanel.getRandomButton();
        inputDecisionPanel.getMusicButton();
        inputDecisionPanel.getHomeButton().addActionListener(e -> cardLayout.show(contentPane, "menuPanel" ));
    }

}
