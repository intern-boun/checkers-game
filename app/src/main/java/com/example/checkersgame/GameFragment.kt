package com.example.checkersgame

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.checkersgame.constants.Constants.Companion.BLACK_KEEP_CAPTURE_MESSAGE
import com.example.checkersgame.constants.Constants.Companion.BLACK_KEEP_CAPTURE_TYPE
import com.example.checkersgame.constants.Constants.Companion.BLACK_MOVE_MESSAGE
import com.example.checkersgame.constants.Constants.Companion.BLACK_MOVE_TYPE
import com.example.checkersgame.constants.Constants.Companion.BLACK_WON_MESSAGE
import com.example.checkersgame.constants.Constants.Companion.BLACK_WON_TYPE
import com.example.checkersgame.constants.Constants.Companion.NO_TOAST
import com.example.checkersgame.constants.Constants.Companion.PIECE_CAN_NOT_MOVE_MESSAGE
import com.example.checkersgame.constants.Constants.Companion.PIECE_CAN_NOT_MOVE_TYPE
import com.example.checkersgame.constants.Constants.Companion.THERE_IS_CAPTURE_MESSAGE
import com.example.checkersgame.constants.Constants.Companion.THERE_IS_CAPTURE_TYPE
import com.example.checkersgame.constants.Constants.Companion.WHITE_KEEP_CAPTURE_MESSAGE
import com.example.checkersgame.constants.Constants.Companion.WHITE_KEEP_CAPTURE_TYPE
import com.example.checkersgame.constants.Constants.Companion.WHITE_MOVE_MESSAGE
import com.example.checkersgame.constants.Constants.Companion.WHITE_MOVE_TYPE
import com.example.checkersgame.constants.Constants.Companion.WHITE_WON_MESSAGE
import com.example.checkersgame.constants.Constants.Companion.WHITE_WON_TYPE
import com.example.checkersgame.databinding.GameFragmentBinding

class GameFragment : Fragment() {

    private val viewModel: GameViewModel by lazy {
        ViewModelProvider(this).get(GameViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = GameFragmentBinding.inflate(inflater)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        //Adapter
        binding.gridRecyclerView.adapter = GridAdapter(GridAdapter.OnClickListener{

            //Whole click process is in the function clickJob which is in ViewModel
            val toastType = viewModel.clickJob(it)


            when(toastType){
                NO_TOAST -> Unit    //Nothing happens

                WHITE_MOVE_TYPE -> Toast.makeText(context, WHITE_MOVE_MESSAGE, Toast.LENGTH_SHORT).show()
                BLACK_MOVE_TYPE -> Toast.makeText(context, BLACK_MOVE_MESSAGE, Toast.LENGTH_SHORT).show()

                WHITE_WON_TYPE -> Toast.makeText(context, WHITE_WON_MESSAGE, Toast.LENGTH_LONG).show()
                BLACK_WON_TYPE -> Toast.makeText(context, BLACK_WON_MESSAGE, Toast.LENGTH_LONG).show()

                WHITE_KEEP_CAPTURE_TYPE -> Toast.makeText(context, WHITE_KEEP_CAPTURE_MESSAGE, Toast.LENGTH_SHORT).show()
                BLACK_KEEP_CAPTURE_TYPE -> Toast.makeText(context, BLACK_KEEP_CAPTURE_MESSAGE, Toast.LENGTH_SHORT).show()

                THERE_IS_CAPTURE_TYPE -> Toast.makeText(context, THERE_IS_CAPTURE_MESSAGE, Toast.LENGTH_SHORT).show()

                PIECE_CAN_NOT_MOVE_TYPE -> Toast.makeText(context, PIECE_CAN_NOT_MOVE_MESSAGE, Toast.LENGTH_SHORT).show()
            }
        })

        return binding.root
    }
    
}