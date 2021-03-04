import controller.InteractiveViewController;
import animator.util.AnimationReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JOptionPane;
import model.BasicAnimatorModel.Builder;
import model.EasyAnimatorModel;
import provider.src.cs3500.animator.ProviderViewCreator;
import provider.src.cs3500.animator.ProviderViewCreator.ProviderViewType;
import provider.src.cs3500.animator.controller.EasyControllerToAnimatorInterface;
import provider.src.cs3500.animator.model.AnimatorModel;
import provider.src.cs3500.animator.model.EasyAnimatorModelToAnimatorModel;
import provider.src.cs3500.animator.view.AnimatorView;
import view.AnimationViewCreator;
import view.AnimationViewCreator.ViewType;
import view.EasyAnimatorView;
import view.EasyInteractiveView;
import viewmodel.IViewModel;
import viewmodel.ViewModel;

/**
 * Represents the program that runs the animation.
 */
public final class Excellence {

  /**
   * The main method. It runs the animation.
   *
   * @param args arguments that dictate what the animation does and how does it look like.
   * @throws IOException When the appendable does not work.
   */
  public static void main(String[] args) throws IOException {

    String[] commands = new String[]{"", "", "", ""};

    for (int i = 0; i < args.length; i++) {

      if (args[i].equals("-in")) {
        commands[0] = args[i + 1];
      }
      if (args[i].equals("-out")) {
        commands[1] = args[i + 1];
      }
      if (args[i].equals("-speed")) {
        commands[2] = args[i + 1];
      }
      if (args[i].equals("-view")) {
        commands[3] = args[i + 1];
      }
    }

    try {
      if (!(commands[2].isEmpty()) && Integer.parseInt(commands[2]) < 0) {
        JOptionPane.showMessageDialog(null, "error",
            "Use a positive number",
            JOptionPane.ERROR_MESSAGE);
      }
    } catch (NumberFormatException e) {
      JOptionPane.showMessageDialog(null, "error", "Use a number",
          JOptionPane.ERROR_MESSAGE);
    }

    if (commands[2].isEmpty()) {
      commands[2] = "1";
    }

    if (!(commands[3].equalsIgnoreCase("text")
        || commands[3].equalsIgnoreCase("svg")
        || commands[3].equalsIgnoreCase("visual")
        || commands[3].equalsIgnoreCase("interactive")
        || commands[3].equalsIgnoreCase("provider"))) {
      JOptionPane.showMessageDialog(null, "error",
          "Illegal view", JOptionPane.ERROR_MESSAGE);
    }

    if (commands[0].isEmpty() || commands[3].isEmpty()) {
      JOptionPane.showMessageDialog(null, "error",
          "You need an input and a view", JOptionPane.ERROR_MESSAGE);
    }
    Appendable ap;

    Readable rd = new FileReader(commands[0]);
    EasyAnimatorModel model = AnimationReader.parseFile(rd, new Builder());
    IViewModel vModel = new ViewModel(model);

    EasyAnimatorView view;
    AnimatorView pView;

    if (commands[1].isEmpty()) {
      ap = System.out;
      switch (commands[3]) {
        case "svg":
          view = AnimationViewCreator.create(ViewType.SVG, vModel, ap);
          view.render(Integer.parseInt(commands[2]));
          break;
        case "visual":
          view = AnimationViewCreator.create(ViewType.VISUAL, vModel, ap);
          view.render(Integer.parseInt(commands[2]));
          break;
        case "text":
          view = AnimationViewCreator.create(ViewType.TEXT, vModel, ap);
          view.render(Integer.parseInt(commands[2]));
          break;
        case "interactive":
          view = AnimationViewCreator.create(ViewType.INTERACTIVE, vModel, ap);
          new InteractiveViewController((EasyInteractiveView) view);
          view.render(Integer.parseInt(commands[2]));
          break;
        case "provider":
          pView = ProviderViewCreator.create(ProviderViewType.PROVIDER);
          pView.setListener(new EasyControllerToAnimatorInterface(pView));

          AnimatorModel pModel = new EasyAnimatorModelToAnimatorModel(vModel);

          pView.render(pModel.getAllAnimations(), Integer.parseInt(commands[2]),
              pModel.getCanvasWidth(), pModel.getCanvasHeight(), pModel.getMinX(),
              pModel.getMinY());
          break;
        default:
          throw new IllegalArgumentException("try again");

      }
    } else {
      FileWriter fw = new FileWriter(commands[1]);
      ap = fw;

      switch (commands[3]) {
        case "svg":
          view = AnimationViewCreator.create(ViewType.SVG, vModel, ap);
          view.render(Integer.parseInt(commands[2]));
          fw.close();
          break;
        case "visual":
          view = AnimationViewCreator.create(ViewType.VISUAL, vModel, ap);
          view.render(Integer.parseInt(commands[2]));
          fw.close();
          break;
        case "text":
          view = AnimationViewCreator.create(ViewType.TEXT, vModel, ap);
          view.render(Integer.parseInt(commands[2]));
          fw.close();
          break;
        case "interactive":
          view = AnimationViewCreator.create(ViewType.INTERACTIVE, vModel, ap);

          InteractiveViewController cont = new InteractiveViewController((EasyInteractiveView)
              view);
          view.render(Integer.parseInt(commands[2]));
          fw.close();
          break;
        case "provider":
          pView = ProviderViewCreator.create(ProviderViewType.PROVIDER);
          pView.setListener(new EasyControllerToAnimatorInterface(pView));

          AnimatorModel pModel = new EasyAnimatorModelToAnimatorModel(vModel);

          pView.render(pModel.getAllAnimations(), Integer.parseInt(commands[2]),
              pModel.getCanvasWidth(), pModel.getCanvasHeight(), pModel.getMinX(),
              pModel.getMinY());
          fw.close();
          break;
        default:
          throw new IllegalArgumentException("try again");
      }

    }
  }
}