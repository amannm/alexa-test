package com.amannmalik.service.alexa.testskill;

import com.amazon.speech.speechlet.servlet.SpeechletServlet;

/**
 * Created by Amann on 6/7/2016.
 */
public class TestSpeechletServlet  extends SpeechletServlet {
    public TestSpeechletServlet() {
        super();
        setSpeechlet(new TestSpeechlet());
    }
}
