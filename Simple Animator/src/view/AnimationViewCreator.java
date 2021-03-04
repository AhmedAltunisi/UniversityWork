package view;

import java.io.IOException;
import viewmodel.IViewModel;

/**
 * Represents the creator for animations views.
 */
public class AnimationViewCreator {

  /**
   * Enumeration that represents all possible view types that can be created.
   */
  public enum ViewType {
    TEXT, SVG, VISUAL, INTERACTIVE, PROVIDER;
  }

  /**
   * Create method that will return the specified viewType or a default viewType.
   * @param viewType Type of view that is requested
   * @param model Model that will be used for the view
   * @param ap Appendable that will be used with the view
   * @return View to be used
   * @throws IllegalArgumentException If a constructor throws an error
   * @throws IOException If an appendable throws an error
   */
  public static EasyAnimatorView create(ViewType viewType, IViewModel model, Appendable ap)
      throws IllegalArgumentException {
    if (viewType == null) {
      return new TextView(model, ap);
    }
    String type = viewType.toString();
    switch (type) {
      case "TEXT":
        return new TextView(model, ap);
      case "SVG":
        return new SVGAnimationView(model, ap);
      case "INTERACTIVE":
        return new InteractiveView(new VisualAnimationView(model));
      default:
        return new VisualAnimationView(model);
    }
  }
}
