package com.example.wellme.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.wellme.R

class ProfileFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // coming soon
        showActivityDialog()
    }

    private fun showActivityDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.profile_fragment_alert)
            .setPositiveButton(getString(R.string.back_to_home)) { _, _ ->
                parentFragmentManager.popBackStack()
            }
            .show()
    }
}