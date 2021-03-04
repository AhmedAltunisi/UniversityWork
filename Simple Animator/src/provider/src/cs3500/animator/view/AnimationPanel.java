package provider.src.cs3500.animator.view;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

/** This class handles the animation panel on the frame. */
public class AnimationPanel extends JPanel {
  private List<AnimationStep> shapes;

  /**
   * Public constructor for the Animation Panel that instantiates the background to white and the
   * shapes to a new hashmap.
   */
  public AnimationPanel() {
    super();
    this.setBackground(Color.WHITE);
    shapes = new ArrayList<>();
  }

  /**
   * This sets the map to the values that are going to be painted next.
   *
   * @param nextSetAnimations The map of the next animations to be painted mapped to their name
   */
  public void setNextAnimation(List<AnimationStep> nextSetAnimations) {
    shapes = new ArrayList<>(nextSetAnimations);
  }

  /**
   * This paints the components in the map, rendering them on the frame.
   *
   * @param g The graphics object passed
   */
  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;

    for (AnimationStep shapeState : shapes) {
      shapeState.draw(g2d);
    }
  }
}
