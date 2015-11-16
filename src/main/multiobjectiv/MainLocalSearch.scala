package main.multiobjectiv

import data.multiobjectiv._
import solver.multiobjectiv._

/**
 * @author danglot
 */
object MainLocalSearch extends App {

  def buildList(lastSolution: PermutationSolution, solutions: List[PermutationSolution], i: Double, j: Double): List[PermutationSolution] = {
    if (j == 0.1) {
      if (i == 0.9)
        solutions
      else
        buildList(lastSolution, solutions, i + 0.1, 1.0 - (i + 0.1))
    } else {
      val newSolution = ScalarSearch.searchFirst(lastSolution, List(i, j), lastSolution.aggregationScore(List(i, j)))
      buildList(newSolution, Filter2o.filterMinSol(Nil, solutions :+ newSolution, 0)._1, i, j + 0.1)
    }
  }

  val instance = tspBuilder.build2mTsp("input/tsp/randomA100.tsp", "input/tsp/randomB100.tsp")

  val firstSolution = RandomSolver.solve(instance)

  val sol = scala.collection.mutable.ListBuffer[PermutationSolution]()

  var lastSolution = firstSolution

  for (i <- 0.1 to 0.9 by 0.1) {
    for (j <- 1.0 - i to 0.1 by -0.1) {
      val point = ScalarSearch.searchFirst(lastSolution, List(i, j), firstSolution.aggregationScore(List(i, j)))
      lastSolution = point
      sol += point
    }
  }
}