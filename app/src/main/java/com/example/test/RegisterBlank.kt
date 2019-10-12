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

/**
 * A simple [Fragment] subclass.
 */



class RegisterBlank : Fragment() {

    private var listener: AcceptPlayerName? = null

    companion object {
        fun newInstance(): RegisterBlank{
            return RegisterBlank()
        }
    }

//  крашит приложение, и не залетает в исключение, хз что
    override fun onAttach(context: Context?) {
        super.onAttach(context)

//    стандартный генерируемый код не работает, потому что AcceptPlayerName не импломентируемо
//        if (context is AcceptPlayerName) {
//            listener = context
//        } else {
//            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
//        }
        this.listener = context as? AcceptPlayerName

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_register_blankt, container, false)
        val AcceptButton: Button = view.findViewById(R.id.AcceptButton)
        val choseExistPlaerButton: Button = view.findViewById(R.id.choseExistButton)
        val PlayerNameInput: EditText = view.findViewById(R.id.PlayerNameInput)

        AcceptButton.setOnClickListener{
            listener?.AcceptPlayerName(PlayerNameInput.text.toString())
            Toast.makeText(view.context, "button_pressed", Toast.LENGTH_SHORT).show()
        }

        choseExistPlaerButton.setOnClickListener {
            listener?.showAllPlayers()
        }


        return view
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }



    interface AcceptPlayerName {
        fun AcceptPlayerName(personName: String)
        fun showAllPlayers()
    }




}// Required empty public constructor