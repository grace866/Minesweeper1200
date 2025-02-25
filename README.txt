=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
CIS 1200 Game Project README
PennKey: grtan
=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=

===================
=: Core Concepts :=
===================

- List the four core concepts, the features they implement, and why each feature
  is an appropriate use of the concept. Incorporate the feedback you got after
  submitting your proposal.

  1. 2D Array
  The arrays will store the location and state of each block in the minefield.
  There will be one array that will store the number of mines surrounding each block (-1 if the
  block itself is a mine), one array that will store which blocks have been revealed, and one
  block that will store which blocks have been flagged. A 2D array is an appropriate choice to
  model the game state because the size of the minefield does not change, and the indexing structure
  of a 2D array allows for easy access to the location of each block.

  2. Recursion
  Recursion is present in the reveal() function of the Minesweeper class. Recursion is used so that
  if the player reveals a block without surrounding mines, neighboring blocks will also be checked
  and continuously revealed until the program reaches blocks that do have surrounding mines.
  This propagation logic allows the player to reveal a large portion of safe blocks at once. This
  concept is an appropriate choice here because we want to keep checking pieces of the board until
  the base is reached (when we reach a block with surrounding mines, the program stops checking
  its neighboring blocks).

  3. File I/O
  File I/O is used to write the game state into a text file when the user wants to save their
  previous game. After closing the game, the user can start a new one and load their previous game,
  which triggers the program to read from the file and update the game state. This is an
  appropriate use of the concept because saving and loading a previous game requires the program to
  write into a file and subsequently read from it.

  4. JUnit Testing
  JUnit testing will test the functionality of the methods that create and manipulate the core
  game-state/model. Because the Minesweeper class is independent of GUI components, the methods can
  also be tested independently, making this an appropriate use of the concept.

===============================
=: File Structure Screenshot :=
===============================
- Include a screenshot of your project's file structure. This should include
  all of the files in your project, and the folders they are in. You can
  upload this screenshot in your homework submission to gradescope, named 
  "file_structure.png".

=========================
=: Your Implementation :=
=========================

- Provide an overview of each of the classes in your code, and what their
  function is in the overall game.

  1. The Minesweeper class is the model for the game. It contains the logic that
  checks if the player has won/lost, reveals the blocks, flags the blocks, etc.
  It is independent of the GUI components and can model the game with calls to the
  methods within the class.

  2. The GameBoard class represents the minefield and acts as both a controller and a view. The
  MouseListeners allow the user to interact with the minefield and update the model. The
  paintComponent method updates the view whenever an interaction occurs.

  3. The RunMinesweeper class also acts as both a controller and a view. The buttons are
  controllers that trigger methods inside the GameBoard class, which in turn update the model.
  It also initializes the view and positions all the components of the game relative to the screen.


- Were there any significant stumbling blocks while you were implementing your
  game (related to your design, or otherwise)?

The row column vs x-axis y-axis coordinate conversion was a little confusing, and I ended up
confusing the coordinates in several places. To fix this, I had to search through the entirety of
my code to find where the coordinates were not consistent, which took a good amount of time.

- Evaluate your design. Is there a good separation of functionality? How well is
  private state encapsulated? What would you refactor, if given the chance?

There is a good separation of functionality. The model can be tested because it is independent of
the RunMinesweeper and GameBoard classes. The GameBoard class handles all user interaction
with the board (which necessitates updates to the model) and the RunMinesweeper class handles
all user interaction with the buttons and creates the initial setup. The RunMinesweeper class
does not directly interact with the model at all. The private state is well encapsulated because
at no point do the methods in the Minesweeper class return pointers to the fields. If necessary,
a copy of the field is created and returned. If given the chance, I would make the size of the
board customizable. I would also create multiple constructors in the Minesweeper class so that
custom boards could be created more easily.

========================
=: External Resources :=
========================

- Cite any external resources (images, tutorials, etc.) that you may have used 
  while implementing your game.
