package com.springer.graffiti.test

import com.springer.graffiti.services.{CanvasServices, CommandValidationServices}
import com.springer.graffiti.model.Error

/**
  * Created by raitis on 11/06/2016.
  */
class CommandValidationTest extends UnitSpec {

  // command
  val validCommand: Array[String] = Array("C", "0", "0")
  val unknownCommand: Array[String] = Array("Z")

  // create canvas
  val validCreateCommand: Array[String] = Array("C", "5", "3")
  val createCommandWithToManyParameters: Array[String] = Array("C", "20", "4", "5")
  val createCommandWithToFewParameters: Array[String] = Array("C", "20")
  val createCommandWithMalformedParameters: Array[String] = Array("C", "abc", "5")
  val createCommandWithMinimalCanvasSize: Array[String] = Array("C", "0", "0") // why not
  val createCommandWithNegativeCanvasSize: Array[String] = Array("C", "1", "-1")

  // add line
  val validAddLineCommand: Array[String] = Array("L", "1", "1", "4", "1")
  val addLineCommandWithToManyParameters: Array[String] = Array("L", "1", "1", "4", "1", "1")
  val addLineCommandWithToFewParameters: Array[String] = Array("L", "1", "1", "4")
  val addLineCommandWithMalformedParameters: Array[String] = Array("L", "abc", "5", "1", "1")
  val addLineCommandForNotHorizontalOrVerticalLine: Array[String] = Array("L", "1", "2", "3", "4")
  val addLineCommandWithX1outLeft: Array[String] = Array("L", "0", "2", "3", "2")
  val addLineCommandWithX1outRight: Array[String] = Array("L", "5", "2", "3", "2")
  val addLineCommandWithX2outLeft: Array[String] = Array("L", "3", "2", "0", "2")
  val addLineCommandWithX2outRight: Array[String] = Array("L", "3", "2", "5", "2")
  val addLineCommandWithY1outTop: Array[String] = Array("L", "3", "0", "3", "2")
  val addLineCommandWithY1outBottom: Array[String] = Array("L", "3", "5", "3", "2")
  val addLineCommandWithY2outTop: Array[String] = Array("L", "3", "2", "3", "0")
  val addLineCommandWithY2outBottom: Array[String] = Array("L", "3", "2", "3", "5")
  val addLineCommandToAddDot: Array[String] = Array("L", "1", "1", "1", "1")

  // add rectangle
  val validAddRectangleCommand: Array[String] = Array("R", "1", "1", "4", "3")
  val addRectangleCommandWithToManyParameters: Array[String] = Array("R", "1", "1", "4", "3", "1")
  val addRectangleCommandWithToFewParameters: Array[String] = Array("R", "1", "1", "4")
  val addRectangleCommandWithMalformedParameters: Array[String] = Array("R", "abc", "1", "4", "3")
  val addRectangleCommandWithRightCornerBeforeLeft: Array[String] = Array("R", "2", "2", "1", "4")
  val addRectangleCommandWithBottomCornerAboveTop: Array[String] = Array("R", "2", "2", "4", "1")
  val addRectangleCommandWithX1outLeft: Array[String] = Array("R", "0", "2", "3", "2")
  val addRectangleCommandWithX1outRight: Array[String] = Array("R", "5", "2", "3", "2")
  val addRectangleCommandWithX2outLeft: Array[String] = Array("R", "1", "2", "0", "2")
  val addRectangleCommandWithX2outRight: Array[String] = Array("R", "3", "2", "5", "2")
  val addRectangleCommandWithY1outTop: Array[String] = Array("R", "3", "0", "3", "2")
  val addRectangleCommandWithY1outBottom: Array[String] = Array("R", "3", "5", "3", "2")
  val addRectangleCommandWithY2outTop: Array[String] = Array("R", "3", "2", "3", "0")
  val addRectangleCommandWithY2outBottom: Array[String] = Array("R", "3", "2", "3", "5")
  val addRectangleCommandToDot: Array[String] = Array("R", "2", "2", "2", "2")

