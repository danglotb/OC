package data.multiobjectiv

/**
 * @author danglot
 */
class PermutationSolution(n : Int, instance : InstanceTSP) {

  val solution = Array.ofDim[Int](n)
  
  def score() : Array[Int] = computeScore(0, Array.ofDim(instance.m))
  
  private def computeScore(i : Int, currentScore : Array[Int]) : Array[Int] = {
    if (i == n-1)
      currentScore
    else {
      for (m <- 0 until instance.m)
        currentScore(m) += instance.getValue(m, solution(i), solution(i+1))
      computeScore(i+1,currentScore)
    }
  }
  
  override def toString() : String = {
    var string = ""
    solution.foreach { f =>
      string += f + "\t"
    }
    string
  }
}