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

async function s3Upload(resizedResult) {
        console.log("s3Upload start - upload resized image")
        console.log(`path: ${resizedResult.data.path}`);
        await s3.upload({
            Key: resizedResult.data.path,
            Body: resizedResult.resizedImage,
            ACL: 'public-read'
        },
            function (err, data) {
                if (err) {
                    return alert('There was an error uploading your photo: ', err.message);
                }
            }
        );
    console.log("s3Upload end - upload resized image")
}

const sleep = async (ms) => {
    console.log(`sleep(${ms})...`);
    await new Promise((resolve) => {
        setTimeout(() => resolve(), ms);
    });
}

async function s3ResizedUpload(data, path, maxSize) {
    console.log(`s3ResizeUpload Start ${data} ${path} ${maxSize}`);
    data.path = path;
    const ok = await resizeImage({
        data: data,
        maxSize: maxSize,
    });
    await s3Upload(ok);
    console.log(`s3ResizeUpload End ${data} ${path} ${maxSize}`)
    await sleep(500);
}
