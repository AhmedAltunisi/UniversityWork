package cs3500.pyramidsolitaire.view;

import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import java.io.IOException;


/**
 *Represents a Textual view for the Pyramid Solitaire game. Allows the game to be seen in a text
 *based format.
 */

public class PyramidSolitaireTextualView implements PyramidSolitaireView {

  private final PyramidSolitaireModel<?> model;
  private Appendable ap;

  public PyramidSolitaireTextualView(PyramidSolitaireModel<?> model) {
    this.model = model;
    this.ap = null;
  }


  public PyramidSolitaireTextualView(PyramidSolitaireModel<?> model, Appendable ap) {
    this.model = model;
    this.ap = ap;
  }

  /**
   * Renders a model in some manner (e.g. as text, or as graphics, etc.).
   *
   * @throws IOException if the rendering fails for some reason
   */
  @Override
  public void render() {
    try {
      this.ap.append(this.toString());
    }
    catch (IOException e) {
      System.out.println("Something wrong with the appendable");
    }
  }

  @Override
  public String toString() {

    if (this.model.getNumDraw() == -1) {
      return "";
    }
    if (this.model.getScore() == 0) {
      return "You win!";
    }
    if (this.model.isGameOver() && this.model.getScore() > 0) {
      return "Game over. Score: " + this.model.getScore();
    }

    String view = "";
    String drawPile = "Draw:";
    for (int i = 0; i < this.model.getNumRows(); i++) {

      view = view + this.spaceAdder(this.model.getNumRows() - 1).substring(0,
          this.spaceAdder(this.model.getNumRows() - 1).length() - i - i);
      for (int y = 0; y < this.model.getRowWidth(i); y++) {
        if (this.model.getCardAt(i, y) == null) {
          view = view + " .  ";
        }
        else {
          if (i == 0 && y == 0) {
            view = view + this.model.getCardAt(i, 0).toString() + " ";
          }
          else {
            if (i == 0 && y < model.getRowWidth(i)) {
              view = view  + " " + this.model.getCardAt(i, y).toString()  + " ";
            }
            else {
              if (y == 0 && this.model.getCardAt(i, y).toString().startsWith("10")) {
                view = view + this.model.getCardAt(i, y).toString();
              }
              else {
                if (y == 0) {
                  view = view + this.model.getCardAt(i, y).toString() + " ";
                }
                else {
                  if (this.model.getCardAt(i, y).toString().startsWith("10")) {
                    view = view + " " + this.model.getCardAt(i, y).toString();
                  }
                  else {
                    view = view + " " + this.model.getCardAt(i, y).toString() + " ";
                  }
                }
              }
            }
          }
        }
      }
      if (view.startsWith("10", view.length() - 3)) {
        view = view + "\n";
      }
      else {
        view = view.substring(0, view.length() - 1) + "\n";
      }
    }
    for (int i = 0; i < this.model.getNumDraw(); i++) {
      drawPile = drawPile + " " + this.model.getDrawCards().get(i).toString() + ",";
    }
    if (drawPile.length() == "Draw:".length()) {
      return view + drawPile;
    }
    else {
      return view + drawPile.substring(0, drawPile.length() - 1);
    }
  }

  private String spaceAdder(int rows) {
    if (rows > 0) {
      return "  " + spaceAdder(rows - 1);
    }
    else {
      return "";
    }
  }
}
