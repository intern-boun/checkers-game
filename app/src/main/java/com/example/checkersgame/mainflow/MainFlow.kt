package com.example.checkersgame.mainflow

import androidx.core.util.component1
import androidx.core.util.component2
import com.example.checkersgame.constants.Constants.Companion.BLACK
import com.example.checkersgame.constants.Constants.Companion.BLACK_KEEP_CAPTURE_TYPE
import com.example.checkersgame.constants.Constants.Companion.BLACK_MOVE_TYPE
import com.example.checkersgame.constants.Constants.Companion.BLACK_WON_TYPE
import com.example.checkersgame.constants.Constants.Companion.NO_PIECE
import com.example.checkersgame.constants.Constants.Companion.NO_TOAST
import com.example.checkersgame.constants.Constants.Companion.PIECE_CAN_NOT_MOVE_TYPE
import com.example.checkersgame.constants.Constants.Companion.THERE_IS_CAPTURE_TYPE
import com.example.checkersgame.constants.Constants.Companion.WHITE
import com.example.checkersgame.constants.Constants.Companion.WHITE_KEEP_CAPTURE_TYPE
import com.example.checkersgame.constants.Constants.Companion.WHITE_MOVE_TYPE
import com.example.checkersgame.constants.Constants.Companion.WHITE_WON_TYPE
import com.example.checkersgame.moves.*
import com.example.checkersgame.positionstate.PositionState


