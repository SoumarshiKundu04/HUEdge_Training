package Test;

import Pojo.CreateJob;
import Pojo.Register;
import com.github.javafaker.Faker;
import genericFunctions.Generic;
import  io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.EndPoints;
import org.example.ExtentReportManager;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import utils.ConfigManager;

import java.io.IOException;

@Listeners(ExtentReportManager.class)
public class APITests {

    CreateJob CreatePayLoad;
    Register registerPayLoad;
    Generic gf;
    Faker faker;
    private static Logger log= LogManager.getLogger(APITests.class);
    ConfigManager configManager;

    @BeforeClass
    public void setup() throws IOException {

        faker=new Faker();
        CreatePayLoad=new CreateJob();
        registerPayLoad=new Register();
        gf=new Generic();
        configManager=ConfigManager.getInstance();
        RestAssured.baseURI=EndPoints.baseURL;
        CreatePayLoad.setName(faker.name().firstName());
        CreatePayLoad.setJob(faker.job().position());
        registerPayLoad.setEmail(configManager.getString("email"));
        registerPayLoad.setPassword(configManager.getString("password"));
        log.info("set up done");
    }

    @Test
    public void createRequest(){
        Response res= RestAssured.given()
                .basePath(EndPoints.POST_Create_User)
                .contentType(ContentType.JSON)
                .body(CreatePayLoad).log().all()
                .when()
                .post();

        System.out.println(res.statusCode());
        Assert.assertEquals(res.statusCode(),201);
        log.info("Create request passed");
    }
    @Test
    public void Register(){
        Response res=RestAssured.given()
                .basePath(EndPoints.POST_Registr)
                .contentType(ContentType.JSON)
                .body(registerPayLoad)
                .log().all()
                .when()
                .post();

        System.out.println(res.prettyPrint());
        Assert.assertEquals(res.statusCode(),200);
        log.info("Register passed");

    }
    @Test(dependsOnMethods = "Register")
    public void Login(){
        Response res=RestAssured.given()
                .basePath(EndPoints.POST_Login)
                .contentType(ContentType.JSON)
                .body(registerPayLoad)
                .log().all()
                .when()
                .post();

        System.out.println(res.prettyPrint());
        Assert.assertEquals(res.statusCode(),200);
        Assert.assertEquals(res.path("token"),configManager.getString("token"));
        log.info("login passed");
    }
    @Test
    public  void validatePaginationLimit1() throws IOException {
        Object object[][]=gf.ReadExcelData("apidata","Sheet1");
        Response res=RestAssured.given()
                .basePath(EndPoints.POST_Create_User)
                .queryParam("page","1")
                .contentType(ContentType.JSON)

                .log().all()
                .when()
                .get();

        System.out.println(res.prettyPrint());
        Assert.assertEquals(res.statusCode(),200);
        JSONObject obj = new JSONObject(res.getBody().asString());
        JSONArray arr = obj.getJSONArray("data");
        for(int i=1;i<object.length;i++){
            JSONObject objind=arr.getJSONObject(i-1);
            int resid=(int) objind.get("id");
            String resemail=(String) objind.get("email");
            String resfirst_name=(String) objind.get("first_name");
            String reslast_name=(String) objind.get("last_name");
            int expid=(int) Double.parseDouble(object[i][0].toString());
            String expemail=(String) object[i][1];
            String expfirst_name=(String) object[i][2];
            String explast_name=(String) object[i][3];
            Assert.assertEquals(resid,expid);
            Assert.assertEquals(resemail,expemail);
            Assert.assertEquals(resfirst_name,expfirst_name);
            Assert.assertEquals(reslast_name,explast_name);
        }

        log.info("page 1 verification passed");
    }
    @Test
    public  void validatePaginationLimit2() throws IOException {
        Object object[][]=gf.ReadExcelData("apidata","Sheet2");
        Response res=RestAssured.given()
                .basePath(EndPoints.POST_Create_User)
                .queryParam("page","2")
                .contentType(ContentType.JSON)

                .log().all()
                .when()
                .get();

        System.out.println(res.prettyPrint());
        Assert.assertEquals(res.statusCode(),200);
        JSONObject obj = new JSONObject(res.getBody().asString());
        JSONArray arr = obj.getJSONArray("data");
        for(int i=1;i<object.length;i++){
            JSONObject objind=arr.getJSONObject(i-1);
            int resid=(int) objind.get("id");
            String resemail=(String) objind.get("email");
            String resfirst_name=(String) objind.get("first_name");
            String reslast_name=(String) objind.get("last_name");
            int expid=(int) Double.parseDouble(object[i][0].toString());
            String expemail=(String) object[i][1];
            String expfirst_name=(String) object[i][2];
            String explast_name=(String) object[i][3];
            Assert.assertEquals(resid,expid);
            Assert.assertEquals(resemail,expemail);
            Assert.assertEquals(resfirst_name,expfirst_name);
            Assert.assertEquals(reslast_name,explast_name);
        }

        log.info("page 2 verification passed");
    }

    @Test
    public void createNegativeScenario(){
        JSONObject Obj = new JSONObject();
        Obj.put("name","abc@gmail.com");
        Response res= RestAssured.given()
                .basePath(EndPoints.POST_Create_User)
                .contentType(ContentType.JSON)
                .body(Obj).log().all()
                .when()
                .post();

        Assert.assertEquals(res.statusCode(),201);
        log.info("create task negative scenario passed");
    }

    @Test
    public void registerNegativeScenario(){
        Response res1=RestAssured.given()
                .basePath(EndPoints.POST_Registr)
                .contentType(ContentType.JSON)
                .body(registerPayLoad)
                .log().all()
                .when()
                .post();

        Response res2=RestAssured.given()
                .basePath(EndPoints.POST_Registr)
                .contentType(ContentType.JSON)
                .body(registerPayLoad)
                .log().all()
                .when()
                .post();
        System.out.println(res2.prettyPrint());
        Assert.assertEquals(res2.statusCode(),200);
        JSONObject object = new JSONObject();
        object.put("email","sydney@fife");
        Response res3=RestAssured.given()
                .basePath(EndPoints.POST_Registr)
                .contentType(ContentType.JSON)
                .body(object)
                .log().all()
                .when()
                .post();
        Assert.assertEquals(res3.statusCode(),400);
        log.info("register negative scenario passed");
    }

    @Test
    public void singleUserNegativeScenario(){

        Response res= RestAssured.given()
                .basePath(EndPoints.POST_Create_User+"/23")
                .contentType(ContentType.JSON)
                .log().all()
                .when()
                .get();

        Assert.assertEquals(res.statusCode(),404);
        log.info("Single user negative scenario passed");
    }

    @Test
    public void LoginNegativeScenario(){
        JSONObject object = new JSONObject();
        object.put("email","peter@klaven");
        Response res=RestAssured.given()
                .basePath(EndPoints.POST_Login)
                .contentType(ContentType.JSON)
                .body(object)
                .log().all()
                .when()
                .post();

        System.out.println(res.prettyPrint());
        Assert.assertEquals(res.statusCode(),400);
        log.info("Login negative scenario passed");
    }

    @Test
    public void deleteNegativeScenario(){
        Response res= RestAssured.given()
                .basePath(EndPoints.POST_Create_User+"/23")
                .contentType(ContentType.JSON)
                .log().all()
                .when()
                .delete();

        Assert.assertEquals(res.statusCode(),204);
        log.info("delete negative scenario passed");
    }

}
