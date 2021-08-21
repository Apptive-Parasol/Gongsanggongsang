package com.example.userapp.ui.main.community.post

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.userapp.R
import com.example.userapp.base.BaseFragment
import com.example.userapp.databinding.FragmentCommunityPhotoBinding
import com.example.userapp.ui.main.community.CommunityViewModel
import com.example.userapp.ui.main.community.write.CommunityPhotoRecyclerAdapter

class CommunityPhotoFragment : BaseFragment<FragmentCommunityPhotoBinding, CommunityViewModel>(){
    override lateinit var viewbinding: FragmentCommunityPhotoBinding

    override val viewmodel: CommunityViewModel by viewModels()


    override fun initViewbinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewbinding = FragmentCommunityPhotoBinding.inflate(inflater, container, false)
        return viewbinding.root
    }

    override fun initViewStart(savedInstanceState: Bundle?) {
        val listToArrayListPhotoUri : ArrayList<String> = arrayListOf()
        val uriArray : List<String>? = arguments?.getStringArray("photo_uri")?.toList()
        if (uriArray != null) {
            for(u in uriArray){
                listToArrayListPhotoUri .add(u)
            }
        }
        viewbinding.communityPhotoImage.run {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context).also { it.orientation = LinearLayoutManager.HORIZONTAL }
            adapter = CommunityPhotoRecyclerAdapter(listToArrayListPhotoUri )
        }

    }

    override fun initDataBinding(savedInstanceState: Bundle?) {
    }

    override fun initViewFinal(savedInstanceState: Bundle?) {
        viewbinding.communityPhotoBackButton.setOnClickListener {
            findNavController().navigate(R.id.action_communityPhotoFragment_pop)
        }

    }
}