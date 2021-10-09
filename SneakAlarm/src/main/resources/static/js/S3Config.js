class S3Config {
    constructor(AWS, albumBucketName, bucketRegion, IdentityPoolId){
        this.albumBucketName = albumBucketName;
        this.bucketRegion = bucketRegion;
        this.IdentityPoolId = IdentityPoolId;
        this.AWS = AWS;
    }
}