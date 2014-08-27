//
//  MainViewController.h
//  SDK Integration Test
//
//  Created by André on 23/07/14.
//  Copyright (c) 2014 SponsorPay. All rights reserved.
//

#import "ViewController.h"
#import "SponsorPaySDK.h"

@interface MainViewController : ViewController <SPVirtualCurrencyConnectionDelegate,SPOfferWallViewControllerDelegate, SPBrandEngageClientDelegate, SPInterstitialClientDelegate>

@end
