import javax.swing.*;
import java.awt.*;

public class ProcessArrival extends JPanel implements Runnable {
    private static final int RECT_WIDTH = 50;
    private static final int RECT_HEIGHT = 30;
    private static final int ANIMATION_DELAY = 10;
    private int numRectangles;
    private int xPosition;
    private int[] rectangleXPositions;
    private boolean[] rectangleArrived;
    private Color[] colors = {Color.BLUE, Color.RED, Color.GREEN, Color.ORANGE, Color.MAGENTA}; // array of colors for each rectangle

    public ProcessArrival(int numRectangles) {
        this.numRectangles = numRectangles;
        this.xPosition = getWidth(); // start at the right edge of the panel
        this.rectangleXPositions = new int[numRectangles];
        this.rectangleArrived = new boolean[numRectangles];
        for (int i = 0; i < numRectangles; i++) {
            rectangleXPositions[i] = getWidth() + (i + 1) * (RECT_WIDTH + 10); // start each rectangle outside the panel
        }
    }

    @Override
    public void run() {
        for (int i = 0; i < numRectangles; i++) {
            while (rectangleXPositions[i] > 0) {
                try {
                    Thread.sleep(ANIMATION_DELAY);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                rectangleXPositions[i]--;
                repaint();
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < numRectangles; i++) {
            g.setColor(colors[i]);
            g.fillRect(rectangleXPositions[i], 0, RECT_WIDTH, RECT_HEIGHT); // draw the individual rectangle
            g.setColor(Color.BLACK);
            g.drawRect(rectangleXPositions[i], 0, RECT_WIDTH, RECT_HEIGHT); // draw the border
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Process Arrival");
        ProcessArrival processArrival = new ProcessArrival(5); // create 5 rectangles
        frame.add(processArrival);
        frame.setSize(500, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        Thread thread = new Thread(processArrival);
        thread.start();
    }
}
