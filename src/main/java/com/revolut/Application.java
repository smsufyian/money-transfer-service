package com.revolut;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.name.Names;
import com.revolut.api.TransferController;
import com.revolut.config.GuiceModule;
import com.revolut.error.TransferException;
import lombok.extern.slf4j.Slf4j;
import spark.ResponseTransformer;

import static java.net.HttpURLConnection.HTTP_INTERNAL_ERROR;
import static spark.Spark.*;

@Slf4j
public class Application {


    public static final String API_RESPONSE_TYPE = "application/json";

    public static void main(String... args) {
        log.info("Starting service");

        Injector injector = Guice.createInjector(new GuiceModule());
        TransferController transferController = injector.getInstance(TransferController.class);
        Gson gson = injector.getInstance(Gson.class);

        Integer port = injector.getInstance(Key.get(Integer.class, Names.named("app.port")));
        port(port);


        routeTransferApi(transferController, gson::toJson);


        log.info("Adding exception handlers");
        exception(TransferException.class, (e, req, res) -> {
            //log.error("Could not complete request {}", req.url(), e);
            log.error("Could not complete request {} because of {}", req.url(), e.getClass().getName(), e);
            res.status(e.getHttpStatus());
            res.type(API_RESPONSE_TYPE);
            JsonObject errorDto = new JsonObject();
            errorDto.addProperty("message", e.getMessage());
            errorDto.addProperty("code", e.getCode());
            res.body(errorDto.toString());
        });

        exception(Exception.class, (e, req, res) -> {
            log.error("Could not complete request {} because of {}", req.url(), e.getClass().getName(), e);
            res.type(API_RESPONSE_TYPE);
            JsonObject errorDto = new JsonObject();
            errorDto.addProperty("message", "Something went wrong");
            res.body(errorDto.toString());
            res.status(HTTP_INTERNAL_ERROR);
        });


        after((request, response) -> {
            response.type(API_RESPONSE_TYPE);
        });

        log.info("finishing booting up service");

    }

    public static void routeTransferApi(TransferController transferController, ResponseTransformer transformer) {

        log.info("Routing transfer api");

        post("/transfer", transferController::makeLocalTransfer, transformer);
        post("/transfer/search", transferController::searchTransfers, transformer);
        get("/transfer/:transferId", transferController::getTransfer, transformer);

    }




}
