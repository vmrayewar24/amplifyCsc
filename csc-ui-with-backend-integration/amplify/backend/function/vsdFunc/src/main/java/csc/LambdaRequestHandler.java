/* Amplify Params - DO NOT EDIT
	ENV
	REGION
	STORAGE_VSDDB_ARN
	STORAGE_VSDDB_NAME
	STORAGE_VSDDB_STREAMARN
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

import csc.customexception.InvalidJSON;
import csc.customexception.NoDataToShow;

public class LambdaRequestHandler {
	static Map<String, String> headers = new HashMap<>();
	APIGatewayProxyResponseEvent apiGatewayProxyResponseEvent = new APIGatewayProxyResponseEvent();
	AmazonDynamoDB amazonDynamoDB = AmazonDynamoDBClientBuilder.standard().build();
	DynamoDB dynamoDB = new DynamoDB(AmazonDynamoDBClientBuilder.standard().build());
	Table table = dynamoDB.getTable(tablename);
	Gson gson = new Gson();

	static {
		headers.put("Access-Control-Allow-Headers", "Content-Type");
		headers.put("Access-Control-Allow-Origin", "*");
		headers.put("Access-Control-Allow-Methods", "OPTIONS,POST,GET");
	}

	private static String tablename = System.getenv("STORAGE_VSDDB_NAME");

	public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent request, Context context) {
		LambdaLogger lambdaLogger = context.getLogger();
		try {
			if (request.getHttpMethod().equals("GET") && request.getPath().equals("/vsd")) {
				ScanResult scanResult = amazonDynamoDB.scan(new ScanRequest().withTableName(tablename));
				List<ResponseClass> vendors = scanResult
						.getItems().stream().map(item -> new ResponseClass(item.get("id").getS(),
								item.get("name").getS(), item.get("category").getS(), item.get("servtype").getS()))
						.collect(Collectors.toList());
				if(vendors.size()==0) {
					throw new NoDataToShow("No Data to show");
				}
				System.out.println("Inside getHttpMethod");
				System.out.println(vendors.toString());
				System.out.println(vendors.size());
				if(vendors.size()==0) {
					System.out.println("Inside if block of htttp get method");
					throw new NoDataToShow("No data to show");	
				}
				
				return apiGatewayProxyResponseEvent.withBody(gson.toJson(vendors)).withStatusCode(200)
						.withHeaders(headers);
			}
			if (request.getHttpMethod().equals("POST") && request.getPath().equals("/vsd")) {
				if(request.getBody()==null || request.getBody().isEmpty()) {
					throw new InvalidJSON("Please give some input");
				}
				RequestClass vendor = gson.fromJson(request.getBody(), RequestClass.class);
				Item item = new Item().withPrimaryKey("id", UUID.randomUUID().toString())
						.withString("name", vendor.getName()).withString("category", vendor.getCategory())
						.withString("servtype", vendor.getServtype());
				table.putItem(item);
				return apiGatewayProxyResponseEvent.withBody(gson.toJson(vendor.getName() + " Added successfully"))
						.withStatusCode(200).withHeaders(headers);
			}
			if (request.getHttpMethod().equals("PUT") && request.getPath().equals("/vsd")) {
				if(request.getBody()==null || request.getBody().isEmpty()) {
					throw new InvalidJSON("Please give some input");
				}
				RequestClass vendor = gson.fromJson(request.getBody(), RequestClass.class);
				Map<String, String> expressionAttributeNames = new HashMap<>();
				expressionAttributeNames.put("#N", "name");
				expressionAttributeNames.put("#C", "category");
				expressionAttributeNames.put("#S", "servtype");
				Map<String, Object> expressionAttributeValues = new HashMap<>();
				expressionAttributeValues.put(":ValN", vendor.getName());
				expressionAttributeValues.put(":ValC", vendor.getCategory());
				expressionAttributeValues.put(":ValS", vendor.getServtype());
				table.updateItem("id", vendor.getId(), "SET #N=:ValN,#C=:ValC,#S=:ValS", expressionAttributeNames,
						expressionAttributeValues);
				return apiGatewayProxyResponseEvent.withBody(gson.toJson(vendor.getName() + " updated"))
						.withStatusCode(200);
			}
			if (request.getHttpMethod().equals("DELETE") && request.getPath().equals("/vsd")) {
				if(request.getBody()==null || request.getBody().isEmpty()) {
					throw new InvalidJSON("Please give some input");
				}
				RequestClass vendor = gson.fromJson(request.getBody(), RequestClass.class);
				table.deleteItem("id", vendor.getId());
				return apiGatewayProxyResponseEvent.withBody(gson.toJson(vendor.getName() + " deleted"))
						.withStatusCode(200);
			}
			if (request.getHttpMethod().equals("GET") && request.getResource().equals("/vsd/{proxy+}")) {
				String vendorId = request.getPathParameters().get("proxy");
				Item item = table.getItem("id", vendorId);
				ResponseClass vendorOut = new ResponseClass(item.getString("id"), item.getString("name"),
						item.getString("category"), item.getString("servtype"));
				return apiGatewayProxyResponseEvent.withBody(gson.toJson(vendorOut)).withStatusCode(200)
						.withHeaders(headers);
			}
		} catch (Exception e) {
			lambdaLogger.log("General Exception " + e);
		}
		return apiGatewayProxyResponseEvent.withHeaders(headers).withBody("resource not found").withStatusCode(404);
	}
}