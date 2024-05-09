package com.example.project_search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.project_search.adapter.SearchAdapter
import com.example.project_search.adapter.SearchListAdapter
import com.example.project_search.data.KakaoImageData
import com.example.project_search.databinding.FragmentSaveBinding
import com.example.project_search.viewmodel.MainViewModel
import com.google.gson.Gson


class SaveFragment : Fragment() {

    private var _binding: FragmentSaveBinding? = null
    private val binding get() = _binding!!

    private val mainViewModel: MainViewModel by activityViewModels()
    private lateinit var searchAdapter: SearchListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSaveBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchAdapter = SearchListAdapter()
        binding.recyclerview.apply {
            adapter = searchAdapter
            layoutManager = GridLayoutManager(context, 2)
        }

        val pref = requireContext().getSharedPreferences("favorite_prefs", 0)
        val allKeys = pref.all.keys

        for (key in allKeys) {
            val likedItemsJson = pref.getString(key, "")
            if (likedItemsJson != null) {
                val likedItems = Gson().fromJson(likedItemsJson, KakaoImageData::class.java)
                mainViewModel.addLiked(likedItems)
            }
        }

        mainViewModel.itemDataList.observe(viewLifecycleOwner, Observer {
            searchAdapter.submitList(it.toList())
        })

        searchAdapter.itemClick = object : SearchListAdapter.ItemClick {
            override fun onClick(view: View, position: Int) {
                if (position < searchAdapter.currentList.size) {
                    searchAdapter.currentList[position].isliked = false
                    mainViewModel.removeLiked(searchAdapter.currentList[position])
                    removeLiked(searchAdapter.currentList[position].thumbnailUrl)
                }
            }
        }
    }

    private fun removeLiked(thumbnailUrl: String) {
        val pref = requireContext().getSharedPreferences("favorite_pref", 0)
        val editor = pref.edit()
        val allData: Map<String, *> = pref.all
        for ((key, value ) in allData) {
            if (value is String && value.contains(thumbnailUrl)) {
                editor.remove(key)
            }
        }
        editor.apply()
    }
}