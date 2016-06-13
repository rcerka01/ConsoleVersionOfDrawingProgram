package com.springer.graffiti.test

import com.springer.graffiti.services.{CanvasServices, CommandValidationServices}

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
  val createCommandWithNegativeCanvasSize: Array[String] = Array("C", "abc", "5")

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
  val addRectangleCommandWithX2outLeft: Array[String] = Array("R", "3", "2", "0", "2")
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
  val addFillCommandWithMoreThanOneSymbol: Array[String] = Array("B", "3", "5", "abc")


  /**
    * Tests for 'Is Valid' command
    *
    */

  "isValidCommand service" should "return 'true' if the command array first element is 'C'" in {
    val expectedResult: Boolean = true
    val result: Boolean = CommandValidationServices.isValidCommand(validCommand)
    assert(result === expectedResult)
  }

  it should "return 'false' if the command array first element is not defined as 'ALLOWED_COMMANDS'" in {
    val expectedResult: Boolean = false
    val result: Boolean = CommandValidationServices.isValidCommand(unknownCommand)
    assert(result === expectedResult)
  }


  /**
    * Tests for 'Create Canvas' command
    *
    */

  "isValidCommandCreateCanvas service" should "return 'true' if the command array first element is 'C' and it has 2 positive size parameters" in {
    val expectedResult: Boolean = true
    val result: Boolean = CommandValidationServices.isValidCommandCreateCanvas(validCreateCommand)
    assert(result === expectedResult)
  }

  it should "return 'false' if the 'Create Canvas' command has to many parameters" in {
    val expectedResult: Boolean = false
    val result: Boolean = CommandValidationServices.isValidCommandCreateCanvas(createCommandWithToManyParameters)
    assert(result === expectedResult)
  }

  it should "return 'false' if the 'Create Canvas' command has to few parameters" in {
    val expectedResult: Boolean = false
    val result: Boolean = CommandValidationServices.isValidCommandCreateCanvas(createCommandWithToFewParameters)
    assert(result === expectedResult)
  }

  it should "return 'false' if the 'Create Canvas' command has malformed parameters" in {
    val expectedResult: Boolean = false
    val result: Boolean = CommandValidationServices.isValidCommandCreateCanvas(createCommandWithMalformedParameters)
    assert(result === expectedResult)
  }

  it should "return 'true' if the 'Create Canvas' command has minimal size 0x0" in {
    val expectedResult: Boolean = true
    val result: Boolean = CommandValidationServices.isValidCommandCreateCanvas(createCommandWithMinimalCanvasSize)
    assert(result === expectedResult)
  }

  it should "return 'false' if the 'Create Canvas' command has negative canvas size" in {
    val expectedResult: Boolean = false
    val result: Boolean = CommandValidationServices.isValidCommandCreateCanvas(createCommandWithNegativeCanvasSize)
    assert(result === expectedResult)
  }


  /**
    * Tests for 'Add Line' command
    *
    */

  "isValidCommandAddLine service" should "return 'false' if canvas is not defined" in {
    val expectedResult: Boolean = false
    val result: Boolean = CommandValidationServices.isValidCommandAddLine(validAddLineCommand)
    assert(result === expectedResult)
  }

  it should "return 'true' if the command array first element is 'L' and it has 4 positive size parameters, where 1st and 3rd or 2nd and 4th must be equal, and canvas is defined" in {
    CanvasServices.createCanvasTest(4, 4)
    val expectedResult: Boolean = true
    val result: Boolean = CommandValidationServices.isValidCommandAddLine(validAddLineCommand)
    assert(result === expectedResult)
    CanvasServices.destroyCanvasTest
  }

  it should "return 'false' if the 'Add Line' command has to many parameters" in {
    CanvasServices.createCanvasTest(4, 4)
    val expectedResult: Boolean = false
    val result: Boolean = CommandValidationServices.isValidCommandAddLine(addLineCommandWithToManyParameters)
    assert(result === expectedResult)
    CanvasServices.destroyCanvasTest
  }

  it should "return 'false' if the 'Add Line' command has to few parameters" in {
    CanvasServices.createCanvasTest(4, 4)
    val expectedResult: Boolean = false
    val result: Boolean = CommandValidationServices.isValidCommandAddLine(addLineCommandWithToFewParameters)
    assert(result === expectedResult)
    CanvasServices.destroyCanvasTest

  }

  it should "return 'false' if the 'Add Line' command has malformed parameters" in {
    CanvasServices.createCanvasTest(4, 4)
    val expectedResult: Boolean = false
    val result: Boolean = CommandValidationServices.isValidCommandAddLine(addLineCommandWithMalformedParameters)
    assert(result === expectedResult)
    CanvasServices.destroyCanvasTest
  }

  it should "return 'false' if the 'Add Line' command do not have horizontal or vertical coordinates" in {
    CanvasServices.createCanvasTest(4, 4)
    val expectedResult: Boolean = false
    val result: Boolean = CommandValidationServices.isValidCommandAddLine(addLineCommandForNotHorizontalOrVerticalLine)
    assert(result === expectedResult)
    CanvasServices.destroyCanvasTest
  }

  it should "return 'false' if the 'Add Line' command coordinates are out of canvas" in {
    CanvasServices.createCanvasTest(4, 4)
    val expectedResult: Boolean = false
    val result1: Boolean = CommandValidationServices.isValidCommandAddLine(addLineCommandWithX1outLeft)
    val result2: Boolean = CommandValidationServices.isValidCommandAddLine(addLineCommandWithX2outLeft)
    val result3: Boolean = CommandValidationServices.isValidCommandAddLine(addLineCommandWithX1outRight)
    val result4: Boolean = CommandValidationServices.isValidCommandAddLine(addLineCommandWithX2outRight)
    val result5: Boolean = CommandValidationServices.isValidCommandAddLine(addLineCommandWithY1outTop)
    val result6: Boolean = CommandValidationServices.isValidCommandAddLine(addLineCommandWithY1outBottom)
    val result7: Boolean = CommandValidationServices.isValidCommandAddLine(addLineCommandWithY2outTop)
    val result8: Boolean = CommandValidationServices.isValidCommandAddLine(addLineCommandWithY2outBottom)
    assert(result1 === expectedResult)
    assert(result2 === expectedResult)
    assert(result3 === expectedResult)
    assert(result4 === expectedResult)
    assert(result5 === expectedResult)
    assert(result6 === expectedResult)
    assert(result7 === expectedResult)
    assert(result8 === expectedResult)
    CanvasServices.destroyCanvasTest
  }

  it should "return 'true' if the 'Add Line' is a dot" in {
    CanvasServices.createCanvasTest(4, 4)
    val expectedResult: Boolean = true
    val result: Boolean = CommandValidationServices.isValidCommandAddLine(addLineCommandToAddDot)
    assert(result === expectedResult)
    CanvasServices.destroyCanvasTest
  }


  /**
    * Tests for 'Add Rectangle' command
    *
    */

  "isValidCommandAddRectangle service" should "return 'false' if canvas is not defined" in {
    val expectedResult: Boolean = false
    val result: Boolean = CommandValidationServices.isValidCommandAddRectangle(validAddRectangleCommand)
    assert(result === expectedResult)
  }

  it should "return 'true' if the command array first element is 'R' and it has 4 positive size parameters, where x1 < x2 and y1 < y2, and canvas is defined" in {
    CanvasServices.createCanvasTest(4, 4)
    val expectedResult: Boolean = true
    val result: Boolean = CommandValidationServices.isValidCommandAddRectangle(validAddRectangleCommand)
    assert(result === expectedResult)
    CanvasServices.destroyCanvasTest
  }

  it should "return 'false' if the 'Add Rectangle' command has to many parameters" in {
    CanvasServices.createCanvasTest(4, 4)
    val expectedResult: Boolean = false
    val result: Boolean = CommandValidationServices.isValidCommandAddRectangle(addRectangleCommandWithToManyParameters)
    assert(result === expectedResult)
    CanvasServices.destroyCanvasTest
  }

  it should "return 'false' if the 'Add Rectangle' command has to few parameters" in {
    CanvasServices.createCanvasTest(4, 4)
    val expectedResult: Boolean = false
    val result: Boolean = CommandValidationServices.isValidCommandAddRectangle(addRectangleCommandWithToFewParameters)
    assert(result === expectedResult)
    CanvasServices.destroyCanvasTest
  }

  it should "return 'false' if the 'Add Rectangle' command has malformed parameters" in {
    CanvasServices.createCanvasTest(4, 4)
    val expectedResult: Boolean = false
    val result: Boolean = CommandValidationServices.isValidCommandAddRectangle(addRectangleCommandWithMalformedParameters)
    assert(result === expectedResult)
    CanvasServices.destroyCanvasTest
  }

  it should "return 'false' if the 'Add Rectangle' command has right corner before left" in {
    CanvasServices.createCanvasTest(4, 4)
    val expectedResult: Boolean = false
    val result: Boolean = CommandValidationServices.isValidCommandAddRectangle(addRectangleCommandWithRightCornerBeforeLeft)
    assert(result === expectedResult)
    CanvasServices.destroyCanvasTest
  }

  it should "return 'false' if the 'Add Rectangle' command has bottom corner above top" in {
    CanvasServices.createCanvasTest(4, 4)
    val expectedResult: Boolean = false
    val result: Boolean = CommandValidationServices.isValidCommandAddRectangle(addRectangleCommandWithBottomCornerAboveTop)
    assert(result === expectedResult)
    CanvasServices.destroyCanvasTest
  }

  it should "return 'false' if the 'Add Rectangle' command coordinates are out of canvas" in {
    CanvasServices.createCanvasTest(4, 4)
    val expectedResult: Boolean = false
    val result1: Boolean = CommandValidationServices.isValidCommandAddRectangle(addRectangleCommandWithX1outLeft)
    val result2: Boolean = CommandValidationServices.isValidCommandAddRectangle(addRectangleCommandWithX2outLeft)
    val result3: Boolean = CommandValidationServices.isValidCommandAddRectangle(addRectangleCommandWithX1outRight)
    val result4: Boolean = CommandValidationServices.isValidCommandAddRectangle(addRectangleCommandWithX2outRight)
    val result5: Boolean = CommandValidationServices.isValidCommandAddRectangle(addRectangleCommandWithY1outTop)
    val result6: Boolean = CommandValidationServices.isValidCommandAddRectangle(addRectangleCommandWithY1outBottom)
    val result7: Boolean = CommandValidationServices.isValidCommandAddRectangle(addRectangleCommandWithY2outTop)
    val result8: Boolean = CommandValidationServices.isValidCommandAddRectangle(addRectangleCommandWithY2outBottom)
    assert(result1 === expectedResult)
    assert(result2 === expectedResult)
    assert(result3 === expectedResult)
    assert(result4 === expectedResult)
    assert(result5 === expectedResult)
    assert(result6 === expectedResult)
    assert(result7 === expectedResult)
    assert(result8 === expectedResult)
    CanvasServices.destroyCanvasTest
  }

  it should "return 'true' if the 'Add Rectangle' command has all coordinates equal (dot)" in {
    CanvasServices.createCanvasTest(4, 4)
    val expectedResult: Boolean = true
    val result: Boolean = CommandValidationServices.isValidCommandAddRectangle(addRectangleCommandToDot)
    assert(result === expectedResult)
    CanvasServices.destroyCanvasTest
  }


  /**
    * Tests for 'Add Fill' command
    *
    */

  "isValidCommandAddFill service" should "return 'false' if canvas is not defined" in {
    val expectedResult: Boolean = false
    val result: Boolean = CommandValidationServices.isValidCommandAddFill(validAddFillCommand)
    assert(result === expectedResult)
  }

  it should "return 'true' if the command array first element is 'F' and it has 2 positive x and y coordinates, symbol to fill and canvas is defined" in {
    CanvasServices.createCanvasTest(4, 4)
    val expectedResult: Boolean = true
    val result: Boolean = CommandValidationServices.isValidCommandAddFill(validAddFillCommand)
    assert(result === expectedResult)
    CanvasServices.destroyCanvasTest
  }

  it should "return 'false' if the 'Add Fill' command has to many parameters" in {
    CanvasServices.createCanvasTest(4, 4)
    val expectedResult: Boolean = false
    val result: Boolean = CommandValidationServices.isValidCommandAddFill(addFillCommandWithToManyParameters)
    assert(result === expectedResult)
    CanvasServices.destroyCanvasTest
  }

  it should "return 'false' if the 'Add Fill' command has to few parameters" in {
    CanvasServices.createCanvasTest(4, 4)
    val expectedResult: Boolean = false
    val result: Boolean = CommandValidationServices.isValidCommandAddFill(addFillCommandWithToFewParameters)
    assert(result === expectedResult)
    CanvasServices.destroyCanvasTest
  }

  it should "return 'false' if the 'Add Fill' command has malformed parameters" in {
    CanvasServices.createCanvasTest(4, 4)
    val expectedResult: Boolean = false
    val result: Boolean = CommandValidationServices.isValidCommandAddFill(addFillCommandWithMalformedParameters)
    assert(result === expectedResult)
    CanvasServices.destroyCanvasTest
  }

  it should "return 'false' if the 'Add Fill' command coordinates are out of canvas" in {
    CanvasServices.createCanvasTest(4, 4)
    val expectedResult: Boolean = false
    val result1: Boolean = CommandValidationServices.isValidCommandAddRectangle(addFillCommandWithXoutLeft)
    val result2: Boolean = CommandValidationServices.isValidCommandAddRectangle(addFillCommandWithXoutRight)
    val result3: Boolean = CommandValidationServices.isValidCommandAddRectangle(addFillCommandWithYoutTop)
    val result4: Boolean = CommandValidationServices.isValidCommandAddRectangle(addFillCommandWithYoutBottom)
    assert(result1 === expectedResult)
    assert(result2 === expectedResult)
    assert(result3 === expectedResult)
    assert(result4 === expectedResult)
    CanvasServices.destroyCanvasTest
  }

  it should "return 'false' if the 'Add Fill' command has more than one symbol" in {
    CanvasServices.createCanvasTest(4, 4)
    val expectedResult: Boolean = false
    val result: Boolean = CommandValidationServices.isValidCommandAddFill(addFillCommandWithMoreThanOneSymbol)
    assert(result === expectedResult)
    CanvasServices.destroyCanvasTest
  }

}
