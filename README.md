# Protein-Folding

This is meant to be a very simplified simulation of protein folding, where the amino acids are simple spherical particles of variable size & charge.
There are 5 forces at play:
-A spring force between neighbouring particles, modified so there is an "optimal distance" between particles"
-Two inverse square forces on two different charches: one were ++ attract, one were they repell.
-A force pushing overlapping paricles apart.
-A Friction force


To get started, create a java project with these 3 classes 

landscape.java contains themain method.
Here one can change settings on screensize, how many diferkinds of particles there are and how many will be used.
The String mode says how the particles are placed. I implemented 
-line
-random
-point
-circle
-spiral

amino.java is the class containing the particle objects.
Here one can wiggle static parameters for force strengths.
"slowdown" is a dampening/friction factor.
dt is the time step.
d is the dimension, which can be set to 2 or 3.

protein.java is the class of protein objects, which are sequences of particles.
