package main

import scala.collection.mutable.ListBuffer
import data._
import solver._

object MainHillClimbing extends App {

  def runAll(): Unit = {
    Logger.open("output/log")

    val values: ListBuffer[(ListBuffer[Int], ListBuffer[Long])] = new ListBuffer[(ListBuffer[Int], ListBuffer[Long])]()
    for (i <- 0 until nbRuns) {
      println(i + " run")
      initFunc(pathname)
      HillClimbing.runAllInstances(genFunc, selectFunc)
      values += ((HillClimbing.scores, HillClimbing.times))
    }

    val avgTime : Array[Long] = Array.ofDim[Long](125)
    val avgScore : Array[Int] = Array.ofDim[Int](125)
    
    var strTime : String = name+"Time"
    var strScore : String = name+"Score"
    
    for( i <- 0 until 125) {
      for (j <- 0 until values.length) {
        avgScore(i) += (values(j)._1(i))
        avgTime(i) += (values(j)._2(i))
      }
    }
    
    for( i <- 0 until 125) {
      avgScore(i) = (avgScore(i) / nbRuns)
      avgTime(i) = (avgTime(i) / nbRuns)
    }
    
    for (i <- 0 until 125) {
      strScore += "\t" + avgScore(i)
      strTime += "\t" + avgTime(i)
    }
    
    Logger.write(strScore+"\n")
    Logger.write(strTime+"\n")
    
    Logger.close()
  }

  val options = HillClimbingOptions.options(Map(), args.toList)

  var selectFunc: ((((Solution) => Solution), Solution) => (Solution)) = _
  var initFunc: (String) => Unit = _
  var genFunc: (Solution) => Solution = _
  var nbRuns: Int = 30
  var pathname: String = ""

  var name: String = ""

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
  
  runAll
  
}

