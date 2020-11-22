# peg-solitaire

Find one or all solutions to the peg solitaire board game (see [wikipedia](https://en.wikipedia.org/wiki/Peg_solitaire)) in java.
A solution can be printed to console. 

Example print of a position for a quadratic board of size 4:
![Screenshot](example_position.jpg)

Supported boards:
* english board
* quadratic board (size 4,5 or 6)

## Algorithm
Ideas from:
* http://code.google.com/p/peg-solitaire/source/browse/src/com/googlecode/pegsolitaire
* http://en.wikipedia.org/wiki/Peg_solitaire#Studies_on_peg_solitaire
* http://www.durangobill.com/Peg33.html
* http://www.gibell.net/pegsolitaire/English/index.html

A java *long* (64 bits) is used to represent positions on a board (bit set means a peg is located at this position).
Symmetric positions are considered to speed up the computation.

For finding a single solution a depth first search is performed.
