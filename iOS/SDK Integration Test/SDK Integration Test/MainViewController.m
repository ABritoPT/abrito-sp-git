//
//  MainViewController.m
//  SDK Integration Test
//
//  Created by André on 23/07/14.
//  Copyright (c) 2014 SponsorPay. All rights reserved.
//

#import "MainViewController.h"
#import "SponsorPaySDK.h"

@interface MainViewController ()

@property (weak, nonatomic) IBOutlet UILabel *creditsText;
@property (weak, nonatomic) IBOutlet UIButton *updateCreditsButton;
@property (weak, nonatomic) IBOutlet UIButton *showOWButton;
@property (weak, nonatomic) IBOutlet UIButton *checkBEButton;
@property (weak, nonatomic) IBOutlet UIButton *showBEButton;
@property (weak, nonatomic) IBOutlet UIButton *checkInterstitialButton;
@property (weak, nonatomic) IBOutlet UIButton *showInterstitialButton;

@end

@implementation MainViewController

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
    self.showBEButton.enabled = NO;
    self.showInterstitialButton.enabled = NO;
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

// OW
- (IBAction)showOW:(id)sender {
    [SponsorPaySDK showOfferWallWithParentViewController:self];
}

- (void) offerWallViewController:(SPOfferWallViewController *)offerWallVC isFinishedWithStatus:(int)status {
    
    // we could know if status determines a network error by comparing it with the
    // SPONSORPAY_ERR_NETWORK constant defined in SPOfferWallViewController.h
}

// Rewarded Video
- (IBAction)checkBE {
    self.showBEButton.enabled = !self.showBEButton.enabled;
}

- (IBAction)showBE {
    self.showBEButton.enabled = NO;
}

// Interstitials
- (IBAction)checkInterstitial {
    self.showInterstitialButton.enabled = !self.showInterstitialButton.enabled;
}

- (IBAction)showInterstitial {
    self.showInterstitialButton.enabled = NO;
}

// VCS

- (IBAction)updateCredits {
    [SponsorPaySDK requestDeltaOfCoinsNotifyingDelegate:self];
}

- (void)virtualCurrencyConnector:(SPVirtualCurrencyServerConnector *)vcConnector
  didReceiveDeltaOfCoinsResponse:(double)deltaOfCoins
             latestTransactionId:(NSString *)transactionId
{
    self.creditsText.text = [NSString stringWithFormat:@"%.2f", deltaOfCoins];
}

- (void)virtualCurrencyConnector:(SPVirtualCurrencyServerConnector *)vcConnector
                 failedWithError:(SPVirtualCurrencyRequestErrorType)error
                       errorCode:(NSString *)errorCode
                    errorMessage:(NSString *)errorMessage
{
    NSString *errorType;
    
    switch (error) {
        case NO_ERROR:
            errorType = @"NO_ERROR";
            break;
        case ERROR_NO_INTERNET_CONNECTION:
            errorType = @"ERROR_NO_INTERNET_CONNECTION";
            break;
        case ERROR_INVALID_RESPONSE:
            errorType = @"ERROR_INVALID_RESPONSE";
            break;
        case ERROR_INVALID_RESPONSE_SIGNATURE:
            errorType = @"ERROR_INVALID_RESPONSE_SIGNATURE";
            break;
        case SERVER_RETURNED_ERROR:
            errorType = @"SERVER_RETURNED_ERROR";
            break;
        case ERROR_OTHER:
            errorType = @"ERROR_OTHER";
            break;
        default:
            errorType = [NSString stringWithFormat:@"%d", error];
            break;
    }
    
    NSString *formattedError = [NSString stringWithFormat:@"%@\n%@\n%@", errorType, errorCode, errorMessage];
    
    UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"Error in VCS Request / Response"
                                                    message:formattedError
                                                   delegate:self cancelButtonTitle:@"OK"
                                          otherButtonTitles:nil];
    [alert show];
}


@end
