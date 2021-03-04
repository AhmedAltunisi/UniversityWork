package cs3500.pyramidsolitaire.model.hw04;


import java.util.ArrayList;
import java.util.List;

/**
 * Represents a "trapezoid template" which is essentially gives the model a way to compute the size
 * of the desired multi-pyramid which, except when given only one row, looks like a trapezoid.
 */
public class TrapezoidTemplate {

  private final int rows;
  private final List<String> trapezoid;
  private int deviationFromRowIndex;

  /**
   * Constructs a template of a multi-pyramid-esque trapezoid given a number of rows.
   * @param rows   the number of rows desired to be in the pyramid.
   */
  protected TrapezoidTemplate(int rows) {
    this.rows = rows;
    this.trapezoid = new ArrayList<>();
    this.deviationFromRowIndex = 0;
  }

  protected void buildTemplateTrapezoid() {

    if (rows == 1) {
      trapezoid.add("a");
    }
    else {
      if (rows < 4) {
        for (int i = 0; i < rows; i++) {
          for (int y = 0; y <= i + 2; y++) {
            trapezoid.add("a");
          }
        }
      } else {
        this.spaceOutFirstRow();
        trapezoid.add("a");
        this.deviationFromRowIndex = trapezoid.size();
        for (int i = 1; i < this.rows; i++) {
          for (int y = 0; y <= i + this.deviationFromRowIndex - 1; y++) {
            trapezoid.add("a");
          }
        }
      }
    }

  }

  private void spaceOutFirstRow() {
    for (int i = 0; i < 2; i++) {
      this.trapezoid.add("a");
      this.addMore(this.howManyMoreToAdd(), this.trapezoid);
    }
  }

  private void addMore(int nulls, List<String> deck) {
    if (nulls > 0) {
      deck.add("a");
      addMore(nulls - 1, deck);
    }
  }

  private int howManyMoreToAdd() {
    if (this.rows % 2 != 0) {

      return this.roadto2(this.rows - 1, 0);
    } else {
      return this.roadto2(this.rows, 0);
    }
  }

  private int roadto2(int currentNum, int counter) {
    if (currentNum == 2) {

      return counter;
    } else {
      return roadto2(currentNum - 2, counter + 1);
    }
  }

  protected int trapezoidSize() {
    return this.trapezoid.size();
  }
}