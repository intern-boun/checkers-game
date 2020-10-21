package com.example.checkersgame.moves

import com.example.checkersgame.positionstate.PositionState


fun setPointsAndSelect(table: MutableList<PositionState>,
                       clickedItem: PositionState,
                       listOfPossiblePositions:List<Pair<Int,List<Int>>>
              ):
        MutableList<PositionState> {

    val tempList = table.toMutableList()

    val positions = listOfPossiblePositions.map { it.first }

    val surPositionList = listOfPossiblePositions.map { it.second }

    val list = surPositionList[positions.indexOf(clickedItem.position)]

    for(index in list){
        tempList.find { it.position == index }?.isPointOn = true
    }

    tempList.find { it.position == clickedItem.position }?.isSelected = true

    return tempList
}

