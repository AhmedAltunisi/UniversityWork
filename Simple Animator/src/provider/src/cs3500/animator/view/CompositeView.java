package provider.src.cs3500.animator.view;

import provider.src.cs3500.animator.model.Animation;
import provider.src.cs3500.animator.util.UtilityFunctions;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * The Composite view takes the concept built in the last iteration of the animator (the graphics
 * view) and expands it by adding a few state-controlling buttons that allows for more interaction
 * with the animation itself. This was accomplished by completely re-writing the logic for the frame
 * (our design below).
 *
 * <p>The interpolation function used by the Graphical view is the same interpolation function used
 * here. It can be found in UtilityFunctions.
 */
public class CompositeView extends JFrame implements AnimatorView {
  private final JButton startButton;
  private final JButton pauseButton;
  private final JButton resumeButton;
  private final JButton restartButton;
  private final JButton enableLoop;
  private final JButton disableLoop;
  private final JButton increaseSpeed;
  private final JButton decreaseSpeed;
  private final AnimationPanel animationPanel;
  private boolean start = false;
  private boolean restart = false;
  private boolean loop = false;
  private int tickPerSec;

  /**
   * Public constructor for the Frame. This function takes no items as parameters, then contructs
   * both the animation panel and the individual buttons and adds them to the frame.
   */
  public CompositeView() {
    super();

    this.setTitle("Awesome Animation");
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLayout(new BorderLayout());

    // Add the new Composite Panel Here
    this.animationPanel = new AnimationPanel();
    this.add(animationPanel, BorderLayout.CENTER);

    // This is where the button panel starts
    javax.swing.JPanel buttonPanel = new javax.swing.JPanel();
    buttonPanel.setLayout(new FlowLayout());
    this.add(buttonPanel, BorderLayout.SOUTH);

    // This is where we add the buttons
    startButton = new JButton("Start");
    startButton.setActionCommand("Start Button");
    buttonPanel.add(startButton);

    pauseButton = new JButton("Pause");
    pauseButton.setActionCommand("Pause Button");
    buttonPanel.add(pauseButton);

    resumeButton = new JButton("Resume");
    resumeButton.setActionCommand("Start Button");
    buttonPanel.add(resumeButton);

    restartButton = new JButton("Restart");
    restartButton.setActionCommand("Restart Button");
    buttonPanel.add(restartButton);

    increaseSpeed = new JButton("Faster");
    increaseSpeed.setActionCommand("Faster Button");
    buttonPanel.add(increaseSpeed);

    decreaseSpeed = new JButton("Slower");
    decreaseSpeed.setActionCommand("Slower Button");
    buttonPanel.add(decreaseSpeed);

    enableLoop = new JButton("Enable Loop");
    enableLoop.setActionCommand("Loop Button");
    buttonPanel.add(enableLoop);

    disableLoop = new JButton("Disable Loop");
    disableLoop.setActionCommand("Loop Button");
    buttonPanel.add(disableLoop);

    this.pack();
  }

  /**
   * This function is used by the controller to set its action handler function as the callback for
   * all of the buttons.
   *
   * @param listener The action listener to be set as the callback.
   */
  public void setListener(ActionListener listener) {
    Objects.requireNonNull(listener);

    this.startButton.addActionListener(listener);
    this.pauseButton.addActionListener(listener);
    this.resumeButton.addActionListener(listener);
    this.restartButton.addActionListener(listener);
    this.enableLoop.addActionListener(listener);
    this.disableLoop.addActionListener(listener);
    this.increaseSpeed.addActionListener(listener);
    this.decreaseSpeed.addActionListener(listener);
  }

  /**
   * Render function similar to that of the graphics view with added checks in the looping section
   * for the commands we added to the frame view.
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
    this.tickPerSec = tickPerSecond;
    this.setSize(canvasWidth, canvasHeight);
    animationPanel.setPreferredSize(new Dimension(canvasWidth, canvasHeight));

    List<List<AnimationStep>> stepsTimeline =
        UtilityFunctions.interpolate(animations, xOffset, yOffset);

    makeVisible();

    paintEverything(stepsTimeline);
  }

  /**
   * Helper function that contains the functionality for actually taking the animation steps and
   * passing them to the animation panel. This version, unlike the Animation Graphics view version,
   * allows for external control in the form of pausing, looping, and restarting.
   *
   * @param stepsTimeline The list of animation steps to draw
   */
  private void paintEverything(List<List<AnimationStep>> stepsTimeline) {
    int i = 0;
    int count = 0;
    while (count != 1) {
      if (this.start && i < stepsTimeline.size()) {

        List<AnimationStep> steps = new ArrayList<>(stepsTimeline.get(i));
        this.animationPanel.setNextAnimation(steps);
        this.refresh();
        i++;

        try {
          Thread.sleep(1000 / this.tickPerSec);
        } catch (InterruptedException error) {
          Thread.currentThread().interrupt();
        }
      } else {
        if (loop) {
          i = 0;
        } else {
          try {
            Thread.sleep(1);
          } catch (InterruptedException error) {
            Thread.currentThread().interrupt();
          }
        }
      }
      if (restart) {
        i = 0;
        restart = false;
      }
    }
  }

  /** Makes the frame and panel visible. */
  public void makeVisible() {
    this.setVisible(true);
  }

  /** This repaints the entire frame. */
  public void refresh() {
    this.repaint();
  }

  /**
   * Called when there is an error in rendering either the frame or the panel. This pops up a little
   * dialogue box on the screen that displays the error.
   *
   * @param error The error encountered.
   */
  public void showErrorMessage(String error) {
    JOptionPane.showMessageDialog(this, error, "Error",
        JOptionPane.ERROR_MESSAGE);
  }

  /**
   * Function for externally modifying the "start" boolean to true so that the animation is running.
   */
  public void toggleStart() {
    this.start = true;
  }

  /** Function for changing the "start" function to false to pause the animation. */
  public void togglePause() {
    this.start = false;
  }

  /** Function for externally triggering a "restart" on the animation. */
  public void restartAnimation() {
    this.restart = true;
  }

  /**
   * This allows for externally toggling the loop boolean that determines whether or not the
   * animation loops when it finishes running.
   */
  public void toggleLoop() {
    this.loop = !this.loop;
  }

  /**
   * This is an external function for increasing the playback of the animation. It adds to the ticks
   * per second count so that the delay between renders is shorter.
   */
  public void increaseSpeed() {
    this.tickPerSec++;
  }

  /**
   * This is an external function for decreasing the playback of the animation. It subtracts from
   * the ticks per second count so that the delay between renders is longer.
   */
  public void decreaseSpeed() {
    this.tickPerSec--;
  }
}
