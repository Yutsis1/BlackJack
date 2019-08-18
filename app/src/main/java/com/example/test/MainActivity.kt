package com.example.test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity(), RegisterBlank.AcceptPlaerName {
    private val activity = this@MainActivity
    private var dbHalper: DBHalper = DBHalper(activity)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


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
            val newPlayer: PlayerData = PlayerData(
                id = dbHalper.getLastID() + 1,
                GameCount = 0,
                name = personName,
                score = 0,
                wins = 0,
                loses = 0,
                drawns = 0
            )
            dbHalper.addPlayer(newPlayer)

            supportFragmentManager
                .beginTransaction()
                .replace(R.id.root_layout, detailFragment, "Game")
                .addToBackStack(null)
                .commit()
        }


    }


}
