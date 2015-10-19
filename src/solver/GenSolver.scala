package solver

import scala.collection.mutable.ListBuffer
import data.Solution

/**
 * @author danglot
 */
object GenSolver {

  val nbGen = 100

  val nbMutation = 3

  def randomTuple(max: Int): (Int, Int) = {
    val r = new java.util.Random
    val p1 = r.nextInt(max)
    var p2 = r.nextInt(max)
    while (p2 == p1) p2 = r.nextInt(max)
    (p1, p2)
  }

  def crossOver(p: (Solution, Solution)): ListBuffer[Solution] = {
    val nbJobs = p._1.instance.nbJobs
    val k = nbJobs / 2
    val indexChosen = new ListBuffer[Int]
    val r = new java.util.Random
    var i = 0
    while(i < k) {
      val index = r.nextInt(nbJobs)
      if (!indexChosen.contains(index)) {
        i += 1
        indexChosen += index
      }
    }
    val child1 = new ListBuffer[Int]
    val child2 = new ListBuffer[Int]
    for(i <- 0 until nbJobs) {
      if (indexChosen.contains(i)) {
        child1 += p._1.solution()(i)
        child2 += p._2.solution()(i)
      } else {
        child2 += p._1.solution()(i)
        child1 += p._2.solution()(i)
      }
    }
    val result = new ListBuffer[Solution]
    result += new Solution(p._1.instance,child1)
    result += new Solution(p._1.instance,child2)
    
    result
  }

  def mutation(p: Solution): Solution = {
    HillClimbing.shuffleCursor(p.instance.nbJobs)
    HillClimbing.swapGen(p)
  }

  def getBest(l1: ListBuffer[Solution], l2: ListBuffer[Solution], l3: ListBuffer[Solution]): ListBuffer[Solution] = {
    val list = new ListBuffer[Solution]
    val end = l1.length
    while (list.length != end) {
      if (l1.maxBy { x => x score }.score > l2.maxBy { x => x score }.score) {
        if (l1.maxBy { x => x score }.score > l3.maxBy { x => x score }.score) {
          list += l1.maxBy { x => x score }
          l1 -= l1.maxBy { x => x score }
        } else {
          list += l3.maxBy { x => x score }
          l3 -= l3.maxBy { x => x score }
        }
      } else if (l2.maxBy { x => x score }.score > l3.maxBy { x => x score }.score) {
        list += l2.maxBy { x => x score }
        l2 -= l2.maxBy { x => x score }
      } else {
        list += l3.maxBy { x => x score }
        l3 -= l3.maxBy { x => x score }
      }
    }
    list
  }

  def run(pc: ListBuffer[Solution], nbRun: Int): Solution = {
    if (nbRun > nbGen)
      pc.maxBy { x => x score }
    else {
      
      val children = new ListBuffer[Solution]

      for (i <- 0 until pc.length / 2) {
        val parents = randomTuple(pc length)
        children ++= crossOver((pc(parents._1), pc(parents._2)))
      }
      
      val mutant = new ListBuffer[Solution]

      for (i <- 0 until nbMutation) {
        val r = new java.util.Random
        mutant += mutation(pc(r.nextInt(pc.length)))
      }
      run(getBest(pc, mutant, children), nbRun + 1)
    }
  }

}