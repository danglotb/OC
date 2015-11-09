package solver.multiobjectiv

import data.multiobjectiv._

/**
 * @author danglot
 */
object RandomSolver {

  def solve(instance: InstanceTSP): PermutationSolution = randomSolve(0, Nil, new PermutationSolution(instance.n, instance))

  private def randomSolve(i: Int, indexChosen: List[Int], solution: PermutationSolution): PermutationSolution = {
    val n = solution.solution.length
    if (i == n)
      solution
    else {
      val r = new java.util.Random
      val rindex = r.nextInt(n)
      if (indexChosen.contains(rindex))
        randomSolve(i, indexChosen, solution)
      else {
        solution.solution(i) = rindex
        randomSolve(i + 1, rindex :: indexChosen, solution)
      }
    }
  }

}