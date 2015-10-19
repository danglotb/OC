package solver

import scala.collection.mutable.ListBuffer
import data.Solution

/**
 * @author danglot
 */
class GenSolver {
  
  val nbGen = 1
  
  val nbMutation = 1
  
  def randomTuple(max : Int) : (Int, Int) = {
    val r = new java.util.Random
    val p1 = r.nextInt(max)
    var p2 = r.nextInt(max)
    while (p2 == p1) p2 = r.nextInt(max)
    (p1,p2)
  }
   
  def crossOver(p : (Solution,Solution)): ListBuffer[Solution] = {
    return new ListBuffer[Solution]
  }
  
  def mutation(p : Solution) : Solution = {
    p
  }
  
  def run(pc : ListBuffer[Solution], nbRun : Int) : Solution = {
    if (nbRun > nbGen)
      pc.maxBy { x => x score }
    else {
      
      val children = new ListBuffer[Solution]
      
      for (i <- 0 until pc.length / 2) {
        val parents = randomTuple(pc length)
        children ++= crossOver((pc(parents._1), pc(parents._2)))
      }
      
      val mutant = new ListBuffer[Solution]
      
      for (i <- 0 until nbGen) {
        val r = new java.util.Random
        mutant += mutation(pc(r.nextInt(pc.length)))
      }
      
    }
    pc(0)
  }
  
}