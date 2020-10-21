package com.example.checkersgame

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.checkersgame.constants.Constants.Companion.BLACK
import com.example.checkersgame.constants.Constants.Companion.BLACK_KEEP_CAPTURE_TYPE
import com.example.checkersgame.constants.Constants.Companion.NO_TOAST
import com.example.checkersgame.constants.Constants.Companion.WHITE
import com.example.checkersgame.constants.Constants.Companion.WHITE_KEEP_CAPTURE_TYPE
import com.example.checkersgame.mainflow.mainFlow
import com.example.checkersgame.positionstate.PositionState

class GameViewModel : ViewModel() {

    private val _positions: MutableLiveData<MutableList<PositionState>>
    private var selectStage: Boolean
    private var whiteOrBlackMove: Boolean   //true -> white's move || false-> black's move
    private var captureOrMove: Boolean
    private var hasCaptureAgain:Boolean
    private var listOfPossiblePositions: List<Int> = listOf()
    private var oldPosition: PositionState

    private var tempList = prepareBoard()


    init {
        _positions = MutableLiveData(prepareBoard())
        selectStage = false // true -> waiting stage
        whiteOrBlackMove = true
        captureOrMove = false // true -> capture || false -> move
        hasCaptureAgain = false
        oldPosition = PositionState(-1)
    }

    val positions: LiveData<MutableList<PositionState>>
        get() = _positions


    //onCLick for item it
    fun clickJob(positionState: PositionState): Int{  //Int -> Toast Type
        if(positionState.isInDomain) {

            val temp = tempList.toMutableList()

            val listOfBools = listOf<Boolean>(whiteOrBlackMove,
                                                            selectStage,
                                                            captureOrMove,
                                                            hasCaptureAgain
                                                            )
            val listOfPositions = listOf<Any>(positionState, oldPosition, listOfPossiblePositions)

            ////////////////////////////////////////////////////////////////////////////////////////////////////
            val (valueList, list, BoolList) = mainFlow(
                temp,
                listOfPositions,
                listOfBools
            )
            ///////////////////////////////////////////////////////////////////////////////////////////////////

            _positions.value = valueList

            tempList = valueList.toMutableList()


            oldPosition = list[0] as PositionState
            listOfPossiblePositions = list[1] as List<Int>
            //list[2] -> toastType


            whiteOrBlackMove = BoolList[0]
            selectStage = BoolList[1]
            captureOrMove = BoolList[2]
            hasCaptureAgain = BoolList[3]


            return list[2] as Int   //returns constant -> Toast Type

        }else{
            if(hasCaptureAgain){
                _positions.value = _positions.value?.toMutableList()
                when(_positions.value?.find { it.isSelected }?.pieceColorId){
                    WHITE -> return WHITE_KEEP_CAPTURE_TYPE
                    BLACK -> return BLACK_KEEP_CAPTURE_TYPE
                }
            }else {
                for (item in _positions.value!!) if (item.isPointOn) item.isPointOn = false
                for (item in _positions.value!!) if (item.isSelected) item.isSelected = false
                selectStage = false
                _positions.value = _positions.value!!.toMutableList()
            }
            return NO_TOAST
        }


    }



    fun restartButton(){
        _positions.value = prepareBoard()
        for (item in _positions.value!!) if (item.isPointOn) item.isPointOn = false
        for (item in _positions.value!!) if (item.isSelected) item.isSelected = false
        tempList = prepareBoard()
        selectStage = false // true -> waiting stage
        whiteOrBlackMove = true
        captureOrMove = false // true -> capture || false -> move
        hasCaptureAgain = false
        oldPosition = PositionState(-1)
    }

    private fun prepareBoard(): MutableList<PositionState>{     //Start position of board

        return mutableListOf(

            //ROW 1th
            PositionState(_position = 0, _pieceColorId = BLACK, _isInDomain = true),PositionState(_position = 1),
            PositionState(_position = 2, _pieceColorId = BLACK, _isInDomain = true),PositionState(_position = 3),
            PositionState(_position = 4, _pieceColorId = BLACK, _isInDomain = true),PositionState(_position = 5),
            PositionState(_position = 6, _pieceColorId = BLACK, _isInDomain = true),PositionState(_position = 7),
            //ROW 2th
            PositionState(_position = 8),PositionState(_position = 9, _pieceColorId = BLACK, _isInDomain = true),
            PositionState(_position = 10),PositionState(_position = 11, _pieceColorId = BLACK, _isInDomain = true),
            PositionState(_position = 12),PositionState(_position = 13, _pieceColorId = BLACK, _isInDomain = true),
            PositionState(_position = 14),PositionState(_position = 15, _pieceColorId = BLACK, _isInDomain = true),
            //ROW 3th
            PositionState(_position = 16, _pieceColorId = BLACK, _isInDomain = true,_canMove = true),PositionState(_position = 17),
            PositionState(_position = 18, _pieceColorId = BLACK, _isInDomain = true,_canMove = true),PositionState(_position = 19),
            PositionState(_position = 20, _pieceColorId = BLACK, _isInDomain = true,_canMove = true),PositionState(_position = 21),
            PositionState(_position = 22, _pieceColorId = BLACK, _isInDomain = true,_canMove = true),PositionState(_position = 23),
            //ROW 4th
            PositionState(_position = 24),PositionState(_position = 25, _isInDomain = true),
            PositionState(_position = 26),PositionState(_position = 27, _isInDomain = true),
            PositionState(_position = 28),PositionState(_position = 29, _isInDomain = true),
            PositionState(_position = 30),PositionState(_position = 31, _isInDomain = true),
            //ROW 5th
            PositionState(_position = 32, _isInDomain = true),PositionState(_position = 33),
            PositionState(_position = 34, _isInDomain = true),PositionState(_position = 35),
            PositionState(_position = 36, _isInDomain = true),PositionState(_position = 37),
            PositionState(_position = 38, _isInDomain = true),PositionState(_position = 39),
            //ROW 6th
            PositionState(_position = 40),PositionState(_position = 41, _pieceColorId = WHITE, _isInDomain = true,_canMove = true),
            PositionState(_position = 42),PositionState(_position = 43, _pieceColorId = WHITE, _isInDomain = true,_canMove = true),
            PositionState(_position = 44),PositionState(_position = 45, _pieceColorId = WHITE, _isInDomain = true,_canMove = true),
            PositionState(_position = 46),PositionState(_position = 47, _pieceColorId = WHITE, _isInDomain = true,_canMove = true),
            //ROW 7th
            PositionState(_position = 48, _pieceColorId = WHITE, _isInDomain = true),PositionState(_position = 49),
            PositionState(_position = 50, _pieceColorId = WHITE, _isInDomain = true),PositionState(_position = 51),
            PositionState(_position = 52, _pieceColorId = WHITE, _isInDomain = true),PositionState(_position = 53),
            PositionState(_position = 54, _pieceColorId = WHITE, _isInDomain = true),PositionState(_position = 55),
            //ROW 8th
            PositionState(_position = 56),PositionState(_position = 57, _pieceColorId = WHITE, _isInDomain = true),
            PositionState(_position = 58),PositionState(_position = 59, _pieceColorId = WHITE, _isInDomain = true),
            PositionState(_position = 60),PositionState(_position = 61, _pieceColorId = WHITE, _isInDomain = true),
            PositionState(_position = 62),PositionState(_position = 63, _pieceColorId = WHITE, _isInDomain = true)

        )
    }
}