package view;
import view.component.Frame;
import  view.component.ImageButton;
import view.component.Panel;
public class MenuPanel extends Panel{


    private Panel menu;

    public MenuPanel(){
        super("bg/menu.png");

    }

    public static void main(String[] args) {
        MenuPanel m = new MenuPanel();
        Frame frame = new Frame("Menu Panel");
        frame.add(m);
        frame.setVisible(true);
    }
}
