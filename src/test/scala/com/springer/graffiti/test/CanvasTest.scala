package com.springer.graffiti.test

/**
  * Created by raitis on 10/06/2016.
  */
import com.springer.graffiti.services.CanvasServices


class CanvasTest extends UnitSpec {


  /**
    * Tests for 'isCanvasDefinedService' service
    *
    */

  "isCanvasDefinedService for undefined canvas" should "return 'false'" in {
    val expectedResult: Boolean = false
    val result: Boolean = CanvasServices.isCanvasDefinedService
    assert(result === expectedResult)
  }

  it should "return 'true' if canvas is defined" in {
    CanvasServices.createCanvasTest(4, 4)
    val expectedResult: Boolean = true
    val result: Boolean = CanvasServices.isCanvasDefinedService
    assert(result === expectedResult)
    CanvasServices.destroyCanvasTest
  }


  /**
    * Tests for 'getCanvasSizeService' service
    *
    */

  "getCanvasSizeService" should "return correct size for defined canvas" in {
    CanvasServices.createCanvasTest(4, 5)
    val expectedX: Int = 4
    val expectedY: Int = 5
    val result: (Int, Int) = CanvasServices.getCanvasSizeService
    assert(result._1 === expectedX)
    assert(result._2 === expectedY)
    CanvasServices.destroyCanvasTest
  }


  /**
    * Tests for 'createCanvasService' service
    *
    */

  "createCanvasService" should "return a new empty canvas of given coordinates plus border" in {
    val canvas = CanvasServices.createCanvasService(Array("C", "4", "5"))
    val canvasArray = canvas.exportToPrint
    val expectedX: Int = 6 // coordinates + 2x border
    val expectedY: Int = 7 // coordinates + 2x border
    val resultY: Int = canvasArray.length
    val wrongX = for (i <- canvasArray.indices if canvasArray(i).length != expectedX) yield canvasArray(i)
    assert(resultY === expectedY)
    assert(wrongX.length === 0)
    CanvasServices.destroyCanvasTest
  }

  it should "have first and last row containing only '-' symbol" in {
    val canvas = CanvasServices.createCanvasService(Array("C", "4", "5"))
    val canvasArray = canvas.exportToPrint
    val firstRowIndex = 0
    val lastRowIndex = canvasArray.length-1
    val wrongFirstRowSymbols = for (i <- canvasArray(firstRowIndex).indices if canvasArray(firstRowIndex)(i) != '-') yield canvasArray(firstRowIndex)(i)
    val wrongLastRowSymbols = for (i <- canvasArray(lastRowIndex).indices if canvasArray(lastRowIndex)(i) != '-') yield canvasArray(lastRowIndex)(i)
    assert(wrongFirstRowSymbols.length === 0)
    assert(wrongLastRowSymbols.length === 0)
    CanvasServices.destroyCanvasTest
  }

  it should "have '|' symbol at first and last index of each row" in {
    val canvas = CanvasServices.createCanvasService(Array("C", "4", "5"))
    val canvasArray = canvas.exportToPrint
    val wrongSymbol = for (i <- 1 until canvasArray.length - 1 if canvasArray(i)(0) != '|' || canvasArray(i)(canvasArray(i).length-1) != '|' ) yield false
    assert(wrongSymbol.length === 0)
    CanvasServices.destroyCanvasTest
  }


  /**
    * Tests for 'addLineServicee' service
    *
    */

  "addLineServicee" should "return existing canvas with added horizontal line in terms of 'x' symbol within its x,y coordinates" in {
    CanvasServices.createCanvasService(Array("C", "4", "5"))
    val canvas = CanvasServices.addElementService(Array("L", "2", "2", "3", "2"))
    val canvasArray = canvas.exportToPrint

    val expectedLength: Int = 2
    val expectedX1: Int = 2
    val expectedY1: Int = 2
    val expectedX2: Int = 3
    val expectedY2: Int = 2

    val line = for  {
      i <- 1 until canvasArray.length
      j <- 1 until canvasArray(i).length
      if canvasArray(i)(j) == 'x'
    } yield (i,j)

    assert(line.length === expectedLength)
    assert(line.head._1 === expectedY1)
    assert(line.head._2 === expectedX1)
    assert(line.last._1 === expectedY2)
    assert(line.last._2 === expectedX2)

    CanvasServices.destroyCanvasTest
  }

