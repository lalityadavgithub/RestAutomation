package API.RestAutomation;

import files.payload;
import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {

	public static void main(String[] args) {
		
		// TODO Auto-generated method stub
		
		int allPrice=0;
		
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
			//int copies=js.getInt("courses["+i+"].copies");

			
			System.out.println("title: " + titles);
			System.out.println("price: " + price);
		//	System.out.println("copies: "+ copies );
			
		}
		
		System.out.println("Print no of copies sold bt RPA Courses");
		
	for(int i=0; i<count; i++) {
			
		String titles=js.get("courses["+i+"].title");
		
		if(titles.equalsIgnoreCase("RPA")) {
			
			
			int copies=js.getInt("courses["+i+"].copies");
			
			System.out.println("copies: " + copies);
			break;
		}
		}
	
	/* System.out.println("Verify if Sum of all Courses prices matches with Purchase Amount");
	
	for(int i=0; i<count; i++) {
		
	
			int prices=js.getInt("courses["+i+"].price");
			
			allPrice=allPrice +prices;
			
		}
	System.out.println("TotalAmount: " + allPrice);
	
	if(allPrice==totalAmount) {
		
		System.out.println("Sum of all Courses prices matches with Purchase Amount");
	}else {
		System.out.println("Not Matching");

	}
	*/
	
	}

}