fun mainFlow(tempListTable: MutableList<PositionState>,
             listOfPositions: List<Any>,
             listOfBools: List<Boolean>
             ): Triple<MutableList<PositionState>, List<Any>, List<Boolean>>{

    val pushedItem: PositionState = listOfPositions[0] as PositionState
    var oldPosition: PositionState = listOfPositions[1] as PositionState
    var listOfPositionsTemp = listOfPositions[2] as List<*>

    var whiteOrBlackMove: Boolean = listOfBools[0]
    var selectStage: Boolean = listOfBools[1]
    var captureOrMove: Boolean = listOfBools[2]
    var hasCaptureAgain: Boolean = listOfBools[3]

    var toastType: Int = NO_TOAST


    val clickedItem = tempListTable.find { it.position == pushedItem.position }

    fun selectStage(pushed: PositionState, colorId: Int) {
        val temp = tempListTable.toMutableList()
        tempListTable.clear()
        val (hasCapTemp, setTableTemp) = setTable(temp, pushed, oldPosition, captureOrMove, colorId)
        tempListTable.addAll(setTableTemp)

        hasCaptureAgain = hasCapTemp

        when(colorId){
            WHITE -> {whiteOrBlackMove = false}
            BLACK -> {whiteOrBlackMove = true}
        }

        selectStage = false

        if (hasCaptureAgain) {

            val pair = tableCaptureCheck(tempListTable, colorId)
                .second
                .find { it.first == pushed.position }

            for (i in 0..63) {
                for (j in pair!!.second) {
                    if (i == j) {
                        tempListTable[i].isPointOn = true
                    }
                }
            }
            tempListTable[pushed.position].isSelected = true
            oldPosition = tempListTable.find { it.justCapture  }!!
            oldPosition.justCapture = false
            tempListTable.find { it.position == pushed.position }!!.justCapture = false
            selectStage = true
            captureOrMove = true

            when(colorId){
                WHITE -> {whiteOrBlackMove = true}
                BLACK -> {whiteOrBlackMove = false}
            }

        }
    }
    fun waitingStage(pushed: PositionState, colorId: Int) {
        var clickedTemp: PositionState
        val (isThereCap, listOfPositionsCanCapture) = tableCaptureCheck(
            tempListTable,
            colorId
        )

        if (isThereCap) {
            val temp = tempListTable.toMutableList()
            tempListTable.clear()
            tempListTable.addAll(setTableCaptureTempList(temp, listOfPositionsCanCapture))
            clickedTemp = tempListTable.find { it.position == pushedItem.position }!!

            if (pushed.canCapture) {
                for (item in tempListTable) if (item.canCapture) item.canCapture = false
                captureOrMove = true
                val temp = tempListTable.toMutableList()
                tempListTable.clear()
                tempListTable.addAll(
                    setPointsAndSelect(
                        temp,
                        clickedTemp,
                        listOfPositionsCanCapture
                    )
                )
                listOfPositionsTemp =
                    getPossiblePositions(clickedTemp, listOfPositionsCanCapture)
                clickedTemp = tempListTable.find { it.position == pushedItem.position }!!
                oldPosition = clickedTemp
                selectStage = true
            } else {
                toastType = THERE_IS_CAPTURE_TYPE
                for (item in tempListTable) if (item.isPointOn) item.isPointOn = false
                for (item in tempListTable) if (item.isSelected) item.isSelected = false
            }
        } else {
            val (isThereMove, listOfPositionsCanMove) = tableMoveCheck(
                tempListTable,
                colorId
            )
            if (!isThereMove) {
                //TODO:GAMEOVER FOR COLOR_ID
                when(colorId){
                    WHITE -> {toastType = BLACK_WON_TYPE}
                    BLACK -> {toastType = WHITE_WON_TYPE}
                }
            } else {
                val temp = tempListTable.toMutableList()
                tempListTable.clear()
                tempListTable.addAll(setTableMoveTempList(temp, listOfPositionsCanMove))
                clickedTemp = tempListTable.find { it.position == pushedItem.position }!!

                if (pushed.canMove) {
                    for (item in tempListTable) if (item.canMove) item.canMove = false
                    captureOrMove = false
                    val temp = tempListTable.toMutableList()
                    tempListTable.clear()
                    tempListTable.addAll(
                        setPointsAndSelect(
                            temp,
                            clickedTemp,
                            listOfPositionsCanMove
                        )
                    )
                    listOfPositionsTemp =
                        getPossiblePositions(clickedTemp, listOfPositionsCanMove)
                    clickedTemp = tempListTable.find { it.position == pushedItem.position }!!
                    oldPosition = clickedTemp
                    selectStage = true
                }else{
                    toastType = PIECE_CAN_NOT_MOVE_TYPE
                }
            }
        }
    }

    fun gameOverControl(colorId: Int) {
        var enemyPieceId : Int = NO_PIECE
        when(colorId){
            WHITE -> {enemyPieceId = BLACK}
            BLACK ->{enemyPieceId = WHITE}
        }

        var isThereEnemyPiece = false
        for (item in tempListTable) if (item.pieceColorId == enemyPieceId) isThereEnemyPiece = true

        if(!isThereEnemyPiece){
            when (colorId) {
                WHITE -> {
                    toastType = WHITE_WON_TYPE
                }
                BLACK -> {
                    toastType = BLACK_WON_TYPE
                }
            }
        }else {
            val enemyHasCapture = tableCaptureCheck(tempListTable, enemyPieceId).first
            val enemyHasMove = tableMoveCheck(tempListTable, enemyPieceId).first

            when (enemyHasCapture || enemyHasMove) {
                false -> {
                    when (colorId) {
                        WHITE -> {
                            toastType = WHITE_WON_TYPE
                        }
                        BLACK -> {
                            toastType = BLACK_WON_TYPE
                        }
                    }
                }
                true -> Unit
            }
        }
    }

    if (whiteOrBlackMove) {   //White move

        if (selectStage && clickedItem!!.isPointOn) {    //Select stage for white

            selectStage(clickedItem, WHITE)

            gameOverControl(WHITE)

        }else if (selectStage && clickedItem!!.pieceColorId == WHITE && !clickedItem.isSelected && !hasCaptureAgain){

            for (item in tempListTable) if (item.isPointOn) item.isPointOn = false
            for (item in tempListTable) if (item.isSelected) item.isSelected = false
            selectStage = false

            waitingStage(clickedItem, WHITE)


        } else if (selectStage) {// pointOn -> false ve selectStage = false

            if(hasCaptureAgain){
                toastType = WHITE_KEEP_CAPTURE_TYPE
                val listOfPos: List<Any> = listOf(oldPosition, listOfPositionsTemp, toastType)
                val listOfBooleans: List<Boolean> = listOf(whiteOrBlackMove,selectStage,captureOrMove, hasCaptureAgain)

                return Triple(tempListTable, listOfPos, listOfBooleans)
            }else {
                when(clickedItem!!.isSelected){
                    true -> {toastType = NO_TOAST}
                    false -> when(clickedItem.pieceColorId) {
                        BLACK -> toastType = WHITE_MOVE_TYPE
                    }
                }
                for (item in tempListTable) if (item.isPointOn) item.isPointOn = false
                for (item in tempListTable) if (item.isSelected) item.isSelected = false
                selectStage = false
            }

        } else if (clickedItem!!.pieceColorId == WHITE) {  //Waiting stage for white


            waitingStage(clickedItem, WHITE)


        } else if(clickedItem.pieceColorId == BLACK) {
            // TODO: TOAST WHITES MOVE
            toastType = WHITE_MOVE_TYPE
        }

    } else {      //BLACK MOVE

        if (selectStage && clickedItem!!.isPointOn) {//Select stage for black

            selectStage(clickedItem, BLACK)

            gameOverControl(BLACK)

        }else if (selectStage && clickedItem!!.pieceColorId == BLACK && !clickedItem.isSelected && !hasCaptureAgain){

            for (item in tempListTable) if (item.isPointOn) item.isPointOn = false
            for (item in tempListTable) if (item.isSelected) item.isSelected = false
            selectStage = false

            waitingStage(clickedItem, BLACK)

        }else if (selectStage) {   // pointOn -> false ve selectStage = false

            if(hasCaptureAgain){
                toastType = BLACK_KEEP_CAPTURE_TYPE
                val listOfPos: List<Any> = listOf(oldPosition, listOfPositionsTemp, toastType)
                val listOfBooleans: List<Boolean> = listOf(whiteOrBlackMove,selectStage,captureOrMove, hasCaptureAgain)

                return Triple(tempListTable, listOfPos, listOfBooleans)
            }else {
                when(clickedItem!!.isSelected){
                    true -> {toastType = NO_TOAST}
                    false -> when(clickedItem.pieceColorId) {
                        WHITE -> toastType = BLACK_MOVE_TYPE
                    }
                }

                for (item in tempListTable) if (item.isPointOn) item.isPointOn = false
                for (item in tempListTable) if (item.isSelected) item.isSelected = false
                selectStage = false
            }

        } else if (clickedItem!!.pieceColorId == BLACK) {  //Waiting stage for Black

            waitingStage(clickedItem, BLACK)

        } else if(clickedItem.pieceColorId == WHITE){
            // TODO: TOAST BLACK MOVE
            toastType = BLACK_MOVE_TYPE
        }

    }

    val listOfPos: List<Any> = listOf(oldPosition, listOfPositionsTemp, toastType)
    val listOfBooleans: List<Boolean> = listOf(whiteOrBlackMove,selectStage,captureOrMove, hasCaptureAgain)

    return Triple(tempListTable, listOfPos, listOfBooleans)
}