  it should "return existing canvas with added vertical line in terms of 'x' symbol within its x,y coordinates" in {
    CanvasServices.createCanvasService(Array("C", "4", "5"))
    val canvas = CanvasServices.addElementService(Array("L", "2", "2", "2", "4"))
    val canvasArray = canvas.exportToPrint

    val expectedLength: Int = 3
    val expectedX1: Int = 2
    val expectedY1: Int = 2
    val expectedX2: Int = 2
    val expectedY2: Int = 4

    val line = for  {
      i <- 1 until canvasArray.length
      j <- 1 until canvasArray(i).length
      if canvasArray(i)(j) == 'x'
    } yield (i,j)

    assert(line.length === expectedLength)
    assert(line.head._1 === expectedY1)
    assert(line.head._2 === expectedX1)
    assert(line.last._1 === expectedY2)
    assert(line.last._2 === expectedX2)
  }

  it should "return dot (one symbol) if all coordinates are equal" in {
    CanvasServices.createCanvasService(Array("C", "4", "5"))
    val canvas = CanvasServices.addElementService(Array("L", "2", "2", "2", "2"))
    val canvasArray = canvas.exportToPrint

    val expectedLength: Int = 1

    val line = for  {
      i <- 1 until canvasArray.length
      j <- 1 until canvasArray(i).length
      if canvasArray(i)(j) == 'x'
    } yield (i,j)

    assert(line.length === expectedLength)

    CanvasServices.destroyCanvasTest
  }


  /**
    * Tests for 'addRectangleService' service
    *
    */

  "addRectangleService" should "return existing canvas with added rectangle in terms of 'x' symbol within perimeter of its x,y coordinates" in {
    CanvasServices.createCanvasService(Array("C", "4", "5"))
    val canvas = CanvasServices.addElementService(Array("R", "2", "3", "4", "5"))
    val canvasArray = canvas.exportToPrint


    val expectedPerimeterLength: Int = 8

    val expectedTopLeftCornerX: Int = 2
    val expectedTopLeftCornerY: Int = 3

    val expectedTopRightCornerX: Int = 4
    val expectedTopRightCornerY: Int = 3

    val expectedBottomLeftCornerX: Int = 2
    val expectedBottomLeftCornerY: Int = 5

    val expectedBottomRightCornerX: Int = 4
    val expectedBottomRightCornerY: Int = 5

    val rectangle = for  {
      i <- 1 until canvasArray.length
      j <- 1 until canvasArray(i).length
      if canvasArray(i)(j) == 'x'
    } yield (i,j)

    // horizontal
    val horizontal = rectangle.groupBy(coordinates => coordinates._1)

    // horizontal left
    val topLine = horizontal(3)

    val topLineStartX = topLine.head._2
    val topLineStartY = topLine.head._1
    val topLineEndX = topLine.last._2
    val topLineEndY = topLine.last._1

    // horizontal right
    val bottomLine = horizontal(5)

    val bottomLineStartX = bottomLine.head._2
    val bottomLineStartY = bottomLine.head._1
    val bottomLineEndX = bottomLine.last._2
    val bottomLineEndY = bottomLine.last._1

    // vertical
    val vertical = rectangle.groupBy(coordinates => coordinates._2)

    // vertical left
    val leftLine = vertical(2)

    val leftLineStartX = leftLine.head._2
    val leftLineStartY = leftLine.head._1
    val leftLineEndX = leftLine.last._2
    val leftLineEndY = leftLine.last._1

    // vertical right
    val rightLine = vertical(4)

    val rightLineStartX = rightLine.head._2
    val rightLineStartY = rightLine.head._1
    val rightLineEndX = rightLine.last._2
    val rightLineEndY = rightLine.last._1

    // perimeter
    assert(rectangle.length === expectedPerimeterLength)

    // top line of rectangle
    assert(expectedTopLeftCornerX === topLineStartX)
    assert(expectedTopLeftCornerY === topLineStartY)
    assert(expectedTopRightCornerX === topLineEndX)
    assert(expectedTopRightCornerY === topLineEndY)

    // bottom line of rectangle
    assert(expectedBottomLeftCornerX === bottomLineStartX)
    assert(expectedBottomLeftCornerY === bottomLineStartY)
    assert(expectedBottomRightCornerX === bottomLineEndX)
    assert(expectedBottomRightCornerY === bottomLineEndY)

    // left line of rectangle
    assert(expectedTopLeftCornerX === leftLineStartX)
    assert(expectedTopLeftCornerY === leftLineStartY)
    assert(expectedBottomLeftCornerX === leftLineEndX)
    assert(expectedBottomLeftCornerY === leftLineEndY)

    // right line of rectangle
    assert(expectedTopRightCornerX === rightLineStartX)
    assert(expectedTopRightCornerY === rightLineStartY)
    assert(expectedBottomRightCornerX === rightLineEndX)
    assert(expectedBottomRightCornerY === rightLineEndY)

    CanvasServices.destroyCanvasTest
  }

