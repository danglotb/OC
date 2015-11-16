package data.multiobjectiv

/**
 * @author danglot
 */
class PermutationSolution(n : Int, instance : InstanceTSP) {

  val solution = Array.ofDim[Int](n)
  
  def score() : Array[Int] = computeScore(0, Array.ofDim(instance.m))
  
  def aggregationScore(weigth : List[Double]) : Int = computeAggregateScore(0,0,weigth)
  
  def getN = n
  
  def getInstance = instance
  
  def copySolution(newSolution : Array[Int]) : Unit = {
    assert(newSolution.length == solution.length)    
    for (i <- 0 until solution.length)
      solution(i) = newSolution(i)
  }
  
  def ==(that : PermutationSolution) = that.solution == this.solution
  
  private def computeScore(i : Int, currentScore : Array[Int]) : Array[Int] = {
    if (i == n-1)
      currentScore
    else {
      for (m <- 0 until instance.m)
        currentScore(m) += instance.getValue(m, solution(i), solution(i+1))
      computeScore(i+1,currentScore)
    }
  }
  
  private def computeAggregateScore(i : Int, currentScore : Int, weigth : List[Double]) : Int = {
     if (i == n-1) {
       currentScore
     } else {
       var updateScore = 0
       for (m <- 0 until instance.m) {
         val addScore = (instance.getValue(m, solution(i), solution(i+1))*weigth(m)).toInt
         updateScore  += addScore
       }
       computeAggregateScore(i+1, currentScore+updateScore, weigth)
     }
  }
  
  override def toString() : String = {
    var string = ""
    solution.foreach { f =>
      string += f + "\t"
    }
    string + "\n"
  }
}