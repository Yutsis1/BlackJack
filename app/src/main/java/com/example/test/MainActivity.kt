package com.example.test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity(), RegisterBlank.AcceptPlaerName {

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
        val detailFragment = GameFragment.newInstance(personName)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.root_layout, detailFragment, "Game")
            .addToBackStack(null)
            .commit()

    }


}
