package main.multiobjectiv

import data.multiobjectiv._
import solver.multiobjectiv._

/**
 * @author danglot
 */
object MainLocalSearch extends App {
  
  def buildList(solutions : List[PermutationSolution]) : Unit = {
    
  }
  
  val instance = tspBuilder.build2mTsp("input/tsp/randomA100.tsp", "input/tsp/randomB100.tsp")
  
  val firstSolution = RandomSolver.solve(instance)
  
  val sol = scala.collection.mutable.ListBuffer[PermutationSolution]()
  
  var lastSolution = firstSolution
  
  for(i <- 0.1 to 0.9 by 0.1) {
    for (j <- 1.0-i to 0.1 by -0.1) {
      val point = ScalarSearch.searchFirst(lastSolution, List(i,j), firstSolution.aggregationScore(List(i,j)))
      lastSolution = point
      sol += point
    }
  }
}