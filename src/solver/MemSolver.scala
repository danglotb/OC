package solver

import scala.collection.mutable.ListBuffer

import data.Solution

object MemSolver {

  def crossOver(p: (Solution, Solution), begin: Int, end: Int): Solution = {
    val solutionChild = new ListBuffer[Int]()
    val solutionChild1 = new ListBuffer[Int]()
    
    for (i <- begin until end)
      solutionChild1 += p._2.solution()(i)
    
    for (i <- 0 until begin)
      solutionChild += p._1.solution()(i)

    for (i <- end until p._1.instance().nbJobs())
      solutionChild += p._1.solution()(i)
      
    return new Solution(p._1.instance(), solutionChild)
  }

  def mutation(p: Solution): Solution = {
    HillClimbing.shuffleCursor(p.instance.nbJobs)
    HillClimbing.swapGen(p)
  }

  def getBest2(l1: ListBuffer[Solution], l2: ListBuffer[Solution]): ListBuffer[Solution] = {
    val list = new ListBuffer[Solution]
    val end = l1.length
    while (list.length < end) {
      if (!l1.isEmpty || !l2.isEmpty && l1.minBy { x => x score }.score > l2.minBy { x => x score }.score) {
        list += l1.minBy { x => x score }
        l1 -= l1.minBy { x => x score }
      } else {
        list += l2.minBy { x => x score }
        l2 -= l2.minBy { x => x score }
      }
    }
    list
  }

  def run(nbGen: Int, pc: ListBuffer[Solution], nbRun: Int, cRate: Double, cMutation: Double,
      begin : Int, end : Int, 
      localSearch : (Solution => Solution)) : Solution = {
    if (nbRun > nbGen)
      pc.minBy { x => x score }
    else {
      val children = new ListBuffer[Solution]()
      val r = new java.util.Random
      for (i <- 0 until (cRate * pc.length).toInt) {
        val parents = GenSolver.randomTuple(pc length)
        var newChild = crossOver((pc(parents._1), pc(parents._2)), begin, end)
        if (r.nextInt() < cMutation)
          newChild = mutation(newChild)
        localSearch(newChild)
        children += newChild
      }
      run(nbGen, getBest2(pc, children), nbRun + 1, cRate, cMutation, begin, end, localSearch)
    }
  }

}