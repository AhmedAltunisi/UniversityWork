package animations;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import model.moves.ShapeState;
import model.shapes.Position2D;

/**
 * A program that can generate a text file that when read, can generate an animated representation
 * of the insertion sort algorithm.
 */
public final class InsertionSort {

  /**
   * The main method, it generates the text file.
   *
   * @param args any arguments found in the command line will not be parsed.
   * @throws IOException when something goes wrong
   */
  public static void main(String[] args) throws IOException {

    FileWriter fw = new FileWriter("insertionSort.txt");
    Appendable ap = fw;

    ap.append("canvas 0 0 800 800").append("\n");

    List<Integer> heights = new ArrayList<>(Arrays.asList(20, 40, 60, 80, 100));
    List<Integer> xPositions = new ArrayList<>(Arrays.asList(100, 200, 300, 400, 500));
    int width = 20;
    int tick = 1;

    Collections.shuffle(heights);

    ShapeState[] shapeStates = new ShapeState[5];
    for (int i = 0; i < 5; i++) {
      ap.append("shape rect").append(String.valueOf(i)).append(" rectangle").append("\n");

      shapeStates[i] = new ShapeState(0, xPositions.get(i), 400,
          heights.get(i), width, 160, 255, 50);
      ShapeState js = shapeStates[i];

      ap.append("motion rect" + (i) + " " + 0 + " " + js.getPos()
          + " " + width + " " + js.getHeight() + " " + js.getColor().toString() + " " + 1
          + " " + js.getPos()
          + " " + width + " " + js.getHeight() + " " + js.getColor().toString()).append("\n");
    }

    int length = shapeStates.length;

    for (int i = 1; i < length; i++) {
      ShapeState key = shapeStates[i];
      int j = i - 1;

      while (j >= 0
          && shapeStates[j].getHeight() > key.getHeight()) {

        ShapeState js1 = new ShapeState(shapeStates[j]);
        ShapeState js2 = new ShapeState(shapeStates[j + 1]);

        heights.indexOf(js1.getHeight());

        ap.append("motion rect").append(String.valueOf(heights.indexOf(js1.getHeight()))).append(" "
            + tick + " ").append((js1.getPos().getX()) + " " + js1.getPos().getY() + " "
            + " " + width + " " + js1.getHeight() + " " + js1.getColor().toString() + " ").append(
            (tick + 10) + " ").append((js2.getPos().getX()) + " " +
            js1.getPos().getY() + " " +
            width + " " + js1.getHeight() + " " + js1.getColor().toString()).append("\n");
        ap.append("motion rect" + (heights.indexOf(js2.getHeight())) + " " + tick + " " +
            js2.getPos() + " " + width + " " + js2.getHeight() + " " + js2.getColor().toString() +
            " " + (tick + 10) + " " +
            (js1.getPos().getX()) + " " + js2.getPos().getY() + " " +
            width + " " + js2.getHeight() + " " + js2.getColor().toString()).append("\n");

        tick = tick + 10;

        shapeStates[j + 1] = new ShapeState(tick,
            new Position2D(js2.getPos().getX(), js1.getPos().getY()), js1.getHeight(), width,
            js1.getColor());
        shapeStates[j] = new ShapeState(tick,
            new Position2D(js1.getPos().getX(), js2.getPos().getY()), js2.getHeight(), width,
            js2.getColor());
        j--;
      }


    }

    fw.close();
    System.out.println(Arrays.toString(shapeStates));
  }

}
