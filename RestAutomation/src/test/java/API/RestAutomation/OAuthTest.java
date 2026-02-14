package API.RestAutomation;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.Assert;

import io.restassured.path.json.JsonPath;
import pojo.Api;
import pojo.Courses;
import pojo.GetCourse;
import pojo.WebAutomation;

public class OAuthTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String[] courseTitle= {"Selenium WebDriver Java", "Cypress", "Protractor"};		
		
		String response=given()
		.formParams("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
		.formParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
		.formParams("grant_type", "client_credentials")
		.formParams("scope", "trust")
		.when().log().all()
		.post("https://rahulshettyacademy.com/oauthapi/oauth2/resourceOwner/token").asString();
		
		
		System.out.println("Response: "+response);
		
		JsonPath jsonPath = new JsonPath(response);
		
		String accessToken = jsonPath.getString("access_token");
		System.out.println("Access Tocken: "+accessToken);
		
		GetCourse gc=given().queryParams("access_token", accessToken)
				.when()
				.get("https://rahulshettyacademy.com/oauthapi/getCourseDetails")
				.as(GetCourse.class);
		
		System.out.println("GC: " +gc);
		
		System.out.println(gc.getlinkedIn());
		System.out.println(gc.getInstructor());
		
		//API - courses
		//it will give the title at index 1
		String title=gc.getCourses().getApi().get(1).getCourseTitle();
		System.out.println("getCourses Title: " + title);
		//index is dynamic then we can use below approach
		List<Api> apiCourses =gc.getCourses().getApi();
		
		for(int i=0; i<apiCourses.size(); i++) {
			
			if(apiCourses.get(i).getCourseTitle().equalsIgnoreCase("SoapUI webservices testing")) {
				
				String priceOfCourse =apiCourses.get(i).getPrice();
				System.out.println(priceOfCourse);
				
			}
			
		}
		
		//webautomation courses
		
		String WebCoursesTitle=gc.getCourses().getWebAutomation().get(0).getCourseTitle();
		
		//index is dynamic then we can use below approach
		
		ArrayList<String>a=new ArrayList<String>();
		
				List<WebAutomation> webCourses =gc.getCourses().getWebAutomation();
				
				System.out.println("*****WebAutomation details*******");
				for(int i=0; i<webCourses.size(); i++) {
					
					a.add(webCourses.get(i).getCourseTitle());
					System.out.println(webCourses.get(i).getCourseTitle());
					System.out.println(webCourses.get(i).getPrice());
					}
				
				List<String> expectedList=Arrays.asList(courseTitle);
				
				Assert.assertTrue(a.equals(expectedList));
				
				}

		
		

		

	}


