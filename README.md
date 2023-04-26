# PathZilla

  ![alt text](https://i.ibb.co/WpJ34y5/Path-Zilla.png)
  
# What is PathZilla ?

The set of BRENDA's reactions (approximately 53000) constitutes a directed graph which can have cycles. The goal is to enumerate all related subgraphs (paths of given length) that have inputs and a specified output. The problem is known to be NP-complete and the naive algorithm is exponential in the length of the searched paths.

- 2 version of this projec have been developed : terminal based one and a "user friendly" GUI made using javaFx.
- The use is pretty simple. You load your set of reactions and inhibitions, and indicates the path you are looking for.
- The result is relatively quick, but of course the greater the depth is the execution time gets worse.
- For searches starting at `depth := 4` the openMP library is used in order to accelerate the process.
- If you have a test file, you can run unit tests in order to verify the accuracy.

# Preview

  ![alt text](https://i.ibb.co/qRbSqjT/Capture-d-cran-du-2023-04-21-22-48-06.png)
  ![alt text](https://i.ibb.co/Gs3g2hZ/Capture-d-cran-du-2023-04-21-22-48-38.png)
  
# Things to be done 

  It was a university project done during my M1 in computer science at Paris-Saclay : https://www.lri.fr/~pa/TER-M1/TER-Extraction-Reactions.html
  
  At the moment the main goal set by the teacher has been done, but a lot can be done / improved such as:
  
  - give a new look at the GUI
  - make a web version ? (a java applet or a django website for instance)
  - allow doing benchmark to see when the HPC version get better over the iterative one
  - show all the paths in a seperate window for a better understanding of what is going on 
  - and so on ...
 
 If you want to contribute or have ideas contact us.
 
 # Credit
 
  This sofware has been coded by Théo Manea & Oleksander Vladimirov . Paris-Saclay University - M1 IOT ~ Class of 2022-2023
