package com.mycorp.twitchapprxjava.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.Toast
import by.kirich1409.viewbindingdelegate.viewBinding
import com.mycorp.twitchapprxjava.R
import com.mycorp.twitchapprxjava.databinding.FragmentRatingBinding

class RatingFragment : Fragment() {

    private val ratingFragmentViewBinding:FragmentRatingBinding by viewBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rating, container, false)
    }

    override fun onStart() {
        super.onStart()
        with(ratingFragmentViewBinding){
            ratingBarDefault.onRatingBarChangeListener =
                RatingBar.OnRatingBarChangeListener { _, rating, _ ->
                    Toast.makeText(
                        context, "рейтинг: $rating",
                        Toast.LENGTH_LONG
                    ).show()
                }

            sendReportBtn.setOnClickListener{
                createGamesListFragment()
            }
            backBtn.setOnClickListener{createGamesListFragment()}
        }
    }

    private fun createGamesListFragment() {
        val ft = requireActivity().supportFragmentManager.beginTransaction()
        val gamesListFragment = GamesListFragment()
        ft.replace(R.id.fragment_layout, gamesListFragment)
        ft.commit()
    }
}