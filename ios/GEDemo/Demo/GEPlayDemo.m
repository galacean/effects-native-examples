#import "GEPlayDemo.h"
#import <GalaceanEffects/GEPlayer.h>
#import <GalaceanEffects/GEUnzipProtocol.h>
#import "UnzipImplement.h"

@interface GEPlayDemo ()

@property (nonatomic, strong) GEPlayer *player;

@property (nonatomic, strong) UIButton *button;

@property (nonatomic, strong) UIGestureRecognizer *tapGesture;

@end

@implementation GEPlayDemo

- (void)viewDidLoad {
    [super viewDidLoad];
    [self.view setBackgroundColor:[UIColor grayColor]];
    
    // 注册解压缩的实现
    if (![GEUnzipManager shared].unzipDelegate) {
        [GEUnzipManager shared].unzipDelegate = [[UnzipImplement alloc] init];
    }
    
    UIButton *button = [UIButton buttonWithType:UIButtonTypeSystem];
    self.button = button;
    [self.view addSubview:button];
    [button setBackgroundColor:[UIColor whiteColor]];
    [button setTitle:@"开始播放" forState:UIControlStateNormal];
    [button setTitleColor:[UIColor blackColor] forState:UIControlStateNormal];
    [button setTranslatesAutoresizingMaskIntoConstraints:NO];
    
    // demo二选一：简单播放/分段播放
    [button addTarget:self action:@selector(simplePlay:) forControlEvents:UIControlEventTouchUpInside];
//    [button addTarget:self action:@selector(segmentPlay:) forControlEvents:UIControlEventTouchUpInside];
    
    [self.view addConstraint:[NSLayoutConstraint constraintWithItem:button
                                                          attribute:NSLayoutAttributeCenterY
                                                          relatedBy:NSLayoutRelationEqual
                                                             toItem:self.view
                                                          attribute:NSLayoutAttributeCenterY
                                                         multiplier:1.0
                                                           constant:0.0]];
    [self.view addConstraint:[NSLayoutConstraint constraintWithItem:button
                                                          attribute:NSLayoutAttributeWidth
                                                          relatedBy:NSLayoutRelationEqual
                                                             toItem:self.view
                                                          attribute:NSLayoutAttributeWidth
                                                         multiplier:1.0
                                                           constant:0.0]];
    [self.view layoutIfNeeded];
}

- (void)simplePlay:(UIView *)view {
    if (self.button) {
        [self.button setHidden:YES];
    }
    
    GEPlayerParams *params = [[GEPlayerParams alloc] init];
    params.url = @"https://mdn.alipayobjects.com/mars/afts/file/A*e7_FTLA_REgAAAAAAAAAAAAAARInAQ";
    self.player = [[GEPlayer alloc] initWithParams:params];
    // 用于判断回调时的player是否是当前成员变量player
    __weak GEPlayer *weakPlayer = self.player;
    // 防止循环调用，建议使用弱引用
    __weak GEPlayDemo *weakThiz = self;
    [self.player loadScene:^(bool success, NSString * errorMsg) {
        // 一定在UI线程回调
        __strong GEPlayDemo *thiz = weakThiz;
        if (!thiz) {
            return;
        }
        if (weakPlayer != thiz.player) {
            return;
        }
        if (!success) {
            NSLog(@"loadScene fail,%@", errorMsg);
            return;
        }
        
        // 目的是等比拉伸动画view，居中显示，撑满父view的最短边
        float w = thiz.view.frame.size.width;
        float h = thiz.view.frame.size.height;
        float x = 0;
        float y = 0;
        float aspect = [thiz.player getAspect];
        if (w / aspect < h) {
            h = w / aspect;
            y = (thiz.view.frame.size.height - h) / 2;
        } else {
            w = h * aspect;
            x = (thiz.view.frame.size.width - w) / 2;
        }
        
        [thiz.player setFrame:CGRectMake(x, y, w, h)];
        [thiz.view addSubview:thiz.player];
        
        [thiz.player playWithRepeatCount:0 Callback:^(bool success, NSString * errorMsg) {
            __strong GEPlayDemo *thiz = weakThiz;
            if (thiz && success) {
                if (thiz.player) {
                    [thiz.player removeFromSuperview];
                    [thiz.player destroy];
                    thiz.player = nil;
                }
                
                [thiz showToast:@"动画播放完毕~"];
                [thiz.button setHidden:NO];
            }
        }];
    }];
}

