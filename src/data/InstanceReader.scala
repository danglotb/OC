package data

import java.util.Scanner

/**
 * @author danglot
 */
class InstanceReader(nbJobs: Int, pathname: String, nbInstances: Int, format: Boolean = false) {

  var nbRead: Int = 0

  private var currentInstance: Instance = new Instance(nbJobs)

  private var file: String = ""

  private var sc: Scanner = _
  
  read()

  private def read(): Unit = {
    val source = scala.io.Source.fromFile(pathname)

    val iterator: Iterator[String] = source getLines

    while (iterator hasNext) {
      file += (iterator next) 
      if (format) file += "\n"
    }

    if (!format) {
      file = file.replaceAll(" +", " ")
      sc = new Scanner(file)
      sc.useDelimiter(" ")
    } else
      sc = new Scanner(file)

  }

  private def getInstanceFormat(): Instance = {

    var scTmp: Scanner = null

    for (i <- 0 until nbJobs) {
      scTmp = new Scanner(sc.nextLine())
      scTmp.useDelimiter(",")
      currentInstance.processingTimes(i) = scTmp.nextInt()
      currentInstance.weights(i) = scTmp.nextInt()
      currentInstance.dueDates(i) = scTmp.nextInt()
      scTmp.close()
    }
    
    currentInstance
  }

  private def getInstanceNonFormat(): Instance = {

    for (i <- 0 until nbJobs)
      currentInstance.processingTimes(i) = sc.nextInt()

    for (i <- 0 until nbJobs)
      currentInstance.weights(i) = sc.nextInt()

    for (i <- 0 until nbJobs)
      currentInstance.dueDates(i) = sc.nextInt()

    currentInstance
  }
  
  def hasNext() : Boolean = nbRead < nbInstances

  def getInstance(): Instance = {
    nbRead += 1 
    if (format)
      getInstanceFormat()
    else
      getInstanceNonFormat()
  }

}

object Main extends App {

  val reader = new InstanceReader(100, "input/wt100.txt", 125)
  
  println(reader.getInstance())

}