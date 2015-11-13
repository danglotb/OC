package solver.multiobjectiv

import data.multiobjectiv.PermutationSolution

import scala.collection.mutable.ListBuffer

/**
 * @author danglot
 */
object Filter2o {

  def filter(solution: List[(Int, Int)], max: Boolean = false): List[(Int, Int)] = {
    if (max)
      filterMax(solution, solution, 0)
    else
      filterMin(solution, solution, 0)
  }

  private def filterMin(solution: List[(Int, Int)], dominator : List[(Int, Int)], i: Int): List[(Int, Int)] = {
    if (i >= solution.length)
      dominator
    else {
      val dominated = solution.filter {p => p != solution(i) && compare2Tuple(p,solution(i))}
      filterMin(solution, dominator.diff(dominated), i+1)
    }
  }
  
  private def filterMax(solution: List[(Int, Int)],dominator : List[(Int, Int)], i : Int): List[(Int, Int)] = {
    if (i >= solution.length)
      dominator
    else {
      val dominated = solution.filter {p => p != solution(i) && compare2Tuple(solution(i),p)}
      filterMax(solution, dominator.diff(dominated), i+1)
    }
  }

  private def compare2Tuple(t1: (Int, Int), t2: (Int, Int)) = t1._1 >= t2._1 && t1._2 >= t2._2

}