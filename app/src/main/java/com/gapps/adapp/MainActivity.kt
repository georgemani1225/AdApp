package com.gapps.adapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.mediation.MediationRewardedAd
import com.google.android.gms.ads.rewarded.RewardItem
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback

class MainActivity : AppCompatActivity() {

    lateinit var mAdView : AdView
    private var mInterstitialAd: InterstitialAd? = null
    private var mRewardedAd: RewardedAd? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadBannerAd()
        loadInterAd()
        loadRewardAd()

        val interAdBtn: Button = findViewById(R.id.interAd)
        val rewardAdBtn: Button = findViewById(R.id.rewardAd)
        interAdBtn.setOnClickListener {
            showInterAd()
        }

        rewardAdBtn.setOnClickListener {
            if (mRewardedAd != null) {
                mRewardedAd?.show(this, OnUserEarnedRewardListener() {
                    fun onUserEarnedReward(rewardItem: RewardItem) {
                        var rewardAmount = rewardItem.amount
                        var rewardType = rewardItem.type
                    }
                })
            }
        }

    }

    private fun loadRewardAd() {
        MobileAds.initialize(this) {}
        var rewardedAdRequest = AdRequest.Builder().build()

        RewardedAd.load(this,"ca-app-pub-2125877317262547/5512559162", rewardedAdRequest, object : RewardedAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                mRewardedAd = null
            }

            override fun onAdLoaded(rewardedAd: RewardedAd) {
                mRewardedAd = rewardedAd
            }
        })
    }

    private fun showInterAd() {
        if(mInterstitialAd != null)
        {
            mInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback(){
                override fun onAdDismissedFullScreenContent() {
                    super.onAdDismissedFullScreenContent()
                    val intent = Intent(this@MainActivity, MainActivity::class.java)
                    startActivity(intent)
                }
            }

            mInterstitialAd?.show(this)

        }
        else
        {
            val intent = Intent(this@MainActivity, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loadInterAd() {
        var interAdRequest = AdRequest.Builder().build()

        InterstitialAd.load(this,"ca-app-pub-2125877317262547/5743110564", interAdRequest, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                mInterstitialAd = null
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                mInterstitialAd = interstitialAd
            }
        })
    }

    private fun loadBannerAd() {
        MobileAds.initialize(this) {}

        mAdView = findViewById(R.id.adView)
        val bannerAdRequest = AdRequest.Builder().build()
        mAdView.loadAd(bannerAdRequest)
    }
}