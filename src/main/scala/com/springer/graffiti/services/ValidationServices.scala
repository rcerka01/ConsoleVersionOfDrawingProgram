package com.springer.graffiti.services

import scala.util.Try
import com.springer.graffiti.model.Error


/**
  * Created by raitis on 08/06/2016.
  */

sealed trait  ValidationServices

object CommandValidationServices extends ValidationServices {


  val ALLOWED_COMMANDS = Seq("C", "L", "R", "B", "Q")


  def validToInt(s: String): Boolean = Try(s.toInt).isSuccess


  def canvasDefined: Boolean = CanvasServices.isCanvasDefinedService


  def isValidCommand(command: Array[String]): Error = {
    val isAllowedCommand = ALLOWED_COMMANDS.contains(command(0))
    if (!isAllowedCommand)
      return Error("Command is not listed as allowed command")
    null
  }


  /**
    * Validate 'Create Canvas' command
    *
    * Must have exactly 3 parameters
    * Can parameters be valid integers (also range)
    * Are parameters positive
    */
  def isValidCommandCreateCanvas(command: Array[String]): Error = {
    val isRightAmountOfParameters: Boolean = command.length == 3
    if (!isRightAmountOfParameters) return Error("Wrong 'Create Canvas' command. Incorrect amount of parameters (example: C 20 4 for canvas 20x4)")

    val x = command(1)
    val y = command(2)

    val isParametersValidToInt: Boolean = validToInt(x) && validToInt(y)
    if (!isParametersValidToInt) return Error("Wrong 'Create Canvas' command. Size parameter is not a valid integer (example: C 20 4 for canvas 20x4)")

    val isPositive: Boolean =
      x.toInt >= 0 &&
        y.toInt >= 0
    if (!isPositive) return Error("Wrong 'Create Canvas' command. Size parameters must be positive (example: C 20 4 for canvas 20x4)")

    null
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
  def isValidCommandAddLine(command: Array[String]): Error = {

    val isRightAmountOfParameters: Boolean = command.length == 5
    if (!isRightAmountOfParameters) return  Error("Wrong 'Add Line' command. Incorrect amount of parameters (example: L 1 2 6 2 for canvas 20x4)")

    val x1 = command(1)
    val y1 = command(2)
    val x2 = command(3)
    val y2 = command(4)

    val isCanvasDefined: Boolean = canvasDefined
    if (!isCanvasDefined) return Error("Wrong 'Add Line' command. Canvas is not defined (example: C 20 4 for canvas 20x4)")

    val canvasSize = CanvasServices.getCanvasSizeService

    val isParametersValidToInt: Boolean = validToInt(x1) && validToInt(x2) && validToInt(y1) && validToInt(y2)
    if (!isParametersValidToInt) return Error("Wrong 'Add Line' command. Size parameter is not a valid integer (example: L 1 2 6 2 for canvas 20x4)")

    val isLineHorizontalOrVertical: Boolean = x1.toInt == x2.toInt || y1.toInt == y2.toInt
    if (!isLineHorizontalOrVertical) return  Error("Wrong 'Add Line' command. Line is not horizontal or vertical (example: L 1 2 6 2 for canvas 20x4)")

    val isLineInCanvas: Boolean =
      canvasSize._1 >= x1.toInt &&
        canvasSize._1 >= x2.toInt &&
        canvasSize._2 >= y1.toInt &&
        canvasSize._2 >= y2.toInt &&
        x1.toInt >= 1 &&
        x2.toInt >= 1 &&
        y1.toInt >= 1 &&
        y2.toInt >= 1
    if (!isLineInCanvas) return Error("Wrong 'Add Line' command. Line is not in canvas (example: L 1 2 6 2 for canvas 20x4)")

    null

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
  def isValidCommandAddRectangle(command: Array[String]): Error = {

    val isRightAmountOfParameters: Boolean = command.length == 5
    if (!isRightAmountOfParameters) return  Error("Wrong 'Add Rectangle' command. Incorrect amount of parameters (example: R 16 1 20 3 for canvas 20x4)")

    val x1 = command(1)
    val y1 = command(2)
    val x2 = command(3)
    val y2 = command(4)

    val isCanvasDefined: Boolean = canvasDefined
    if (!isCanvasDefined) return  Error("Wrong 'Add Rectangle' command. Canvas is not defined (example: C 20 4 for canvas 20x4)")

    val canvasSize = CanvasServices.getCanvasSizeService

    val isParametersValidToInt: Boolean = validToInt(x1) && validToInt(x2) && validToInt(y1) && validToInt(y2)
    if (!isParametersValidToInt) return Error("Wrong 'Add Rectangle' command. Coordinate parameter is not a valid integer (example: R 16 1 20 3 for canvas 20x4)")

    val isOnLeft: Boolean = x2.toInt >= x1.toInt
    if (!isOnLeft) return  Error("Wrong 'Add Rectaangle' command. The bottom-right corner is more on left than top-left corner (example: R 16 1 20 3 for canvas 20x4)")

    val isOnBottom = y2.toInt >= y1.toInt
    if (!isOnBottom) return  Error("Wrong 'Add Rectaangle' command. The bottom-right corner is more on top than top-left corner (example: R 16 1 20 3 for canvas 20x4)")

    val isRectangleInCanvas: Boolean =
      canvasSize._1 >= x1.toInt &&
        canvasSize._1 >= x2.toInt &&
        canvasSize._2 >= y1.toInt &&
        canvasSize._2 >= y2.toInt &&
        x1.toInt >= 1 &&
        x2.toInt >= 1 &&
        y1.toInt >= 1 &&
        y2.toInt >= 1
    if (!isRectangleInCanvas) return Error("Wrong 'Add Rectangle' command. Rectangle is not in canvas (example: R 16 1 20 3 for canvas 20x4)")

    null

  }


  /**
    * Validate 'Bucket' command
    *
    * Must have exactly 4 parameters
    * Is canvas defined
    * Can 2 parameters be valid integers
    * Is fill in canvas
    */
  def isValidCommandAddFill(command: Array[String]): Error = {

    val isRightAmountOfParameters: Boolean = command.length == 4
    if (!isRightAmountOfParameters) return Error("Wrong 'Add Bucket' command. Incorrect amount of parameters (example: B 10 3 o for canvas 20x4)")

    val x = command(1)
    val y = command(2)
    val z = command(3)

    val isCanvasDefined: Boolean = canvasDefined
    if (!isCanvasDefined) return  Error("Wrong 'Add Bucket' command. Canvas is not defined (example: C 20 4 for canvas 20x4)")

    val canvasSize = CanvasServices.getCanvasSizeService

    val isParametersValidToInt: Boolean = validToInt(x) && validToInt(y)
    if (!isParametersValidToInt) return Error("Wrong 'Add Bucket' command. Coordinate parameter is not a valid integer (example: B 10 3 o for canvas 20x4)")

    val isFillInCanvas: Boolean =
      canvasSize._1 >= x.toInt &&
        canvasSize._2 >= y.toInt &&
        x.toInt >= 1 &&
        y.toInt >= 1
    if (!isFillInCanvas) return Error("Wrong 'Add Bucket' command. Coordinates is not in canvas (example: B 10 3 o for canvas 20x4)")

    val isMoreThanOneSymbol: Boolean = z.length == 1
    if (!isMoreThanOneSymbol) return Error("Wrong 'Add Bucket' command. There is more than one filling symbol (example: B 10 3 o for canvas 20x4)")

    null

  }


}
