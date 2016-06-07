package com.amannmalik.service.alexa.testskill;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.slu.Slot;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.LaunchRequest;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SessionEndedRequest;
import com.amazon.speech.speechlet.SessionStartedRequest;
import com.amazon.speech.speechlet.Speechlet;
import com.amazon.speech.speechlet.SpeechletException;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.Reprompt;
import com.amazon.speech.ui.SimpleCard;
import com.amazon.speech.ui.SsmlOutputSpeech;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Amann Malik
 */

public class TestSpeechlet implements Speechlet {

    private static final Logger LOG = LoggerFactory.getLogger(TestSpeechlet.class);

    private static final String SLOT_CITY = "City";
    private static final String SLOT_DATE = "Date";

    @Override
    public SpeechletResponse onLaunch(final LaunchRequest request, final Session session) throws SpeechletException {
        LOG.info("onLaunch requestId={}, sessionId={}", request.getRequestId(), session.getSessionId());

        String speechOutput = "<speak>"
                + "Hey."
                + "</speak>";
        String repromptText = "<speak>"
                +"What?"
                + "</speak>";

        return ask(speechOutput, repromptText);
    }

    @Override
    public SpeechletResponse onIntent(final IntentRequest request, final Session session) throws SpeechletException {
        LOG.info("onIntent requestId={}, sessionId={}", request.getRequestId(), session.getSessionId());

        Intent intent = request.getIntent();
        String intentName = intent.getName();

        switch(intentName) {
            case "TestIntent":
                Slot citySlot = intent.getSlot(SLOT_CITY);
                Slot dateSlot = intent.getSlot(SLOT_DATE);
                return tell("Ok");
            case "AMAZON.HelpIntent":
                return tell("Help yourself");
            case "AMAZON.StopIntent":
                return tell("No you stop");
            case "AMAZON.CancelIntent":
                return tell("Goodbye");
            default:
                throw new SpeechletException("Invalid Intent");
        }
    }


    @Override
    public void onSessionStarted(final SessionStartedRequest request, final Session session) throws SpeechletException {
        LOG.info("onSessionStarted requestId={}, sessionId={}", request.getRequestId(), session.getSessionId());
    }

    @Override
    public void onSessionEnded(final SessionEndedRequest request, final Session session) throws SpeechletException {
        LOG.info("onSessionEnded requestId={}, sessionId={}", request.getRequestId(), session.getSessionId());
    }

    private SpeechletResponse tell(String speechOutput) {

        PlainTextOutputSpeech outputSpeech = new PlainTextOutputSpeech();
        outputSpeech.setText(speechOutput);

        return SpeechletResponse.newTellResponse(outputSpeech);
    }

    private SpeechletResponse tell(String speechOutput, String cardTitle) {

        SimpleCard card = new SimpleCard();
        card.setTitle(cardTitle);
        card.setContent(speechOutput);

        PlainTextOutputSpeech outputSpeech = new PlainTextOutputSpeech();
        outputSpeech.setText(speechOutput);

        return SpeechletResponse.newTellResponse(outputSpeech, card);
    }


    private SpeechletResponse ask(String stringOutput) {

        SsmlOutputSpeech outputSpeech = new SsmlOutputSpeech();
        outputSpeech.setSsml(stringOutput);

        Reprompt reprompt = new Reprompt();
        reprompt.setOutputSpeech(outputSpeech);

        return SpeechletResponse.newAskResponse(outputSpeech, reprompt);
    }

    private SpeechletResponse ask(String stringOutput, String repromptText) {

        SsmlOutputSpeech outputSpeech = new SsmlOutputSpeech();
        outputSpeech.setSsml(stringOutput);

        SsmlOutputSpeech repromptOutputSpeech = new SsmlOutputSpeech();
        outputSpeech.setSsml(repromptText);

        Reprompt reprompt = new Reprompt();
        reprompt.setOutputSpeech(repromptOutputSpeech);

        return SpeechletResponse.newAskResponse(outputSpeech, reprompt);
    }

}
