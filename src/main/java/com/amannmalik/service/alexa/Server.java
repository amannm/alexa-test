package com.amannmalik.service.alexa;

import com.amannmalik.service.alexa.testskill.TestSpeechlet;
import com.amannmalik.service.alexa.testskill.TestSpeechletServlet;
import com.amazon.speech.speechlet.servlet.SpeechletServlet;
import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.handlers.PathHandler;
import io.undertow.server.handlers.resource.ClassPathResourceManager;
import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.DeploymentManager;

import javax.servlet.ServletException;

/**
 * Created by Amann Malik
 */

public class Server {

    public static void main(String[] args) throws ServletException {

        DeploymentInfo servletBuilder = Servlets.deployment()
                .setClassLoader(Server.class.getClassLoader())
                .setResourceManager(new ClassPathResourceManager(Server.class.getClassLoader()))
                .setContextPath("/services")
                .setDeploymentName("services.war")
                .addServlets(
                        Servlets.servlet(TestSpeechletServlet.class)
                                .setLoadOnStartup(1)
                                .addMapping("/test")
                );

        DeploymentManager manager = Servlets.defaultContainer().addDeployment(servletBuilder);
        manager.deploy();
        HttpHandler servletHandler = manager.start();
        PathHandler path = Handlers.path(Handlers.redirect("/services"))
                .addPrefixPath("/services", servletHandler);

        Undertow server = Undertow.builder().addHttpListener(8080, "localhost").setHandler(path).build();

        server.start();

    }

}
