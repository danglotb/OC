package solver

import scala.collection.mutable.ListBuffer
import data._
import solver._
import sun.reflect.generics.reflectiveObjects.NotImplementedException

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

  var score: Int = 0

  var start : (Int, Int) = (0,1)
  var cursor : (Int, Int) = start
  
  def report(): String = return "score : " + score

  def runAllInstances(gen: (Solution) => Solution,
                      select: (((Solution) => Solution), Solution) => (Solution) ): Unit = {
    while (reader.hasNext()) {
      start = (0,1)
      run(genFirstSolution, gen, select)
    }
  }

  def run(currentSolution: Solution,
          gen: (Solution) => Solution,
          select: (((Solution) => Solution), Solution) => (Solution) ): Unit = {
    start = cursor
    val selected = select(gen, currentSolution)
    if (selected == currentSolution) {
      print(currentSolution.score() + "\t")
      return
    }
    run(selected , gen, select)
  }

  //Generate neighbors

  def exchangeGen(current: Solution): Solution = {
    throw new NotImplementedException()
    return current
  }

  def insertGen(current: Solution): Solution = {
    throw new NotImplementedException()
    return current
  }

  def swapGen(current: Solution): Solution = {
    if (cursor._1 == cursor._2) {
      cursor = (cursor._1 ,(cursor._2 + 1) % (current.instance.nbJobs-1))
      if (cursor._2 == start._2)
        cursor = ((cursor._1 + 1) % (current.instance.nbJobs-1), cursor._2)
    } else {
      val ret = current.solution().clone
      ret -= ((current.solution())(cursor._1), (current.solution())(cursor._2))
      ret.insert(cursor._2, (current.solution())(cursor._1))
      ret.insert(cursor._1, (current.solution())(cursor._2))
      cursor = (cursor._1 ,(cursor._2 + 1) % (current.instance.nbJobs-1))
      if (cursor._2 == start._2)
         cursor = ((cursor._1 + 1) % (current.instance.nbJobs-1), cursor._2)
           return new Solution(current.instance, ret)
    }
    return current
  }

  //Select the right neighbor

  def selectFirst(genfunc: (Solution) => Solution,
                     currentSolution: Solution): Solution = {
    cursor = start
    
    var neigbhor : Solution = genfunc(currentSolution)
    
    while (neigbhor.score >= currentSolution.score) {
       neigbhor = genfunc(currentSolution)
       if (start == cursor) return currentSolution
    }
    
    neigbhor
  }

  def selectBest(genfunc: (Solution) => ListBuffer[Int],
                 currentSolution: Solution): Solution = {
    throw new NotImplementedException()
    return new Solution(currentSolution.instance(), currentSolution.solution())
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
    return new Solution(solver.instance, solver.solution)
  }

}
