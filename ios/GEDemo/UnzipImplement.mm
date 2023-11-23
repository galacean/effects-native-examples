#import "UnzipImplement.h"
#import "DemoZipArchive.h"

@interface UnzipImplement ()

@property (nonatomic, strong) DemoZipArchive* zip;

@end

@implementation UnzipImplement

- (instancetype)init {
    if (self = [super init]) {
        self.zip = [[DemoZipArchive alloc] init];
    }
    return self;
}

-(BOOL) UnzipOpenFile:(NSString*) zipFile {
    return [self.zip UnzipOpenFile:zipFile];
}

-(BOOL) UnzipFileTo:(NSString*) path overWrite:(BOOL) overwrite {
    return [self.zip UnzipFileTo:path overWrite:overwrite];
}

-(BOOL) UnzipCloseFile {
    return [self.zip UnzipCloseFile];
}

@end
