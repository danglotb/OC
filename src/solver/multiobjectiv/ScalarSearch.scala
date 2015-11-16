package solver.multiobjectiv

import data.multiobjectiv.PermutationSolution

/**
 * @author danglot
 */
object ScalarSearch {

  private val end = (0, 0)

  private var cursor = (0, 1)

  def searchFirst(current: PermutationSolution, weigth: List[Double], currentScore: Int): PermutationSolution = {
    val newSolution = buildSolutionFrom(current)
    if (newSolution == current || end == cursor)
      return current
    else {
      val newScore = newSolution.aggregationScore(weigth)
      if (newScore <= currentScore)
        searchFirst(newSolution, weigth, newScore)
      else
        searchFirst(current, weigth, currentScore)
    }
  }

  def searchBest(current: PermutationSolution, weigth: List[Double], currentScore: Int): PermutationSolution = {
    val listNewSolution = buildAllSolutionFrom(current, Nil)
    val newSolution = listNewSolution.maxBy { x => x.aggregationScore(weigth) }
    val newScore = newSolution.aggregationScore(weigth)
    if (newScore >= currentScore)
      searchBest(newSolution, weigth, newScore)
    else
      current
  }

  private def buildSolutionFrom(ref: PermutationSolution): PermutationSolution = {
    val newSolution = new PermutationSolution(ref getN, ref getInstance)
    newSolution.copySolution(twoOpt(ref.solution))
    newSolution
  }

  private def buildAllSolutionFrom(ref: PermutationSolution, newsSolutions: List[PermutationSolution]): List[PermutationSolution] = {
    if (cursor == end)
      newsSolutions
    else
      buildAllSolutionFrom(ref, newsSolutions :+ buildSolutionFrom(ref))
  }

  private def twoOpt(current: Array[Int]): Array[Int] = {
    val newElement = current.slice(math.min(cursor._1, cursor._2), math.max(cursor._1, cursor._2)).reverse
    val ret = current.patch(math.min(cursor._1, cursor._2), newElement, newElement.length)
    updateCursor(current.length)
    ret
  }

  private def updateCursor(bound: Int): Unit = {
    if (cursor._2 + 1 == bound)
      if (cursor._1 + 1 == bound)
        cursor = end
      else
        cursor = (cursor._1 + 1, 0)
    else
      cursor = (cursor._1, cursor._2 + 1)
  }

}