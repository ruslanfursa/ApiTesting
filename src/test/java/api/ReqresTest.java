package api;

import io.restassured.http.ContentType;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;

public class ReqresTest {
    private final static String URL = "https://reqres.in";
    @Test
    public void testIfAvatarContainsId(){
        List<UserData> users = given()
                .when()
                .contentType(ContentType.JSON)
                .get(URL + "/api/users?page=2")
                .then().log().all()
                .extract().body().jsonPath().getList("data", UserData.class);
//        users.forEach(x -> Assert.assertTrue(x.getAvatar().contains(x.getId().toString())));
        for(UserData us : users){
            Assert.assertTrue(us.getAvatar().contains(us.getId().toString()));
        }
    }
}
