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
import Models.Option;
import ninja.Context;
import ninja.Result;
import ninja.Results;

import com.google.inject.Singleton;
import ninja.params.PathParam;

import java.util.ArrayList;

@Singleton
public class ApplicationController {

    public Result index() {
        return Results.html().template("views/Blackjack.ftl.html");
    }

    public Result blackjackInitialization(){
        //=================================================================================
        //Remove test data before the assignment is due
        //=================================================================================
        String backURL = "assets/cards/cardback.jpg";

        Hand dealerHand = new Hand();
        dealerHand.cards.add(new Card(backURL, "assets/cards/2Clubs.png", false));
        dealerHand.cards.add(new Card("assets/cards/7Clubs.png", backURL, true));
        dealerHand.status = "";

        Hand playerHand = new Hand();
        playerHand.cards.add(new Card("assets/cards/14Diamonds.png", backURL, true));
        playerHand.cards.add(new Card("assets/cards/6Clubs.png", backURL, true));
        playerHand.bet = 2;
        playerHand.status = "";
        playerHand.handOptions.add(new Option("hit", "Hit"));
        playerHand.handOptions.add(new Option("stay", "Stay"));
        playerHand.handOptions.add(new Option("doubleDown", "Double Down"));
        playerHand.handOptions.add(new Option("split", "Split"));

        Hand playerHand2 = new Hand();
        playerHand2.cards.add(new Card("assets/cards/14Diamonds.png", backURL, true));
        playerHand2.cards.add(new Card("assets/cards/7Hearts.png", backURL, true));
        playerHand2.cards.add(new Card("assets/cards/12Hearts.png", backURL, true));
        playerHand2.bet = 4;
        playerHand2.status = "";
        playerHand2.handOptions.add(new Option("hit", "Hit"));
        playerHand2.handOptions.add(new Option("stay", "Stay"));
        playerHand2.handOptions.add(new Option("doubleDown", "Double Down"));
        playerHand2.handOptions.add(new Option("split", "Split"));

        Hand playerHand3 = new Hand();
        playerHand3.cards.add(new Card("assets/cards/14Diamonds.png", backURL, true));
        playerHand3.cards.add(new Card("assets/cards/12Diamonds.png", backURL, true));
        playerHand3.bet = 2;
        playerHand3.status = "";
        playerHand3.handOptions.add(new Option("hit", "Hit"));
        playerHand3.handOptions.add(new Option("stay", "Stay"));
        playerHand3.handOptions.add(new Option("doubleDown", "Double Down"));
        playerHand3.handOptions.add(new Option("split", "Split"));

        Blackjack blackjack = new Blackjack();

        blackjack.errorState = false;
        blackjack.gameOptions.add(new Option("newRound", "Deal"));
        blackjack.dealerTurnInProgress = false;
        blackjack.playerBalance = 1;
        blackjack.dealerHand = dealerHand;
        blackjack.playerHands.add(playerHand);
        blackjack.playerHands.add(playerHand2);
        blackjack.playerHands.add(playerHand3);
        //=================================================================================
        //Remove test data before the assignment is due
        //=================================================================================

        return Results.json().render(blackjack);
    }

    public Result newRound(Blackjack blackjack){

        //Check that player is short of funds before enabling the error state
        if(blackjack.playerBalance<2){
            blackjack.errorState = true;
        }
        return Results.json().render(blackjack);
    }

    public Result concludeRound(Blackjack blackjack){

        //

        return Results.json().render(blackjack);
    }

    public Result dealerAction(Blackjack blackjack){

        //

        return Results.json().render(blackjack);
    }

    public Result hit(@PathParam("hand") int handIndex, Blackjack blackjack){
        //If given an invalid index we should probably put up an error state

        //=================================================================================
        //Just a test for now
        String frontURL = "assets/cards/14Hearts.png";
        String backURL = "assets/cards/cardback.jpg";
        blackjack.playerHands.get(handIndex).cards.add(new Card(frontURL, backURL, true));
        //=================================================================================

        return Results.json().render(blackjack);
    }

    public Result doubleDown(@PathParam("hand") String handIndex, Blackjack blackjack){
        //If given an invalid index we should probably put up an error state

        //

        return Results.json().render(blackjack);
    }

    public Result split(@PathParam("hand") int handIndex, Blackjack blackjack){
        //If given an invalid index we should probably put up an error state

        //

        return Results.json().render(blackjack);
    }

    public Result stay(@PathParam("hand") int handIndex, Blackjack blackjack){
        //If given an invalid index we should probably put up an error state

        //

        return Results.json().render(blackjack);
    }
}
