package com.example.myapplication

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.viewmodel.AppViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel by viewModels<AppViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        with(binding) {
            send.setOnClickListener {
                mainViewModel.sendUser(name.text.toString(),age.text.toString().toInt())
                lifecycleScope.launch {
                    mainViewModel.id.collectLatest {
                        it.let { value ->
                            binding.result.text = value.data?.toString() ?: ""
                        }
                    }
                }
            }
            receive.setOnClickListener {
                mainViewModel.fetchUsers()
                lifecycleScope.launch {
                    mainViewModel.users.collect {
                        it.let { users ->
                            binding.result.text = users.data?.joinToString("\n") ?: ""
                        }
                    }
                }
            }
            get.setOnClickListener {
                lifecycleScope.launch {
                    mainViewModel.fetchUser(3)
                    mainViewModel.user.collect {
                        it.let { user ->
                            binding.result.text = user.data?.toString() ?: ""
                        }
                    }
                }
            }
        }

    }
}