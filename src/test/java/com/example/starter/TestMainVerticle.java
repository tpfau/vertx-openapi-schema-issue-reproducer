package com.example.starter;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.HttpRequest;
import io.vertx.ext.web.client.WebClient;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;

@ExtendWith(VertxExtension.class)
public class TestMainVerticle {

  @BeforeEach
  void deploy_verticle(Vertx vertx, VertxTestContext testContext) {
    vertx.deployVerticle(new MainVerticle(), testContext.succeeding(id -> testContext.completeNow()));
  }

  @Test
  void verticle_deployed(Vertx vertx, VertxTestContext testContext) throws Throwable {	  
		JsonObject JsonToValidate = new JsonObject(Files.readString(Paths.get(TestMainVerticle.class.getClassLoader().getResource("testData.txt").getPath())));
		System.out.println(JsonToValidate.encodePrettily());
		WebClient client = WebClient.create(vertx);
		HttpRequest request =  client.post(8080,"localhost","/experiment/my/target");
		request.sendJsonObject(JsonToValidate);
  }
}
