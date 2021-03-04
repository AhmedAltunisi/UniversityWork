package provider.src.cs3500.animator;

import provider.src.cs3500.animator.view.AnimatorView;
import provider.src.cs3500.animator.view.CompositeView;

/**
 * Creator class for creating a provider interactive view.
 */
public class ProviderViewCreator {

  /**
   * All possible enumerations of view types for a provider view.
   */
  public enum ProviderViewType {
    PROVIDER;
  }

  /**
   * Static method that creates a provider view given a view type.
   * @param viewType Type of view to create
   * @return Animator view which is the provider view
   */
  public static AnimatorView create(ProviderViewType viewType) {
    if (viewType == null) {
      throw new IllegalArgumentException("no");
    }

    String type = viewType.toString();

    if (type.equals("PROVIDER")) {
      return new CompositeView();

    }
    else {
      throw new IllegalArgumentException("balls");
    }
  }
}
