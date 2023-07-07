package api.tests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.UserEndPoints;
import api.payload.User;
import io.restassured.response.Response;

public class UserTest {
	Faker faker;
	User userpayload;
	public Logger logger;
	@BeforeClass
	public void setup() {
		faker=new Faker();
		userpayload=new User();
		userpayload.setId(faker.idNumber().hashCode());
		userpayload.setUsername(faker.name().username());
		userpayload.setFirstName(faker.name().firstName());
		userpayload.setLastName(faker.name().lastName());
		userpayload.setEmail(faker.internet().safeEmailAddress());
		userpayload.setPassword(faker.internet().password());
		userpayload.setPhone(faker.phoneNumber().cellPhone());
		
		logger=LogManager.getLogger(this.getClass());
		
	}
	@Test(priority=1)
	public void testPostUser() {
		logger.info("*********logging started******************");
		Response response=UserEndPoints.createUser(userpayload);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
	}
	@Test(priority=2)
	public void testGetUser() {
		Response response=UserEndPoints.getUser(this.userpayload.getUsername());
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
	}
	@Test(priority=3)
	public void testPutUser() {
		userpayload.setFirstName(faker.name().firstName());
		userpayload.setLastName(faker.name().lastName());
		userpayload.setEmail(faker.internet().safeEmailAddress());
		
		Response response=UserEndPoints.updateUser(this.userpayload.getUsername(), userpayload);
		response.then().log().body();
		Assert.assertEquals(response.getStatusCode(), 200);
		Response responseafterupdate=UserEndPoints.getUser(this.userpayload.getUsername());
		Assert.assertEquals(responseafterupdate.getStatusCode(), 200);
	}
	@Test(priority=4)
	public void testDeleteUser() {
		Response response=UserEndPoints.deleteUser(this.userpayload.getUsername());
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
	}
	

}
