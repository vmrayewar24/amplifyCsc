/* Amplify Params - DO NOT EDIT
	ENV
	REGION
	STORAGE_SERVDB_ARN
	STORAGE_SERVDB_NAME
	STORAGE_SERVDB_STREAMARN
Amplify Params - DO NOT EDIT */

package csc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.Gson;

public class LambdaRequestHandler{
	  
	private static String tablename=System.getenv("STORAGE_SERVDB_NAME");
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent request, Context context){
		Map<String , String>headers=new HashMap<>();
        headers.put("Access-Control-Allow-Headers", "Content-Type");
        headers.put("Access-Control-Allow-Origin", "*");    
        headers.put("Access-Control-Allow-Methods", "OPTIONS,POST,GET");
		APIGatewayProxyResponseEvent apiGatewayProxyResponseEvent=new APIGatewayProxyResponseEvent();
		LambdaLogger lambdaLogger=context.getLogger();
		Gson gson=new Gson();
		try{
			if(request.getHttpMethod().equals("GET") && request.getPath().equals("/serv")){
				AmazonDynamoDB amazonDynamoDB=AmazonDynamoDBClientBuilder.standard().build();
				ScanResult scanResult=amazonDynamoDB.scan(new ScanRequest().withTableName(tablename));
				List<ResponseClass>veggies=scanResult.getItems().stream().map(
					item->new ResponseClass(
						item.get("id").getS(),
						item.get("name").getS()
					)
				)
				.collect(Collectors.toList());
				return apiGatewayProxyResponseEvent.withBody(gson.toJson(veggies)).withStatusCode(200).withHeaders(headers);
			}
			if(request.getHttpMethod().equals("POST") && request.getPath().equals("/serv")){
				RequestClass veggie=gson.fromJson(request.getBody(),RequestClass.class);
				DynamoDB dynamoDB=new DynamoDB(AmazonDynamoDBClientBuilder.standard().build());
				Table table=dynamoDB.getTable(tablename);
				Item item=new Item().withPrimaryKey("id", UUID.randomUUID().toString())
									.withString("name", veggie.getName());
				table.putItem(item);
				return apiGatewayProxyResponseEvent.withBody(gson.toJson(veggie.getName()+" Added successfully")).withStatusCode(200).withHeaders(headers);
			}
			if(request.getHttpMethod().equals("PUT") && request.getPath().equals("/serv")){
				RequestClass veggie=gson.fromJson(request.getBody(),RequestClass.class);
				DynamoDB dynamoDB=new DynamoDB(AmazonDynamoDBClientBuilder.standard().build());
				Table table=dynamoDB.getTable(tablename);
				Map<String,String>expressionAttributeNames=new HashMap<>();
				expressionAttributeNames.put("#N", "name");
				Map<String,Object>expressionAttributeValues=new HashMap<>();
				expressionAttributeValues.put(":ValN",veggie.getName());
				table.updateItem("id", veggie.getId(),
				 "SET #N=:ValN",expressionAttributeNames,expressionAttributeValues);
				return apiGatewayProxyResponseEvent.withBody(gson.toJson(veggie.getName()+" updated")).withStatusCode(200);
			}
			if(request.getHttpMethod().equals("DELETE") && request.getPath().equals("/serv")){
				RequestClass veggie=gson.fromJson(request.getBody(),RequestClass.class);
				DynamoDB dynamoDB=new DynamoDB(AmazonDynamoDBClientBuilder.standard().build());
				Table table=dynamoDB.getTable(tablename);
				table.deleteItem("id", veggie.getId());
				return apiGatewayProxyResponseEvent.withBody(gson.toJson(veggie.getName()+" deleted")).withStatusCode(200);
			}
			if(request.getHttpMethod().equals("GET") && request.getResource().equals("/serv/{proxy+}")){
				String veggieId=request.getPathParameters().get("proxy");
				DynamoDB dynamoDB=new DynamoDB(AmazonDynamoDBClientBuilder.standard().build());
				Table table=dynamoDB.getTable(tablename);
				Item item=table.getItem("id",veggieId);
				ResponseClass veggieout=new ResponseClass(
					item.getString("id"),
					item.getString("name")
				);
				return apiGatewayProxyResponseEvent.withBody(gson.toJson(veggieout)).withStatusCode(200).withHeaders(headers);
			}
		}catch(Exception e){
			lambdaLogger.log("General Exception "+e);
		}
		// return apiGatewayProxyResponseEvent.withHeaders(headers).withBody("resource not found");
		return null;
    }
}