  it should "return dot (one symbol) if all coordinates are equal" in {
    CanvasServices.createCanvasService(Array("C", "4", "5"))
    val canvas = CanvasServices.addElementService(Array("R", "2", "2", "2", "2"))
    val canvasArray = canvas.exportToPrint

    val expectedLength: Int = 1

    val line = for  {
      i <- 1 until canvasArray.length
      j <- 1 until canvasArray(i).length
      if canvasArray(i)(j) == 'x'
    } yield (i,j)

    assert(line.length === expectedLength)

    CanvasServices.destroyCanvasTest
  }


  /**
    * Tests for 'addFillService' service
    *
    */

  "addFillService" should "return existing canvas with changed surrounded surface symbols by symbol provided (bucket)" in {
    CanvasServices.createCanvasService(Array("C", "4", "5"))
    val canvas = CanvasServices.addElementService(Array("B", "1", "1", "c"))
    val canvasArray = canvas.exportToPrint

    val expectedAmount: Int = 20

    val fill = for  {
      i <- 1 until canvasArray.length
      j <- 1 until canvasArray(i).length
      if canvasArray(i)(j) == 'c'
    } yield (i,j)

    val fillAmount = fill.length
    assert(fillAmount === expectedAmount)
    CanvasServices.destroyCanvasTest
  }

  it should "return existing canvas and line with changed surrounded surface symbols, by symbol provided (bucket)" in {
    CanvasServices.createCanvasService(Array("C", "4", "5"))
    CanvasServices.addElementService(Array("L", "2", "1","2", "2"))
    val canvas = CanvasServices.addElementService(Array("B", "1", "1", "c"))
    val canvasArray = canvas.exportToPrint

    val expectedAmount: Int = 18

    val fill = for  {
      i <- 1 until canvasArray.length
      j <- 1 until canvasArray(i).length
      if canvasArray(i)(j) == 'c'
    } yield (i,j)

    val fillAmount = fill.length
    assert(fillAmount === expectedAmount)
    CanvasServices.destroyCanvasTest
  }

  it should "return existing canvas and line and rectangle with changed surrounded surface symbols, by symbol provided (bucket)" in {
    CanvasServices.createCanvasService(Array("C", "4", "5"))
    CanvasServices.addElementService(Array("L", "2", "1","2", "2"))
    CanvasServices.addElementService(Array("R", "2", "2","4", "4"))
    val canvas = CanvasServices.addElementService(Array("B", "1", "1", "c"))
    val canvasArray = canvas.exportToPrint

    val expectedAmount: Int = 8 // left and bottom side only

    val fill = for  {
      i <- 1 until canvasArray.length
      j <- 1 until canvasArray(i).length
      if canvasArray(i)(j) == 'c'
    } yield (i,j)

    val fillAmount = fill.length
    assert(fillAmount === expectedAmount)
    CanvasServices.destroyCanvasTest
  }

  it should "return existing canvas and line and rectangle with changed surface on the connected lines symbols, by symbol provided (bucket)" in {
    CanvasServices.createCanvasService(Array("C", "4", "5"))
    CanvasServices.addElementService(Array("L", "2", "1","2", "2"))
    CanvasServices.addElementService(Array("R", "2", "2","4", "4"))
    val canvas = CanvasServices.addElementService(Array("B", "2", "1", "c"))
    val canvasArray = canvas.exportToPrint

    val expectedAmount: Int = 9

    val fill = for  {
      i <- 1 until canvasArray.length
      j <- 1 until canvasArray(i).length
      if canvasArray(i)(j) == 'c'
    } yield (i,j)

    val fillAmount = fill.length
    assert(fillAmount === expectedAmount)
    CanvasServices.destroyCanvasTest
  }


  it should "return existing canvas and line and rectangle with changed close surface inside rectangle, by symbol provided (bucket)" in {
    CanvasServices.createCanvasService(Array("C", "4", "5"))
    CanvasServices.addElementService(Array("L", "2", "1","2", "2"))
    CanvasServices.addElementService(Array("R", "2", "2","4", "4"))
    val canvas = CanvasServices.addElementService(Array("B", "3", "3", "c"))
    val canvasArray = canvas.exportToPrint

    val expectedAmount: Int = 1

    val fill = for  {
      i <- 1 until canvasArray.length
      j <- 1 until canvasArray(i).length
      if canvasArray(i)(j) == 'c'
    } yield (i,j)

    val fillAmount = fill.length
    assert(fillAmount === expectedAmount)
    CanvasServices.destroyCanvasTest
  }

}