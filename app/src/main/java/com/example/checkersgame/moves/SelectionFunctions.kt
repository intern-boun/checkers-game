package com.example.checkersgame.moves

import android.util.Pair
import com.example.checkersgame.constants.Constants.Companion.BLACK
import com.example.checkersgame.constants.Constants.Companion.NO_PIECE
import com.example.checkersgame.constants.Constants.Companion.WHITE
import com.example.checkersgame.getSurroundings
import com.example.checkersgame.positionstate.PositionState

fun setTable(table: MutableList<PositionState>,
             clickedItem: PositionState,
             oldPosition: PositionState,
             captureOrMove: Boolean,
             colorId: Int
             ): Pair<Boolean, MutableList<PositionState>> {
    var hasCaptureAgain = false
    val tempList = table.toMutableList()

    if(captureOrMove) {  //CAPTURE
        for (item in table) if (item.justCapture) item.justCapture = false

        val midItemPosition: Int = (clickedItem.position + oldPosition.position).div(2)
        tempList[midItemPosition].pieceColorId = NO_PIECE
        tempList[midItemPosition].isKing = false

        tempList[oldPosition.position].pieceColorId = NO_PIECE

        tempList[clickedItem.position].pieceColorId = colorId
        tempList[clickedItem.position].isKing = oldPosition.isKing
        tempList[oldPosition.position].isKing = false


        when (colorId) {
            WHITE -> when (clickedItem.position) {
                0, 2, 4, 6 -> {
                    tempList[clickedItem.position].isKing = true
                }
            }

            BLACK -> when (clickedItem.position) {
                57, 59, 61, 63 -> {
                    tempList[clickedItem.position].isKing = true
                }
            }
        }

        hasCaptureAgain = checkItemHasCapture(clickedItem, getSurroundings(tempList,clickedItem.position)).first
        tempList[clickedItem.position].justCapture = hasCaptureAgain



        for(item in table) if(item.isPointOn) item.isPointOn = false
        for(item in table) if(item.isSelected) item.isSelected = false
        for(item in table) if(item.canCapture) item.canCapture = false
        for(item in table) if(item.canMove) item.canMove = false

    }else{  //MOVE
        tempList[oldPosition.position].pieceColorId = NO_PIECE
        tempList[oldPosition.position].isSelected = false

        tempList[clickedItem.position].pieceColorId = colorId
        tempList[clickedItem.position].isKing = oldPosition.isKing
        tempList[oldPosition.position].isKing = false

        when (colorId) {
            WHITE -> when (clickedItem.position) {
                0, 2, 4, 6 -> {
                    tempList[clickedItem.position].isKing = true
                }
            }

            BLACK -> when (clickedItem.position) {
                57, 59, 61, 63 -> {
                    tempList[clickedItem.position].isKing = true
                }
            }
        }

        for(item in table) if(item.isPointOn) item.isPointOn = false
        for(item in table) if(item.isSelected) item.isSelected = false
        for(item in table) if(item.canCapture) item.canCapture = false
        for(item in table) if(item.canMove) item.canMove = false
    }

return Pair(hasCaptureAgain,tempList)
}