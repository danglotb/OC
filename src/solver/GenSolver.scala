package solver

import scala.collection.mutable.ListBuffer
import data.Solution

/**
 * @author danglot
 */
object GenSolver {

  def randomTuple(max: Int): (Int, Int) = {
    val r = new java.util.Random
    val p1 = r.nextInt(max)
    var p2 = r.nextInt(max)
    while (p2 == p1) p2 = r.nextInt(max)
    (p1, p2)
  }

  def chooseIndex(k: Int, l: List[Int], nbJobs: Int): List[Int] = {
    if (l.length >= k)
      l
    else {
      val r = new java.util.Random
      val index = r.nextInt(nbJobs)
      if (!l.contains(index))
        chooseIndex(k, l :+ index, nbJobs)
      else
        chooseIndex(k, l, nbJobs)
    }
  }

  def crossOver(p: (Solution, Solution)): List[Solution] = {
    val nbJobs = p._1.instance.nbJobs
    val k = nbJobs / 2
    val indexChosen = chooseIndex(k, Nil, nbJobs)
    val child1 = new ListBuffer[Int]
    val child2 = new ListBuffer[Int]
    for (i <- 0 until nbJobs) {
      if (indexChosen.contains(i)) {
        child1 += p._1.solution()(i)
        child2 += p._2.solution()(i)
      } else {
        child2 += p._1.solution()(i)
        child1 += p._2.solution()(i)
      }
    }
//    val result = new ListBuffer[Solution]
    val result : List[Solution] = new Solution(p._1.instance, child1) :: Nil
    return (result :+ new Solution(p._1.instance, child2))

  }

  def mutation(p: Solution): Solution = {
    HillClimbing.shuffleCursor(p.instance.nbJobs)
    HillClimbing.swapGen(p)
  }

  def getBest(l1: ListBuffer[Solution], l2: ListBuffer[Solution], l3: ListBuffer[Solution]): ListBuffer[Solution] = {
    val list = new ListBuffer[Solution]
    val end = l1.length
    while (list.length < end) {
      if (!l1.isEmpty || !l2.isEmpty && l1.minBy { x => x score }.score > l2.minBy { x => x score }.score) {
        if (l3.isEmpty || l1.minBy { x => x score }.score > l3.minBy { x => x score }.score) {
          list += l1.minBy { x => x score }
          l1 -= l1.minBy { x => x score }
        } else {
          list += l3.minBy { x => x score }
          l3 -= l3.minBy { x => x score }
        }
      } else if (l3.isEmpty || l2.minBy { x => x score }.score > l3.minBy { x => x score }.score) {
        list += l2.minBy { x => x score }
        l2 -= l2.minBy { x => x score }
      } else {
        list += l3.minBy { x => x score }
        l3 -= l3.minBy { x => x score }
      }
    }
    list
  }

  def getBestFr3(lr: List[Solution], l1: List[Solution], l2: List[Solution], l3: List[Solution], sizeOfPop: Int): List[Solution] = {
    if (lr.length >= sizeOfPop)
      lr
    else {
      if (!l1.isEmpty || !l2.isEmpty && l1.minBy { x => x score }.score > l2.minBy { x => x score }.score) {
        if (l3.isEmpty || l1.minBy { x => x score }.score > l3.minBy { x => x score }.score) {
          getBestFr3(lr :+ l1.minBy { x => x score }, l1.filter { x => x == l1.minBy { x => x score } }, l2, l3, sizeOfPop)
        } else {
          getBestFr3(lr :+ l3.minBy { x => x score }, l1, l2, l3.filter { x => x == l1.minBy { x => x score } }, sizeOfPop)
        }
      } else if (l3.isEmpty || l2.minBy { x => x score }.score > l3.minBy { x => x score }.score) {
        getBestFr3(lr :+ l2.minBy { x => x score }, l1, l2.filter { x => x == l1.minBy { x => x score } }, l3, sizeOfPop)
      } else {
        getBestFr3(lr :+ l3.minBy { x => x score }, l1, l2, l3.filter { x => x == l1.minBy { x => x score } }, sizeOfPop)
      }
    }
  }

  def run(nbGen: Int, nbMutation: Int, pc: ListBuffer[Solution], nbRun: Int): Solution = {
    println("NbRun " + nbRun)
    if (nbRun >= nbGen)
      pc.minBy { x => x score }
    else {

      
      val children2 : List[Solution] = Nil
      
      val children = new ListBuffer[Solution]

      for (i <- 0 until pc.length / 2) {
        val parents = randomTuple(pc length)
//        children ++= crossOver((pc(parents._1), pc(parents._2)))
        children2 ::: crossOver((pc(parents._1), pc(parents._2)))
      }

      val mutant = new ListBuffer[Solution]

      for (i <- 0 until nbMutation) {
        val r = new java.util.Random
        mutant += mutation(pc(r.nextInt(pc.length)))
      }
      run(nbGen, nbMutation, getBest(pc, mutant, children), nbRun + 1)
    }
  }

}