package com.trifingzw.emodule.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.trifingzw.emodule.activity.GodotEngineActivity
import com.trifingzw.emodule.adapter.BtlAdapter
import com.trifingzw.emodule.adapter.SpacesItemDecoration
import com.trifingzw.emodule.databinding.ContentBtlBinding
import com.trifingzw.emodule.helper.Constants
import com.trifingzw.emodule.helper.FileData
import com.trifingzw.emodule.helper.getBtlPath
import com.trifingzw.emodule.helper.getFileData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

class BtlFragmentViewModel(private val workPath: String, private val filter: String) : ViewModel() {
    val btlList = MutableLiveData<List<FileData>>()

    init {
        loadBtlList()
    }

    private fun loadBtlList() {
        viewModelScope.launch {
            val dataList = withContext(Dispatchers.IO) {
                File(getBtlPath(workPath)).listFiles()?.filter { it.name.contains(filter) }?.map {
                    getFileData(it)
                } ?: emptyList()
            }
            btlList.postValue(dataList)
        }
    }
}

@Suppress("UNCHECKED_CAST")
class BtlFragmentViewModelFactory(private val workPath: String, private val filter: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return BtlFragmentViewModel(workPath, filter) as T
    }
}

class BtlFragment(private val workPath: String, val map: Map.Entry<String, String>) : Fragment() {
    private lateinit var binding: ContentBtlBinding
    private lateinit var recyclerView: RecyclerView

    private val viewModel: BtlFragmentViewModel by viewModels {
        BtlFragmentViewModelFactory(workPath, map.value)
    }

    private val adapter by lazy {
        BtlAdapter(arrayListOf()) {
            File(Constants.WORK_TXT_FILE_PATH).writeText(workPath)
            File(Constants.BTL_TXT_FILE_PATH).writeText(it.path)
            context?.startActivity(Intent(context, GodotEngineActivity::class.java))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = ContentBtlBinding.inflate(inflater, container, false)
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.addItemDecoration(SpacesItemDecoration(32))
        recyclerView.adapter = adapter
        viewModel.btlList.observe(viewLifecycleOwner) { list ->
            adapter.refresh(list)
        }
        return binding.root
    }

    companion object {
        // 工厂方法来创建新实例
        fun newInstance(workPath: String, map: Map.Entry<String, String>): BtlFragment {
            return BtlFragment(workPath, map)
        }
    }
}