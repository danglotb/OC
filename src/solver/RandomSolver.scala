package solver

import data.InstanceReader
import data.Instance

/**
 * @author danglot
 */
class RandomSolver(nbJobs: Int, reader: InstanceReader) extends Solver(nbJobs, reader) {

  override def run(): Unit = {
    this.instance = reader.getInstance()
    solution = scala.util.Random.shuffle(solution)
    computeScore()
  }
}
