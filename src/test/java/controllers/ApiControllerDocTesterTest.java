/**
 * Copyright (C) 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package controllers;


import Models.Blackjack;
import Models.Card;
import Models.Hand;
import Models.PlayerHand;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import ninja.NinjaDocTester;
import org.doctester.testbrowser.Request;
import org.doctester.testbrowser.Response;
import org.hamcrest.CoreMatchers;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class ApiControllerDocTesterTest extends NinjaDocTester {
    
    String URL_INDEX = "/";
    String URL_BLACKJACK_INITIALIZATION = "/blackjackInitialization";
    String URL_NEW_ROUND = "/newRound";
    String URL_CONCLUDE_ROUND = "/concludeRound";
    String URL_DEALER_ACTION = "/dealerAction";
    String URL_HIT = "/hit";
    String URL_DOUBLE_DOWN = "/doubleDown";
    String URL_SPLIT = "/split";
    String URL_STAY = "/stay";

    @Test
    public void testIndex(){
        Response response = makeRequest(
                Request.GET().url(
                        testServerUrl().path(URL_INDEX)));

       assertEquals(response.httpStatus, 200);
    }

    @Test
    public void testBlackjackInitialization(){
        Response response = makeRequest(
                Request.GET().url(
                        testServerUrl().path(URL_BLACKJACK_INITIALIZATION)));

        assertEquals(response.httpStatus, 200);
        //We might want to test that blackjack was updated correctly
    }

    @Test
    public void testNewRoundThrowsSetsErrorState(){
        Blackjack blackjack = new Blackjack(100, 2);
        blackjack.errorState = false;
        blackjack.playerBalance = 1;
        Response response = makeRequest(
                Request.POST().url(
                        testServerUrl().path(URL_NEW_ROUND)).payload(blackjack).contentTypeApplicationJson());

        //Test that the POST  was successful
        assertEquals(response.httpStatus, 200);

        //Test that the error state was set to true
        assertEquals(response.payloadAs(Blackjack.class).errorState, true);
    }

    @Test
    public void testConcludeRound(){
        Blackjack blackjack = new Blackjack(100, 2);
        Response response = makeRequest(
                Request.POST().url(
                        testServerUrl().path(URL_CONCLUDE_ROUND)).payload(blackjack).contentTypeApplicationJson());

        assertEquals(response.httpStatus, 200);

        //We might want to test that blackjack was updated correctly
    }

    @Test
    public void testDealerAction(){
        Blackjack blackjack = new Blackjack(100, 2);
        Response response = makeRequest(
                Request.POST().url(
                        testServerUrl().path(URL_DEALER_ACTION)).payload(blackjack).contentTypeApplicationJson());

        assertEquals(response.httpStatus, 200);

        //We might want to test that blackjack was updated correctly
    }

    @Test
    public void testHit(){
        Blackjack blackjack = new Blackjack(100, 2);
        PlayerHand hand = new PlayerHand(new ArrayList<Card>(), "");
        blackjack.playerHands.add(hand);
        Response response = makeRequest(
                Request.POST().url(
                        testServerUrl().path(URL_HIT + "/" + 0)).payload(blackjack).contentTypeApplicationJson());

        assertEquals(response.httpStatus, 200);

        //We might want to test that blackjack was updated correctly
        //Hand has one more card and status might have changed
    }

    @Test
    public void testDoubleDown(){
        Blackjack blackjack = new Blackjack(100, 2);
        Response response = makeRequest(
                Request.POST().url(
                        testServerUrl().path(URL_DOUBLE_DOWN + "/" + 0)).payload(blackjack).contentTypeApplicationJson());

        assertEquals(response.httpStatus, 200);

        //We might want to test that blackjack was updated correctly
        //Hand has one more card, bet was doubled and status might have changed
    }

    @Test
    public void testSplit(){
        Blackjack blackjack = new Blackjack(100, 2);
        Response response = makeRequest(
                Request.POST().url(
                        testServerUrl().path(URL_SPLIT + "/" + 0)).payload(blackjack).contentTypeApplicationJson());

        assertEquals(response.httpStatus, 200);

        //We might want to test that blackjack was updated correctly
        //New hand was created from the second card of the initial hand. Both hands now have a new card. ect..
    }

    @Test
    public void testStay(){
        Blackjack blackjack = new Blackjack(100, 2);
        Response response = makeRequest(
                Request.POST().url(
                        testServerUrl().path(URL_STAY + "/" + 0)).payload(blackjack).contentTypeApplicationJson());

        assertEquals(response.httpStatus, 200);

        //We might want to test that blackjack was updated correctly
        //Hand has no more options. Dealers turn may have begun
    }
}
