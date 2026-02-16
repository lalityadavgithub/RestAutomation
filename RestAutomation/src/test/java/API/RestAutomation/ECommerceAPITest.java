package API.RestAutomation;

import static io.restassured.RestAssured.given;

import java.io.File;
import java.io.ObjectInputFilter.Status;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.methods.RequestBuilder;
import org.testng.Assert;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import pojo.LoginRequest;
import pojo.LoginResponse;
import pojo.OrderDetail;
import pojo.Orders;

public class ECommerceAPITest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		RequestSpecification req=new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").setContentType(ContentType.JSON).build();
		
		LoginRequest loginRequest=new LoginRequest();
		loginRequest.setUserEmail("rahulshetty@gmail.com");
		loginRequest.setUserPassword("Iamking@000");
		
		
		//bypass SSL certification user : relaxedHTTPSValidation
		RequestSpecification reqLogin=given().relaxedHTTPSValidation().log().all().spec(req).body(loginRequest);
		LoginResponse loginResponse=reqLogin.when().post("/api/ecom/auth/login").then().log().all()
		.extract().response().as(LoginResponse.class);
		
		String token=loginResponse.getToken();
		String userId=loginResponse.getUserId();
		System.out.println(token);
		System.out.println(userId);
		
		// Add Product
		
		RequestSpecification addProductBaseReq=new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("authorization", token)
				.build();
		
		RequestSpecification reqAddProduct=given().log().all().spec(addProductBaseReq).param("productName", "Laptop")
		.param("productAddedBy", userId).param("productCategory", "fashion")
		.param("productSubCategory", "shirts").param("productPrice", 11500)
		.param("productDescription", "Lenova").param("productFor", "men")
		.multiPart("productImage", new File("C://Users//Administrator//Documents//laptop.png"));
		
		//Getting garbage response section 13 lec 70
		String addProductResponse=reqAddProduct.when().post("/api/ecom/product/add-product")
		.then().log().all().extract().response().asString();
		
		JsonPath js= new JsonPath(addProductResponse);
		
		String productId=js.get("productId");
	//	String statusCode=js.get("status");
		
		//Assert.assertEquals(statusCode, 200);
		
		
		//Create Order
		
		RequestSpecification createOrderBaseRequest=new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("authorization", token).setContentType(ContentType.JSON)
				.build();
		
		OrderDetail orderDetails=new OrderDetail();
		orderDetails.setCountry("India");
		orderDetails.setProductOrderId(productId);
		
		List<OrderDetail> orderDetailList=new ArrayList<OrderDetail>();
		orderDetailList.add(orderDetails);
		Orders order=new Orders();
		order.setOrders(orderDetailList);
		
		RequestSpecification createOrderReq=given().spec(createOrderBaseRequest).body(order);
		
		String responseAddOrder=createOrderReq.when().post("/api/ecom/order/create-order").then().extract().response().asString();

		System.out.println(responseAddOrder);
		
		//Delete Product
		
		
		RequestSpecification deleteProdBaseReq=new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("authorization", token).setContentType(ContentType.JSON)
				.build();
		
		RequestSpecification deleteProdReq=given().log().all().spec(deleteProdBaseReq).pathParam("productId", productId);
		
		//use curly bracket it should consider path parameter
		String deleteProductResponse=deleteProdReq.when().delete("/api/ecom/product/delete-product/{productId}").then().log().all().extract().response().asString();

		JsonPath js1=new JsonPath(deleteProductResponse);
		Assert.assertEquals("Product Deleted Successfully", js1.get("message"));

		
	}

}
