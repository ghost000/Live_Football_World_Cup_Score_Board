package org.example.library.api;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.library.api.messages.AddNewGameRequest;
import org.example.library.api.messages.UpdateScoreRequest;
import org.example.library.model.Game;
import org.example.library.service.LiveFootballWorldCupScoreBoardLibrary;

import java.util.List;

@Path("/games")
public class LiveFootballWorldCupScoreBoardRestApi {

    @Inject
    LiveFootballWorldCupScoreBoardLibrary scoreBoardLibrary;

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addNewGame(AddNewGameRequest request) {
        try {
            System.console().printf(request.awayTeamName() + " " + request.homeTeamName());
            int gameId = scoreBoardLibrary.addNewGame(request.homeTeamName(), request.awayTeamName());
            return Response.ok("{\"message\":\"Game added with ID: " + gameId + "\"}").build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\":\"Invalid team names provided: " + e.getMessage() + "\"}")
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\":\"An unexpected error occurred: " + e.getMessage() + "\"}")
                    .build();
        }
    }

    @PUT
    @Path("/{id}/update-score")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateScore(@PathParam("id") int gameId, UpdateScoreRequest request) {
        try {
            scoreBoardLibrary.updateScore(gameId, request.homeTeamGoals(), request.awayTeamGoals());
            return Response.ok("{\"message\":\"Score updated for game ID: " + gameId + "\"}").build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\":\"Game with ID " + gameId + " not found.\"}")
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\":\"An unexpected error occurred: " + e.getMessage() + "\"}")
                    .build();
        }
    }

    @DELETE
    @Path("/{id}/finish")
    @Produces(MediaType.APPLICATION_JSON)
    public Response finishGame(@PathParam("id") int gameId) {
        try {
            scoreBoardLibrary.finishGame(gameId);
            return Response.ok("{\"message\":\"Game finished with ID: " + gameId + "\"}").build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\":\"Game with ID " + gameId + " not found.\"}")
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\":\"An unexpected error occurred: " + e.getMessage() + "\"}")
                    .build();
        }
    }

    @GET
    @Path("/summary")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getGameSummary() {
        try {
            List<Game> summary = scoreBoardLibrary.getASummary();
            return Response.ok(summary).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\":\"An unexpected error occurred while generating the summary: " + e.getMessage() + "\"}")
                    .build();
        }
    }
}
