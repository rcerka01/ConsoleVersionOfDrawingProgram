package com.springer.graffiti.services

import com.springer.graffiti.model.Canvas

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
    canvas = new Canvas(x1, y1, List())
    canvas
  }


  def addElementService(command: Array[String]): Canvas = {
    canvas = new Canvas(canvas.x, canvas.y, canvas.commands :+ command)
    canvas
  }

  // only for tests
  def createCanvasTest(x: Int, y: Int): Unit = canvas = Canvas(x, y, List())


  // only for tests
  def destroyCanvasTest: Unit = canvas = null

}

