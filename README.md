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

- Hangman: a model that does all of the backend needed to play the game of hangman (class vars are saved local) also contains game settings
- HiScores: a model that makes a hightscore list and saves it to the local memory

Model Classes (code design)
---------------------------

Hangman
-------

**Class:              Hangman()**

Class var:          String playerName

Class var:          int wordLength

Class var:          int tries


Const Class var:    Array userInputStates

Class var:          Array wordList

Class var:          String currentWord

Class var:          String currentWordState = `"_A__B_"`

Class var:          String usedLetters

Class var:          String computerMonologue


**Method:             initList()**

* // sets the list array with all the possible words

**Method:             chooseRandomnWord(wordLength)**

* // picks a psuedo randomn word from the list by set length

**Method:             doUserInput(letter)**

* // already used
* // invalid input
* // wrong guess
* // correct guess

**Method:             getCurrentWord()**

* // gets the word that has been picked to play with

**Method:             getCurrentWordState()**

* // gets the state of the word (`"_A_BC_"`)

**Method:             getUsedLetters()**

* // gets the used letters 

**Method:             getComputerMonologue()**

* // gets current computer monologue switch that checks witch one to pick

**Method:             reset()**

* // resets the game

HiScores
--------

**Class:              HiScores**

Class var:          ArrayList scores

* // the constructor will retrieve the scores

Class var:          Array top10

**Method:             addScore()**

* // adds a score to the scores arraylist

**Method:             getTop10()**

* // gets the top 10 from the scores list


Images
------

Schets van het scherm waar hangman in gespeeld word:
![Game screen](doc/Opzet interface.JPG)

Opzet classes evil hangman (niet meer nodig):
![Classes](doc/Poging tot class.JPG)


Design doc
----------

https://moqups.com/svandenhout/RbfktZxW
