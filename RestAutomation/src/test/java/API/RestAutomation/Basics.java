package API.RestAutomation;

import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import files.payload;


public class Basics {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//given - all input details
		//when - Submit the API
		//Then -  validate the response
		
		RestAssured.baseURI="https://rahulshettyacademy.com";
		String response=given().log().all().queryParam("key" , "qaclick123").header("Content-Type","application/json")
		.body(payload.AddPlace())
		.when().post("/maps/api/place/add/json")
		.then().assertThat().statusCode(200).body("scope", equalTo("APP"))
		.header("server", "Apache/2.4.52 (Ubuntu)").extract().response().asString();
		
		System.out.println("Response: " + response);
		
		JsonPath js=new JsonPath(response);  // for parsing json
		String placeId=js.get("place_id");
		System.out.println("placeId is : " + placeId);
		
		
		
		// Update Place
		
		
		
	}

}
