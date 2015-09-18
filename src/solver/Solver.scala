package solver

import data.InstanceReader
import data.Instance
import data.Solution
import scala.collection.mutable.ListBuffer

/**
 * @author danglot
 */
abstract class Solver(nbJobs: Int, reader: InstanceReader) {
  
  var solution: ListBuffer[Int] = ListBuffer[Int]()
  
  var instance: Instance = _

  for (i <- 0 until nbJobs)
    solution += i

  var score : Int = 0

  def run(): Unit

  override def toString(): String = {
    return this.reader.nbRead + " : " + this.score;
  }

}