package app.inisiator.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.initialization.InitializationStatus
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener
import com.google.android.gms.ads.reward.RewardItem
import com.google.android.gms.ads.reward.RewardedVideoAd
import com.google.android.gms.ads.reward.RewardedVideoAdListener

class RewardAdActivity : AppCompatActivity(), RewardedVideoAdListener {

    private lateinit var mRewardedVideoAd: RewardedVideoAd

    companion object {
        const val TAG = "Reward Activity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reward_ad)

        MobileAds.initialize(this) { p0 -> logD(TAG, p0.toString()) }
        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this)
        mRewardedVideoAd.rewardedVideoAdListener = this
        loadRewardedVideoAd()
        if (mRewardedVideoAd.isLoaded) {
            mRewardedVideoAd.show()
        }
    }

    private fun loadRewardedVideoAd() {
        mRewardedVideoAd.loadAd(
                "ca-app-pub-8284020667834710~1009522972",
                AdRequest.Builder().build()
        )
    }

    override fun onRewarded(p0: RewardItem?) {
        logD(TAG, "Here's your reward")
        logD(TAG, "You received ${p0!!.amount} ${p0.type}")
        logD(TAG, p0.type)
        logD(TAG, p0.amount.toString())
        logD(TAG, p0.toString())
        toast("Ini hadiah kamu!!")
    }

    override fun onRewardedVideoAdClosed() {
        logD(TAG, "onRewardedVideoAdClosed")
    }

    override fun onRewardedVideoAdLoaded() {
        logD(TAG, "onRewardedVideoAdLoaded")
        mRewardedVideoAd.show()
    }

    override fun onRewardedVideoAdLeftApplication() {
        logD(TAG, "onRewardedVideoAdLeftApplication")
    }

    override fun onRewardedVideoAdOpened() {
        logD(TAG, "onRewardedVideoAdOpened")
    }

    override fun onRewardedVideoCompleted() {
        logD(TAG, "onRewardedVideoCompleted")
    }

    override fun onRewardedVideoStarted() {
        logD(TAG, "onRewardedVideoStarted")
    }

    override fun onRewardedVideoAdFailedToLoad(p0: Int) {
        logE(TAG, "onRewardedVideoAdFailedToLoad")
        logE(TAG, "$p0")
    }

    override fun onPause() {
        super.onPause()
        mRewardedVideoAd.pause(this)
    }

    override fun onResume() {
        super.onResume()
        mRewardedVideoAd.resume(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        mRewardedVideoAd.destroy(this)
    }
}
