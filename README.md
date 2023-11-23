# Galacean Effects Native Examples

## Introduction
Galacean Effects Native is a implement of [Galacean Effects](https://galacean.antgroup.com/effects/#/home) targeting mobile platform(iOS & Android), which shares same [Galacean Effects format](https://github.com/galacean/effects-specification) and Galacean Effects Studio(https://huoxing.alipay.com/) workflow. It provides an easy-to-use interface and supports both Android and iOS.  This repository demonstrates how to use GEPlayer to play Galacean Effects Native animation resources on Android or iOS.

It features:

* high performance,  max reaches 120fps, the core engine is implemented upon c++ and the  standard-compliant OpenGL graphic layer.
* seamless integrate with mobile widgets, you can combine Galacean Effects Native animation with mobile animations flexibly to achieve better immersive experience.
* vector based, and mp4 support coming soon, mass adoption in mobile app(Alipay).

This repo is for demo of integration of Galacean Effects Native Player(take GEPlayer for short).

## OS Version Requirement
- Android: 6.0 or later
- iOS: 11.0 or later

## Directory layout

* "ios": iOS demo xcode project
* "android": Android demo gradle project

## Getting Started

To use GEPlayer in your Android or iOS project, follow these steps:

### Android

Add the following dependencies to your build.gradle file

```
dependencies {
    // ...
    implementation 'io.github.galacean:effects:0.0.1.202311221223'
    implementation 'com.squareup.okhttp3:okhttp:3.10.0'
    implementation 'com.alibaba:fastjson:1.2.76'
}
```

and extends your own activity

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

### iOS

Add the following code snippet to your Podfile
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
