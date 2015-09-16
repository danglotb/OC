package solver

import scala.collection.mutable.ListBuffer
import data._
import solver._

/**
 * @author danglot
 */

object HillClimbingOption {

  type Options = Map[String, Any]

  def options(opt: Options, args: List[String]): Options = {

    args match {
      case "-select" :: selection :: tail => options(opt ++ Map("select" -> selection), tail)
      case "-nb" :: nb :: tail            => options(opt ++ Map("nb" -> nb), tail)
      case "-init" :: init :: tail        => options(opt ++ Map("init" -> init), tail)
      case _                              => usage(opt)
    }
  }

  def usage(opt: Options): Options = {
    opt
  }

}

object HillClimbing {

  def run(currentSolution: Solution, nbRun: Int,
          gen: (Solution, (Int, Int)) => ListBuffer[Int],
          select: (((Solution, (Int, Int)) => ListBuffer[Int]), Solution, (Int, Int)) => Solution): Unit = {
    if (nbRun == -1)
      return
    println("nbRun : " + nbRun + " Score : " + currentSolution.score)
    run(select(gen, currentSolution, (0, 1)), nbRun - 1, gen, select)
  }

  def swapGen(current: Solution, index: (Int, Int)): ListBuffer[Int] = {
    val ret = current.solution().clone
    ret -= ((current.solution())(index._1), (current.solution())(index._2))
    ret.insert(index._2, (current.solution())(index._1))
    ret.insert(index._1, (current.solution())(index._2))
    return ret
  }

  def selectFirst(genfunc: (Solution, (Int, Int)) => ListBuffer[Int],
                  currentSolution: Solution,
                  index: (Int, Int)): Solution = {
    if (index._1 == index._2)
      return selectFirst(genfunc, currentSolution, (index._1, index._2 + 1))
    else if (index._2 == currentSolution.instance().nbJobs()-1)
      return selectFirst(genfunc, currentSolution, (index._1 + 1, 0))
    else {
      val voisin = new Solution(currentSolution.instance(), genfunc(currentSolution, (index._1, index._2)))
      if (voisin.score < currentSolution.score)
        return voisin
      else
        return selectFirst(genfunc, currentSolution, (index._1, index._2 + 1))
    }
  }

  def initRandom(): Solution = {
    val reader = new InstanceReader(100, "input/wt100.txt", 125)
    val solver: Solver = new RandomSolver(100, reader)
    solver.run()
    return new Solution(solver.instance, solver.solution)
  }
  
  def initEdd() : Solution = {
    val reader = new InstanceReader(100, "input/wt100.txt", 125)
    val solver: Solver = new EddSolver(100, reader)
    solver.run()
    return new Solution(solver.instance, solver.solution)
  }
  
  def initMdd() : Solution = {
    val reader = new InstanceReader(100, "input/wt100.txt", 125)
    val solver: Solver = new MddSolver(100, reader)
    solver.run()
    return new Solution(solver.instance, solver.solution)
  }

}