  // add fill
  val validAddFillCommand: Array[String] = Array("B", "1", "1", "c")
  val addFillCommandWithToManyParameters: Array[String] = Array("B", "1", "1", "c", "1")
  val addFillCommandWithToFewParameters: Array[String] = Array("B", "1", "1")
  val addFillCommandWithMalformedParameters: Array[String] = Array("B", "abc", "1", "c")
  val addFillCommandWithXoutLeft: Array[String] = Array("B", "0", "2", "c")
  val addFillCommandWithXoutRight: Array[String] = Array("B", "5", "2", "c")
  val addFillCommandWithYoutTop: Array[String] = Array("B", "3", "0", "c")
  val addFillCommandWithYoutBottom: Array[String] = Array("B", "3", "5", "c")
  val addFillCommandWithMoreThanOneSymbol: Array[String] = Array("B", "3", "3", "abc")


  /**
    * Tests for 'Is Valid' command
    *
    */

  "isValidCommand service" should "return 'null' if the command array first element is 'C'" in {
    val expectedResult: Error = null
    val result: Error = CommandValidationServices.isValidCommand(validCommand)
    assert(result === expectedResult)
  }

  it should "return 'Error' if the command array first element is not defined as 'ALLOWED_COMMANDS'" in {
    val expectedResult: String = "Command is not listed as allowed command"
    val result: Error = CommandValidationServices.isValidCommand(unknownCommand)
    assert(result.message === expectedResult)
  }


  /**
    * Tests for 'Create Canvas' command
    *
    */

  "isValidCommandCreateCanvas service" should "return 'null' if the command array first element is 'C' and it has 2 positive size parameters" in {
    val expectedResult: Error = null
    val result: Error = CommandValidationServices.isValidCommandCreateCanvas(validCreateCommand)
    assert(result === expectedResult)
  }

  it should "return 'Error' if the 'Create Canvas' command has to many parameters" in {
    val expectedResult: String = "Wrong 'Create Canvas' command. Incorrect amount of parameters (example: C 20 4 for canvas 20x4)"
    val result: Error = CommandValidationServices.isValidCommandCreateCanvas(createCommandWithToManyParameters)
    assert(result.message === expectedResult)
  }

  it should "return 'Error' if the 'Create Canvas' command has to few parameters" in {
    val expectedResult: String = "Wrong 'Create Canvas' command. Incorrect amount of parameters (example: C 20 4 for canvas 20x4)"
    val result: Error = CommandValidationServices.isValidCommandCreateCanvas(createCommandWithToFewParameters)
    assert(result.message === expectedResult)
  }

  it should "return 'Error' if the 'Create Canvas' command has malformed parameters" in {
    val expectedResult: String = "Wrong 'Create Canvas' command. Size parameter is not a valid integer (example: C 20 4 for canvas 20x4)"
    val result: Error = CommandValidationServices.isValidCommandCreateCanvas(createCommandWithMalformedParameters)
    assert(result.message === expectedResult)
  }

  it should "return 'null' if the 'Create Canvas' command has minimal size 0x0" in {
    val expectedResult: Error = null
    val result: Error = CommandValidationServices.isValidCommandCreateCanvas(createCommandWithMinimalCanvasSize)
    assert(result === expectedResult)
  }

  it should "return 'Error' if the 'Create Canvas' command has negative canvas size" in {
    val expectedResult: String = "Wrong 'Create Canvas' command. Size parameters must be positive (example: C 20 4 for canvas 20x4)"
    val result: Error = CommandValidationServices.isValidCommandCreateCanvas(createCommandWithNegativeCanvasSize)
    assert(result.message === expectedResult)
  }


  /**
    * Tests for 'Add Line' command
    *
    */

  "isValidCommandAddLine service" should "return 'Error' if canvas is not defined" in {
    val expectedResult: String = "Wrong 'Add Line' command. Canvas is not defined (example: C 20 4 for canvas 20x4)"
    val result: Error = CommandValidationServices.isValidCommandAddLine(validAddLineCommand)
    assert(result.message === expectedResult)
  }

  it should "return 'null' if the command array first element is 'L' and it has 4 positive size parameters, where 1st and 3rd or 2nd and 4th must be equal, and canvas is defined" in {
    CanvasServices.createCanvasTest(4, 4)
    val expectedResult: Error = null
    val result: Error = CommandValidationServices.isValidCommandAddLine(validAddLineCommand)
    assert(result === expectedResult)
    CanvasServices.destroyCanvasTest
  }

  it should "return 'Error' if the 'Add Line' command has to many parameters" in {
    CanvasServices.createCanvasTest(4, 4)
    val expectedResult: String = "Wrong 'Add Line' command. Incorrect amount of parameters (example: L 1 2 6 2 for canvas 20x4)"
    val result: Error = CommandValidationServices.isValidCommandAddLine(addLineCommandWithToManyParameters)
    assert(result.message === expectedResult)
    CanvasServices.destroyCanvasTest
  }

