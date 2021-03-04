package view;

import controller.Features;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.Timer;
import viewmodel.IViewModel;

/**
 * Represents an interactive view. It allows the user to control the animation. Uses a
 * VisualAnimationView and delegates to that object to complete some of the functionality for this
 * class.
 */
public class InteractiveView extends JFrame implements EasyInteractiveView {


  private final EasyVisualView view;
  private final int lastTick;
  private final DrawPanel panel;
  private final JButton start;
  private final JButton resume;
  private final JButton speedUp;
  private final JButton slowDown;
  private final JToggleButton loops;
  private Timer timer;
  private int currentTick;


  /**
   * A constructor, takes in a model to generate a visual view based on it, which will act as the
   * crux of our interactive view.
   *
   * @param visualView the model to be used to generate the visual view used by this view and the
   *                   last tick int.
   */
  public InteractiveView(EasyVisualView visualView) {

    this.view = visualView;
    this.lastTick = visualView.getFinalTick();
    IViewModel model = view.getModel();
    setSize(model.getWidth(), model.getHeight());
    setLocation(model.getX(), model.getY());
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    this.setLayout(new BorderLayout());

    this.currentTick = 0;
    panel = new DrawPanel(model.getWidth(), model.getHeight());
    add(panel, BorderLayout.CENTER);

    JPanel buttons = new JPanel(new FlowLayout());
    add(buttons, BorderLayout.SOUTH);

    JScrollPane scroll = new JScrollPane(panel);
    scroll.setPreferredSize(new Dimension(800, 300));
    this.add(scroll);

    start = new JButton("Start");
    buttons.add(start);

    resume = new JButton("Play/Pause");
    resume.setEnabled(false);
    buttons.add(resume);

    slowDown = new JButton("Slow Down");
    buttons.add(slowDown);

    speedUp = new JButton("Speed Up");
    buttons.add(speedUp);

    loops = new JToggleButton("Loop");
    buttons.add(loops);

    pack();
    setVisible(true);
  }

  /**
   * Method used to render an animation model using the constraints of the specific animation view
   * class.
   *
   * @param tickPerSecond Amount of ticks per second used for this rendering of the view
   */
  @Override
  public void render(int tickPerSecond) {
    int delay = (int) (((double) 1 / (double) tickPerSecond) * 1000.0);
    this.timer = new Timer(delay, e -> {
      view.updateView(panel, currentTick);
      if (currentTick > 0) {
        resume.setEnabled(true);
      }

      currentTick++;

      if (currentTick == 1) {
        resume.setText("Pause");
      }
      if (currentTick >= lastTick) {
        resume.setEnabled(false);
        resume.setText("Play/Pause");
      }

      if (currentTick == lastTick && loops.isSelected()) {
        currentTick = 0;
        timer.restart();
      } else {
        if (currentTick == lastTick) {
          timer.stop();
        }
      }
    });
  }

  @Override
  public void toggleLoop() {

    if (lastTick == currentTick & loops.isSelected()) {
      timer.restart();
      currentTick = 0;
    }
  }

  @Override
  public void speedUp() {
    int delay = timer.getDelay();
    timer.setDelay(Math.max(delay - 10, 0));
  }

  @Override
  public void slowDown() {
    timer.setDelay(timer.getDelay() + 10);
  }

  @Override
  public void pauseUnpause() {
    if (currentTick == 0) {
      timer.stop();
    } else {
      if (timer.isRunning()) {
        timer.stop();
        resume.setText("Resume");
      } else {

        timer.restart();
        resume.setText("Pause");
      }
    }

  }

  @Override
  public void startRestart() {
    if (timer.isRunning() || currentTick == lastTick) {
      timer.restart();
      currentTick = 0;
    } else {
      if (currentTick == 0) {
        timer.start();
        start.setText("Restart");
      }
    }
  }

  @Override
  public void addFeatures(Features features) {
    start.addActionListener(e -> features.toggleStart());
    resume.addActionListener(e -> features.pauseUnpause());
    speedUp.addActionListener(e -> features.speedUp());
    slowDown.addActionListener(e -> features.slowDown());
    loops.addActionListener(e -> features.toggleLoop());
  }
}
