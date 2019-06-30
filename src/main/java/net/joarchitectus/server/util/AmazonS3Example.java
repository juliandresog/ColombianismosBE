/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.joarchitectus.server.util;

import com.amazonaws.AmazonServiceException;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.List;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 *
 * @author josorio
 */
public class AmazonS3Example {

    private static final String SUFFIX = "/";

    public static void main(String[] args) {
        // credentials object identifying user for authentication
        // user must have AWSConnector and AmazonS3FullAccess for 
        // this example to work

        System.out.println("Soportado S3 en zona:"+
        Region.getRegion(Regions.US_WEST_2)
                .isServiceSupported(AmazonS3.ENDPOINT_PREFIX));

        //--https://docs.aws.amazon.com/es_es/sdk-for-java/v1/developer-guide/credentials.html
        BasicAWSCredentials awsCreds = new BasicAWSCredentials("YourAccessKeyID", "eRdFaJxcc/yZoc7uc4fPqkV7UYVX/SIbhvWFgN7o"); //YourAccessKeyID y YourSecretAccessKey
        AmazonS3 s3client = AmazonS3ClientBuilder.standard()
                .withRegion(Regions.US_WEST_2)
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                .build();

        // create bucket - name must be unique for all S3 users
        String bucketName = "boos-tmp-jj";
        //s3client.createBucket(bucketName);
        Bucket b = null;
        if (s3client.doesBucketExistV2(bucketName)) {
            System.out.format("Bucket %s already exists.\n", bucketName);
            //b = getBucket(bucketName);
        } else {
            try {
                b = s3client.createBucket(bucketName);
            } catch (AmazonS3Exception e) {
                System.err.println(e.getErrorMessage());
            }
        }

        // list buckets
        for (Bucket bucket : s3client.listBuckets()) {
            System.out.println(" - " + bucket.getName());
        }

        // create folder into bucket
        String folderName = "/opt/archivos/talento/fotos/Talento/";
        
        System.out.println("folder name: "+folderName);
        if(folderName.startsWith("/")){
            folderName = folderName.substring(1);
        }
        System.out.println("folder name: "+folderName);
        if(folderName.endsWith("/")){
            folderName = folderName.substring(0, folderName.length()-1);
        }
        System.out.println("folder name: "+folderName);
          
        
        System.out.println("Existe folder en bucket? R:"+s3client.doesObjectExist(bucketName, folderName+"/"));
        System.out.println("Existe folder en bucket? R2:"+s3client.getObjectMetadata(bucketName, folderName+"/soporte-hv.png"));
        
        s3client.deleteObject(bucketName, folderName+"/soporte-hv.png");
        System.out.println("Existe folder en bucket? R2:"+s3client.doesObjectExist(bucketName, folderName+"/soporte-hv.png"));
        
        createFolder(bucketName, folderName, s3client);
        
        

        // upload file to folder and set it to public
        String fileName = folderName + SUFFIX + "soporte-hv.png";
        s3client.putObject(new PutObjectRequest(bucketName, fileName,
                new File(File.separator + "opt/archivos/talento/fotos/Talento/soporte-hv.png")) //File.separator
                .withCannedAcl(CannedAccessControlList.BucketOwnerFullControl));
        
        fileName = folderName + SUFFIX + "soporte-tn_rdg_col.png";
        s3client.putObject(new PutObjectRequest(bucketName, fileName,
                new File(File.separator + "opt/archivos/talento/fotos/Talento/soporte-tn_rdg_col.png")) //File.separator
                .withCannedAcl(CannedAccessControlList.BucketOwnerFullControl));

        //listart
        ListObjectsV2Result result = s3client.listObjectsV2(bucketName);
        List<S3ObjectSummary> objects = result.getObjectSummaries();
        for (S3ObjectSummary os : objects) {
            System.out.println("* " + os.getKey());
        }
        
        //descargar un objeto
        try {
            S3Object o = s3client.getObject(bucketName, fileName);
            S3ObjectInputStream s3is = o.getObjectContent();
            FileOutputStream fos = new FileOutputStream(new File(File.separator + "tmp" + File.separator + "down_soporte-tn_rdg_col.png"));
            byte[] read_buf = new byte[1024];
            int read_len = 0;
            while ((read_len = s3is.read(read_buf)) > 0) {
                fos.write(read_buf, 0, read_len);
            }
            s3is.close();
            fos.close();
        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
            System.exit(1);
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }

        //deleteFolder(bucketName, folderName, s3client);
        // deletes bucket
        //s3client.deleteBucket(bucketName);
    }

    /**
     * Creo folder
     *
     * @param bucketName
     * @param folderName
     * @param client
     */
    public static void createFolder(String bucketName, String folderName, AmazonS3 client) {
        // create meta-data for your folder and set content-length to 0
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(0);
        // create empty content
        InputStream emptyContent = new ByteArrayInputStream(new byte[0]);
        // create a PutObjectRequest passing the folder name suffixed by /
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName,
                folderName + SUFFIX, emptyContent, metadata);
        // send request to S3 to create folder
        client.putObject(putObjectRequest);
    }

    /**
     * This method first deletes all the files in given folder and than the
     * folder itself
     */
    public static void deleteFolder(String bucketName, String folderName, AmazonS3 client) {
        List<S3ObjectSummary> fileList
                = client.listObjects(bucketName, folderName).getObjectSummaries();
        for (S3ObjectSummary file : fileList) {
            client.deleteObject(bucketName, file.getKey());
        }
        client.deleteObject(bucketName, folderName);
    }

    public static Bucket getBucket(String bucket_name) {
        final AmazonS3 s3 = AmazonS3ClientBuilder.defaultClient();
        Bucket named_bucket = null;
        List<Bucket> buckets = s3.listBuckets();
        for (Bucket b : buckets) {
            if (b.getName().equals(bucket_name)) {
                named_bucket = b;
            }
        }
        return named_bucket;
    }

    public static Bucket createBucket(String bucket_name) {
        final AmazonS3 s3 = AmazonS3ClientBuilder.defaultClient();
        Bucket b = null;
        if (s3.doesBucketExist(bucket_name)) {
            System.out.format("Bucket %s already exists.\n", bucket_name);
            b = getBucket(bucket_name);
        } else {
            try {
                b = s3.createBucket(bucket_name);
            } catch (AmazonS3Exception e) {
                System.err.println(e.getErrorMessage());
            }
        }
        return b;
    }
}
