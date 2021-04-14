Simulation completed for a class assignment for an Objected Oriented Software Development class. 
From the assignment spec:
"a graphical simulation of a world inhabited by creatures called gatherers. Their
purpose in life is to gather fruit from the trees, and deposit them at stockpiles. Once they have
gathered all the fruit from their trees, they rest in front of fences.
Making their life difficult is the thief who aims to steal fruit from the stockpiles and place it in
their hoards. The thief and gatherers follow rigid rules, and once they all reach their final goals (the
fence), the simulation halts. ...
The behaviour of the simulation is entirely determined by the world file loaded when the Shadow
Life program starts: each gatherer, thief, and other element begins at a specified location and
follows a set of rules to determine their behaviour. ... Your simulation must take three command-line arguments: the tick rate (in milliseconds), the
maximum number of ticks, and the world file."

ShadowLife.java runs the overall simulation logic
World.java manages the information about the particular simulated world
Actor.java, and Agent.java are abstract classes for elements within the simulation
Pile.java, Gatherer.java, Thief.java, Tree.java, and BasicActor.java are classes for elements within the simulation
TileCoordinates.java, Fruits.java and Directions.java are used by the element classes.
