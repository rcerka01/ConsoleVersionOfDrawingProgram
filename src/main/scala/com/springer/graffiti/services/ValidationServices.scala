package com.springer.graffiti.services

import scala.util.Try

/**
  * Created by raitis on 08/06/2016.
  */

sealed trait  ValidationServices

object CommandValidationServices extends ValidationServices {


  val ALLOWED_COMMANDS = Seq("C", "L", "R", "B")


  def validToInt(s: String): Boolean = Try(s.toInt).isSuccess


  def canvasDefined: Boolean = CanvasServices.isCanvasDefinedService


  def isValidCommand(command: Array[String]): Boolean = ALLOWED_COMMANDS.contains(command(0))


  /**
    * Validate 'Create Canvas' command
    *
    * Must have exactly 3 parameters
    * Can parameters be valid integers (also range)
    * Are parameters positive
    */
  def isValidCommandCreateCanvas(command: Array[String]): Boolean = {
    val isRightAmountOfParameters: Boolean = command.length == 3
    if (!isRightAmountOfParameters) return false

    val x = command(1)
    val y = command(2)

    val isParametersValidToInt: Boolean = validToInt(x) && validToInt(y)
    if (!isParametersValidToInt) return false

    val isPositive: Boolean =
        x.toInt >= 0 &&
        y.toInt >= 0
    if (!isPositive) return false

    true

  }


  /**
    * Validate 'New Line' command
    *
    * Must have exactly 5 parameters
    * Is canvas defined
    * Can parameters be valid integers
    * Is line horizontal or vertical
    * Is line in canvas
    */
  def isValidCommandAddLine(command: Array[String]): Boolean = {

    val isRightAmountOfParameters: Boolean = command.length == 5
    if (!isRightAmountOfParameters) return false

    val x1 = command(1)
    val y1 = command(2)
    val x2 = command(3)
    val y2 = command(4)

    val isCanvasDefined: Boolean = canvasDefined
    if (!isCanvasDefined) return false

    val canvasSize = CanvasServices.getCanvasSizeService

    val isParametersValidToInt: Boolean = validToInt(x1) && validToInt(x2) && validToInt(y1) && validToInt(y2)
    if (!isParametersValidToInt) return false

    val isLineHorizontalOrVertical: Boolean = x1.toInt == x2.toInt || y1.toInt == y2.toInt
    if (!isLineHorizontalOrVertical) return false

    val isLineInCanvas: Boolean =
        canvasSize._1 >= x1.toInt &&
        canvasSize._1 >= x2.toInt &&
        canvasSize._2 >= y1.toInt &&
        canvasSize._2 >= y2.toInt &&
        x1.toInt >= 1 &&
        x2.toInt >= 1 &&
        y1.toInt >= 1 &&
        y2.toInt >= 1
    if (!isLineInCanvas) return false

    true

  }


  /**
    * Validate 'New Rectangle' command
    *
    * Must have exactly 5 parameters
    * Is canvas defined
    * Can parameters be valid integers
    * Is the right corner further on right
    * Is the bottom corner lower on bottom
    * Is rectangle in canvas
    */
  def isValidCommandAddRectangle(command: Array[String]): Boolean = {

    val isRightAmountOfParameters: Boolean = command.length == 5
    if (!isRightAmountOfParameters) return false

    val x1 = command(1)
    val y1 = command(2)
    val x2 = command(3)
    val y2 = command(4)

    val isCanvasDefined: Boolean = canvasDefined
    if (!isCanvasDefined) return false

    val canvasSize = CanvasServices.getCanvasSizeService

    val isParametersValidToInt: Boolean = validToInt(x1) && validToInt(x2) && validToInt(y1) && validToInt(y2)
    if (!isParametersValidToInt) return false

    val isOnLeft: Boolean = x2.toInt >= x1.toInt
    if (!isOnLeft) return false

    val isOnBottom = y2.toInt >= y1.toInt
    if (!isOnBottom) return false

    val isRectangleInCanvas: Boolean =
      canvasSize._1 >= x1.toInt &&
        canvasSize._1 >= x2.toInt &&
        canvasSize._2 >= y1.toInt &&
        canvasSize._2 >= y2.toInt &&
        x1.toInt >= 1 &&
        x2.toInt >= 1 &&
        y1.toInt >= 1 &&
        y2.toInt >= 1
    if (!isRectangleInCanvas) return false

   true

  }


  /**
    * Validate 'Bucket' command
    *
    * Must have exactly 4 parameters
    * Is canvas defined
    * Can 2 parameters be valid integers
    * Is fill in canvas
    */
  def isValidCommandAddFill(command: Array[String]): Boolean = {

    val isRightAmountOfParameters: Boolean = command.length == 4
    if (!isRightAmountOfParameters) return false

    val x = command(1)
    val y = command(2)
    val z = command(3)

    val isCanvasDefined: Boolean = canvasDefined
    if (!isCanvasDefined) return false

    val canvasSize = CanvasServices.getCanvasSizeService

    val isParametersValidToInt: Boolean = validToInt(x) && validToInt(y)
    if (!isParametersValidToInt) return false

    val isFillInCanvas: Boolean =
      canvasSize._1 >= x.toInt &&
        canvasSize._2 >= y.toInt &&
        x.toInt >= 1 &&
        y.toInt >= 1
    if (!isFillInCanvas) return false

    val isMoreThanOneSymbol: Boolean = z.length == 1
    if (!isMoreThanOneSymbol) return false

    true

  }


}