  it should "return 'Error' if the 'Add Line' command has to few parameters" in {
    CanvasServices.createCanvasTest(4, 4)
    val expectedResult: String = "Wrong 'Add Line' command. Incorrect amount of parameters (example: L 1 2 6 2 for canvas 20x4)"
    val result: Error = CommandValidationServices.isValidCommandAddLine(addLineCommandWithToFewParameters)
    assert(result.message === expectedResult)
    CanvasServices.destroyCanvasTest
  }

  it should "return 'Error' if the 'Add Line' command has malformed parameters" in {
    CanvasServices.createCanvasTest(4, 4)
    val expectedResult: String = "Wrong 'Add Line' command. Size parameter is not a valid integer (example: L 1 2 6 2 for canvas 20x4)"
    val result: Error = CommandValidationServices.isValidCommandAddLine(addLineCommandWithMalformedParameters)
    assert(result.message === expectedResult)
    CanvasServices.destroyCanvasTest
  }

  it should "return 'Error' if the 'Add Line' command do not have horizontal or vertical coordinates" in {
    CanvasServices.createCanvasTest(4, 4)
    val expectedResult: String = "Wrong 'Add Line' command. Line is not horizontal or vertical (example: L 1 2 6 2 for canvas 20x4)"
    val result: Error = CommandValidationServices.isValidCommandAddLine(addLineCommandForNotHorizontalOrVerticalLine)
    assert(result.message === expectedResult)
    CanvasServices.destroyCanvasTest
  }

  it should "return 'Error' if the 'Add Line' command coordinates are out of canvas" in {
    CanvasServices.createCanvasTest(4, 4)
    val expectedResult: String = "Wrong 'Add Line' command. Line is not in canvas (example: L 1 2 6 2 for canvas 20x4)"
    val result1: Error = CommandValidationServices.isValidCommandAddLine(addLineCommandWithX1outLeft)
    val result2: Error = CommandValidationServices.isValidCommandAddLine(addLineCommandWithX2outLeft)
    val result3: Error = CommandValidationServices.isValidCommandAddLine(addLineCommandWithX1outRight)
    val result4: Error = CommandValidationServices.isValidCommandAddLine(addLineCommandWithX2outRight)
    val result5: Error = CommandValidationServices.isValidCommandAddLine(addLineCommandWithY1outTop)
    val result6: Error = CommandValidationServices.isValidCommandAddLine(addLineCommandWithY1outBottom)
    val result7: Error = CommandValidationServices.isValidCommandAddLine(addLineCommandWithY2outTop)
    val result8: Error = CommandValidationServices.isValidCommandAddLine(addLineCommandWithY2outBottom)
    assert(result1.message === expectedResult)
    assert(result2.message === expectedResult)
    assert(result3.message === expectedResult)
    assert(result4.message === expectedResult)
    assert(result5.message === expectedResult)
    assert(result6.message === expectedResult)
    assert(result7.message === expectedResult)
    assert(result8.message === expectedResult)
    CanvasServices.destroyCanvasTest
  }

  it should "return 'null' if the 'Add Line' is a dot" in {
    CanvasServices.createCanvasTest(4, 4)
    val expectedResult: Error = null
    val result: Error = CommandValidationServices.isValidCommandAddLine(addLineCommandToAddDot)
    assert(result === expectedResult)
    CanvasServices.destroyCanvasTest
  }


  /**
    * Tests for 'Add Rectangle' command
    *
    */

  "isValidCommandAddRectangle service" should "return 'false' if canvas is not defined" in {
    val expectedResult: String = "Wrong 'Add Rectangle' command. Canvas is not defined (example: C 20 4 for canvas 20x4)"
    val result: Error = CommandValidationServices.isValidCommandAddRectangle(validAddRectangleCommand)
    assert(result.message === expectedResult)
  }

  it should "return 'null' if the command array first element is 'R' and it has 4 positive size parameters, where x1 < x2 and y1 < y2, and canvas is defined" in {
    CanvasServices.createCanvasTest(4, 4)
    val expectedResult: Error = null
    val result: Error = CommandValidationServices.isValidCommandAddRectangle(validAddRectangleCommand)
    assert(result === expectedResult)
    CanvasServices.destroyCanvasTest
  }

