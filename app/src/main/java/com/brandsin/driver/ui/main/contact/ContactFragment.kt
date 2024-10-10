package com.brandsin.driver.ui.main.contact

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.brandsin.driver.R
import com.brandsin.driver.databinding.MenuFragmentContactBinding
import com.brandsin.driver.model.constants.Codes
import com.brandsin.driver.ui.activity.BaseHomeFragment
import com.brandsin.driver.utils.Utils

class ContactFragment : BaseHomeFragment(), Observer<Any?>
{
    private lateinit var binding : MenuFragmentContactBinding
    private lateinit var viewModel: ContactViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.menu_fragment_contact, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(ContactViewModel::class.java)
        binding.viewModel = viewModel

        binding.swipeLayout.setOnRefreshListener {
            binding.swipeLayout.isRefreshing = false
            viewModel.getPhoneNumber()
            viewModel.getSocialLinks()
        }

        viewModel.mutableLiveData.observe(viewLifecycleOwner, this)

        setBarName(getString(R.string.contact_us))
    }

    override fun onChanged(it: Any?)
    {
        if(it == null) return
        when (it) {
            Codes.SHOW_SOCIAL -> {
                if (viewModel.socialLinks.facebook==null){
                    binding.ivFacebook.visibility = View.GONE
                }
                if (viewModel.socialLinks.twitter==null){
                    binding.ivTwitter.visibility = View.GONE
                }
                if (viewModel.socialLinks.mail==null){
                    binding.ivGmail.visibility = View.GONE
                }
                if (viewModel.socialLinks.tikTok==null){
                    binding.ivTiktok.visibility = View.GONE
                }
            }
            Codes.PHONE_CLICKED -> {
                Utils.callPhone(requireActivity(), viewModel.obsPhoneNumber.get().toString())
            }
            Codes.TIKTOK_CLICKED ->{
                Utils.openLink(requireActivity(), viewModel.socialLinks.tikTok)
            }
            Codes.FACE_CLICKED -> {
                Utils.openLink(requireActivity(), "https://www.facebook.com/brandsin.sa")
            }
            Codes.GMAIL_CLICKED -> {
                Utils.openMail(requireActivity(), viewModel.socialLinks.mail.toString())
            }
            Codes.WHATSAPP_CLICKED -> {
                val url = "https://api.whatsapp.com/send?phone=" + viewModel.obsPhoneNumber.get().toString()
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse(url)
                startActivity(i)
            }
            Codes.TWITTER_CLICKED -> {
                Utils.openLink(requireActivity(), viewModel.socialLinks.twitter)
            }
        }
    }
}