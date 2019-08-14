package com.example.test

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import kotlinx.android.synthetic.main.fragment_register_blankt.*

import com.example.test.DBHalper

/**
 * A simple [Fragment] subclass.
 */



class RegisterBlank : Fragment() {

    private var listener: AcceptPlaerName? = null

    companion object {
        fun newInstance(): RegisterBlank{
            return RegisterBlank()
        }
    }

//  крашит приложение, и не залетает в исключение, хз что
    override fun onAttach(context: Context?) {
        super.onAttach(context)
//    стандартный генерируемый код не работает, лучше использовать что-то другое
//        if (context is AcceptPlaerName) {
//            listener = context
//        } else {
//            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
//        }
        this.listener = context as? AcceptPlaerName

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_register_blankt, container, false)
        val AcceptButton: Button = view.findViewById(R.id.AcceptButton)
        val PlayerNameInput: EditText = view.findViewById(R.id.PlayerNameInput)

        AcceptButton.setOnClickListener{
            listener!!.AcceptPlayerName(PlayerNameInput.text.toString())
            Toast.makeText(view.context, "button_pressed", Toast.LENGTH_SHORT).show()

            GetPlayerName()
//            if ()

        }

        return view
    }

    fun GetPlayerName(){
        val PlayerName = PlayerNameInput.text.toString()
        listener?.AcceptPlayerName(PlayerName)
    }

    interface AcceptPlaerName {
        fun AcceptPlayerName(personName: String)
    }




}// Required empty public constructor