package com.example.roomdatabase.fragment.update

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
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

        //Add menu
        setHasOptionsMenu(true)

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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_delete) {
            deleteUser()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteUser() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            mUserViewModel.deleteUser(args.currentUser)
            Toast.makeText(
                requireContext(),
                "Successfully removed: ${args.currentUser.firstname}",
                Toast.LENGTH_LONG
            ).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }

        builder.setNegativeButton("No") { _, _ ->
        }

        builder.setTitle("Delete ${args.currentUser.firstname}?")
        builder.setMessage("Are you sure you want to delete ${args.currentUser.firstname}?")
        builder.create().show()
    }
}