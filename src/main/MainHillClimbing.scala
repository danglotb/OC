package main

import scala.collection.mutable.ListBuffer
import data._
import solver._

object MainHillClimbing extends App {

  var selectFunc: ((((Solution) => Solution), Solution) => (Solution)) = _
  var initFunc: (String, Int, Int, Boolean) => Unit = _
  var genFunc: (Solution) => Solution = _
  var nbRuns: Int = 30
  var pathname: String = ""

  var name: String = ""

  parseOptions
  runAll

  def runAll(): Unit = {
    Logger.open("output/log")

    val values: ListBuffer[(ListBuffer[Int], ListBuffer[Long])] = new ListBuffer[(ListBuffer[Int], ListBuffer[Long])]()
    for (i <- 0 until nbRuns) {
      println(i + " run")
      initFunc(pathname, 100, 125, false)
      val time : Long = System.currentTimeMillis()
      HillClimbing.runAllInstances(genFunc, selectFunc)
      values += ((HillClimbing.scores, HillClimbing.times))
    }

    val avgTime: Array[Long] = Array.ofDim[Long](125)
    val avgScore: Array[Int] = Array.ofDim[Int](125)

    var strTime: String = name + "Time"
    var strScore: String = name + "Score"

    for (i <- 0 until 125) {
      for (j <- 0 until values.length) {
        avgScore(i) += (values(j)._1(i))
        avgTime(i) += (values(j)._2(i))
      }
    }

    for (i <- 0 until 125) {
      avgScore(i) = (avgScore(i) / nbRuns)
      avgTime(i) = (avgTime(i) / nbRuns)
    }

    for (i <- 0 until 125) {
      strScore += "\t" + avgScore(i)
      strTime += "\t" + avgTime(i)
    }

    Logger.write(strScore + "\n")
    Logger.write(strTime + "\n")

    Logger.close

  }

  def parseOptions(): Unit = {
    val options = HillClimbingOptions.options(Map(), args.toList)

    options.get("select") match {
      case Some(strSelect) =>
        strSelect match {
          case "first" => selectFunc = HillClimbing.selectFirst
          case "best"  => selectFunc = HillClimbing.selectBest
          case _       => HillClimbingOptions.usage(options)
        }
        name += strSelect
      case _ => HillClimbingOptions.usage(options)
    }

    options.get("init") match {
      case Some(strInit) =>
        strInit match {
          case "rnd" => initFunc = HillClimbing.initRandom
          case "edd" => initFunc = HillClimbing.initEdd
          case "mdd" => initFunc = HillClimbing.initMdd
          case _     => HillClimbingOptions.usage(options)
        }
        name += strInit
      case _ => HillClimbingOptions.usage(options)
    }

    options.get("neighbor") match {
      case Some(strNeighbor) =>
        strNeighbor match {
          case "insert"   => genFunc = HillClimbing.insertGen
          case "swap"     => genFunc = HillClimbing.swapGen
          case "exchange" => genFunc = HillClimbing.exchangeGen
          case _          => HillClimbingOptions.usage(options)
        }
        name += strNeighbor
      case _ => HillClimbingOptions.usage(options)
    }

    options.get("k") match {
      case Some(k) => nbRuns = k.toString.toInt
      case _       =>
    }

    options.get("file") match {
      case Some(file) => pathname = file.toString
      case _          => HillClimbingOptions.usage(options)
    }

  }
}

object MainVnd extends App {

  Logger.open("output/logvnd")
  
  HillClimbing.initRandom("input/wt100.txt", 100, 125)
  var time : Long = System.currentTimeMillis
  Logger.write("exchange_insert_swap_rnd\t"+ 
      (HillClimbing.runVnd(Array(HillClimbing.exchangeGen, HillClimbing.insertGen, HillClimbing.swapGen), 
      HillClimbing.genFirstSolution(), HillClimbing.selectFirst, 0)).score +"\t"+ (System.currentTimeMillis - time) + "ms")
 
