package jchess.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.Response;
import de.mki.jchess.commons.Client;
import jchess.client.models.Game;
import jchess.client.models.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Future;

/**
 * API to communicate with the server and map JSON to objects
 */
public class ServerApi {

    String host;
    int port;

    public ServerApi(String host, int port) {
        this.host = host;
        this.port = port;
    }

    /**
     * Get the hostname of the server
     *
     * @return hostname
     */
    public String getHost() {
        return host;
    }

    /**
     * Get the port of the server
     *
     * @return port
     */
    public int getPort() {
        return port;
    }

    /**
     * Connect to a hosted game.
     *
     * @param nickname Nickname of the new player
     * @param gameId   Identifier of the game to join
     * @return Client Client-Model with the Client-Identifier
     * @throws Exception
     */
    public Optional<Client> connectToGame(String nickname, String gameId) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        Future<Response> future = asyncHttpClient
                .preparePost("http://" + host + ":" + port + "/host/joinAsPlayer/" + gameId)
                .setBody("{\"nickname\":\"" + nickname + "\"}")
                .setHeader("Content-Type", "application/json")
                .setHeader("Accept", "application/json")
                .execute();
        Response response = future.get();
        asyncHttpClient.close();
        if (response.getStatusCode() == 200) {
            return Optional.of(objectMapper.readValue(response.getResponseBody(), Client.class));
        } else {
            throw new Exception(response.getResponseBody());
        }
    }

    /**
     * Get the supported game Modes from the server
     *
     * @return List of Game Modes (Strings)
     * @throws Exception
     */
    public List<String> getAvailableGameModes() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        Future<Response> future = asyncHttpClient.preparePost("http://" + host + ":" + port + "/gamemodes/available/").execute();
        Response response = future.get();
        asyncHttpClient.close();
        if (response.getStatusCode() == 200) {
            return objectMapper.readValue(response.getResponseBody(), new TypeReference<List<String>>() {
            });
        } else {
            throw new Exception(response.getResponseBody());
        }
    }

    /**
     * Request a new hosted game on the server
     *
     * @param gameMode Mode of the game e.g. default-3-person-chess
     * @return Game Model of the started Game containing the Identifier of the game
     * @throws Exception
     */
    public Optional<Game> hostGame(String gameMode) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        Future<Response> future = asyncHttpClient.preparePost("http://" + host + ":" + port + "/host/byGameMode/" + gameMode).execute();
        Response response = future.get();
        asyncHttpClient.close();
        if (response.getStatusCode() == 200) {
            return Optional.of(objectMapper.readValue(response.getResponseBody(), Game.class));
        } else {
            throw new Exception(response.getResponseBody());
        }
    }

    /**
     * Get all available game information from the server
     *
     * @param gameID Identifier of the game
     * @return Game Model of the started Game containing the Identifier of the game
     * @throws Exception
     */
    public Optional<Game> getFullGame(String gameID) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        Future<Response> future = asyncHttpClient.preparePost("http://" + host + ":" + port + "/game/" + gameID + "/full").execute();
        Response response = future.get();
        asyncHttpClient.close();
        if (response.getStatusCode() == 200) {
            return Optional.of(objectMapper.readValue(response.getResponseBody(), Game.class));
        } else {
            throw new Exception(response.getResponseBody());
        }
    }


    /**
     * Request the possible moves for a figure on specified field
     *
     * @param gameID        Identifier of the game
     * @param fieldNotation Field the figure is located e.g. A4
     * @return ArrayList<String> Array Containing all possible Fields to move to as {@link Position} object
     * @throws Exception
     */
    public ArrayList<String> getPossibleMoves(String gameID, String fieldNotation) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        Future<Response> future = asyncHttpClient.preparePost("http://" + host + ":" + port + "/game/" + gameID + "/possibleMovesByField/" + fieldNotation.toLowerCase()).execute();
        Response response = future.get();
        asyncHttpClient.close();
        if (response.getStatusCode() == 200) {
            List<Position> positions = objectMapper.readValue(response.getResponseBody(), objectMapper.getTypeFactory().constructCollectionType(List.class, Position.class));

            // cast position to List of Notations
            ArrayList<String> fieldsToHighlight = new ArrayList<String>();
            for (Position position : positions) {
                fieldsToHighlight.add(position.getNotation());
            }

            return fieldsToHighlight;
        } else {
            throw new Exception(response.getResponseBody());
        }
    }

    /**
     * Try to perform a movement form source field to target field.
     *
     * @param gameID              Identifier of the game
     * @param sourcefieldNotation Field the figure is located e.g. A4
     * @param targetFieldNotation Field the figure should be moved to
     * @param clientID            Identifier of the Client requesting this movement
     * @throws Exception
     */
    public void performMovement(String gameID, String sourcefieldNotation, String targetFieldNotation, String clientID) throws Exception {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        Future<Response> future = asyncHttpClient.preparePost("http://" + host + ":" + port + "/game/" + gameID + "/performMoveByField/" + sourcefieldNotation.toLowerCase() + "/" + clientID + "/" + targetFieldNotation.toLowerCase()).execute();
        Response response = future.get();
        asyncHttpClient.close();
        if (response.getStatusCode() != 200) {
            throw new Exception(response.getResponseBody());
        }
    }
}
