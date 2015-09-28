package main

import solver.IterateLocalSearch
import solver.HillClimbing

/**
 * @author danglot
 */
object MainILS extends App {

  HillClimbing.initMdd("input/wt100.txt", 100, 125)

  //reach the optima
  for (i <- 0 until 125) {
    val time = System.currentTimeMillis()
     println(IterateLocalSearch.run(HillClimbing.genFirstSolution(), HillClimbing.selectBest, HillClimbing.insertGen, HillClimbing.exchangeGen,
    IterateLocalSearch.selectTheBetterSolution, IterateLocalSearch.terminationCounter).score + " : " + (System.currentTimeMillis()-time) + " ms")
  }

//  println(IterateLocalSearch.runVnd(HillClimbing.genFirstSolution , HillClimbing.runVnd, HillClimbing.selectFirst, 0, Array(HillClimbing.insertGen, HillClimbing.exchangeGen),
//      HillClimbing.swapGen, IterateLocalSearch.selectTheBetterSolution, IterateLocalSearch.terminationCounter))
    
}