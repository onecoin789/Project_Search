package com.example.project_search

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.project_search.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)





        binding.apply {
            btnMainBottom1.setOnClickListener {
                setFragment(SearchFragment())
            }
            btnMainBottom2.setOnClickListener {
                setFragment(SaveFragment())
            }
            setFragment(SearchFragment())
        }





    }

    private fun setFragment(frag : Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.layout_Frame, frag)
            .setReorderingAllowed(true)
            .addToBackStack("")
            .commit()
    }


}