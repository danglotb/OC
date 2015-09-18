package solver

import data._
import scala.collection.mutable.ListBuffer

/**
 * @author danglot
 */
class EddSolver(nbJobs: Int, reader: InstanceReader) extends Solver(nbJobs, reader) {

  def run(): Unit = {
    val solutionIndex : ListBuffer[Int] = new ListBuffer[Int]()
    val instance : Instance = reader.getInstance()
    for (i <- 0 until nbJobs) solutionIndex += i
    solution = new Solution(instance,  solutionIndex.sortBy { x => instance.dueDates(x) })
  }

}
