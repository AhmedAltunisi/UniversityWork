package provider.src.cs3500.animator.view;

import provider.src.cs3500.animator.model.Animation;
import provider.src.cs3500.animator.model.Shape;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * This class is responsible for outputting the textual description of the steps in a passed
 * animation in the format specified where the individual operations are listed with timestamps in
 * and differences in either dimension, position, or color.
 */
public class AnimatorTextView implements AnimatorView {
  Appendable output;

  /**
   * Public constructor for the text view.
   *
   * @param out The Appendable to put the text output into
   */
  public AnimatorTextView(Appendable out) {
    this.output = out;
  }

  /**
   * The render method for the text animation. This is what creates the string to be output.
   *
   * @param animations List of the animation operations to be done during the animation
   * @param tickPerSecond a frame-rate with which to run the animation
   * @param canvasWidth The width of the canvas
   * @param canvasHeight The height of the canvas
   * @param xOffset The x offset of the animation
   * @param yOffset The y offset of the animation
   */
  @Override
  public void render(
      List<Animation> animations,
      int tickPerSecond,
      int canvasWidth,
      int canvasHeight,
      int xOffset,
      int yOffset) {
    if (Objects.isNull(animations)
        || animations.size() <= 0
        || tickPerSecond < 0
        || canvasWidth <= -1
        || canvasHeight <= -1) {
      throw new IllegalArgumentException("Invalid Parameters Passed");
    }
    float timeStep = (1 / (float) tickPerSecond);
    StringBuilder outString = new StringBuilder();

    outString.append(
        String.format("canvas %d %d %d %d \n", xOffset, yOffset, canvasWidth, canvasHeight));

    for (Animation a : animations) {
      outString.append(
          String.format(
              ("shape %s %s\n"), a.getStartShape().getName(), a.getStartShape().getType()));

      Iterator<Map.Entry<Integer, Shape>> ahead = a.getShapeTimeline().entrySet().iterator();
      ahead.next();
      for (Map.Entry<Integer, Shape> keyFrame : a.getShapeTimeline().entrySet()) {
        if (!ahead.hasNext()) {
          break;
        }
        Map.Entry<Integer, Shape> nextKeyFrame = ahead.next();
        outString.append(
            String.format(
                "motion %s %f %d %d %d %d %d %d %d \t",
                keyFrame.getValue().getName(),
                timeStep * keyFrame.getKey(),
                keyFrame.getValue().getPosition().getX(),
                keyFrame.getValue().getPosition().getY(),
                keyFrame.getValue().getScale().getX(),
                keyFrame.getValue().getScale().getY(),
                keyFrame.getValue().getColor().getRed(),
                keyFrame.getValue().getColor().getGreen(),
                keyFrame.getValue().getColor().getBlue()));

        outString.append(
            String.format(
                "%f %d %d %d %d %d %d %d \n",
                timeStep * nextKeyFrame.getKey(),
                nextKeyFrame.getValue().getPosition().getX(),
                nextKeyFrame.getValue().getPosition().getY(),
                nextKeyFrame.getValue().getScale().getX(),
                nextKeyFrame.getValue().getScale().getY(),
                nextKeyFrame.getValue().getColor().getRed(),
                nextKeyFrame.getValue().getColor().getGreen(),
                nextKeyFrame.getValue().getColor().getBlue()));
      }
    }

    appendOutput(outString.toString());
  }

  /**
   * This is an overridden function for this view because it only applies to the interactive view.
   *
   * @param listener The valid animation listener that would be passed if this were the interactive
   *     view
   */
  @Override
  public void setListener(ActionListener listener) {
    throw new UnsupportedOperationException("This operation is not supported for this view type");
  }

  /**
   * This is an overridden function for this view because it only applies to the interactive view.
   */
  @Override
  public void toggleStart() {
    throw new UnsupportedOperationException("This method is not supported in this view");
  }

  /**
   * This is an overridden function for this view because it only applies to the interactive view.
   */
  @Override
  public void togglePause() {
    throw new UnsupportedOperationException("This method is not supported in this view");
  }

  /**
   * This is an overridden function for this view because it only applies to the interactive view.
   */
  @Override
  public void restartAnimation() {
    throw new UnsupportedOperationException("This method is not supported in this view");
  }

  /**
   * This is an overridden function for this view because it only applies to the interactive view.
   */
  @Override
  public void toggleLoop() {
    throw new UnsupportedOperationException("This method is not supported in this view");
  }

  /**
   * This is an overridden function for this view because it only applies to the interactive view.
   */
  @Override
  public void increaseSpeed() {
    throw new UnsupportedOperationException("This method is not supported in this view");
  }

  /**
   * This is an overridden function for this view because it only applies to the interactive view.
   */
  @Override
  public void decreaseSpeed() {
    throw new UnsupportedOperationException("This method is not supported in this view");
  }

  /**
   * This puts a string into the output stream.
   *
   * @param s The string to be output.
   */
  private void appendOutput(String s) {
    try {
      output.append(s);
    } catch (IOException e) {
      throw new IllegalStateException("Something went wrong appending to output in View");
    }
  }
}
