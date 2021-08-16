package com.example.adminapp.ui.signin

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.adminapp.R
import com.example.adminapp.base.BaseSessionFragment
import com.example.adminapp.data.model.AdminStatus
import com.example.adminapp.databinding.FragmentSigninBinding
import com.example.adminapp.utils.WrapedDialogBasicOneButton
import com.example.adminapp.utils.hideKeyboard
import kotlinx.android.parcel.Parcelize

class SignInFragment : BaseSessionFragment<FragmentSigninBinding, SignInViewModel>(){
    override lateinit var viewbinding: FragmentSigninBinding
    override val viewmodel: SignInViewModel by viewModels()

    private var idInfoExist : Boolean = false
    private var pwdInfoExist : Boolean = false
    private var nextBtnAvailable : Boolean = false


    override fun initViewbinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewbinding = FragmentSigninBinding.inflate(inflater, container, false)
        return viewbinding.root
    }

    override fun initViewStart(savedInstanceState: Bundle?) {
        viewbinding.fragmentRootLayout.setOnClickListener { hideKeyboard(it) }
    }

    override fun initDataBinding(savedInstanceState: Bundle?) {
        viewmodel.run {
            onFailCheckingAdminData.observe(viewLifecycleOwner){ showErrorDialog(it) }
            adminStatusEvent.observe(viewLifecycleOwner) {
                when (it) {
                    AdminStatus.NOT_ADMIN -> showErrorDialog("로그인에 실패했습니다.\n아이디나 비밀번호를 확인해주세요.")
                    /*AdminStatus.WAIT_APPROVE -> showErrorDialog("가입 승인 대기중이에요.\n공간 관리자님께 문의해주세요.")*/
                    AdminStatus.ADMIN -> viewmodel.saveUserInfo()
                    else -> showErrorDialog("오류가 발생했습니다. 다시 시도하거나 문의해주세요.") }
            }
            onSuccessSaveUserInfo.observe(viewLifecycleOwner) { findNavController().navigate(R.id.action_signInFragment_to_mainFragment) }
        }
    }

    override fun initViewFinal(savedInstanceState: Bundle?) {
        viewbinding.run{
            findIdBtn.setOnClickListener {
                findNavController().navigate(SignInFragmentDirections.actionSignInFragmentToSignInFindInfoFragment(FindInfoType.ID))
            }
            findPwdBtn.setOnClickListener {
                findNavController().navigate(SignInFragmentDirections.actionSignInFragmentToSignInFindInfoFragment(FindInfoType.PWD))
            }
            loginBtn.setOnClickListener {
                if (viewmodel.checkForSignInInfo(getAdminId(), getAdminPwd())) viewmodel.sendSignInInfo(getAdminId(), getAdminPwd()) }

        }
    }

    private fun getAdminId() = viewbinding.editTextId.text.toString().trim()
    private fun getAdminPwd() = viewbinding.editTextPwd.text.toString().trim()

    private fun showErrorDialog(errorMessage : String){
        val dialog = WrapedDialogBasicOneButton(requireContext(),  errorMessage).apply {
            clickListener = object : WrapedDialogBasicOneButton.DialogButtonClickListener {
                override fun dialogClickListener() { dismiss() }
            }
        }
        showDialog(dialog, viewLifecycleOwner)
    }


}

@Parcelize
enum class FindInfoType() : Parcelable { ID, PWD }