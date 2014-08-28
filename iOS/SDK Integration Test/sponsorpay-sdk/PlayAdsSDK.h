//
//  PlayAdsSDK.h
//  PlayAdsSDK V4.0.2 b3938
//
//  Created by the AppLift iOS Team on 2/27/13.
//  Copyright (c) 2013 AppLift. All rights reserved.
//

#import <Foundation/Foundation.h>

// Types and Contstants
//================================

typedef NS_ENUM(NSInteger, PlayAdsAdType) {
    PlayAdsAdTypeScratch      = 1,
    PlayAdsAdTypeGamesList    = 2,
    PlayAdsAdTypeSlotMachine  = 3,
    PlayAdsAdTypeSmart        = 4,
    PlayAdsAdTypeCoverFlow    = 5,
    PlayAdsAdTypeMemory       = 6,
    PlayAdsAdTypeInterstitial = 7,
    PlayAdsAdTypeVideo        = 8
};

// SDK Delegate
//================================

@protocol PlayAdsSDKDelegate <NSObject>

@optional

- (void)playAdsStartDidEnd;
- (void)playAdsStartDidFailWithError:(NSError*)error;

- (void)playAdsAdReady;
- (void)playAdsAdDidShow;
- (void)playAdsAdDidFailWithError:(NSError*)error;
- (void)playAdsAdDidClose;

@end

@interface PlayAdsSDK : NSObject

//=================================================================================================
// PROPERTIES
//-------------------------------------------------------------------------------------------------
+ (void)setDelegate:(NSObject<PlayAdsSDKDelegate>*)delegate;   // Set the specified delegate for callbacks
//-------------------------------------------------------------------------------------------------
// START
//-------------------------------------------------------------------------------------------------
+ (void)startWithAppID:(NSString *)appId
           secretToken:(NSString *)secretToken;         // Configure SDK for specified AppID & secretToken
+ (void)startWithAppID:(NSString *)appId
           secretToken:(NSString *)secretToken
              delegate:(id<PlayAdsSDKDelegate>)delegate;// Configure SDK for specified AppID & secretToken & set delegate
//-------------------------------------------------------------------------------------------------
// CACHE
//-------------------------------------------------------------------------------------------------
+ (void)cache;                          // Cache an ad choosen randomly from the APD
+ (void)cacheType:(PlayAdsAdType)type;  // Cache the specific ad type
//-------------------------------------------------------------------------------------------------
// SHOW
//-------------------------------------------------------------------------------------------------
+ (void)show;                           // Shows an ad choosen randomly from the APD
+ (void)showType:(PlayAdsAdType)type;   // Shows the specific ad type
//-------------------------------------------------------------------------------------------------
// READY
//-------------------------------------------------------------------------------------------------
+ (BOOL)ready;                          // Returns YES if the current ad is ready
+ (BOOL)readyType:(PlayAdsAdType)type;  // Returns YES if the specified adType is ready
//-------------------------------------------------------------------------------------------------
// HELPERS
//-------------------------------------------------------------------------------------------------
+ (void)showLoadingScreen:(BOOL)show;   // Show loading screen if directly shown
+ (void)cleanCache;                     // Remove cached data for memory improve
+ (void)destroy;                        // Completely remove the SDK
+ (NSString*)version;                   // Current version
+ (UIViewController*)currentAd;         // Current interstitial view controller
//-------------------------------------------------------------------------------------------------
//=================================================================================================

@end
