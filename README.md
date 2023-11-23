# Galacean Effects Native Examples

## Introduction
GEPlayer is a powerful tool for playing Galacean Effects Native animations on mobile platforms. It provides an easy-to-use interface and supports both Android and iOS. This repository demonstrates how to use GEPlayer to play Galacean Effects Native animation resources on Android or iOS.
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
```

## API Documentation
For more information about the APIs of GEPlayer, please visit: [https://galacean.antgroup.com/effects/#/user/ox4pb0gu4zuol6st](https://galacean.antgroup.com/effects/#/user/ox4pb0gu4zuol6st)

## Contributions
Contributions are welcome! If you have any suggestions or improvements, please feel free to open an issue or submit a pull request.

## License
This project is licensed under the [MIT License](LICENSE).

## Thanks
ZipArchive: [https://github.com/ZipArchive/ZipArchive](https://github.com/ZipArchive/ZipArchive)
