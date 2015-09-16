package solver

import data.InstanceReader

/**
 * @author danglot
 */
class EddSolver(nbJobs: Int, reader: InstanceReader) extends Solver(nbJobs, reader) {

  def run(): Unit = {
    this.instance = reader.getInstance()
    solution = solution.sortBy { x => instance.dueDates(x) }
  }

}
