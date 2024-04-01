package com.zoro.blinkitclone.auth

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.zoro.blinkitclone.R
import com.zoro.blinkitclone.Utils
import com.zoro.blinkitclone.databinding.FragmentOTPBinding
import com.zoro.blinkitclone.viewmodel.AuthViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class OTPFragment : Fragment() {
   private  val viewModel : AuthViewModel by viewModels()
    private lateinit var binding:FragmentOTPBinding
    private lateinit var userNumber : String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ):View {
        binding = FragmentOTPBinding.inflate(layoutInflater)

        getUserNumber()
        sendOTP()
        onLoginButtonClicked()
        customizingEnteringOTP()
        onBackButtonClicked()
        return binding.root
    }

    private fun onLoginButtonClicked() {
        binding.btnLogin.setOnClickListener{
            Utils.showDialog(requireContext(), "Signing You")
            val editTexts = arrayOf(binding.et0tp1,binding.et0tp2,binding.et0tp3,binding.et0tp4,binding.et0tp5,binding.et0tp6)
          val otp =   editTexts.joinToString (""){it.text.toString()  }

            if(otp.length < editTexts.size){
                Utils.showToast(requireContext(), "Please enter right otp")
            }
            else {
                editTexts.forEach { it.text?.clear();it.clearFocus() }
                verifyOtp(otp)
            }
        }
    }

    private fun verifyOtp(otp: String) {


        viewModel.signInWithPhoneAuthCredential(otp,userNumber)
        lifecycleScope.launch {
            viewModel.isSignedInSuccessfully.collect{
                if(it)
                {
                    Utils.hideDialog()
                    Utils.showToast(requireContext(),"Logged In...")
                }
            }
        }
    }

    private fun sendOTP() {
        Utils.showDialog(requireContext(),"Sending OTP...")
    viewModel.apply {
        sendOTP(userNumber, requireActivity())
        lifecycleScope.launch {
            otpSent.collect{
                if(it)
                    Utils.hideDialog()
                Utils.showToast(requireContext(), "Otp Send..")
            }
        }
    }
    }

    private fun onBackButtonClicked() {
        binding.tbOtpFragment.setNavigationOnClickListener{
            findNavController().navigate(R.id.action_OTPFragment_to_singinFragment2)
        }
    }
    private fun customizingEnteringOTP() {
        val editTexts = arrayOf(binding.et0tp1,binding.et0tp2,binding.et0tp3,binding.et0tp4,binding.et0tp5,binding.et0tp6)
        for(i in editTexts.indices){
            editTexts[i].addTextChangedListener (object :TextWatcher{
                override fun beforeTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun afterTextChanged(s: Editable?) {
                 if(s?.length==1){
                     if(i < editTexts.size-1){
                         editTexts[i+1].requestFocus()

                     }

                 }
                    else if(s?.length==0)
                 {
                        if(i > 0){
                            editTexts[i-1].requestFocus()
                        }
                 }
                }
            })
        }

    }
    private fun getUserNumber() {
        val bundle = arguments
        userNumber = bundle?.getString("number").toString()


        binding.tvUserNumber.text = userNumber
    }


}