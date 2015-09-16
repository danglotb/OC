package main

import scala.collection.mutable.ListBuffer
import data._
import solver._

object MainHillClimbing extends App {

  val options = HillClimbingOptions.options(Map(), args.toList)

  var selectFunc: ((((Solution, (Int, Int)) => ListBuffer[Int]), Solution, (Int, Int)) => Solution) = _
  var initFunc : (String) => Unit = _
  var genFunc: (Solution, (Int, Int)) => ListBuffer[Int] = _
  var nbRuns: Int = 30
  var pathname: String = ""

  options.get("select") match {
    case Some(strSelect) => strSelect match {
      case "first" => selectFunc = HillClimbing.selectFirst
      case "best"  => selectFunc = HillClimbing.selectBest
      case _       => HillClimbingOptions.usage(options)
    }
    case _ => HillClimbingOptions.usage(options)
  }

  options.get("init") match {
    case Some(strInit) => strInit match {
      case "rnd" => initFunc = HillClimbing.initRandom
      case "edd" => initFunc = HillClimbing.initEdd
      case "mdd" => initFunc = HillClimbing.initMdd
      case _     => HillClimbingOptions.usage(options)
    }
    case _ => HillClimbingOptions.usage(options)
  }

  options.get("neighbor") match {
    case Some(strNeighbor) => strNeighbor match {
      case "insert"   => genFunc = HillClimbing.insertGen
      case "swap"     => genFunc = HillClimbing.swapGen
      case "exchange" => genFunc = HillClimbing.exchangeGen
      case _          => HillClimbingOptions.usage(options)
    }
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

//  initFunc(pathname)
//  HillClimbing.runAllInstances(genFunc, selectFunc)
  
  
//  for (i <- 0 until nbRuns) {
//    val time : Long = System.currentTimeMillis()
    initFunc(pathname)
    HillClimbing.run(HillClimbing.genFirstSolution, genFunc, selectFunc)
//    println(i+":"+ (System.currentTimeMillis()-time) + " ms")
//  }
}
