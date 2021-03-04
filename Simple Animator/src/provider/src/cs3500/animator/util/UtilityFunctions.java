package provider.src.cs3500.animator.util;

import provider.src.cs3500.animator.model.Animation;
import provider.src.cs3500.animator.model.Shape;
import provider.src.cs3500.animator.view.AnimationStep;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * This is a generic utility function class that we built to abstract out some of the repeat code
 * from the composite and graphic view classes. This allows us to repeat generic code so our project
 * files are reduced in size.
 */
public class UtilityFunctions {
  /**
   * This function handles the interpolation of the shapes along the timeline. It takes our shapes
   * and the start and end states of their animation, then finds where their state is at any given
   * point on the timeline using the provided interpolation equation from the assignment.
   *
   * @param animations List of animations to be rendered
   * @return The sorted map of each shapes state at every tick
   */
  public static List<List<AnimationStep>> interpolate(
      List<Animation> animations, int xOffset, int yOffset) {
    SortedMap<Integer, List<AnimationStep>> timelineMap = new TreeMap<>();
    for (Animation a : animations) {
      int prevTime = 0;
      Shape startShape = a.getStartShape();
      for (Map.Entry<Integer, Shape> entry :
          a.getShapeTimeline().entrySet()) {
        Shape shape = entry.getValue();
        float total = entry.getKey();
        float x = startShape.getPosition().getX();
        float y = startShape.getPosition().getY();
        float w = startShape.getScale().getX();
        float h = startShape.getScale().getY();
        float r = startShape.getColor().getRed();
        float g = startShape.getColor().getGreen();
        float b = startShape.getColor().getBlue();
        for (int i = prevTime; i <= total; i++) {
          float tempx =
              x * ((total - i) / (total - prevTime))
                  + shape.getPosition().getX() * ((i - prevTime) / (total - prevTime));
          float tempy =
              y * ((total - i) / (total - prevTime))
                  + shape.getPosition().getY() * ((i - prevTime) / (total - prevTime));
          float tempw =
              w * ((total - i) / (total - prevTime))
                  + shape.getScale().getX() * ((i - prevTime) / (total - prevTime));
          float temph =
              h * ((total - i) / (total - prevTime))
                  + shape.getScale().getY() * ((i - prevTime) / (total - prevTime));
          float tempr =
              r * ((total - i) / (total - prevTime))
                  + shape.getColor().getRed() * ((i - prevTime) / (total - prevTime));
          float tempg =
              g * ((total - i) / (total - prevTime))
                  + shape.getColor().getGreen() * ((i - prevTime) / (total - prevTime));
          float tempb =
              b * ((total - i) / (total - prevTime))
                  + shape.getColor().getBlue() * ((i - prevTime) / (total - prevTime));
          Color temp = new Color((int) tempr, (int) tempg, (int) tempb);
          List<AnimationStep> tempList = timelineMap.get(i);
          if (tempList == null || tempList.isEmpty()) {
            tempList = new ArrayList<>();
          }
          if (prevTime < a.getStartTick()) {
            tempList.add(
                new AnimationStep(
                    0, 0, 0, 0, new Color(255, 255, 255), shape.getType(), shape.getName()));
          } else {
            tempList.add(
                new AnimationStep(
                    (int) tempx - xOffset,
                    (int) tempy - yOffset,
                    (int) temph,
                    (int) tempw,
                    temp,
                    shape.getType(),
                    shape.getName()));
          }
          timelineMap.put(i, tempList);
        }
        prevTime = entry.getKey();
        startShape = entry.getValue();
      }
    }
    return new ArrayList<>(timelineMap.values());
  }
}
