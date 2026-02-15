package API.RestAutomation;

import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.Assert;

import files.ReUsableMethods;
import files.payload;


public class Basics {
	
	static String placeId;

	public static void main(String[] args) throws IOException {
		
		//given - all input details
		//when - Submit the API
		//Then -  validate the response
		
		RestAssured.baseURI="https://rahulshettyacademy.com";
		String response=given().log().all().queryParam("key" , "qaclick123").header("Content-Type","application/json")
		//.body(payload.AddPlace())

		.body(new String (Files.readAllBytes(Paths.get("C:\\Users\\lalit\\git\\RestAutomation\\resources\\testdata\\addPlace.json"))))		

		.when().post("/maps/api/place/add/json")
		.then().assertThat().statusCode(200).body("scope", equalTo("APP"))
		.header("server", "Apache/2.4.52 (Ubuntu)").extract().response().asString();
		
		System.out.println("Response: " + response);
		
		JsonPath js=new JsonPath(response);  // for parsing json
		String placeId=js.get("place_id");
		System.out.println("placeId is : " + placeId);
		
		
		
		// Update Place
		String newAddress="Summer Walk Africa";
		
		String putBody = payload.UpdatePlace(placeId,newAddress);
		String PutResponse=given().log().all().queryParam("key", "qaclick123 ").header("Content-Type","application/json")
		.body(putBody)
		.when().put("maps/api/place/update/json")
		.then().log().all().assertThat().statusCode(200)
		.body("msg", equalTo("Address successfully updated"))
		.extract().response().asString();
		
		System.out.println("Response: " + PutResponse);
		
		//Get Place
		
		String getPlaceResponse=given().log().all().queryParam("key", "qaclick123")
		.queryParam("place_id", placeId)
		.when().get("/maps/api/place/get/json")
		.then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		JsonPath js1=ReUsableMethods.rawToJson(getPlaceResponse);
		
		//JsonPath js1=new JsonPath(getPlaceResponse);
		String actualAddress=js1.getString("address");
		System.out.println(actualAddress);
		
		Assert.assertEquals(actualAddress, newAddress);
		
		
	}

}
