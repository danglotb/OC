package solver

import data.InstanceReader
import scala.collection.mutable.ListBuffer

/**
 * @author danglot
 */
abstract class Solver(nbJobs : Int, reader : InstanceReader) {
  
  var solution : ListBuffer[Int] = ListBuffer[Int]()
  
  var score : Int = 0
  
  def run() : Unit
  
  override def toString() : String = {
    return this.reader.nbRead+" : "+this.score;
  }

}