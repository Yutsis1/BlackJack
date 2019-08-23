package com.example.test

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class MainActivity : AppCompatActivity(), RegisterBlank.AcceptPlaerName {
    private lateinit var dbHalper: DBHalper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        dbHalper = DBHalper(this as Context)


        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.root_layout, RegisterBlank.newInstance(), "Register")
                .commit()
        }

    }

    override fun AcceptPlayerName(personName: String){
        if (dbHalper.checkExistingPlayer(personName)){
            val user: PlayerData = dbHalper.getPlayer(personName)
            val detailFragment = GameFragment.makeGameFragment(user)

            supportFragmentManager
                .beginTransaction()
                .replace(R.id.root_layout, detailFragment, "Game")
                .addToBackStack(null)
                .commit()
        } else {
            val detailFragment = GameFragment.makeGameFragment(personName)
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.root_layout, detailFragment, "Game")
                .addToBackStack(null)
                .commit()
            val newPlayer = PlayerData(
                id = 0 ,
                GameCount = 0,
                name = personName,
                score = 0,
                wins = 0,
                loses = 0,
                drawns = 0
            )
            dbHalper.addPlayer(newPlayer)


        }

    }

    fun fromFragmentData(playerData: PlayerData){

    }


}
