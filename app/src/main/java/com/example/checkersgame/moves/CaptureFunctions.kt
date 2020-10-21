package com.example.checkersgame.moves

import com.example.checkersgame.positionstate.PositionState
import com.example.checkersgame.constants.Constants.Companion.BLACK
import com.example.checkersgame.constants.Constants.Companion.NO_PIECE
import com.example.checkersgame.constants.Constants.Companion.NO_SUCH_POSITION
import com.example.checkersgame.constants.Constants.Companion.WHITE
import com.example.checkersgame.getSurroundings


fun checkItemHasCapture(item: PositionState, surroundings: List<PositionState>):
        Pair<Boolean, List<Int>>{
    var hasCapture: Boolean
    val listOfIndex: MutableList<Int> = mutableListOf()

    if(item.isKing){   //king durumları irdeleniyor.

        if(item.pieceColorId == WHITE){    //King White
            if((surroundings[0].position != NO_SUCH_POSITION) && (surroundings[0].pieceColorId == BLACK)) {
                if (surroundings[1].position != NO_SUCH_POSITION && surroundings[1].pieceColorId == NO_PIECE) { //UR CAPTURE
                    listOfIndex.add(surroundings[1].position)
                }
            }
            if((surroundings[2].position != NO_SUCH_POSITION) && (surroundings[2].pieceColorId == BLACK)) {
                if (surroundings[3].position != NO_SUCH_POSITION && surroundings[3].pieceColorId == NO_PIECE) {//UL CAPTURE
                    listOfIndex.add(surroundings[3].position)
                }
            }
            if ((surroundings[4].position != NO_SUCH_POSITION) && (surroundings[4].pieceColorId == BLACK)) {
                if (surroundings[5].position != NO_SUCH_POSITION && surroundings[5].pieceColorId == NO_PIECE) { //DR CAPTURE
                    listOfIndex.add(surroundings[5].position)
                }
            }
            if ((surroundings[6].position != NO_SUCH_POSITION) && (surroundings[6].pieceColorId == BLACK)) {
                if (surroundings[7].position != NO_SUCH_POSITION && surroundings[7].pieceColorId == NO_PIECE) {//DL CAPTURE
                    listOfIndex.add(surroundings[7].position)
                }
            }
        }else {  //King Black
            if((surroundings[0].position != NO_SUCH_POSITION) && (surroundings[0].pieceColorId == WHITE)) {
                if (surroundings[1].position != NO_SUCH_POSITION && surroundings[1].pieceColorId == NO_PIECE) { //UR CAPTURE
                    listOfIndex.add(surroundings[1].position)
                }
            }
            if((surroundings[2].position != NO_SUCH_POSITION) && (surroundings[2].pieceColorId == WHITE)) {
                if (surroundings[3].position != NO_SUCH_POSITION && surroundings[3].pieceColorId == NO_PIECE) {//UL CAPTURE
                    listOfIndex.add(surroundings[3].position)
                }
            }
            if ((surroundings[4].position != NO_SUCH_POSITION) && (surroundings[4].pieceColorId == WHITE)) {
                if (surroundings[5].position != NO_SUCH_POSITION && surroundings[5].pieceColorId == NO_PIECE) { //DR CAPTURE
                    listOfIndex.add(surroundings[5].position)
                }
            }
            if ((surroundings[6].position != NO_SUCH_POSITION) && (surroundings[6].pieceColorId == WHITE)) {
                if (surroundings[7].position != NO_SUCH_POSITION && surroundings[7].pieceColorId == NO_PIECE) {//DL CAPTURE
                    listOfIndex.add(surroundings[7].position)
                }
            }
        }

    }else{
        if(item.pieceColorId == WHITE){    //UR and UL for WHITE
            if((surroundings[0].position != NO_SUCH_POSITION) && (surroundings[0].pieceColorId == BLACK)) {
                if (surroundings[1].position != NO_SUCH_POSITION && surroundings[1].pieceColorId == NO_PIECE) { //UR CAPTURE
                    listOfIndex.add(surroundings[1].position)
                }
            }
            if((surroundings[2].position != NO_SUCH_POSITION) && (surroundings[2].pieceColorId == BLACK)) {
                if (surroundings[3].position != NO_SUCH_POSITION && surroundings[3].pieceColorId == NO_PIECE) {//UL CAPTURE
                    listOfIndex.add(surroundings[3].position)
                }
            }
        }else {  //DR and DL for BLACK
            if ((surroundings[4].position != NO_SUCH_POSITION) && (surroundings[4].pieceColorId == WHITE)) {
                if (surroundings[5].position != NO_SUCH_POSITION && surroundings[5].pieceColorId == NO_PIECE) { //DR CAPTURE
                    listOfIndex.add(surroundings[5].position)
                }
            }
            if ((surroundings[6].position != NO_SUCH_POSITION) && (surroundings[6].pieceColorId == WHITE)) {
                if (surroundings[7].position != NO_SUCH_POSITION && surroundings[7].pieceColorId == NO_PIECE) {//DL CAPTURE
                    listOfIndex.add(surroundings[7].position)
                }
            }
        }

    }

    hasCapture = listOfIndex.size > 0

    return Pair(hasCapture, listOfIndex.toList())
}

fun tableCaptureCheck(table: MutableList<PositionState>, color:Int):
        Pair<Boolean, List<Pair<Int, List<Int>>>>{
    table.set(1, PositionState(1))

    var isThereCapture = false
    val listOfPositions: MutableList<Pair<Int, List<Int>>> = mutableListOf()

    for(item in 0..63) {
        if (table.get(item).isInDomain && table.get(item).pieceColorId == color) {

            val (hasCapture, captureListOfItem) = checkItemHasCapture(
                table[item],
                getSurroundings(table,item))
            if(hasCapture){
                listOfPositions.add(Pair(item, captureListOfItem))
                isThereCapture = true
            }
        }
    }

return Pair(isThereCapture, listOfPositions.toList())
}

fun setTableCaptureTempList(table: MutableList<PositionState>,
                    positionsHasCaptureList:List<Pair<Int,List<Int>>>
                    ):
        MutableList<PositionState>{
    for((positionIndex, _) in positionsHasCaptureList){
        table.find { it.position == positionIndex }?.canCapture = true
    }
    return table
}

fun getPossiblePositions(
    pushedItem: PositionState,
    listOfPossiblePositions: List<Pair<Int, List<Int>>>
): List<Int>{

    val positions = listOfPossiblePositions.map { it.first }

    val surPositionList = listOfPossiblePositions.map { it.second }

    return surPositionList.get(positions.indexOf(pushedItem.position))
}