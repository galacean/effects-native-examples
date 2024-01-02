# Galacean Effects Native Examples

## Introduction
This repo is for demo of integration of [GalaceanEffectsNative](https://github.com/galacean/effects-native) player (take ``GEPlayer`` for short).

## OS Requirement
- Android: 6.0 or later
- iOS: 11.0 or later

## Directory layout
```
GalaceanEffectsNativeExamples
├── android # Android demo gradle project
├── ios # iOS demo Xcode project
├── libs # binary artifact
│   ├── aar  
│   └── framework 
└── ...
```

## Getting Started
To use GEPlayer in your Android or iOS project, follow these steps:

### Android
Add the following dependencies to your build.gradle file:
```
dependencies {
    // ...
    implementation 'io.github.galacean:effects:0.0.1.202311221223'
    implementation 'com.squareup.okhttp3:okhttp:3.10.0'
    implementation 'com.alibaba:fastjson:1.2.76'
}
```

### iOS

Add the following dependency to your Podfile:
```
target 'xxx' do
  # ...
  pod 'GalaceanEffects'
end
```

## API Documentation

For more information of the GEPlayer API, please visit: [https://galacean.antgroup.com/effects/#/user/ox4pb0gu4zuol6st](https://galacean.antgroup.com/effects/#/user/ox4pb0gu4zuol6st)

## Contribution

Contributions are welcome! If you have any suggestions or improvements, please feel free to open an issue or submit a pull request.

## License
This project is licensed under the [MIT License](LICENSE).

## Thanks
ZipArchive: [https://github.com/ZipArchive/ZipArchive](https://github.com/ZipArchive/ZipArchive)