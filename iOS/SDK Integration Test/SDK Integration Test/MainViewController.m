//
//  MainViewController.m
//  SDK Integration Test
//
//  Created by André on 23/07/14.
//  Copyright (c) 2014 SponsorPay. All rights reserved.
//

#import "MainViewController.h"

@interface MainViewController ()

@property (weak, nonatomic) IBOutlet UILabel *creditsTest;
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

- (IBAction)showOW:(id)sender {
    
}

- (IBAction)checkBE {
    self.showBEButton.enabled = !self.showBEButton.enabled;
}

- (IBAction)showBE {
    self.showBEButton.enabled = NO;
}

- (IBAction)checkInterstitial {
    self.showInterstitialButton.enabled = !self.showInterstitialButton.enabled;
}

- (IBAction)showInterstitial {
    self.showInterstitialButton.enabled = NO;
}

@end