  it should "return 'Error' if the 'Add Rectangle' command has to many parameters" in {
    CanvasServices.createCanvasTest(4, 4)
    val expectedResult: String = "Wrong 'Add Rectangle' command. Incorrect amount of parameters (example: R 16 1 20 3 for canvas 20x4)"
    val result: Error = CommandValidationServices.isValidCommandAddRectangle(addRectangleCommandWithToManyParameters)
    assert(result.message === expectedResult)
    CanvasServices.destroyCanvasTest
  }

  it should "return 'Error' if the 'Add Rectangle' command has to few parameters" in {
    CanvasServices.createCanvasTest(4, 4)
    val expectedResult: String = "Wrong 'Add Rectangle' command. Incorrect amount of parameters (example: R 16 1 20 3 for canvas 20x4)"
    val result: Error = CommandValidationServices.isValidCommandAddRectangle(addRectangleCommandWithToFewParameters)
    assert(result.message === expectedResult)
    CanvasServices.destroyCanvasTest
  }

  it should "return 'Error' if the 'Add Rectangle' command has malformed parameters" in {
    CanvasServices.createCanvasTest(4, 4)
    val expectedResult: String = "Wrong 'Add Rectangle' command. Coordinate parameter is not a valid integer (example: R 16 1 20 3 for canvas 20x4)"
    val result: Error = CommandValidationServices.isValidCommandAddRectangle(addRectangleCommandWithMalformedParameters)
    assert(result.message === expectedResult)
    CanvasServices.destroyCanvasTest
  }

  it should "return 'Error' if the 'Add Rectangle' command has right corner before left" in {
    CanvasServices.createCanvasTest(4, 4)
    val expectedResult: String = "Wrong 'Add Rectaangle' command. The bottom-right corner is more on left than top-left corner (example: R 16 1 20 3 for canvas 20x4)"
    val result: Error = CommandValidationServices.isValidCommandAddRectangle(addRectangleCommandWithRightCornerBeforeLeft)
    assert(result.message === expectedResult)
    CanvasServices.destroyCanvasTest
  }

  it should "return 'Error' if the 'Add Rectangle' command has bottom corner above top" in {
    CanvasServices.createCanvasTest(4, 4)
    val expectedResult: String = "Wrong 'Add Rectaangle' command. The bottom-right corner is more on top than top-left corner (example: R 16 1 20 3 for canvas 20x4)"
    val result: Error = CommandValidationServices.isValidCommandAddRectangle(addRectangleCommandWithBottomCornerAboveTop)
    assert(result.message === expectedResult)
    CanvasServices.destroyCanvasTest
  }

  it should "return 'Error' if the 'Add Rectangle' command coordinates are out of canvas" in {
    CanvasServices.createCanvasTest(4, 4)
    val expectedResult: String = "Wrong 'Add Rectangle' command. Rectangle is not in canvas (example: R 16 1 20 3 for canvas 20x4)"
    val result1: Error = CommandValidationServices.isValidCommandAddRectangle(addRectangleCommandWithX1outLeft)
    val result2: Error = CommandValidationServices.isValidCommandAddRectangle(addRectangleCommandWithX2outLeft)
    val result3: Error = CommandValidationServices.isValidCommandAddRectangle(addRectangleCommandWithX1outRight)
    val result4: Error = CommandValidationServices.isValidCommandAddRectangle(addRectangleCommandWithX2outRight)
    val result5: Error = CommandValidationServices.isValidCommandAddRectangle(addRectangleCommandWithY1outTop)
    val result6: Error = CommandValidationServices.isValidCommandAddRectangle(addRectangleCommandWithY1outBottom)
    val result7: Error = CommandValidationServices.isValidCommandAddRectangle(addRectangleCommandWithY2outTop)
    val result8: Error = CommandValidationServices.isValidCommandAddRectangle(addRectangleCommandWithY2outBottom)
    assert(result1.message === expectedResult)
    // x2 < x1
    assert(result2.message === "Wrong 'Add Rectaangle' command. The bottom-right corner is more on left than top-left corner (example: R 16 1 20 3 for canvas 20x4)")
    // x2 < x1
    assert(result3.message === "Wrong 'Add Rectaangle' command. The bottom-right corner is more on left than top-left corner (example: R 16 1 20 3 for canvas 20x4)")
    assert(result4.message === expectedResult)
    assert(result5.message === expectedResult)
    // y2 < y1
    assert(result6.message === "Wrong 'Add Rectaangle' command. The bottom-right corner is more on top than top-left corner (example: R 16 1 20 3 for canvas 20x4)")
    // y2 < y1
    assert(result7.message === "Wrong 'Add Rectaangle' command. The bottom-right corner is more on top than top-left corner (example: R 16 1 20 3 for canvas 20x4)")
    assert(result8.message === expectedResult)
    CanvasServices.destroyCanvasTest
  }

