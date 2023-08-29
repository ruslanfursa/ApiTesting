package api;

import io.restassured.http.ContentType;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.stream.Collectors;

import static api.Specifications.*;
import static io.restassured.RestAssured.given;

public class ReqresTest {
    private final static String URL = "https://reqres.in";

    @Test
    public void testIfAvatarContainsId() {
        Specifications.installSpecifications(Specifications.requestSpec(URL), Specifications.responseSpec(200));
        List<UserData> users = given()
                .when()
//                .contentType(ContentType.JSON)
                .get("/api/users?page=2")
                .then().log().all()
                .extract().body().jsonPath().getList("data", UserData.class);
//        users.forEach(x -> Assert.assertTrue(x.getAvatar().contains(x.getId().toString())));
//        for (UserData us : users) {
//            Assert.assertTrue(us.getAvatar().contains(us.getId().toString()));
//        }
        List<String> avatars = users.stream().map(UserData::getAvatar).toList();
        List<String> ids = users.stream().map(x -> x.getId().toString()).toList();

        for (int i = 0; i < avatars.size(); i++) {
            Assert.assertTrue(avatars.get(i).contains(ids.get(i)));
        }
    }

    @Test
    public void testSuccessRegistration() {
        Specifications.installSpecifications(Specifications.requestSpec(URL), Specifications.responseSpec(200));

        Integer id = 4;
        String token = "QpwL5tke4Pnpja7X4";

        Registration newUser = new Registration("eve.holt@reqres.in", "pistol");
        SuccessRegistration successRegistration = given()
                .when()
                .body(newUser)
                .post("/api/register")
                .then().log().all()
                .extract().as(SuccessRegistration.class);
        Assert.assertEquals(id, successRegistration.getId());
        Assert.assertEquals(token, successRegistration.getToken());
    }

    @Test
    public void testUnsuccessfulRegistration() {
        Specifications.installSpecifications(Specifications.requestSpec(URL), Specifications.responseSpec(400));
        String errorMessage = "Missing password";

        Registration newUser = new Registration("sydney@fife", "");
        UnSuccessRegistration unSuccessRegistration = given()
                .when()
                .body(newUser)
                .post("/api/register")
                .then().log().all()
                .extract().as(UnSuccessRegistration.class);
        Assert.assertEquals(errorMessage, unSuccessRegistration.getError());
    }

    @Test
    public void testDeleteUser() {
        Specifications.installSpecifications(Specifications.requestSpec(URL), Specifications.responseSpec(204));

        given()
                .when()
                .delete("/api/users/2")
                .then().log().all();

    }
}
