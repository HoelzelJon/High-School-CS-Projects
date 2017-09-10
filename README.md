# High School CS Projects

These are a few coding projects I made during my free time in high school.  They are all in Java, and were made in BlueJ (so it is probably easiest to open them with BlueJ).

The programs (in order from most to least interesting, in my opinion):

-GeneticRunning: This is a genetic algorithm that produces "creatures" that run as quickly as possible.  I based the program off of this(https://www.youtube.com/watch?v=GOFws_hhZs8) series of videos, but all of the code for this is my own.  To run the program, use the main method of the SimpleEvolutionTester class.  Then, input the number on nodes, the population size, and whether each pair of nodes should be connected, and watch your custom creature evolve.

-Chess: The name says it all.  You can play chess, either against yourself or against the (not-totally-terrible) computer player.  To play, use the main method in the MainGame class.

-Checkers: Same as chess, but it's checkers. Again, use the MainGame class to play.

-FingerGame:  At school, there was a game that some of my friends would play.  It can be played with effectively as many people as you want, but this program only deals with the two-player scenario.  The rules are:

 -Each player has 2 hands.  Each hand can have 1-4 fingers raised at a time.  To start, both players have one finger raised on each hand.
 -If one of your hands ever has 5 or more fingers raised, that hand is then dead.
 -Each turn, you have two options.
  -You can hit one of your opponent's hands with one of yours.  When you do this, your opponent's hand gains the number of fingers that you hit with (i.e. if you hit on the first turn, one of your opponent's hands would then have 2 fingers raised)
  -You can pass some of your fingers from one hand to the other.  This can be done to bring back a dead hand, or even to intentionally kill a hand.  However, you cannot pass such that your hands are still effectively the same (i.e. 2 on one hand + 1 on the other cannot be passed to become 1 on one hand + 2 on the other)
Based on these rules, the second player can always win, assuming they play correctly.  Try it yourself! If you run the main method of MainGame, you can face off against the undefeated computer.  (Note: the hands are numbered 0 and 1, because arrays.  hand 0 is on the left and 1 is on the right)

-RandomWalks: This program has various interesting visuals relating to random walks.  If you are wondering why all of the class names involve drunks, that is the analogy that my CS teacher made, so that was where I started with the program. The main method of the DrunksDrawer class puts 20,000 "drunks" (randomly-colored points) and has them wander randomly on the screen.  The main method of the RememberingDrunkDrawer class has a single point wander for a user-specified number of steps, and then displays an image of the path the point took (darker patches indicate the point passed over an area more times).  The user can then input a "shading factor" to make the image look cool.  The main method in RememberingDrunksDrawer draws a user-specified number of randomly-colored points as they do -- you guessed it -- a random walk.

-BrickBreaker:  Just a pretty basic brick-breaker game.  Run the main method in the Game class to play.  Use the mouse to control the paddle.

-Minesweeper: A shoddy reproduction of the classic game. Run the main method in the Game class, specify the board size and number of mines, and enjoy! (Controls = left click to reveal a square, right click to flag a square)

PlatformJumper: A very simple version of a certain popular mobile game.  Move the mouse to make the black box jump as high as possible!  Use the main method in the Game class to play.