  it should "return 'null' if the 'Add Rectangle' command has all coordinates equal (dot)" in {
    CanvasServices.createCanvasTest(4, 4)
    val expectedResult: Error = null
    val result: Error = CommandValidationServices.isValidCommandAddRectangle(addRectangleCommandToDot)
    assert(result === expectedResult)
    CanvasServices.destroyCanvasTest
  }


  /**
    * Tests for 'Add Fill' command
    *
    */

  "isValidCommandAddFill service" should "return 'Eroor' if canvas is not defined" in {
    val expectedResult: String = "Wrong 'Add Bucket' command. Canvas is not defined (example: C 20 4 for canvas 20x4)"
    val result: Error = CommandValidationServices.isValidCommandAddFill(validAddFillCommand)
    assert(result.message === expectedResult)
  }

  it should "return 'null' if the command array first element is 'F' and it has 2 positive x and y coordinates, symbol to fill and canvas is defined" in {
    CanvasServices.createCanvasTest(4, 4)
    val expectedResult: Error = null
    val result: Error = CommandValidationServices.isValidCommandAddFill(validAddFillCommand)
    assert(result === expectedResult)
    CanvasServices.destroyCanvasTest
  }

  it should "return 'Eroor' if the 'Add Fill' command has to many parameters" in {
    CanvasServices.createCanvasTest(4, 4)
    val expectedResult: String = "Wrong 'Add Bucket' command. Incorrect amount of parameters (example: B 10 3 o for canvas 20x4)"
    val result: Error = CommandValidationServices.isValidCommandAddFill(addFillCommandWithToManyParameters)
    assert(result.message === expectedResult)
    CanvasServices.destroyCanvasTest
  }

  it should "return 'Eroor' if the 'Add Fill' command has to few parameters" in {
    CanvasServices.createCanvasTest(4, 4)
    val expectedResult: String = "Wrong 'Add Bucket' command. Incorrect amount of parameters (example: B 10 3 o for canvas 20x4)"
    val result: Error = CommandValidationServices.isValidCommandAddFill(addFillCommandWithToFewParameters)
    assert(result.message === expectedResult)
    CanvasServices.destroyCanvasTest
  }

  it should "return 'Eroor' if the 'Add Fill' command has malformed parameters" in {
    CanvasServices.createCanvasTest(4, 4)
    val expectedResult: String = "Wrong 'Add Bucket' command. Coordinate parameter is not a valid integer (example: B 10 3 o for canvas 20x4)"
    val result: Error= CommandValidationServices.isValidCommandAddFill(addFillCommandWithMalformedParameters)
    assert(result.message === expectedResult)
    CanvasServices.destroyCanvasTest
  }

  it should "return 'Eroor' if the 'Add Fill' command coordinates are out of canvas" in {
    CanvasServices.createCanvasTest(4, 4)
    val expectedResult: String = "Wrong 'Add Bucket' command. Coordinates is not in canvas (example: B 10 3 o for canvas 20x4)"
    val result1: Error = CommandValidationServices.isValidCommandAddFill(addFillCommandWithXoutLeft)
    val result2: Error = CommandValidationServices.isValidCommandAddFill(addFillCommandWithXoutRight)
    val result3: Error = CommandValidationServices.isValidCommandAddFill(addFillCommandWithYoutTop)
    val result4: Error = CommandValidationServices.isValidCommandAddFill(addFillCommandWithYoutBottom)
    assert(result1.message === expectedResult)
    assert(result2.message === expectedResult)
    assert(result3.message === expectedResult)
    assert(result4.message === expectedResult)
    CanvasServices.destroyCanvasTest
  }

  it should "return 'Eroor' if the 'Add Fill' command has more than one symbol" in {
    CanvasServices.createCanvasTest(4, 4)
    val expectedResult: String = "Wrong 'Add Bucket' command. There is more than one filling symbol (example: B 10 3 o for canvas 20x4)"
    val result: Error = CommandValidationServices.isValidCommandAddFill(addFillCommandWithMoreThanOneSymbol)
    assert(result.message === expectedResult)
    CanvasServices.destroyCanvasTest
  }

}
