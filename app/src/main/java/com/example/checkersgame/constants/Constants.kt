package com.example.checkersgame.constants

import com.example.checkersgame.R

class Constants {
    companion object {
        const val BLACK = R.drawable.black
        const val WHITE = R.drawable.white
        const val NO_PIECE = -1
        const val NO_SUCH_POSITION = -1

        //Move
        const val UR_1 = -7     //UP-RIGHT
        const val UL_1 = -9     //UP-LEFT
        const val DR_1 = 9
        const val DL_1 = 7
        //Capture
        const val UR_2 = -14
        const val UL_2 = -18
        const val DR_2 = 18
        const val DL_2 = 14

        //Toast Types
        const val NO_TOAST = -1
        const val WHITE_MOVE_TYPE = 0
        const val BLACK_MOVE_TYPE = 1

        const val WHITE_WON_TYPE = 2
        const val BLACK_WON_TYPE = 3

        const val WHITE_KEEP_CAPTURE_TYPE = 4
        const val BLACK_KEEP_CAPTURE_TYPE = 5

        const val THERE_IS_CAPTURE_TYPE = 6

        const val PIECE_CAN_NOT_MOVE_TYPE = 7

        //Toast Messages
        const val WHITE_MOVE_MESSAGE: String = "IT IS WHITE'S MOVE"
        const val BLACK_MOVE_MESSAGE: String = "IT IS BLACK'S MOVE"

        const val WHITE_WON_MESSAGE: String = "WHITE WON!!!"
        const val BLACK_WON_MESSAGE: String = "BLACK WON!!!"

        const val WHITE_KEEP_CAPTURE_MESSAGE: String = "PLEASE KEEP CAPTURE WITH WHITE"
        const val BLACK_KEEP_CAPTURE_MESSAGE: String = "PLEASE KEEP CAPTURE WITH BLACK"

        const val THERE_IS_CAPTURE_MESSAGE = "THERE IS A CAPTURE"
        const val PIECE_CAN_NOT_MOVE_MESSAGE: String = "THIS PIECE CAN NOT MOVE"

    }
}