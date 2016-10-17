# Mastermind

Mastermind board game in Java.

Uses swing for GUI.

Includes solver.

How to use:
    1. To launch the game, navigate to the game's directory and run 'java Mastermind' or 'java Mastermind textual' for the non-GUI version
    2. You will be asked to enter the following:
        * Number of Pegs - This is the number of pegs that the code contains (3-8)
        * Number of Colours - This is the number of different coloured pegs there are (3-8)
        * Player modes for Code Maker and Code Breaker - AI or Human. The AI will play against either another human or another AI. Two humans can also play each other
        * You can save at any time by pressing 'Save and Exit' in GUI mode, or typing save in textual mode mid-game
        * You can load your saved game by pressing 'Load' at the menu in GUI mode, or typing it at the menu in textual mode
    3. Play the game and have fun! (If you like a challenge, the AI difficulty has been set to maximum. You are not prepared.)


How Mastermind Works:
    There are two players: the code maker and the code breaker
    A code consists of a number of coloured pegs
    At the start, the code maker creates a secret code
    The aim of the game is for the code breaker to break the code within 12 attempts
    They can do this by making a guess, which they are given feedback on by the code maker
    The feedback consists of a number of black and white pegs.
    A black peg means that one of the pegs in the guess was the correct colour and in the correct location.
    A white peg means that one of the pegs in the guess was the correct colour, but it was in the wrong location.
    Good luck!

A more detailed explanation can be found here: https://en.wikipedia.org/wiki/Mastermind_%28board_game%29