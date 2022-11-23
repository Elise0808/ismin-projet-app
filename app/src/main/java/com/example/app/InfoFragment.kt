package com.example.app

import android.os.Bundle
import android.text.method.LinkMovementMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

/**
 * Fragment that displays information about the app.
 */
class InfoFragment : Fragment() {

    private lateinit var txtURL : TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var rootView : View = inflater.inflate(R.layout.fragment_info, container, false)

        txtURL = rootView.findViewById(R.id.f_info_txv_urldata)

        // Add hyperlink
        txtURL.movementMethod = LinkMovementMethod.getInstance()

        return rootView
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            InfoFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}