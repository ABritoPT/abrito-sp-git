package com.hyprmx.android.activities;

import android.app.ListActivity;

import android.content.Intent;
import android.os.Bundle;

import com.hyprmx.android.sdk.HyprMXHelper;
import com.hyprmx.android.sdk.HyprMXHelper.HyprMXListener;

/**
 * An abstract base class for SDK users to subclass to easily manage the offer
 * lifecycle and receive callbacks at appropriate times. Simply subclass,
 * implement the abstract <code>HyprMXListener</code> interface, include HyprMX
 * design elements in your layout and that's it.
 * 
 * Ensure that if your subclass overrides <code>onActivityResult()</code> you
 * call <code>super.onActivityResult()</code> in order to properly process
 * HyprMX offer results and receive callbacks to your
 * <code>HyprMXListener</code>.
 *  
 */
public abstract class HyprMXListActivity extends ListActivity implements HyprMXListener {

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		HyprMXHelper.handleOnCreate(this, savedInstanceState);
	}
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        HyprMXHelper.processActivityResult(this, requestCode, resultCode, data, this);
    }
    
    @Override
    public void onBackPressed() {
    	HyprMXHelper.handleOnBackPressed();
        super.onBackPressed();
    }

}
