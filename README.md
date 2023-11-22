## Galacean Effects Native Examples
This repository is a usage example of Galacean Effects Native, showing how to play animations using GEPlayer.
- android/: Android Examples
- ios/: iOS Examples

## OS Version Requirement
- Android: 6.0+
- iOS: 11.0+

## Getting Started
- Android: add the following to your build.gradle 
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
```
- iOS: Add the following to your Podfile
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
```

## API Documentation
See: [https://galacean.antgroup.com/effects/#/user/ox4pb0gu4zuol6st](https://galacean.antgroup.com/effects/#/user/ox4pb0gu4zuol6st)
## Thanks
ZipArchive: [https://github.com/ZipArchive/ZipArchive](https://github.com/ZipArchive/ZipArchive)
