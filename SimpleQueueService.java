import java.util.List;
import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.CreateQueueRequest;
import com.amazonaws.services.sqs.model.DeleteMessageRequest;
import com.amazonaws.services.sqs.model.DeleteQueueRequest;
import com.amazonaws.services.sqs.model.ListQueuesResult;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;

public class SimpleQueueService
{
	
	AmazonSQS sqs = new AmazonSQSClient(new ClasspathPropertiesFileCredentialsProvider());

	public SimpleQueueService()
	{
		Region usWest2 = Region.getRegion(Regions.US_WEST_2);
		sqs.setRegion(usWest2);
	}
	
	/**
	 * Creates a queue and returns the url of the queue
	 * @param queueName
	 * @return queueUrl
	 */
  	public String createQueue(String queueName)
  	{
  		CreateQueueRequest createQueueRequest = new CreateQueueRequest(queueName);
  		String queueUrl = sqs.createQueue(createQueueRequest).getQueueUrl();
  		return queueUrl;
  	}

  	/**
  	 * Lists all the queues
  	 * @return listQueues
  	 */
  	public ListQueuesResult listQueues()
  	{
  		return this.sqs.listQueues();
    
        //List queues using for loop
        //for (String queueUrl : sqs.listQueues().getQueueUrls())
  		//{
        //    System.out.println("  QueueUrl: " + queueUrl);
        //}
  	}
	      
   
  	/**
  	 * Deletes a queue
  	 */      
  	public void deteleQeueu(String queueName)
  	{
	  sqs.deleteQueue(new DeleteQueueRequest(queueName));
  	}

  	
  	/**
  	 * Send a single message to the SQS queue
  	 * @param queueUrl
  	 * @param message
  	 */
  	public void sendMessageToQueue(String queueUrl, String message)
  	{
  		SendMessageResult messageResult =  this.sqs.sendMessage(new SendMessageRequest(queueUrl, message));
  		System.out.println(messageResult.toString());
  	}
      
  	
  	/**
  	 * Gets the messages from a queue
  	 * @param queueUrl
  	 * @return messages
  	 */
  	public List<Message> getMessagesFromQueue(String queueUrl)
  	{
  		ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest(queueUrl);
  		List<Message> messages = sqs.receiveMessage(receiveMessageRequest).getMessages();
     
  		//for (Message message : messages)
        //    {
        //        System.out.println("  Message");
        //        System.out.println("    MessageId:     " + message.getMessageId());
        //        System.out.println("    ReceiptHandle: " + message.getReceiptHandle());
        //        System.out.println("    MD5OfBody:     " + message.getMD5OfBody());
        //        System.out.println("    Body:          " + message.getBody());
        //        for (Entry<String, String> entry : message.getAttributes().entrySet())
        //        {
        //            System.out.println("  Attribute");
        //            System.out.println("    Name:  " + entry.getKey());
        //            System.out.println("    Value: " + entry.getValue());
        //        }
        //    }      
         return messages;
      }
   
  	
  	/**
  	 * Deletes a single message from a queue
  	 * @param queueUrl
  	 * @param message
  	 */
  	public void deleteMessageFromQueue(String queueUrl, Message message)
  	{
  		String messageRecieptHandle = message.getReceiptHandle();
  		System.out.println("message deleted : " + message.getBody() + "." + message.getReceiptHandle());
  		sqs.deleteMessage(new DeleteMessageRequest(queueUrl, messageRecieptHandle));
  	}  
}
    
