package com.springer.graffiti.services

import com.springer.graffiti.model.{Fill, Rectangle, Line, Canvas}

/**
  * Created by raitis on 09/06/2016.
  */
object CanvasServices {


  // output - the only changeable object
  private var canvas: Canvas = _


  // for validation
  def isCanvasDefinedService: Boolean = canvas != null


  // for validation
  def getCanvasSizeService: (Int, Int) = (canvas.x, canvas.y)


  def createCanvasService(command: Array[String]): Canvas = {

    val x1 = command(1).toInt
    val y1 = command(2).toInt

    canvas = new Canvas(x1,y1,Seq(), Seq(), Seq())
    canvas
  }


  def addLineService(command: Array[String]): Canvas = {

    val x1 = command(1).toInt
    val y1 = command(2).toInt
    val x2 = command(3).toInt
    val y2 = command(4).toInt

    val line = Line(x1, y1, x2, y2)

    canvas = new Canvas(canvas.x, canvas.y, canvas.lines :+ line, canvas.rectangles, canvas.fills)
    canvas
  }


  def addRectangleService(command: Array[String]) = {

    val x1 = command(1).toInt
    val y1 = command(2).toInt
    val x2 = command(3).toInt
    val y2 = command(4).toInt

    val rectangle = Rectangle(x1, y1, x2, y2)

    canvas = new Canvas(canvas.x, canvas.y, canvas.lines, canvas.rectangles :+ rectangle, canvas.fills)
    canvas
  }


  def addFillService(command: Array[String]) = {

    val x = command(1).toInt
    val y = command(2).toInt
    val c = command(3).head

    val fill = Fill(x, y, c)

    canvas = new Canvas(canvas.x, canvas.y, canvas.lines, canvas.rectangles, canvas.fills :+ fill)
    canvas
  }


  // only for tests
  def createCanvasTest(x: Int, y: Int): Unit = canvas = Canvas(x, y, Seq(), Seq(), Seq())


  // only for tests
  def destroyCanvasTest: Unit = canvas = null

}

