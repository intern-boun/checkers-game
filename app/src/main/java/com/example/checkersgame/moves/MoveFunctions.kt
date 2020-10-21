package com.example.checkersgame.moves

import com.example.checkersgame.constants.Constants
import com.example.checkersgame.constants.Constants.Companion.NO_PIECE
import com.example.checkersgame.constants.Constants.Companion.NO_SUCH_POSITION
import com.example.checkersgame.getSurroundings
import com.example.checkersgame.positionstate.PositionState

fun checkItemHasMove(item: PositionState, surroundings: List<PositionState>):
        Pair<Boolean, List<Int>>{
    val hasMove: Boolean
    val listOfIndex: MutableList<Int> = mutableListOf()

    if(item.isKing){   //KING MOVES
        if(item.pieceColorId != NO_PIECE){
            if((surroundings[0].position != NO_SUCH_POSITION) && (surroundings[0].pieceColorId == NO_PIECE)){
                listOfIndex.add(surroundings[0].position)   //UR MOVE
            }
            if((surroundings[2].position != NO_SUCH_POSITION) && (surroundings[2].pieceColorId == NO_PIECE)){
                listOfIndex.add(surroundings[2].position)   //UL MOVE
            }
            if((surroundings[4].position != NO_SUCH_POSITION) && (surroundings[4].pieceColorId == NO_PIECE)) {
                listOfIndex.add(surroundings[4].position)   //DR MOVE
            }
            if(surroundings[6].position != NO_SUCH_POSITION && surroundings[6].pieceColorId == NO_PIECE){
                listOfIndex.add(surroundings[6].position)   //DL MOVE
            }
        }
    }else{
        if(item.pieceColorId == Constants.WHITE){    //UR and UL for WHITE
            if((surroundings[0].position != NO_SUCH_POSITION) && (surroundings[0].pieceColorId == NO_PIECE)){
                listOfIndex.add(surroundings[0].position)   //UR MOVE
            }
            if((surroundings[2].position != NO_SUCH_POSITION) && (surroundings[2].pieceColorId == NO_PIECE)){
                listOfIndex.add(surroundings[2].position)   //UL MOVE
            }

        }else{  //DR and DL for BLACK
            if((surroundings[4].position != NO_SUCH_POSITION) && (surroundings[4].pieceColorId == NO_PIECE)) {
                listOfIndex.add(surroundings[4].position)   //DR MOVE
            }
            if(surroundings[6].position != NO_SUCH_POSITION && surroundings[6].pieceColorId == NO_PIECE){
                listOfIndex.add(surroundings[6].position)   //DL MOVE
            }
        }
    }

    hasMove = listOfIndex.size > 0

    return Pair(hasMove, listOfIndex.toList())
}

fun tableMoveCheck(table: MutableList<PositionState>, color:Int):
        Pair<Boolean, List<Pair<Int, List<Int>>>>{
    var isThereMove = false
    val listOfPositions: MutableList<Pair<Int, List<Int>>> = mutableListOf()

    for(i in 0..63) {
        if (table[i].isInDomain && table[i].pieceColorId == color) {
            val (hasMove, moveListOfItem) = checkItemHasMove(
                table[i],
                getSurroundings(table,i)
            )
            if(hasMove){
                listOfPositions.add(Pair(i, moveListOfItem))
                isThereMove = true
            }
        }
    }
    return Pair(isThereMove, listOfPositions.toList())
}

fun setTableMoveTempList(table:MutableList<PositionState>,
                            positionsHasMoveList:List<Pair<Int,List<Int>>>
):
        MutableList<PositionState>{
    val tableList = table.toMutableList()

    for((positionIndex, _) in positionsHasMoveList){
        tableList.find { it.position == positionIndex }?.canMove = true
    }
    return tableList
}