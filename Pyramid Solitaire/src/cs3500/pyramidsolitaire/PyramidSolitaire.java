package cs3500.pyramidsolitaire;

import cs3500.pyramidsolitaire.controller.PyramidSolitaireController;
import cs3500.pyramidsolitaire.controller.PyramidSolitaireTextualController;
import cs3500.pyramidsolitaire.model.hw02.Cards;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import cs3500.pyramidsolitaire.model.hw04.PyramidSolitaireCreator;
import cs3500.pyramidsolitaire.model.hw04.PyramidSolitaireCreator.GameType;
import java.io.InputStreamReader;



/**
 *A class who's main functionality allows for users to play Pyramid Solitaire through system.in.
 */
public class PyramidSolitaire {

  /**
   * The main method for this game, allows users to interact with the game through the controller.
   * @param args        Allows for the game to be run.
   */
  public static void main(String[] args) {
    String modelName = args[0];
    PyramidSolitaireController controller =
        new PyramidSolitaireTextualController(new InputStreamReader(System.in), System.out);
    if (args.length == 3) {
      int numRows = Integer.parseInt(args[1]);
      int numDraw = Integer.parseInt(args[2]);

      if (modelName.equalsIgnoreCase("basic")) {
        PyramidSolitaireModel<Cards> model = PyramidSolitaireCreator.create(GameType.BASIC);

        try {
          controller.playGame(model,model.getDeck(), false, numRows, numDraw);
        }
        catch (IllegalStateException e) {
          controller.playGame(model,model.getDeck(), false, 7, 3);
        }
      }
      else {
        if (modelName.equalsIgnoreCase("relaxed")) {


          PyramidSolitaireModel<Cards> model = PyramidSolitaireCreator.create(GameType.RELAXED);

          try {
            controller.playGame(model,model.getDeck(), false, numRows, numDraw);
          }
          catch (IllegalStateException e) {
            controller.playGame(model,model.getDeck(), false, 7, 3);
          }
        }
        else {
          if (modelName.equalsIgnoreCase("multipyramid")) {
            PyramidSolitaireModel<Cards> model =
                PyramidSolitaireCreator.create(GameType.MULTIPYRAMID);
            try {
              controller.playGame(model,model.getDeck(), false, numRows, numDraw);
            }
            catch (IllegalStateException e) {
              controller.playGame(model,model.getDeck(), false, 7, 3);
            }
          }
        }
      }
    }
    else {
      if (modelName.equalsIgnoreCase("basic")) {
        PyramidSolitaireModel<Cards> model = PyramidSolitaireCreator.create(GameType.BASIC);

        controller.playGame(model,model.getDeck(), false, 7, 3);
      }
      else {
        if (modelName.equalsIgnoreCase("relaxed")) {
          PyramidSolitaireModel<Cards> model = PyramidSolitaireCreator.create(GameType.RELAXED);
          controller.playGame(model,model.getDeck(), false, 7, 3);
        }
        else {
          if (modelName.equalsIgnoreCase("multipyramid")) {
            PyramidSolitaireModel<Cards> model =
                PyramidSolitaireCreator.create(GameType.MULTIPYRAMID);
            controller.playGame(model,model.getDeck(), false, 7, 3);
          }
        }
      }
    }

  }
}
