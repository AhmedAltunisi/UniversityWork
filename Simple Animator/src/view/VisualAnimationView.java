package view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.Timer;
import model.moves.Moves;
import model.shapes.Shapes;
import viewmodel.IViewModel;

/**
 * Represents the visual view of an animation.
 */
public class VisualAnimationView extends JFrame implements EasyVisualView {

  private final IViewModel model;
  private final DrawPanel panel;
  private Timer timer;
  private int currentTick;
  private final int lastTick;

  /**
   * A constructor, takes in the model and uses it's information to generate an animation.
   * @param model the model.
   */
  public VisualAnimationView(IViewModel model) {

    if (model == null) {
      throw new IllegalArgumentException("model cannot be null");
    }
    this.model = model;

    setSize(model.getWidth(), model.getHeight());
    setLocation(model.getX(),model.getY());
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    panel = new DrawPanel(model.getWidth(), model.getHeight());
    add(panel);

    JScrollPane scroll = new JScrollPane(panel);
    scroll.setPreferredSize(new Dimension(800,300));
    this.add(scroll);

    pack();
    setVisible(false);

    this.currentTick = 0;

    this.lastTick = model.getLastTick();
  }

  @Override
  public void render(int tickPerSecond) {
    setVisible(true);
    int delay = (int) (((double) 1 / (double) tickPerSecond) * 1000.0);
    this.timer = new Timer(delay, new ActionListener() {
      /**
       * Invoked when an action occurs.
       *
       * @param e the event to be processed
       */
      @Override
      public void actionPerformed(ActionEvent e) {
        updateView(panel, currentTick);
        currentTick++;

        if (currentTick == lastTick) {
          timer.stop();
        }
      }
    });
    timer.start();
  }

  @Override
  public void updateView(DrawPanel panel, int currentTick) {
    Map<Shapes, List<Moves>> map = model.getShapesAndMoves();
    for (Map.Entry<Shapes, List<Moves>> entry : map.entrySet()) {
      Shapes shape = entry.getKey();
      List<Moves> moves = entry.getValue();
      for (Moves move : moves) {
        if (!(move.isMoveFinished(currentTick))) {
          panel.drawShape(shape.makeViewShape(move.apply(currentTick)));
          panel.repaint();
        }
      }
    }
  }

  @Override
  public int getFinalTick() {
    return this.lastTick;
  }

  @Override
  public IViewModel getModel() {
    return this.model;
  }

}
