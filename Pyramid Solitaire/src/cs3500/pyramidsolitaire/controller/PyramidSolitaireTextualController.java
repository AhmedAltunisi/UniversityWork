package cs3500.pyramidsolitaire.controller;

import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import cs3500.pyramidsolitaire.view.PyramidSolitaireTextualView;
import cs3500.pyramidsolitaire.view.PyramidSolitaireView;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;


/**
 * A controller for Pyramid Solitaire, it allows the game to be played in a text based format.
 */
public class PyramidSolitaireTextualController implements PyramidSolitaireController {

  private final Readable rd;
  private final Appendable ap;

  /**
   * A constructor, allows for users to play the game given a readable and an appendable.
   * @param rd    A readable, a data type that allows inputs to be made
   * @param ap    An appendable, a data type that allows outputs to be registered
   * @throws IllegalArgumentException When given a null readable or appendable
   */
  public PyramidSolitaireTextualController(Readable rd, Appendable ap)
      throws IllegalArgumentException {

    if (rd == null || ap == null) {
      throw new IllegalArgumentException("Readable and appendable cannot be null");
    }
    this.rd = rd;
    this.ap = ap;
  }

  /**
   * The primary method for beginning and playing a game.
   *
   * @param model   The game of solitaire to be played
   * @param deck    The deck of cards to be used
   * @param shuffle Whether to shuffle the deck or not
   * @param numRows How many rows should be in the pyramid
   * @param numDraw How many draw cards should be visible
   * @throws IllegalArgumentException if the model or deck is null
   * @throws IllegalStateException    if the game cannot be started, or if the controller cannot
   *                                  interact with the player.
   */
  @Override
  public <Cards> void playGame(PyramidSolitaireModel<Cards> model, List<Cards> deck,
      boolean shuffle,
      int numRows, int numDraw) throws IllegalArgumentException,
      IllegalStateException {
    if (model == null || deck == null) {
      throw new IllegalArgumentException("Model/deck cannot be null");
    }

    Scanner scan = new Scanner(this.rd);
    try {
      model.startGame(deck, shuffle, numRows, numDraw);
    }
    catch (IllegalArgumentException e) {
      throw new IllegalStateException(e.getMessage());
    }

    PyramidSolitaireView view = new PyramidSolitaireTextualView(model, this.ap);


    this.controlGame(model, view, scan);
  }

  /**
   * A helper that accepts player input and outputs the results.
   * @param model   The model for the pyramid solitaire game,
   *                dictates the rules in which the game is played
   * @param view    The view for the game, provides the user with a way to see the game
   * @param scan    A scanner, takes in user input
   * @param <Cards> The type of cards taken by the model
   */
  private <Cards> void controlGame(PyramidSolitaireModel<Cards> model,
      PyramidSolitaireView view, Scanner scan) {
    String mouseButton;
    int row1;
    int card1;
    int row2;
    int card2;
    int drawIndex;
    boolean ongoing = true;
    try {
      view.render();
      this.ap.append("\n" + "Score: " + model.getScore() + "\n");
    }
    catch (IOException e) {
      e.printStackTrace();
    }

    while (ongoing) {
      if (scan.hasNext()) {
        mouseButton = scan.next();
        if (mouseButton.equalsIgnoreCase("q")) {
          ongoing = false;
          scan.close();
          try {
            this.ap.append("Game quit!" + "\n" + "State of game when quit:" + "\n");
          } catch (IOException e) {
            e.printStackTrace();
          }
        } else {
          switch (mouseButton) {
            case "rm1":
              row1 = this.intScan(scan, ongoing);
              card1 = this.intScan(scan, ongoing);
              try {
                model.remove(row1, card1);
              } catch (IllegalArgumentException e) {
                try {
                  this.ap.append("Invalid move. Play again. " + e.getMessage() + "\n");
                } catch (IOException ee) {
                  ee.printStackTrace();
                  return;
                }
              }
              break;
            case "rm2":
              row1 = this.intScan(scan, ongoing);
              card1 = this.intScan(scan, ongoing);
              row2 = this.intScan(scan, ongoing);
              card2 = this.intScan(scan, ongoing);
              try {
                model.remove(row1, card1, row2, card2);
              } catch (IllegalArgumentException e) {
                try {
                  this.ap.append("Invalid move. Play again. " + e.getMessage() + "\n");
                } catch (IOException ee) {
                  ee.printStackTrace();
                  return;
                }
              }
              break;
            case "rmwd":
              drawIndex = this.intScan(scan, ongoing);
              row1 = this.intScan(scan, ongoing);
              card1 = this.intScan(scan, ongoing);
              try {
                model.removeUsingDraw(drawIndex, row1, card1);
              } catch (IllegalArgumentException e) {
                try {
                  this.ap.append("Invalid move. Play again. " + e.getMessage() + "\n");
                } catch (IOException ee) {
                  ee.printStackTrace();
                  return;
                }
              }
              break;
            case "dd":
              drawIndex = this.intScan(scan, ongoing);
              try {
                model.discardDraw(drawIndex);
              } catch (IllegalArgumentException e) {
                try {
                  this.ap.append("Invalid move. Play again. " + e.getMessage() + "\n");
                } catch (IOException ee) {
                  e.printStackTrace();
                  return;
                }
              }
              break;
            default:
              try {
                this.ap.append("Try Again!" + "\n");
              } catch (IOException e) {
                e.printStackTrace();
                return; }
          }
        }
        if (model.isGameOver()) {
          ongoing = false;
          try {
            view.render();
            this.ap.append("\n");
          }
          catch (IOException e) {
            e.printStackTrace();
          }
        }
        else {
          try {
            view.render();
            this.ap.append("\n" + "Score: " + model.getScore() + "\n");
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
      }
      else {
        ongoing = false;
        try {
          view.render();
          this.ap.append("\n" + "Score: " + model.getScore() + "\n");
        }
        catch (IOException e) {
          e.printStackTrace();
          return;
        }
      }
    }
  }

  /**
   * Allows for the scanner to skip bad inputs.
   * @param scan    The scanner
   * @param ongoing The boolean dictating whether game runs or not
   * @return An integer that acts as the input given by the user
   */
  private int intScan(Scanner scan, boolean ongoing) {
    if (!scan.hasNextInt() && scan.hasNext("q") || scan.hasNext("Q")) {
      ongoing = false;
      return 0;
    } else {
      if (!scan.hasNextInt()) {
        scan.next();
        return this.intScan(scan, ongoing);
      } else {
        return scan.nextInt() - 1;
      }
    }
  }
}