# StarfinderGM
Program to help design and run campaigns and adventures for the Starfinder tabletop RPG. 
Ueses the website Archives of Nethys, https://www.aonsrd.com/Default.aspx for information.\
Currently in early stages of devlopment.
## Archives
Holds the .txt files created from ripping the website's urls
## tools
Hold the jar files used in the project
## txtfiles 
to be removed once Archives is fully implemented.
## database
the SQLite database needed to run the main GUI

# Current Build
Currently can be usued to build a starship and to view equipment found in the game
## file->ship
new ship is the only working selection, used to buld a ship 
## file->Equipment
current funchtions include searching through and reading the database by equipment type then filtering by sub type and levels
## Galaxy bulder and vier
Currently only useable in a CLI enviorment. lets you add sectors to the galaxy and view existing systems.
on View Body #: command '0' will send back to start.
## rest
function of other options still in development.

# To run
## Main GUI app
gradle build -> gradle runApp

## Galaxy CLI
gradle build -> gradle worldbuilder
