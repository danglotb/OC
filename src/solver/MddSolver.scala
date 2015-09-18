package solver

import data._
import scala.collection.mutable.ListBuffer

/**
 * @author danglot
 */
class MddSolver(nbJobs: Int, reader: InstanceReader) extends Solver(nbJobs, reader) {

  def run(): Unit = {
    var currentTime : Int = 0
    val instance : Instance = reader.getInstance()
    val indexRemains : ListBuffer[Int] = new ListBuffer[Int]()
    val indexSolution : ListBuffer[Int] = new ListBuffer[Int]()
    for (i <- 0 until nbJobs) indexRemains += i
    
    var nextJobs : Int = -1
    
    while ( ! indexRemains.isEmpty) {
      nextJobs = indexRemains.minBy { x =>
        Math.max(instance.processingTimes(x) + currentTime, instance.dueDates(x))
      }
      currentTime += instance.processingTimes(nextJobs)
      indexRemains -= nextJobs
      indexSolution += nextJobs
    }
    currentTime = 0
    
    solution = new Solution(instance, indexSolution)
  }


}