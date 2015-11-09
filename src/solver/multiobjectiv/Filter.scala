package solver.multiobjectiv

import data.multiobjectiv.PermutationSolution

import scala.collection.mutable.ListBuffer

/**
 * @author danglot
 */
object Filter2o {

  def filter(solution: List[(Int, Int)], max: Boolean = false): List[(Int, Int)] = {
    if (max)
      filterMax(solution, 0)
    else
      filterMin(solution, 0)
  }

  private def filterMin(solution: List[(Int, Int)],  i: Int): List[(Int, Int)] = {
    if (i >= solution.length)
      solution
    else {
      val dominated = solution.filter { p => compare2Tuple(p,solution(i))}
      if (dominated.length == 0)
        filterMin(solution.filter(p => p != solution(i)), 0)
      else
        filterMin(solution.diff(dominated), i+1)   
    }
  }

  private def filterMax(solution: List[(Int, Int)], i : Int): List[(Int, Int)] = {
    if (i >= solution.length)
      solution
    else {
      val dominated = solution.filter { p => compare2Tuple(solution(i), p)}
      if (dominated.length == 0)
        filterMax(solution.filter(p => p != solution(i)), 0)
      else
        filterMax(solution.diff(dominated), i+1)   
    }
  }

  private def compare2Tuple(t1: (Int, Int), t2: (Int, Int)) = t1._1 > t2._1 && t1._2 > t2._2

}