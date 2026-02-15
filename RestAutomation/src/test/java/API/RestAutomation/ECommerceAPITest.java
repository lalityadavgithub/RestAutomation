package API.RestAutomation;

import static io.restassured.RestAssured.given;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import pojo.LoginRequest;
import pojo.LoginResponse;

public class ECommerceAPITest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		RequestSpecification req=new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").setContentType(ContentType.JSON).build();
		
		LoginRequest loginRequest=new LoginRequest();
		loginRequest.setUserEmail("rahulshetty@gmail.com");
		loginRequest.setUserPassword("Iamking@000");
		
		
		RequestSpecification reqLogin=given().log().all().spec(req).body(loginRequest);
		LoginResponse loginResponse=reqLogin.when().post("/api/ecom/auth/login").then().log().all()
		.extract().response().as(LoginResponse.class);
		
		System.out.println(loginResponse.getTocken());
		System.out.println(loginResponse.getUserId());

	}

}
