Summary of Included Files:
-
- (Interface) AnimatorModel
    - (Interface) Animation
    - (Interface) Shape
        - (Implementation) BasicShape
            - (Implementation) Position2D
    - (Interface) Operation
- (Interface) AnimatorView
    - (Implementation) CompositeView
        - (Implementation) UtilityFunctions
        - (Implementation) AnimationStep
        - (Implementation) AnimationPanel
    - (Implementation) AnimatorTextView
- (Interface) AnimatorInterface (Controller)


---
Notes on Views:
-
- The TextView and CompositeView both work as we believe is required by the assignments. 
- Our text view declares shapes and all of their motions one at a time, rather than declaring all shapes and then declaring all motions. It can output to any Appendable.
- Our CompositeView implements the required features of Assignment 8 minimally by only using control buttons.

Notes on Interfaces/Classes:
-
- Shape and BasicShape are value classes for our Animation objects to store keyframe data

- Operation class provides functionality for appending "incremental" motions to an Animation, however it does not necessarily need to be implemented since it is not coupled to our view implementations.
    - This also allows our Shape objects to remain immutable, as the Operation.apply() function can construct a new Shape.

- UtilityFunctions class has a single method in it currently that performs the AnimationStep interpolation for the CompositeView

- Position2D class is a value class borrowed/modified from PyramidSolitaire that helps us track Shape positions and scales


General Notes:
-
- To adapt our AnimatorView interface to your Model, the only interface that you absolutely have to implement is the Animation interface. Implementing the Operation interface is recommended.