  HillClimbing.initRandom("input/wt100.txt", 100, 125)
  time = System.currentTimeMillis
   Logger.write("exchange_swap_insert_rnd\t"+ 
       (HillClimbing.runVnd(Array(HillClimbing.exchangeGen, HillClimbing.swapGen, HillClimbing.insertGen ), 
      HillClimbing.genFirstSolution(), HillClimbing.selectFirst, 0)).score +"\t"+ (System.currentTimeMillis - time) + "ms")
      
  HillClimbing.initMdd("input/wt100.txt", 100, 125)
  time = System.currentTimeMillis
  Logger.write("exchange_insert_swap_mdd\t"+ 
      (HillClimbing.runVnd(Array(HillClimbing.exchangeGen, HillClimbing.insertGen, HillClimbing.swapGen), 
      HillClimbing.genFirstSolution(), HillClimbing.selectFirst, 0)).score +"\t"+ (System.currentTimeMillis - time) + "ms")
 
  HillClimbing.initMdd("input/wt100.txt", 100, 125)
  time = System.currentTimeMillis
   Logger.write("exchange_swap_insert_mdd\t"+ 
       (HillClimbing.runVnd(Array(HillClimbing.exchangeGen, HillClimbing.swapGen, HillClimbing.insertGen ), 
      HillClimbing.genFirstSolution(), HillClimbing.selectFirst, 0)).score +"\t"+ (System.currentTimeMillis - time) + "ms")

}

object MainPipedVnd extends App {

 Logger.open("output/logpipedvnd")
  
  HillClimbing.initRandom("input/wt100.txt", 100, 125)
  var time : Long = System.currentTimeMillis
  Logger.write("exchange_insert_swap_rnd\t"+ 
      (HillClimbing.runPipedVnd(Array(HillClimbing.exchangeGen, HillClimbing.insertGen, HillClimbing.swapGen), 
      HillClimbing.genFirstSolution(), HillClimbing.selectFirst, 0, false, 0)).score +"\t"+ (System.currentTimeMillis - time) + "ms")
 
  HillClimbing.initRandom("input/wt100.txt", 100, 125)
  time = System.currentTimeMillis
   Logger.write("exchange_swap_insert_rnd\t"+ 
       (HillClimbing.runPipedVnd(Array(HillClimbing.exchangeGen, HillClimbing.swapGen, HillClimbing.insertGen ), 
      HillClimbing.genFirstSolution(), HillClimbing.selectFirst, 0, false, 0)).score +"\t"+ (System.currentTimeMillis - time) + "ms")
      
  HillClimbing.initMdd("input/wt100.txt", 100, 125)
  time = System.currentTimeMillis
  Logger.write("exchange_insert_swap_mdd\t"+ 
      (HillClimbing.runPipedVnd(Array(HillClimbing.exchangeGen, HillClimbing.insertGen, HillClimbing.swapGen), 
      HillClimbing.genFirstSolution(), HillClimbing.selectFirst, 0, false, 0)).score +"\t"+ (System.currentTimeMillis - time) + "ms")
 
  HillClimbing.initMdd("input/wt100.txt", 100, 125)
  time = System.currentTimeMillis
   Logger.write("exchange_swap_insert_mdd\t"+ 
       (HillClimbing.runPipedVnd(Array(HillClimbing.exchangeGen, HillClimbing.swapGen, HillClimbing.insertGen ), 
      HillClimbing.genFirstSolution(), HillClimbing.selectFirst, 0, false, 0)).score +"\t"+ (System.currentTimeMillis - time) + "ms")
  
  //Best Config find by score/time
  HillClimbing.initMdd("input/wt100.txt", 100, 125)  
    time = System.currentTimeMillis()
  Logger.write("insert_swap_best_mdd\t"+
    (HillClimbing.runPipedVnd(Array(HillClimbing.insertGen, HillClimbing.swapGen), 
      HillClimbing.genFirstSolution(), HillClimbing.selectBest, 0, false, 0)).score +"\t"+ (System.currentTimeMillis() - time) + "ms")
      

}

