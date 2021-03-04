package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

/**
 * Represents the panel of our visual view.
 */
public class DrawPanel extends JPanel {
  private final List<ViewShapes> shapes;

  /**
   * A constructor, creates a DrawPanel by taking in a width and a height.
   * @param w width
   * @param h height
   */

  public DrawPanel(int w, int h) {
    super();
    setPreferredSize(new Dimension(w,h));
    setBackground(Color.WHITE);
    shapes = new ArrayList<>();
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    for (ViewShapes shape : shapes) {
      shape.render((Graphics2D) g);
    }
  }

  /**
   * Draws the shape that is given.
   * @param shape the shape to be drawn.
   */
  public void drawShape(ViewShapes shape) {
    for (int i = 0; i < shapes.size(); i++) {
      if (shapes.get(i).getName().equals(shape.getName())) {
        shapes.set(i, shape);
        return;
      }
    }
    shapes.add(shape);
  }


}
