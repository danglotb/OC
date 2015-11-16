package main.multiobjectiv

import data.multiobjectiv._
import solver.multiobjectiv._
import data.Logger

object Build500Random {
   def build500RandomOnLine(dominators : List[(Int, Int)], dominate: List[(Int, Int)], instance: InstanceTSP, p : Int): (List[(Int, Int)], List[(Int, Int)]) = {
     if ((dominators.length + dominate.length) == p)
      (dominators,dominate)
    else {
      val newSolution = RandomSolver.solve(instance).score
      val newTupleSolution = Filter2o.filter(dominators :+ (newSolution(0), newSolution(1)))
      build500RandomOnLine(newTupleSolution._1, newTupleSolution._2.++(dominate), instance, p)
    }
  }

  def build500Random(solutions: List[(Int, Int)], instance: InstanceTSP, p : Int): List[(Int, Int)] = {
    if (solutions.length == p)
      solutions
    else {
      val newSolution = RandomSolver.solve(instance).score
      build500Random(solutions :+ (newSolution(0), newSolution(1)), instance, p)
    }
  }
}

object MainOnLineRandom extends App {

  val instance = tspBuilder.build2mTsp("input/tsp/randomA100.tsp", "input/tsp/randomB100.tsp")

  val time = System.currentTimeMillis()
  
  val solutions = Build500Random.build500RandomOnLine(Nil, Nil,instance,1000)

  println(System.currentTimeMillis() - time+ " ms")
  
  //build data
  var str = ""
  solutions._1.foreach { x =>
    str += x._1 + "\t" + x._2 + "\n"
  }
  data.Util.write("data/domRandom500mTSP.dat", str)

  str = ""
  solutions._2.foreach { x =>
    str += x._1 + "\t" + x._2 + "\n"
  }
  data.Util.write("data/random500mTSP.dat", str)

  //build plt file
  str = "plot \'../data/random500mTSP.dat\' title \'non-dominate\' ,  \'../data/domRandom500mTSP.dat\' title \'dominate\'"
  data.Util.write("plot/random500mTSP.plt", str)
}

object MainOffLineRandom extends App {
 
  val instance = tspBuilder.build2mTsp("input/tsp/randomA100.tsp", "input/tsp/randomB100.tsp")

  val time = System.currentTimeMillis()
  
  val solutions = Build500Random.build500Random(Nil, instance, 1000)

  val solution2Tuple =  Filter2o.filter(solutions)
  
  println(System.currentTimeMillis() - time + " ms")
  
  //build data
  var str = ""
  solution2Tuple._1.foreach { x =>
      str += x._1 + "\t" + x._2 + "\n"
  }
  data.Util.write("data/domRandom500mTSP.dat", str)

  str = ""
  solution2Tuple._2.foreach { x =>
    str += x._1 + "\t" + x._2 + "\n"
  }
  data.Util.write("data/random500mTSP.dat", str)

  //build plt file
  str = "plot \'../data/random500mTSP.dat\' title \'non-dominate\' ,  \'../data/domRandom500mTSP.dat\' title \'dominate\'"
  data.Util.write("plot/random500mTSP.plt", str)
}