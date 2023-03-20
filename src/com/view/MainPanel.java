package view;
import view.component.Frame;
import  view.component.ImageButton;
import view.component.Panel;

public class MainPanel extends Panel{

    public MainPanel(){
        super("bg/main-panel-bg.png");
    }

    public static void main(String[] args) {
        MainPanel m = new MainPanel();
        Frame frame = new Frame("Menu Panel");
        frame.add(m);
        frame.setVisible(true);
    }
}
