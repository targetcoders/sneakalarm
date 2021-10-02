var albumBucketName = 'sneakalarm.media';
var bucketRegion = 'ap-northeast-2';
var IdentityPoolId = 'ap-northeast-2:e33e44cf-1621-4149-98b8-9dd28c45ecc8';

AWS.config.update({
    region: bucketRegion,
    credentials: new AWS.CognitoIdentityCredentials({ IdentityPoolId: IdentityPoolId })
});
var s3 = new AWS.S3({
    apiVersion: '2006-03-01',
    params: { Bucket: albumBucketName }
});

function s3Upload(result) {
    console.log("upload resized image")
    console.log(result);
    s3.upload({
        Key: result.data.photoKey,
        Body: result.resizedImage,
        ACL: 'public-read'
    },
        function (err, data) {
            if (err) {
                return alert('There was an error uploading your photo: ', err.message);
            }
        }
    );
}

function s3ResizedUpload(data, maxSize) {
    return new Promise((resolve, reject) => {
        setTimeout(() => {
            resizeImage({
                data: data,
                maxSize: maxSize,
            }).then(function (result) {
                s3Upload(result);
            });
            resolve(data);
        }, 500);
    });

}
