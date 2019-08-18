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

    //переменные нужные для работы  логики игры
    private var PlayerScore: Byte = 0
    private var DillerScore: Byte = 0
    private var mCount: Byte = 0

    companion object{

        private const val PLAYERNAME = "model"

        fun makeGameFragment(playerName: String):GameFragment{
            val args= Bundle()
            args.putString(
                PLAYERNAME, playerName)
            val fragment = GameFragment()
            fragment.arguments=args
            return fragment
        }

        fun makeGameFragment(playerData: PlayerData):GameFragment{
            val fragment = GameFragment()
//            ToDo update logic of adding arguments
//            val args =
//            fragment.arguments =
            return fragment
        }


    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        val view=inflater.inflate(R.layout.fragment_game, container, false)
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
           RefreshFun()
        }


// присваеваем значения имени игрока
        val model = arguments!!.getString(PLAYERNAME) as String
        PlayerField.text=model
        return view
    }
// Функция для работы вытягивания карт из колоды
//    ToDo поменять логику работы, чтобы данные хранились в переменных, а не в полях
    fun MoreForPlayer(){
        if (PlayerField.text.toString() == getString(R.string.WinMassage)
                || PlayerField.text.toString() == getString(R.string.LoseMassage)){
            RefreshFun()
        } else {
            val cardFromDeck = Random.nextInt(2, 12)
            if (mCount < 1) {
                PlayerField.text = cardFromDeck.toString()
            } else {
                val supCount: Int = PlayerField.text.toString().toInt()
                PlayerField.text = (cardFromDeck + supCount).toString()
            }
            mCount++
        }
    }
//  Функция проверки очков

    fun CheckPlayerDeck(){
        if (PlayerField.text.toString() == arguments!!.getString(PLAYERNAME) as String
                || PlayerField.text.toString() == getString(R.string.WinMassage)
                || PlayerField.text == getString(R.string.LoseMassage)) {

            RefreshFun()
        } else {
            val DillerResult : Int = GameLogickAI()
            if ((PlayerField.text.toString().toInt() > DillerResult && PlayerField.text.toString().toInt() < 22)
                    && DillerResult < 22) {
                PlayerField.text = getString(R.string.WinMassage)
                ComputerField.text = DillerResult.toString()
            } else if (PlayerField.text.toString().toInt() < DillerResult || (DillerResult < 22 && PlayerField.text.toString().toInt() > 21)) {
                PlayerField.text = getString(R.string.LoseMassage)
                ComputerField.text = DillerResult.toString()
            } else {
                PlayerField.text = getString(R.string.DrawnGame)
            }

        }

    }

    fun GameLogickAI(): Int {
        var cardFromDeck = Random.nextInt(2,12)
        var BlackJackDeck = Random.nextInt(2,12) + cardFromDeck
        if (BlackJackDeck < 12) {
            cardFromDeck = Random.nextInt(2,12)
            BlackJackDeck += cardFromDeck
            return BlackJackDeck
        } else{
            return BlackJackDeck
        }
    }

    fun RefreshFun(){
        ComputerField.text=getString(R.string.GameName)
        PlayerField.text= arguments!!.getString(PLAYERNAME)
        mCount=0
    }



}// Required empty public constructor