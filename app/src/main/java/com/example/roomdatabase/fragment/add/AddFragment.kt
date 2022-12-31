package com.example.roomdatabase.fragment.add

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.roomdatabase.R
import com.example.roomdatabase.model.User
import com.example.roomdatabase.viewmodel.UserViewModel

class AddFragment : Fragment() {

    private lateinit var mUserViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add, container, false)

        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        view.findViewById<Button>(R.id.buttonAdd).setOnClickListener {
            insertDatabase(view)

        }

        return view
    }

    private fun insertDatabase(view: View) {
        val firstName = view.findViewById<EditText>(R.id.tvFirstName).text.toString()
        val lastName = view.findViewById<EditText>(R.id.tvLastName).text.toString()
        val age = view.findViewById<EditText>(R.id.tvAge).text.toString()

        if (inputCheck(firstName, lastName, age)) {
            //Create User object
            val user = User(0, firstName, lastName, Integer.parseInt(age))
            //Add Data to Database
            mUserViewModel.addUser(user)
            Toast.makeText(requireContext(), "Successfully added!", Toast.LENGTH_LONG).show()
            //Navigate Back
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        } else {
            Toast.makeText(requireContext(), "please fill out all fields.", Toast.LENGTH_LONG)
                .show()
        }
    }

    private fun inputCheck(firstName: String, lastName: String, age: String): Boolean {
        return !(firstName.isEmpty() || lastName.isEmpty() || age.isEmpty())
    }

}