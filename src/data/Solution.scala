package data

import scala.collection.mutable.ListBuffer

class Solution(instance : Instance, solution : ListBuffer[Int]) {
  
  def solution() : ListBuffer[Int] = this.solution
  def instance() : Instance = this.instance
  
  def score() : Int = {
    var score = 0
    var currentTime : Int = 0
    for (i <- 0 until instance.nbJobs) {
      currentTime += instance.processingTimes(this.solution(i));
      val time = Math.max((currentTime - instance.dueDates(this.solution(i))), 0);
      score += instance.weights(this.solution(i)) * time;
    }
    score
  }
  
}