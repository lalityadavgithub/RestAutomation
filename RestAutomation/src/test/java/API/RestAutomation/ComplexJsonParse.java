package API.RestAutomation;

import files.payload;
import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		JsonPath js=new JsonPath(payload.CoursePrice());
		
		//print number of courses return by API
		int count=js.getInt("courses.size()");
		System.out.println(count);
		
		//Print purchase amount
		int totalAmount=js.getInt("dashboard.purchaseAmount");
		System.out.println(totalAmount);
		
		//Print Title of the first courses
		String titleFirstCourse=js.get("courses[0].title");
		System.out.println(titleFirstCourse);

		//Print All course title and their respective
		
		for(int i=0; i<count; i++) {
			
			String titles=js.get("courses["+i+"].title");
			int price=js.getInt("courses["+i+"].price");
			
			System.out.println("title: " + titles);
			System.out.println("price: " + price);
			System.out.println();
			
		}
	}

}
