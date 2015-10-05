package solver

import data.Solution

/**
 * @author danglot
 */
object IterateLocalSearch {

  val nbRun = 10

  var count: Int = 0

  var current: Solution = _

  def run(firstSol: Solution,
          localSearch: ((Solution) => Solution, Solution) => Solution,
          localGen: (Solution) => Solution,
          pertubGen: (Solution) => Solution,
          select: (Solution, Solution) => Solution,
          termination: () => Boolean): Solution = {
    count = 0
    current = localSearch(localGen, firstSol)
    while (termination()) {
      val selected = localSearch(localGen, pertubation(pertubGen, current))
      current = select(current, selected)
    }
    current
  }

  //generate worst solution
  def runVnd(firstSol: Solution,
             localSearch: (Array[(Solution) => Solution], Solution, (((Solution) => Solution), Solution) => (Solution), Int) => Solution,
             localSelect: (Solution => Solution, Solution) => Solution,
             localCount: Int,
             localGen: Array[(Solution) => Solution],
             pertubGen: (Solution) => Solution,
             select: (Solution, Solution) => Solution,
             termination: () => Boolean): Solution = {
    var current = localSearch(localGen, firstSol, localSelect, localCount)
    while (termination()) {
      val selected = localSearch(localGen, pertubation(pertubGen, current), localSelect, localCount)
      current = select(current, selected)
    }
    current

  }

  def pertubation(gen: (Solution) => Solution, current: Solution): Solution = {
    val r: scala.util.Random = scala.util.Random
    val c = (r.nextInt(10) + 3)
    var iterateSol = gen(current)
    for (i <- 1 until c) { iterateSol = gen(iterateSol) }
    iterateSol
  }

  def terminationCounter(): Boolean = return count <= nbRun

  def selectBestSolution(s1: Solution, s2: Solution): Solution = {
    if (s2.score < s1.score) {
      count = 0
      s2
    } else {
      count += 1
      s1
    }
  }

  def selectBetterSolution(s1: Solution, s2: Solution): Solution = {
    count += 1
    if (s2.score <= s1.score) s2 else s1
  }

}