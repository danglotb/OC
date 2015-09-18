package solver

import data.InstanceReader
import data.Instance

/**
 * @author danglot
 */
class MddSolver(nbJobs: Int, reader: InstanceReader) extends Solver(nbJobs, reader) {

  var currentTime = 0

  var nextJobs = -1

  def run(): Unit = {
    this.instance = reader.getInstance()

    var indexRemains = solution.clone()
    
    solution.clear

    while (indexRemains.length != 0) {
      nextJobs = indexRemains.minBy { x =>
        Math.max(instance.processingTimes(x) + currentTime, instance.dueDates(x))
      }
      computeScore()
      indexRemains = indexRemains - nextJobs
      solution += nextJobs
    }
    
    currentTime = 0
  }

  def computeScore(): Unit = {
    currentTime += instance.processingTimes(nextJobs);
    val time = Math.max((currentTime - instance.dueDates(nextJobs)), 0);
    this.score += instance.weights(nextJobs) * time;
  }

}