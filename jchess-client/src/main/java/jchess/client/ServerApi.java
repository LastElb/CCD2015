package jchess.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.Response;
import de.mki.jchess.commons.Client;
import jchess.client.models.Game;
import jchess.client.models.Position;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by Igor on 27.11.2015.
 */
public class ServerApi {

    String host;
    int port;

    public ServerApi(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    /**
     *
     * @param nickname
     * @param gameId
     * @return
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
     *
     * @return
     * @throws Exception
     */
    public List<String> getAvailableGameModes() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        Future<Response> future = asyncHttpClient.preparePost("http://" + host + ":" + port + "/gamemodes/available/").execute();
        Response response = future.get();
        asyncHttpClient.close();
        if (response.getStatusCode() == 200) {
            return objectMapper.readValue(response.getResponseBody(), new TypeReference<List<String>>() {});
        } else {
            throw new Exception(response.getResponseBody());
        }
    }

    /**
     * 
     * @param gameMode
     * @return
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
     * @param gameID
     * @return
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


    public ArrayList<String> getPossibleMoves(String gameID, String fieldNotation) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        Future<Response> future = asyncHttpClient.preparePost("http://" + host + ":" + port + "/game/" + gameID + "/possibleMovesByField/" + fieldNotation.toLowerCase()).execute();
        Response response = future.get();
        asyncHttpClient.close();
        if (response.getStatusCode() == 200) {
            List<Position> positions =  objectMapper.readValue(response.getResponseBody(), objectMapper.getTypeFactory().constructCollectionType(List.class, Position.class));

            // cast position to List of Notations
            ArrayList<String> fieldsToHighlight = new ArrayList<String>();
            for (Position position  : positions) {
                fieldsToHighlight.add(position.getNotation());
            }

            return fieldsToHighlight;
        } else {
            throw new Exception(response.getResponseBody());
        }
    }

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
