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

import Models.*;
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

        Blackjack blackjack = new Blackjack();

        blackjack.playingCards = new PlayingCardsContainer();
        blackjack.dealerHand = new DealerHand(blackjack.playingCards.drawCards(2), "");
        blackjack.playerHands.add(new PlayerHand(blackjack.playingCards.drawCards(2), ""));
        blackjack.playerHands.get(0).resetHand();
        blackjack.errorState = false;
        blackjack.gameOptions.add(new Option("newRound", "Deal"));
        blackjack.dealerTurnInProgress = false;
        blackjack.playerBalance = 100;

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

        //We need to check that the player has enough or throw error state

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
