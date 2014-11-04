package com.example.test;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.flurry.android.FlurryAgent;
import com.sponsorpay.SponsorPay;
import com.sponsorpay.publisher.SponsorPayPublisher;
import com.sponsorpay.publisher.currency.SPCurrencyServerErrorResponse;
import com.sponsorpay.publisher.currency.SPCurrencyServerListener;
import com.sponsorpay.publisher.currency.SPCurrencyServerSuccessfulResponse;
import com.sponsorpay.publisher.interstitial.SPInterstitialRequestListener;
import com.sponsorpay.publisher.mbe.SPBrandEngageRequestListener;
import com.sponsorpay.utils.SponsorPayLogger;


public class MainActivity extends ActionBarActivity implements SPBrandEngageRequestListener, SPInterstitialRequestListener {
	
	private static final String TAG = "A_SDK_TEST";
	
	private static final String USER_ID = null; // the current user id of your game (or null if you don't use any)
	
	private static final String APP_ID = "23528";
	private static final String SECURITY_TOKEN = "92e8c4c4f63c117a1e3279d9c62bf551";
	
//	private static final String APP_ID = "23762";
//	private static final String SECURITY_TOKEN = "c97597d3702a0a85ddd0d997aacf0b16";
	
	private SPCurrencyServerListener mVCSListener;
	private Intent _RVOfferIntent;
	private Intent interstitialOfferIntent;
	
	private TextView creditsInfoText;
	private Button playRVButton;
	private Button showInterstitialButton;
	
	private int _credits = 0;
	
	private void updateCredits(int value) {
		_credits = value;
		creditsInfoText.setText("Credits: " + _credits);
	}
	
	private void incrementCredits(int value) {
		updateCredits(_credits + value);
	}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        creditsInfoText = (TextView)findViewById(R.id.textCreditInfo);
        playRVButton = (Button)findViewById(R.id.buttonRVPlay);
        showInterstitialButton = (Button)findViewById(R.id.buttonInterstitialShow);
        
        updateCredits(0);
        setRVOfferIntent(null);
        setInterstitialIntent(null);
        
        SponsorPayLogger.enableLogging(true);
		mVCSListener = new SPCurrencyServerListener() {

			@Override
			public void onSPCurrencyServerError(
					SPCurrencyServerErrorResponse resp) {
				Toast.makeText(getApplicationContext(), "Check failed.",
						Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onSPCurrencyDeltaReceived(
					SPCurrencyServerSuccessfulResponse resp) {
				Toast.makeText(getApplicationContext(),
						"Currency Delta=" + resp.getDeltaOfCoins(),
						Toast.LENGTH_SHORT).show();
				incrementCredits((int) resp.getDeltaOfCoins());
			}
		};
    }
    
    @Override
    protected void onResume() {
    	super.onResume();
    	
    	try {
    		FlurryAgent.setLogEnabled(true);
            SponsorPay.start(APP_ID, USER_ID, SECURITY_TOKEN, this);
        } catch (RuntimeException e){
            Log.d(TAG, e.getLocalizedMessage());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    @Override
    protected void onActivityResult(int arg0, int arg1, Intent arg2) {
    	// TODO Auto-generated method stub
    	super.onActivityResult(arg0, arg1, arg2);
    	
    	//TODO check for offerwall return
    }
    
    // Buttons
    public void onButtonCheckDelta(View view) {
    	SponsorPayPublisher.requestNewCoins(getApplicationContext(), mVCSListener);
    }
    
    public void onButtonShowMOWClick(View view) {
    	Intent offerWallIntent = SponsorPayPublisher.getIntentForOfferWallActivity(getApplicationContext(), true);
    	startActivityForResult(offerWallIntent, 1);
    }
    
    public void onButtonRVCheck(View view) {
    	requestVideo();
    }
    
    public void onButtonRVPlay(View view) {
    	playVideo();
    }
    
    public void onButtonInterstitialCheck(View view) {
    	requestInterstitial();
    }
    
    public void onButtonInterstitialShow(View view) {
    	showInterstitial();
    }
    
    // Rewarded Videos
    private void requestVideo() {
    	SponsorPayPublisher.getIntentForMBEActivity(this, this, mVCSListener);
    }
    
    private void playVideo() {
    	synchronized (this) {
			Intent intent = this._RVOfferIntent;
			if (intent != null) {
				startActivityForResult(intent, 1);
				setRVOfferIntent(null);
			}
		}
    }

	private void setRVOfferIntent(Intent intent) {
		synchronized (this) {
			this._RVOfferIntent = intent;
			playRVButton.setEnabled(intent != null);
		}
	}
	
	// Interstitials
	private void requestInterstitial() {
    	SponsorPayPublisher.getIntentForInterstitialActivity(this, this);
    }
    
    private void showInterstitial() {
    	synchronized (this) {
			Intent intent = this.interstitialOfferIntent;
			if (intent != null) {
				startActivityForResult(intent, 1);
				setInterstitialIntent(null);
			}
		}
    }
    
	private void setInterstitialIntent(Intent intent) {
		synchronized (this) {
			this.interstitialOfferIntent = intent;
			showInterstitialButton.setEnabled(intent != null);
		}
	}

    // SPBrandEngageRequestListener interface implementation
    @Override
    public void onSPBrandEngageOffersAvailable(Intent spBrandEngageActivity) {
        Log.d(TAG, "SPBrandEngage - intent available");
        setRVOfferIntent(spBrandEngageActivity);
    }

    @Override
    public void onSPBrandEngageOffersNotAvailable() {
        Log.d(TAG, "SPBrandEngage - no offers for the moment");
        setRVOfferIntent(null);
    }

    @Override
    public void onSPBrandEngageError(String errorMessage) {
        Log.d(TAG, "SPBrandEngage - an error occurred:\n" + errorMessage);
        setRVOfferIntent(null);
    }
    
    // SPInterstitialRequestListener interface implementation
	@Override
	public void onSPInterstitialAdAvailable(Intent interstitialActivity) {
		Log.d(TAG, "SPInterstitialAd - intent available");
        setInterstitialIntent(interstitialActivity);
	}

	@Override
	public void onSPInterstitialAdNotAvailable() {
		Log.d(TAG, "SPInterstitialAd - no offers for the moment");
		setInterstitialIntent(null);
	}

	@Override
	public void onSPInterstitialAdError(String errorMessage) {
		Log.d(TAG, "SPInterstitialAd - an error occurred:\n" + errorMessage);
		setInterstitialIntent(null);
	}
}
