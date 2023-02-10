package com.example.starter;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.ext.web.openapi.RouterBuilder;

public class MainVerticle extends AbstractVerticle {

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
	RouterBuilder.create(vertx, MainVerticle.class.getClassLoader().getResource("api.yml").getFile())
	.onSuccess( builder -> {
		builder.operation("updateExperiment").handler(context -> {
			context.response().end("Success");
		});
    vertx.createHttpServer().requestHandler(builder.createRouter()).listen(8080, http -> {
      if (http.succeeded()) {
        startPromise.complete();
        System.out.println("HTTP server started on port 8080");
      } else {
        startPromise.fail(http.cause());
      }
    });
	})
	.onFailure(err -> startPromise.fail(err));
  }

}
