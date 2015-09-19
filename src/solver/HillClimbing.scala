package solver

import scala.collection.mutable.ListBuffer
import data._
import solver._

/**
 * @author danglot
 */

object HillClimbingOptions {

  type Options = Map[String, Any]

  def options(opt: Options, args: List[String]): Options = {
    if (args.isEmpty)
      opt
    else {
      args match {
        case "-select" :: selection :: tail  => options(opt ++ Map("select" -> selection), tail)
        case "-neighbor" :: neighbor :: tail => options(opt ++ Map("neighbor" -> neighbor), tail)
        case "-init" :: init :: tail         => options(opt ++ Map("init" -> init), tail)
        case "-k" :: k :: tail               => options(opt ++ Map("k" -> k), tail)
        case "-file" :: file :: tail         => options(opt ++ Map("file" -> file), tail)
        case _                               => usage(opt)
      }
    }
  }

  def usage(opt: Options): Options = {
    print("Options availaibles : \n")
    print("\t -select [first,best] to choose the selection mode\n")
    print("\t -neighbor [insert,swap,exchange] to choose the generation of neighbors\n")
    print("\t -init [rnd,edd,mdd] to choose the how the first solution is generates\n")
    print("\t -file <pathname> to specify the path to the file used\n")
    print("\t -k <Int> to choose how many run the apps will do (30 if no specified).\n")
    System.exit(1)
    opt
  }

}

object HillClimbing {

  var solver: Solver = _

  var reader: InstanceReader = _

  var start: (Int, Int) = (0, 0)
  
  var cursor: (Int, Int) = start
  
  var scores : ListBuffer[Int] = new ListBuffer[Int]()
  var times : ListBuffer[Long] = new ListBuffer[Long]()

  def runAllInstances(gen: (Solution) => Solution,
                      select: (((Solution) => Solution), Solution) => (Solution)): Unit = {
    scores.clear
    times.clear
    while (reader.hasNext()) {
      start = (0, 1)
      val time = System.currentTimeMillis()
      scores += run(genFirstSolution, gen, select)
      times += (System.currentTimeMillis() - time)
      if (times.length % 25 == 0)
        println(times.length)
    }
    println()
  }

  def run(currentSolution: Solution,
          gen: (Solution) => Solution,
          select: (((Solution) => Solution), Solution) => (Solution)): Int = {
    start = cursor
    val time = System.currentTimeMillis()
    val selected = select(gen, currentSolution)
    println("selection : " + (System.currentTimeMillis() - time) + " ms")
    if ( (selected == currentSolution)||(selected.score == currentSolution.score) ) {
//      print(currentSolution.score() + "\t")
      return selected.score
    }
    run(selected, gen, select)
  }

  //Generate neighbors

  def exchangeGen(current: Solution): Solution = {
    val ret = current.solution().clone
    ret(cursor._1) = current.solution()((cursor._1 + 1) % (current.instance.nbJobs - 1))
    ret((cursor._1 + 1) % (current.instance.nbJobs - 1)) = current.solution()(cursor._1)
    cursor = ( ((cursor._1 + 1) % (current.instance.nbJobs - 1), cursor._2) )
    return new Solution(current.instance, ret)
  }

  def insertGen(current: Solution): Solution = {
    if (cursor._1 == cursor._2) {
      cursor = (cursor._1, (cursor._2 + 1) % (current.instance.nbJobs - 1))
      if (cursor._2 == start._2)
        cursor = ((cursor._1 + 1) % (current.instance.nbJobs - 1), cursor._2)
    } else {
      val ret = current.solution().clone
      val move = ret remove current.solution()(cursor._1)
      ret insert (cursor._2, move)
      cursor = (cursor._1, (cursor._2 + 1) % (current.instance.nbJobs - 1))
      if (cursor._2 == start._2)
        cursor = ((cursor._1 + 1) % (current.instance.nbJobs - 1), cursor._2)
      return new Solution(current.instance, ret)
    }
    return current
  }

  def swapGen(current: Solution): Solution = {
    if (cursor._1 == cursor._2) {
      cursor = (cursor._1, (cursor._2 + 1) % (current.instance.nbJobs - 1))
      if (cursor._2 == start._2)
        cursor = ((cursor._1 + 1) % (current.instance.nbJobs - 1), cursor._2)
    } else {
      val ret = current.solution().clone
      ret(cursor._1) = current.solution()(cursor._2)
      ret(cursor._2) = current.solution()(cursor._1)
      cursor = (cursor._1, (cursor._2 + 1) % (current.instance.nbJobs - 1))
      if (cursor._2 == start._2)
        cursor = ((cursor._1 + 1) % (current.instance.nbJobs - 1), cursor._2)
      return new Solution(current.instance, ret)
    }
    return current
  }

  //Select the right neighbor

  def selectFirst(genfunc: (Solution) => Solution,
                  currentSolution: Solution): Solution = {
    cursor = start
    val score = currentSolution.score
    var neigbhor : Solution = genfunc(currentSolution)
    while (neigbhor.score >= score) {
      neigbhor = genfunc(currentSolution)
      if (start == cursor) return currentSolution
    }
    neigbhor
  }

  def selectBest(genfunc: (Solution) => Solution,
                 currentSolution: Solution): Solution = {
    val neigbhors: ListBuffer[Solution] = new ListBuffer[Solution]()
    do {
      neigbhors += genfunc(currentSolution)
    } while (start != cursor);
    return neigbhors.minBy { x => x.score() }
  }

  //Init the first solution

  def initRandom(pathname: String): Unit = {
    reader = new InstanceReader(100, pathname, 125)
    solver = new RandomSolver(100, reader)
  }

  def initEdd(pathname: String): Unit = {
    reader = new InstanceReader(100, pathname, 125)
    solver = new EddSolver(100, reader)
  }

  def initMdd(pathname: String): Unit = {
    reader = new InstanceReader(100, pathname, 125)
    solver = new MddSolver(100, reader)
  }

  def genFirstSolution(): Solution = {
    solver.run()
    return solver.solution
  }

}
