package com.example.checkersgame.positionstate

import com.example.checkersgame.constants.Constants.Companion.NO_PIECE

class PositionState(
    private val _position: Int,
    private var _pieceColorId: Int = NO_PIECE,
    private var _isSelected: Boolean = false,
    private var _isPointOn: Boolean = false,
    private var _isKing: Boolean = false,
    private val _isInDomain: Boolean = false,
    private var _canCapture: Boolean = false,
    private var _canMove: Boolean = false,
    private var _justCaptured: Boolean = false
) {

    val position: Int
        get() = _position

    var pieceColorId: Int
        get() = _pieceColorId
        set(value) {_pieceColorId = value}

    var isSelected: Boolean
        get() = _isSelected
        set(value) {_isSelected = value}

    var isPointOn: Boolean
        get() = _isPointOn
        set(value) {_isPointOn = value}

    var isKing: Boolean
        get() = _isKing
        set(value) {_isKing = value}

    val isInDomain: Boolean
        get() = _isInDomain

    var canCapture: Boolean
        get() = _canCapture
        set(value) {_canCapture = value}

    var canMove: Boolean
        get() = _canMove
        set(value) {_canMove = value}

    var justCapture: Boolean
        get() = _justCaptured
        set(value) {_justCaptured = value}
}