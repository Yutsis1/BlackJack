package com.example.test

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

import kotlin.random.Random




/**
 * A simple [Fragment] subclass.
 */

//ToDo перерисовать слой, написать логику игры
class GameFragment : Fragment() {



    private lateinit var MoreButton: Button
    private lateinit var CheckButton: Button
    private lateinit var RefreshButton: Button
    private lateinit var PlayerField : TextView
    private lateinit var ComputerField: TextView

    private var mCount: Int = 0

    companion object{

        private const val PLAYERNAME = "model"

        fun newInstance(PlayerName: String):GameFragment{
            val args= Bundle()
            args.putString(
                PLAYERNAME, PlayerName)
            val fragment = GameFragment()
            fragment.arguments=args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        val view=inflater!!.inflate(R.layout.fragment_game, container, false)
//        инитим текстовые поля
        PlayerField= view.findViewById(R.id.playerField)
        ComputerField=view.findViewById(R.id.computerField)

//        инитим кнопки
        MoreButton=view.findViewById(R.id.moreButton)
        CheckButton=view.findViewById(R.id.checkButton)
        RefreshButton=view.findViewById(R.id.refreshBtn)
//          настраиваем кликабильность
        MoreButton.setOnClickListener {
            MoreForPlayer()
        }
        CheckButton.setOnClickListener {
            CheckPlayerDeck()
        }
        RefreshButton.setOnClickListener {
            // ToDo Выяснить, почему эта штука возвращает hex код
            ComputerField.text=R.string.GameName.toString()
            PlayerField.text= arguments!!.getString(PLAYERNAME)
            mCount=0
        }



        val model = arguments!!.getString(PLAYERNAME) as String
        PlayerField.text=model
        return view
    }
// Функция для работы вытягивания карт из колоды
    fun MoreForPlayer(){
        val cardFromDeck = Random.nextInt(2, 12)
        if (mCount<1) {
            PlayerField.text = cardFromDeck.toString()
        } else{
            val supCount: Int = PlayerField.text.toString().toInt()
            PlayerField.text =(cardFromDeck+supCount).toString()
        }
        mCount++
    }
//  Функция проверки очков
    fun CheckPlayerDeck(){
        if (PlayerField.text.toString().toInt()>2 && PlayerField.text.toString().toInt()<22) {
            ComputerField.text="you win"
        } else{
            ComputerField.text="you lose"
        }

    }



}// Required empty public constructor