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
    private lateinit var SaveButton: Button
    private lateinit var PlayerField : TextView
    private lateinit var ComputerField: TextView

    //переменные нужные для работы  логики игры
    private var playerScore: Int = 0
    private var gameCount: Int = 0
    private var winCount: Int = 0
    private var loseCount: Int = 0
    private var drwnCount: Int = 0
    private var mCount: Int = 0

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
            val args= Bundle()
            val playerName = playerData.name
            args.putString(
                PLAYERNAME, playerName)

            fragment.arguments=args
            return fragment
        }


    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        val view=inflater.inflate(R.layout.fragment_game, container, false)
        // присваеваем значения имени игрока
        val playerName = arguments!!.getString(PLAYERNAME) as String


//        инитим текстовые поля
        PlayerField= view.findViewById(R.id.playerField)
        ComputerField=view.findViewById(R.id.computerField)

        PlayerField.text=playerName
//        инитим кнопки
        MoreButton=view.findViewById(R.id.moreButton)
        CheckButton=view.findViewById(R.id.checkButton)
        RefreshButton=view.findViewById(R.id.refreshBtn)
        SaveButton=view.findViewById(R.id.saveBtn)
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
//        Saving data of this player
        SaveButton.setOnClickListener {
//            make playerData class
            val playerData = PlayerData(0, gameCount, playerName, playerScore, winCount , loseCount, drwnCount)
            toActivity(playerData)
        }



        return view
    }
// Функция для работы вытягивания карт из колоды
    private fun MoreForPlayer(){
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

    private fun CheckPlayerDeck(){

        if (PlayerField.text.toString() == arguments!!.getString(PLAYERNAME) as String
                || PlayerField.text.toString() == getString(R.string.WinMassage)
                || PlayerField.text == getString(R.string.LoseMassage)) {

            RefreshFun()
        } else {
            val DillerResult : Int = GameLogickAI()
            val playerRes = PlayerField.text.toString().toInt()
//              win Branch
            if ((playerRes > DillerResult && playerRes < 22)
                || (DillerResult > 21 && playerRes < 22)) {
                PlayerField.text = getString(R.string.WinMassage)
                ComputerField.text = DillerResult.toString()
                playerScore+=3
                winCount++
//                Lose branch
            } else if (playerRes < DillerResult || (DillerResult < 22 && playerRes > 21)) {
                PlayerField.text = getString(R.string.LoseMassage)
                ComputerField.text = DillerResult.toString()
                loseCount++
//                Drawn branch
            } else {
                PlayerField.text = getString(R.string.DrawnGame)
                ComputerField.text = DillerResult.toString()
                playerScore+=1
                drwnCount++
            }
        }
        gameCount++

    }

    private fun GameLogickAI(): Int {
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

    private fun RefreshFun(){
        ComputerField.text=getString(R.string.GameName)
        PlayerField.text= arguments!!.getString(PLAYERNAME)
        mCount=0
        gameCount=0
        winCount=0
        drwnCount=0
        loseCount=0
        playerScore=0
    }

//    special function to send data to activity and in activity data will work with database
    fun toActivity(playerData: PlayerData){
        val activity = activity
        if (activity != null && !activity.isFinishing() && activity is MainActivity) {
            activity.fromGameFragmentData(playerData)
        }
    }



}// Required empty public constructor