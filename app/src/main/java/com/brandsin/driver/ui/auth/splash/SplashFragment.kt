package com.brandsin.driver.ui.auth.splash

import android.content.Intent
import android.graphics.PixelFormat
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.brandsin.driver.R
import com.brandsin.driver.databinding.AuthFragmentSplashBinding
import com.brandsin.driver.ui.activity.auth.AuthActivity
import com.brandsin.driver.ui.activity.auth.BaseAuthFragment
import com.brandsin.driver.ui.activity.home.HomeActivity
import com.brandsin.driver.utils.PrefMethods
import com.google.android.exoplayer2.SimpleExoPlayer
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File

class SplashFragment : BaseAuthFragment(), MediaPlayer.OnCompletionListener
{
    lateinit var binding: AuthFragmentSplashBinding

    private var player: SimpleExoPlayer? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.auth_fragment_splash,
            container,
            false
        )
//
//                lifecycleScope.launch {
//            delay(2000)
//
//            when {
//                PrefMethods.getLoginState() -> {
//                    requireActivity().startActivity(Intent(requireActivity(), HomeActivity::class.java))
//                    requireActivity().finishAffinity()
//                }
//                else -> {
//                    findNavController().navigate(R.id.splash_to_login_ways)
//                }
//            }
//        }
//
//        (requireActivity() as AuthActivity).setBarName(getString(R.string.login))
        return binding.root


    }
/*
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val path = "android.resource://" + requireActivity().packageName.toString() + "/" + R.raw.splash

        binding.vedioSplash.setVideoURI(Uri.parse(path),false)

        binding.vedioSplash.start()
        binding.vedioSplash.setOnCompletionListener {
            when {
                PrefMethods.getLoginState() -> {
                    requireActivity().startActivity(Intent(requireActivity(), HomeActivity::class.java))
                    requireActivity().finishAffinity()
                }
                else -> {
                    findNavController().navigate(R.id.splash_to_login_ways)
                }
            }
        }
    }
*/
override fun onStart() {
    super.onStart()
    startVideo()

}

    private fun startVideo() {
        val url: Uri =
            Uri.parse("android.resource://" + requireActivity().packageName.toString() + "/" + R.raw.splash)
        val file = File(url.toString())
        val mc = MediaController(context)
        requireActivity().window.setFormat(PixelFormat.TRANSLUCENT)
        binding.videoView1.setMediaController(null)
        binding.videoView1.requestFocus();
        binding.videoView1.setVideoURI(url)
        binding.videoView1.setOnCompletionListener(this)
        binding.videoView1.start()
    }

    override fun onCompletion(p0: MediaPlayer?) {
        lifecycleScope.launch {
            //delay(2000)
            when {
                PrefMethods.getLoginState() -> {
                    requireActivity().startActivity(Intent(requireActivity(), HomeActivity::class.java))
                    requireActivity().finishAffinity()
                }
                else -> {
                    findNavController().navigate(R.id.splash_to_login_ways)
                }
            }
        }

    }

}