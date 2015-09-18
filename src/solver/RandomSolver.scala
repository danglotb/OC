package solver

import data._

import scala.collection.mutable.ListBuffer

/**
 * @author danglot
 */
class RandomSolver(nbJobs: Int, reader: InstanceReader) extends Solver(nbJobs, reader) {

  override def run(): Unit = {
    val solutionIndex : ListBuffer[Int] = new ListBuffer[Int]()
    for (i <- 0 until nbJobs) solutionIndex += i
    solution = new Solution(reader.getInstance(),  scala.util.Random.shuffle(solutionIndex))
  }
}
