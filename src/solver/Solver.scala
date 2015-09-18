package solver

import data.InstanceReader
import data.Instance
import data.Solution
import scala.collection.mutable.ListBuffer

/**
 * @author danglot
 */
abstract class Solver(nbJobs: Int, reader: InstanceReader) {
  
  var solution : Solution = _

  def run(): Unit

}