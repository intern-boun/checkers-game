package com.example.checkersgame

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.checkersgame.positionstate.PositionState
import com.example.checkersgame.constants.Constants.Companion.NO_SUCH_POSITION
import com.example.checkersgame.constants.Constants.Companion.DL_1
import com.example.checkersgame.constants.Constants.Companion.DL_2
import com.example.checkersgame.constants.Constants.Companion.DR_1
import com.example.checkersgame.constants.Constants.Companion.DR_2
import com.example.checkersgame.constants.Constants.Companion.UL_1
import com.example.checkersgame.constants.Constants.Companion.UL_2
import com.example.checkersgame.constants.Constants.Companion.UR_1
import com.example.checkersgame.constants.Constants.Companion.UR_2

fun getDirectionItem(table: MutableList<PositionState>, index: Int, direction: Int):
        PositionState {
    if ((index + direction) >= 0 && ((index + direction) <= 63) && (table.get(index + direction)!!.isInDomain)) {
        return table.get(index + direction)!!
    } else {
        return PositionState(NO_SUCH_POSITION)
    }
}


fun getSurroundings(table: MutableList<PositionState>, index:Int): List<PositionState>{
    return listOf<PositionState>(
        getDirectionItem(table, index, UR_1), getDirectionItem(table, index, UR_2),
        getDirectionItem(table, index, UL_1), getDirectionItem(table, index, UL_2),
        getDirectionItem(table, index, DR_1), getDirectionItem(table, index, DR_2),
        getDirectionItem(table, index, DL_1),getDirectionItem(table, index, DL_2)
    )
}