///*
// * Copyright 2010-2014 Amazon.com, Inc. or its affiliates. All Rights Reserved.
// *
// * Licensed under the Apache License, Version 2.0 (the "License").
// * You may not use this file except in compliance with the License.
// * A copy of the License is located at
// *
// *  http://aws.amazon.com/apache2.0
// *
// * or in the "license" file accompanying this file. This file is distributed
// * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
// * express or implied. See the License for the specific language governing
// * permissions and limitations under the License.
// */
//import java.util.List;
//import java.util.Map.Entry;
//
//import com.amazonaws.AmazonClientException;
//import com.amazonaws.AmazonServiceException;
//import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
//import com.amazonaws.regions.Region;
//import com.amazonaws.regions.Regions;
//import com.amazonaws.services.sqs.AmazonSQS;
//import com.amazonaws.services.sqs.AmazonSQSClient;
//import com.amazonaws.services.sqs.model.CreateQueueRequest;
//import com.amazonaws.services.sqs.model.DeleteMessageRequest;
//import com.amazonaws.services.sqs.model.DeleteQueueRequest;
//import com.amazonaws.services.sqs.model.Message;
//import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
//import com.amazonaws.services.sqs.model.SendMessageRequest;
//
///**
// * This sample demonstrates how to make basic requests to Amazon SQS using the
// * AWS SDK for Java.
// * <p>
// * <b>Prerequisites:</b> You must have a valid Amazon Web
// * Services developer account, and be signed up to use Amazon SQS. For more
// * information on Amazon SQS, see http://aws.amazon.com/sqs.
// * <p>
// * <b>Important:</b> Be sure to fill in your AWS access credentials in the
// *                   AwsCredentials.properties file before you try to run this
// *                   sample.
// * http://aws.amazon.com/security-credentials
// */
//public class SimpleQueueServiceSample {
//
//	        public static void main(String[] args) throws Exception {
//	            /*
//	             * This credentials provider implementation loads your AWS credentials
//	             * from a properties file at the root of your classpath.
//	             *
//	             * Important: Be sure to fill in your AWS access credentials in the
//	             *            AwsCredentials.properties file before you try to run this
//	             *            sample.
//	             * http://aws.amazon.com/security-credentials
//	             */
//	            AmazonSQS sqs = new AmazonSQSClient(new ClasspathPropertiesFileCredentialsProvider());
//	    		Region usWest2 = Region.getRegion(Regions.US_WEST_2);
//	    		sqs.setRegion(usWest2);
//
//	            System.out.println("===========================================");
//	            System.out.println("Getting Started with Amazon SQS");
//	            System.out.println("===========================================\n");
//
//	            try {
//	                // Create a queue
//	                System.out.println("Creating a new SQS queue called MyQueue.\n");
//	                CreateQueueRequest createQueueRequest = new CreateQueueRequest("MyQueue");
//	                String myQueueUrl = sqs.createQueue(createQueueRequest).getQueueUrl();
//
//	                // List queues
//	                System.out.println("Listing all queues in your account.\n");
//	                for (String queueUrl : sqs.listQueues().getQueueUrls()) {
//	                    System.out.println("  QueueUrl: " + queueUrl);
//	                }
//	                System.out.println();
//
//	                // Send a message
//	                System.out.println("Sending a message to MyQueue.\n");
//	                sqs.sendMessage(new SendMessageRequest(myQueueUrl, "This is my message text."));
//
//	                // Receive messages
//	                System.out.println("Receiving messages from MyQueue.\n");
//	                ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest(myQueueUrl);
//	                List<Message> messages = sqs.receiveMessage(receiveMessageRequest).getMessages();
//	                for (Message message : messages) {
//	                    System.out.println("  Message");
//	                    System.out.println("    MessageId:     " + message.getMessageId());
//	                    System.out.println("    ReceiptHandle: " + message.getReceiptHandle());
//	                    System.out.println("    MD5OfBody:     " + message.getMD5OfBody());
//	                    System.out.println("    Body:          " + message.getBody());
//	                    for (Entry<String, String> entry : message.getAttributes().entrySet()) {
//	                        System.out.println("  Attribute");
//	                        System.out.println("    Name:  " + entry.getKey());
//	                        System.out.println("    Value: " + entry.getValue());
//	                    }
//	                }
//	                System.out.println();
//
//	                // Delete a message
//	                System.out.println("Deleting a message.\n");
//	                String messageRecieptHandle = messages.get(0).getReceiptHandle();
//	                sqs.deleteMessage(new DeleteMessageRequest(myQueueUrl, messageRecieptHandle));
//
//	                // Delete a queue
//	                System.out.println("Deleting the test queue.\n");
//	                sqs.deleteQueue(new DeleteQueueRequest(myQueueUrl));
//	            } catch (AmazonServiceException ase) {
//	                System.out.println("Caught an AmazonServiceException, which means your request made it " +
//	                        "to Amazon SQS, but was rejected with an error response for some reason.");
//	                System.out.println("Error Message:    " + ase.getMessage());
//	                System.out.println("HTTP Status Code: " + ase.getStatusCode());
//	                System.out.println("AWS Error Code:   " + ase.getErrorCode());
//	                System.out.println("Error Type:       " + ase.getErrorType());
//	                System.out.println("Request ID:       " + ase.getRequestId());
//	            } catch (AmazonClientException ace) {
//	                System.out.println("Caught an AmazonClientException, which means the client encountered " +
//	                        "a serious internal problem while trying to communicate with SQS, such as not " +
//	                        "being able to access the network.");
//	                System.out.println("Error Message: " + ace.getMessage());
//	            }
//	        }
//}    
