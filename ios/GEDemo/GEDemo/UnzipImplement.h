#import <Foundation/Foundation.h>
#import <GalaceanEffects/GEUnzipProtocol.h>

NS_ASSUME_NONNULL_BEGIN

@interface UnzipImplement : NSObject<GEUnzipProtocol>

-(BOOL) UnzipOpenFile:(NSString*) zipFile;
-(BOOL) UnzipFileTo:(NSString*) path overWrite:(BOOL) overwrite;
-(BOOL) UnzipCloseFile;

@end

NS_ASSUME_NONNULL_END
