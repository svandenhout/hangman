project5
========

Evil hangman, a game of hangman where the computer secretly changes the word to defeat the human player

features
--------

- The game of hangman
- A secretly cheating computer system
- live computer monologue

Frameworks Languages & libraries
--------------------------------

- java
- android sdk

Classes etc (code design)
-------------------------

- Class:              Cheater()

- Class var:          Array list
- Class var:          Array equivList
- Class var:          String currentWord
- Class var:          String cMonologue

// sets the list array with all the possible words
- Method:             getList()

// sets the equivList array by checking for possibilities that are equivilant to the guess the user made
- Method:             checkEquivalence()

// sets the current word giving the user the hightest possible chance to lose
- Method:             cheat()

Images
------

// coming soon