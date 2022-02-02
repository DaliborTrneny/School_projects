package cz.mendelu.catan.networking;

import cz.mendelu.catan.game.Game;
import cz.mendelu.catan.greenfoot.gamemode.playing.GameWorldState;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class CatanClient implements Runnable{
    public static final int LISTENING_PORT_OFFSET = 1;

    private Game game;
    private GameWorldState currentState;
    CatanClientEventListener eventListener;

    private String name;

    private String serverIP;
    private int serverPortNumber;

    private int listeningPortNumber;
    private ServerSocket listeningSocket;

    private boolean gameRunning;

    public CatanClient(String name, String serverIP, int serverPortNumber){
        this.name = name;
        this.gameRunning = false;
        this.serverIP = serverIP;
        this.serverPortNumber = serverPortNumber;
        this.eventListener = eventListener;
    }

    public interface CatanClientEventListener{
        void onConnectedToServer();
        void onFailedToConnectToServer();
        void onGameStarted(GameWorldState state, Game game);
        void onGameUpdated(GameWorldState state, Game game);
        void onGameEnded(GameWorldState state, Game game);
    }

    /**
     * Klient vyšle úvodní zprávu serveru, že se chce připojit ke hře
     *
     * @version etapa 5
     */
    private void connectToServer(){
        try {
            Socket sendingSocket = new Socket(serverIP, serverPortNumber);
            //the listening port for this client is based on the original port number sent in initial server connection
            this.listeningPortNumber = sendingSocket.getLocalPort() + LISTENING_PORT_OFFSET;

            ObjectOutputStream oos = new ObjectOutputStream(sendingSocket.getOutputStream());
            oos.writeObject(name);
            oos.close();
            sendingSocket.close();
            this.listeningSocket = new ServerSocket(listeningPortNumber);

            if (this.eventListener != null) this.eventListener.onConnectedToServer();
            System.out.println("Listening for server updates on port: " + listeningPortNumber);
        } catch (IOException e) {
            eventListener.onFailedToConnectToServer();
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        if (!gameRunning) {
            connectToServer();
            listenForInitialGameStart();
        }
        while (gameRunning) {
            listenToServerGameUpdates();
        }
    }

    /**
     * Klient poslouchá, než se připojí všichni hráči a server rozešle herní data
     *
     * @version etapa 5
     */
    private void listenForInitialGameStart(){
        try {
            Socket serverUpdate = listeningSocket.accept();
            receiveServerGameUpdate(serverUpdate);
            gameRunning = true;
            if (this.eventListener != null) this.eventListener.onGameStarted(this.currentState, this.game);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Klient dlouhodobě čeká na updaty od serveru s novými upravenými hreními daty.
     *
     * @version etapa 5
     */
    private void listenToServerGameUpdates(){
        try {
            Socket serverUpdate = listeningSocket.accept();
            receiveServerGameUpdate(serverUpdate);
            if (this.eventListener != null) this.eventListener.onGameUpdated(this.currentState, this.game);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Po přijetí updatu od serveru se přeloží poslaná data, aby byla lokálně použitelná
     *
     * @param serverUpdate zpráva od serveru
     * @version etapa 5
     */
    private void receiveServerGameUpdate(Socket serverUpdate) throws IOException, ClassNotFoundException {
        if (serverUpdate.getInetAddress().getHostAddress().equals(this.serverIP)){
            InputStream is = serverUpdate.getInputStream();
            ObjectInputStream ois = new ObjectInputStream(is);
            GameWorldState newState = (GameWorldState) ois.readObject();
            Game newGameInstance = (Game)ois.readObject();
            if (newState != null && newGameInstance != null){
                this.currentState = newState;
                this.game = newGameInstance;
            }
            System.out.println("Got update from server, new GameWorldState = " + this.currentState);
        }
    }


    /**
     * Odešle na server stav hry a herní data, aby byly updatovány u dalších hráčů.
     *
     * @param gameInstance herní data
     * @param gameState stav hry
     * @version etapa 5
     */
    //Sending update to server
    public void sendGameUpdateToServer(GameWorldState gameState, Game gameInstance){
        try {
            Socket sendingSocket = new Socket(serverIP, serverPortNumber);

            ObjectOutputStream oos = new ObjectOutputStream(sendingSocket.getOutputStream());
            oos.writeObject(this.name);
            oos.writeObject(gameState);
            oos.writeObject(gameInstance);
            oos.close();
            sendingSocket.close();
            System.out.println("Update odeslan na server");
        } catch (IOException e) {
            eventListener.onFailedToConnectToServer();
            System.out.println("Odeslani updatu se nepovedlo");
            e.printStackTrace();
        }
    }


    public String getName() {
        return name;
    }

    public void setEventListener(CatanClientEventListener eventListener) {
        this.eventListener = eventListener;
    }



    public static void main(String[] args) {
        CatanClient client = new CatanClient("Candice", "127.0.0.1", 46323);
        client.run();
    }


}
