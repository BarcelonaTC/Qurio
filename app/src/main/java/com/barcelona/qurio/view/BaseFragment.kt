package com.barcelona.qurio.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseFragment<B : ViewDataBinding> : Fragment() {
    abstract val layoutIdFragment: Int
    private lateinit var _binding: B
    protected val binding: B
        get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, layoutIdFragment, container, false)
        _binding.apply {
            return root
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding.unbind()
    }

    protected fun setTitle(visibility: Boolean, title: String? = null) {
        if (visibility) {
            (activity as AppCompatActivity).supportActionBar?.show()
            title?.let {
                (activity as AppCompatActivity).supportActionBar?.title = it
            }
        } else {
            (activity as AppCompatActivity).supportActionBar?.hide()
        }
    }
}