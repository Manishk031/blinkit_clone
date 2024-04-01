package com.zoro.blinkitclone.auth

import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.zoro.blinkitclone.R
import com.zoro.blinkitclone.Utils
import com.zoro.blinkitclone.databinding.FragmentSinginBinding


class SinginFragment : Fragment() {

    private lateinit var binding: FragmentSinginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        setStatusBarColor()
        getUserNumber()
        onContinueButtonClick()
        return binding.root
    }

    private fun setStatusBarColor() {

        activity?.window?.apply {
            val statusBarColors = ContextCompat.getColor(requireContext(), R.color.yellow)
            statusBarColor = statusBarColors
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }
        }
    }


    private fun getUserNumber() {
        binding.etUserNumber.addTextChangedListener(object :TextWatcher
        {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }

            override fun onTextChanged(number: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val len = number?.length
                if(len == 10){
                    binding.btnContinue.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.green)) }
                else {
                    binding.btnContinue.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.grey)) }
            }

            override fun afterTextChanged(p0: Editable?) { }
            })
    }
    private fun onContinueButtonClick() {
        //Check the number is empty or not
        binding.btnContinue.setOnClickListener{
            val number = binding.etUserNumber.text.toString()
            if(number.isEmpty() || number.length != 10){
                Utils.showToast(requireContext(), "Please enter valid number")
            }
            else {
                val bundle = Bundle()
                bundle.putString("number", number)
                findNavController().navigate(R.id.action_singinFragment_to_OTPFragment,bundle)

            }
        }
    }



}