- (void)segmentPlay:(UIView *)view {
    [self.button setHidden:YES];
    
    GEPlayerParams *params = [[GEPlayerParams alloc] init];
    params.url = @"https://mdn.alipayobjects.com/mars/afts/file/A*1ljVQIC-nfAAAAAAAAAAAAAAARInAQ";
    self.player = [[GEPlayer alloc] initWithParams:params];
    // 用于判断回调时的player是否是当前成员变量player
    __weak GEPlayer *weakPlayer = self.player;
    // 防止循环调用，建议使用弱引用
    __weak GEPlayDemo *weakThiz = self;
    [self.player loadScene:^(bool success, NSString * errorMsg) {
        // 一定在UI线程回调
        __strong GEPlayDemo *thiz = weakThiz;
        if (!thiz) {
            return;
        }
        if (weakPlayer != thiz.player) {
            return;
        }
        if (!success) {
            NSLog(@"loadScene fail,%@", errorMsg);
            return;
        }
        
        // 目的是等比拉伸动画view，居中显示，撑满父view的最短边
        float w = thiz.view.frame.size.width;
        float h = thiz.view.frame.size.height;
        float x = 0;
        float y = 0;
        float aspect = [thiz.player getAspect];
        if (w / aspect < h) {
            h = w / aspect;
            y = (thiz.view.frame.size.height - h) / 2;
        } else {
            w = h * aspect;
            x = (thiz.view.frame.size.width - w) / 2;
        }
        
        [thiz.player setFrame:CGRectMake(x, y, w, h)];
        [thiz.view addSubview:thiz.player];
        
        [thiz.player playWithFromFrame:0 ToFrame:59 RepeatCount:-1 Callback:nil];
        [thiz showToast:@"循环播第一段，点击可播第二段"];
        if (thiz.player) {
            double delayInSeconds = 0.8;
            dispatch_time_t t = dispatch_time(DISPATCH_TIME_NOW, (int64_t)(delayInSeconds * NSEC_PER_SEC));
            dispatch_after(t, dispatch_get_main_queue(), ^(void) {
                __strong GEPlayDemo *thiz = weakThiz;
                
                thiz.tapGesture = [[UITapGestureRecognizer alloc] initWithTarget:self
                                                                          action:@selector(handlePlayerClicked:)];
                [thiz.player addGestureRecognizer:thiz.tapGesture];
            });
        }
    }];
}

- (void)handlePlayerClicked:(UIView*)sender {
    [self showToast:@"开始播第二段"];
    [self.player removeGestureRecognizer:self.tapGesture];
    __weak GEPlayDemo *weakThiz = self;
    [self.player playWithFromFrame:60 ToFrame:239 RepeatCount:0 Callback:^(bool success, NSString * errorMsg) {
        __strong GEPlayDemo *thiz = weakThiz;
        if (thiz.player) {
            [thiz.player removeFromSuperview];
            [thiz.player destroy];
            thiz.player = nil;
        }
        [thiz showToast:@"播放结束"];
        [thiz.button setHidden:NO];
    }];
}

- (void)showToast: (NSString*)msg {
    UIWindow *window = UIApplication.sharedApplication.keyWindow;
    UIView *toastView = [[UIView alloc] initWithFrame:CGRectMake(0, 0, 300, 45)];
    CGFloat toastX = window.bounds.size.width / 2;
    CGFloat toastY = window.bounds.size.height * 4 / 5;
    toastView.center = CGPointMake(toastX, toastY);
    toastView.backgroundColor = [UIColor blackColor];
    toastView.alpha = 0.7;
    toastView.layer.cornerRadius = 10;
    toastView.clipsToBounds = YES;
    [window addSubview:toastView];
    
    UILabel *label = [[UILabel alloc] initWithFrame:toastView.bounds];
    label.text = msg;
    label.textColor = [UIColor whiteColor];
    label.textAlignment = NSTextAlignmentCenter;
    [toastView addSubview:label];
    
    double delayInSeconds = 1.0;
    dispatch_time_t popTime = dispatch_time(DISPATCH_TIME_NOW, (int64_t)(delayInSeconds * NSEC_PER_SEC));
    dispatch_after(popTime, dispatch_get_main_queue(), ^(void){
        [toastView removeFromSuperview];
    });
}

@end
