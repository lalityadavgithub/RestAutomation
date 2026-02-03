package files;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class DynamicJson {
	
	
	@Test(dataProvider="Booksdata")
	public void addBook(String isbn, String aisle) {
		
		RestAssured.baseURI="http://216.10.245.166";
		
		String response=given().header("Content-Type", "application/json")
		.body(payload.AddBook(isbn,aisle))
		.when()
		.post("Library/Addbook.php")
		.then().log().all().assertThat().statusCode(200)
		.extract().response().asString();
		
		JsonPath js=ReUsableMethods.rawToJson(response);
		
		String id=js.get("ID");
		System.out.println(id);
		
		
	}
	
	@DataProvider(name="Booksdata")
	public Object[][] getData() {
		
		//array: collection of element
		//multi dimentional arrays: collection of array
		
		return new Object[][] {{"tet69","2346"}, {"tesh","8995"}, {"hh77k","97607"} };
	}
	
	@Test(dataProvider="Booksdata")
	public void deleteBook(String isbn, String aisle) {
		
		String delete=given().header("Content-Type","application/json")
		.body(payload.AddBook(isbn,aisle))
		.when().delete("Library/Addbook.php")
		.then().log().all().assertThat().statusCode(200)
		.extract().response().asString();
	}
	

}
