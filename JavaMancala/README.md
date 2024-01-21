# Mancala Game

## Description

This is a simple implementation of the board game Mancala. There is a board with 2 sides and 6 "pits" per side, there is also 2 "stores" on either side of the board. 
Each pit will start with 4 stones and the stores start empty, the goal is to get the most amount of stones in your store. Each move a player may choose a pit from their side
of the board to distribute stones from. The stones will then be taken out of that pit and distributed one stone at a time around the board. Distribution varies depending on which rule set is being used,
there are 2 options of rule sets, one that implements the rules of Kalah and another that followed the rules outlined in Ayo

## Getting Started

### Dependencies

* Built in the scioer development container
* Takes advantage of Java built in packages for handling files and graphics


### Executing program

* Use the following command to compile the program:
```
gradle build
```
* Run the program with:
```
java -jar build/libs/MancalaUI.jar
```
```
java -cp build/classes/java/main ui.MancalaUI
```
* Expected Outcome:

First the game will prompt each player for thier names or they will have the option to load an old profile, then the board will be printed as seen bellow and ask for a move, this will continue until the game is over
based on the conditions of the game. Bellow is an example of what the board looks like:

```
---------------------------------
|¯¯|[04][04][04][04][04][04]|¯¯|
|00|                        |00|
|__|[04][04][04][04][04][04]|__|
---------------------------------
```

When the game is over the winner will be displayed and the user will have the option to start a new game and to save their stats


## Limitations

Currently there are no limitations that the author is aware of.

## Author Information

Benjamin Holbrook  
holbrook@uoguelph.ca

## Development History

- This project is an continuation of the Mancala game developed for A2
- The first thing that was done is some refactoring to the original implementation to break up the Board class into GameRules and MancalaDataStructure
- Support was added for percistance, along with an implemenation of user profiles that contains saved stats for each player
- A second rule set was implemenated which follows the rules of Ayayoh
- A graphical interface was created to handle the physical display of the game
- More refactoring was done at this stage to fix various bugs and pmd conventions that weren't followed at first

## Acknowledgments

Inspiration, code snippets, etc.
* [awesome-readme](https://github.com/matiassingers/awesome-readme)
* [simple-readme] (https://gist.githubusercontent.com/DomPizzie/7a5ff55ffa9081f2de27c315f5018afc/raw/d59043abbb123089ad6602aba571121b71d91d7f/README-Template.md)


