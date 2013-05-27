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

Models
------

- Hangman: a model that does all of the backend needed to play the game of hangman
- hi scores: a model that makes a hightscore list and keeps track of the list

Classes etc (code design)
-------------------------

normal hangman
--------------


**Class:              EvilHangman()**

**Class var:          Array list**
**Class var:          Array equivList**
**Class var:          String currentWord**
**Class var:          String cMonologue**

**Method:             getList()**
* // sets the list array with all the possible words

**Method:             checkEquivalence()**
* // sets the equivList array by checking for possibilities that are equivilant to the guess the user made

**Method:             cheat()**
* // sets the current word giving the user the hightest possible chance to lose


Images
------

// coming soon
