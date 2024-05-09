package com.example.project_search

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.project_search.adapter.SearchAdapter
import com.example.project_search.adapter.SearchListAdapter
import com.example.project_search.data.ImageResponse
import com.example.project_search.data.KakaoImageData
import com.example.project_search.databinding.FragmentSearchBinding
import com.example.project_search.retrofit.RetrofitClient
import com.example.project_search.viewmodel.MainViewModel
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private lateinit var searchAdapter: SearchListAdapter
    private lateinit var searchWord: String
    private val mainViewModel: MainViewModel by activityViewModels()
    private var dataList = mutableListOf<KakaoImageData>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchAdapter = SearchListAdapter()
        binding.recyclerview.apply {
            adapter = searchAdapter
            layoutManager = GridLayoutManager(context, 2)

            loadSearchWord()

            mainViewModel.searchDataList.observe(viewLifecycleOwner, Observer {
                searchAdapter.submitList(it)
            })

            searchClick()
            clicked()


        }

    }

    private fun getSearchImg(searchWord: String) {
        lifecycleScope.launch {
            val responseList = withContext(Dispatchers.IO) {
                RetrofitClient.kakaoNetwork.searchImage(query = searchWord).documents
            }
            dataList.clear()
            dataList.addAll(responseList)
            searchAdapter.submitList(dataList)
        }
    }

    private fun searchClick() {
        binding.btnSearch.setOnClickListener {
            searchWord = binding.etMainSearch.text.toString()
            getSearchImg(searchWord)
            mainViewModel.addData(dataList)
            hideKeyboard()
            saveSearchWord(searchWord)
        }
    }

    private fun clicked() {
        searchAdapter.itemClick = object : SearchListAdapter.ItemClick {
            override fun onClick(view: View, position: Int) {
                if (!dataList[position].isliked) {
                    dataList[position].isliked = true
                    mainViewModel.addLiked(dataList[position])
                    saveLiked(position)
                } else {
                    dataList[position].isliked = false
                    mainViewModel.removeLiked(dataList[position])
                    removeLiked(dataList[position].thumbnailUrl)
                }
                searchAdapter.notifyItemChanged(position)
            }
        }
    }

    private fun saveLiked(position: Int) {
        val pref = requireContext().getSharedPreferences("favorte_pref", 0)
        val editor = pref?.edit()
        val likeDataJson = Gson().toJson(dataList[position])
        editor?.putString("FavoriteData$position", likeDataJson)
        editor?.apply()
    }

    private fun removeLiked(thumbnailUrl: String) {
        val pref = requireContext().getSharedPreferences("favorte_pref", 0)
        val editor = pref?.edit()
        val allData: Map<String, *> = pref.all
        for ((key, value) in allData) {
            if (value is String && value.contains(thumbnailUrl)) {
                editor?.remove(key)
            }
        }
        editor?.apply()
    }


    private fun saveSearchWord(searchWord: String) {
        val pref = requireContext().getSharedPreferences("pref", 0)
        val edit = pref.edit()
        edit.putString("lastSearchWord", searchWord)
        edit.apply()
    }

    private fun loadSearchWord() {
        val pref = requireContext().getSharedPreferences("pref", 0)
        binding.etMainSearch.setText(pref.getString("lastSearchWord", ""))
    }

    private fun hideKeyboard() {
        val inputMethodManager =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)

    }
}

