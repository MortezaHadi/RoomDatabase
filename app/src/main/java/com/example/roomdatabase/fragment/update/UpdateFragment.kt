package com.example.roomdatabase.fragment.update

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.roomdatabase.R
import com.example.roomdatabase.model.User
import com.example.roomdatabase.viewmodel.UserViewModel

class UpdateFragment : Fragment() {

    private lateinit var mUserViewModel: UserViewModel

    private val args by navArgs<UpdateFragmentArgs>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_update, container, false)

        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        view.findViewById<EditText>(R.id.updateFirstName).setText(args.currentUser.firstname)
        view.findViewById<EditText>(R.id.updateLastName).setText(args.currentUser.lastName)
        view.findViewById<EditText>(R.id.updateAge).setText(args.currentUser.age.toString())

        view.findViewById<Button>(R.id.updateButtonAdd).setOnClickListener {
            getData(view)
        }


        return view
    }

    private fun getData(view: View) {
        val firstName = view.findViewById<EditText>(R.id.updateFirstName).text.toString()
        val lastName = view.findViewById<EditText>(R.id.updateLastName).text.toString()
        val age = view.findViewById<EditText>(R.id.updateAge).text.toString()

        if (inputCheck(firstName, lastName, age)) {
            //Create User object
            val user = User(args.currentUser.id, firstName, lastName, Integer.parseInt(age))
            //Add Data to Database
            mUserViewModel.updateUser(user)
            Toast.makeText(requireContext(), "Successfully updated!", Toast.LENGTH_LONG).show()
            //Navigate Back
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        } else {
            Toast.makeText(requireContext(), "please fill out all fields.", Toast.LENGTH_LONG)
                .show()
        }
    }

    private fun inputCheck(firstName: String, lastName: String, age: String): Boolean {
        return !(firstName.isEmpty() || lastName.isEmpty() || age.isEmpty())
    }
}