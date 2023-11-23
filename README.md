# Galacean Effects Native Examples

## Introduction
GEPlayer is a powerful tool for playing Galacean Effects Native animations on mobile platforms. It provides an easy-to-use interface and supports both Android and iOS.  This repository demonstrates how to use GEPlayer to play Galacean Effects Native animation resources on Android or iOS.
- android/: Android Examples
- ios/: iOS Examples

## OS Version Requirement
- Android: 6.0 or later
- iOS: 11.0 or later

## Getting Started
To use GEPlayer in your Android or iOS project, follow these steps:

- Android: Add the following code snippet to your build.gradle file
```
dependencies {
    // ...
    implementation 'io.github.galacean:effects:0.0.1.202311221223'
    implementation 'com.squareup.okhttp3:okhttp:3.10.0'
    implementation 'com.alibaba:fastjson:1.2.76'
}
```  
and
```
import io.github.galacean.effects.GEPlayer
// ...

public class GEPlayDemo extends Activity {
    // ...
    private LinearLayout mRoot;
    private GEPlayer mPlayer;
    
	private void playAnimation() {
        GEPlayer.GEPlayerParams params = new GEPlayer.GEPlayerParams();
        params.url = "xxxxx";
        this.player = new GEPlayer(this, params); 
        
        WeakReference<GEPlayDemo> weakThiz = new WeakReference<>(this);
        mPlayer.loadScene(new GEPlayer.Callback() {
            @Override
            public void onResult(boolean success, String errorMsg) {
                GEPlayDemo thiz = weakThiz.get();
                if (thiz == null || !success) {
                    return;
                }

                // ... 
                thiz.mRoot.addView(thiz.mPlayer);

                thiz.mPlayer.play(0, new GEPlayer.Callback() {
                    @Override
                    public void onResult(boolean success, String errorMsg) {
                        GEPlayDemo thiz = weakThiz.get();
                        if (thiz == null || !success) {
                            return;
                        }
                        if (thiz.mPlayer != null) {
                            thiz.mRoot.removeView(thiz.mPlayer);
                            thiz.mPlayer.destroy();
                            thiz.mPlayer = null;
                        }
                        // ...
                    }
                });
            }
        });
    }
}
```
- iOS: Add the following code snippet to your Podfile
```
target 'xxx' do
  # ...
  pod 'GalaceanEffects'
end
```  
and
```
#import <GalaceanEffects/GEPlayer.h>
#import <GalaceanEffects/GEUnzipProtocol.h>
// ...

@interface GEPlayDemo : UIViewController
// ...
@end

@interface GEPlayDemo ()
@property (nonatomic, strong) GEPlayer *player;
// ...
@end

@implementation GEPlayDemo
// ...
- (void)playAnimation:(UIView *)view {
    GEPlayerParams *params = [[GEPlayerParams alloc] init];
    params.url = @"xxx";
    self.player = [[GEPlayer alloc] initWithParams:params];

    __weak GEPlayDemo *weakThiz = self;
    [self.player loadScene:^(BOOL success, NSString *errorMsg) {
        __strong GEPlayDemo *thiz = weakThiz;
        if (!thiz || !success) {
            return;
        }
        
        // ...
        [thiz.view addSubview:thiz.player];

        [thiz.player playWithRepeatCount:0 Callback:^(BOOL success, NSString *errorMsg) {
            __strong GEPlayDemo *thiz = weakThiz;
            if (!thiz || !success) {
                return;
            }
            if (thiz.player) {
                [thiz.player removeFromSuperview];
                [thiz.player destroy];
                thiz.player = nil;
            }
            // ...
        }];
    }];
}
@end
```

## API Documentation
For more information about the APIs of GEPlayer, please visit: [https://galacean.antgroup.com/effects/#/user/ox4pb0gu4zuol6st](https://galacean.antgroup.com/effects/#/user/ox4pb0gu4zuol6st)

## Contributions
Contributions are welcome! If you have any suggestions or improvements, please feel free to open an issue or submit a pull request.

## License
This project is licensed under the [MIT License](LICENSE).

## Thanks
ZipArchive: [https://github.com/ZipArchive/ZipArchive](https://github.com/ZipArchive/ZipArchive)
