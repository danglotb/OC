package data

import scala.collection.mutable.ListBuffer

class Solution(instance : Instance, solution : ListBuffer[Int]) {
  
  def solution() : ListBuffer[Int] = this.solution
  def instance() : Instance = this.instance
  
  def score() : Int = {
    var score = 0
    var currentTime : Int = 0
    for (i <- 0 until instance.nbJobs) {
      currentTime += instance.processingTimes(this.solution(i))
      val time = Math.max((currentTime - instance.dueDates(this.solution(i))), 0)
      score += instance.weights(this.solution(i)) * time
    }
    score
  }
  
  override def toString() : String = "score "+score()+"\n"+solution+"\n"+solution.length+"\n"+check
  
  def ==(other : Solution) : Boolean = solution.equals(other.solution())
  
  def !=(other : Solution) : Boolean = !(this == other)
 
  def check() : Boolean = solution.distinct.length == solution.length
  
}