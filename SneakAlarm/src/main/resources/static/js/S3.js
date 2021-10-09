class S3 {
    constructor(S3Config) {
        this.S3Config = S3Config;

        this.S3Config.AWS.config.update({
            region: this.S3Config.bucketRegion,
            credentials: new this.S3Config.AWS.CognitoIdentityCredentials({ IdentityPoolId: this.S3Config.IdentityPoolId })
        });
    }

    instance(){
        return new this.S3Config.AWS.S3({
            apiVersion: '2006-03-01',
            params: { Bucket: this.S3Config.albumBucketName }
        });
    }
}