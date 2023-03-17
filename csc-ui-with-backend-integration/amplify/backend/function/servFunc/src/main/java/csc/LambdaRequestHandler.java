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

import csc.customexception.InvalidJSON;
import csc.customexception.NoDataToShow;

public class LambdaRequestHandler {
	private APIGatewayProxyResponseEvent apiGatewayProxyResponseEvent = new APIGatewayProxyResponseEvent();
	private static Map<String, String> headers = new HashMap<>();
	private Gson gson = new Gson();
	private static String tablename = System.getenv("STORAGE_SERVDB_NAME");
	private AmazonDynamoDB amazonDynamoDB = AmazonDynamoDBClientBuilder.standard().build();
	
	private DynamoDB dynamoDB = new DynamoDB(AmazonDynamoDBClientBuilder.standard().build());
	private Table table = dynamoDB.getTable(tablename);
	static {
		headers.put("Access-Control-Allow-Headers", "Content-Type");
		headers.put("Access-Control-Allow-Origin", "*");
		headers.put("Access-Control-Allow-Methods", "OPTIONS,POST,GET");
	}

	public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent request, Context context) {
		LambdaLogger lambdaLogger = context.getLogger();
		try {
			if (request.getHttpMethod().equals("GET") && request.getPath().equals("/serv")) {
				ScanResult scanResult = amazonDynamoDB.scan(new ScanRequest().withTableName(tablename));
				List<ResponseClass> services = scanResult.getItems().stream()
						.map(item -> new ResponseClass(item.get("id").getS(), item.get("name").getS()))
						.collect(Collectors.toList());
				if(services.size()==0) {
					throw new NoDataToShow("No Data to show");
				}
				return apiGatewayProxyResponseEvent.withBody(gson.toJson(services)).withStatusCode(200)
						.withHeaders(headers);
			}
			if (request.getHttpMethod().equals("POST") && request.getPath().equals("/serv")) {
				if(request.getBody()==null || request.getBody().isEmpty()) {
					throw new InvalidJSON("Please give some input");
				}
				RequestClass service = gson.fromJson(request.getBody(), RequestClass.class);
				Item item = new Item().withPrimaryKey("id", UUID.randomUUID().toString()).withString("name",
						service.getName());
				table.putItem(item);
				return apiGatewayProxyResponseEvent.withBody(gson.toJson(service.getName() + " Added successfully"))
						.withStatusCode(201).withHeaders(headers);
			}
			if (request.getHttpMethod().equals("PUT") && request.getPath().equals("/serv")) {
				if(request.getBody()==null || request.getBody().isEmpty()) {
					throw new InvalidJSON("Please give some input");
				}
				RequestClass service = gson.fromJson(request.getBody(), RequestClass.class);
				Map<String, String> expressionAttributeNames = new HashMap<>();
				expressionAttributeNames.put("#N", "name");
				Map<String, Object> expressionAttributeValues = new HashMap<>();
				expressionAttributeValues.put(":ValN", service.getName());
				table.updateItem("id", service.getId(), "SET #N=:ValN", expressionAttributeNames,
						expressionAttributeValues);
				return apiGatewayProxyResponseEvent.withBody(gson.toJson(service.getName() + " updated"))
						.withStatusCode(201);
			}
			if (request.getHttpMethod().equals("DELETE") && request.getPath().equals("/serv")) {
				if(request.getBody().equals(null)) {
					throw new InvalidJSON("Please give some input");
				}
				RequestClass service = gson.fromJson(request.getBody(), RequestClass.class);
				table.deleteItem("id", service.getId());
				return apiGatewayProxyResponseEvent.withBody(gson.toJson(service.getName() + " deleted"))
						.withStatusCode(200);
			}
			if (request.getHttpMethod().equals("GET") && request.getResource().equals("/serv/{proxy+}")) {
				String serviceId = request.getPathParameters().get("proxy");
				Item item = table.getItem("id", serviceId);
				ResponseClass serviceout = new ResponseClass(item.getString("id"), item.getString("name"));
				return apiGatewayProxyResponseEvent.withBody(gson.toJson(serviceout)).withStatusCode(200)
						.withHeaders(headers);
			}
		} catch (Exception e) {
			lambdaLogger.log("General Exception " + e);
		}
		return apiGatewayProxyResponseEvent.withHeaders(headers).withBody("resource not found").withStatusCode(500);
	}
}