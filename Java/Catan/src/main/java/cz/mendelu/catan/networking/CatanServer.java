package cz.mendelu.catan.networking;

import cz.mendelu.catan.game.Game;
import cz.mendelu.catan.game.gamebuilder.DefaultPresetBuilder;
import cz.mendelu.catan.game.gamebuilder.GameBuilder;
import cz.mendelu.catan.greenfoot.gamemode.playing.GameWorldState;

import java.awt.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class CatanServer implements Runnable{
    private GameBuilder builder;
    private int numberOfPlayers;
    private boolean gameRunning;

    private Game gameInstance;
    private GameWorldState currentState;

    private ServerSocket serverSocket;
    private int serverPortNumber;
    private List<ServerSideClientContainer> connectedPlayers;

    public CatanServer(int port, int numberOfPlayers, GameBuilder builder){
        this.builder = builder;
        this.serverPortNumber = port;
        this.numberOfPlayers = numberOfPlayers;
        this.connectedPlayers = new ArrayList<>();
        this.gameRunning = false;
    }

    @Override
    public void run() {
        if (!gameRunning){
            listenForConnectingPlayers();
        }
        while(gameRunning){
            listenToPlayerGameUpdates();
        }
    }


    /**
     * Server naslouchá a příjmá žádosti o připojení od hráčů.
     *
     * @version etapa 5
     */
    private void listenForConnectingPlayers(){
        try {
            serverSocket = new ServerSocket(this.serverPortNumber);
            //Cekani nez se pripoji vsichni hraci
            System.out.println("IP adresa serveru: " + serverSocket.getInetAddress().getHostAddress() +
                    "\nPort: " + serverSocket.getLocalPort() + "\nCeka se na pripojeni vsech hracu");

            while(connectedPlayers.size() < numberOfPlayers){
                Socket clientConnection = serverSocket.accept();

                String clientIpAddress = clientConnection.getInetAddress().getHostAddress();
                //Klient si vytvari port pro naslouchani s urcitym offsetem od portu, kterym posle uvodni pripojeni k serveru
                int clientPortNumber = clientConnection.getPort() + CatanClient.LISTENING_PORT_OFFSET;

                //Pokud v uvodnim requestu uspesne ziskame hracovo jmeno (v pripojovacim requestu jej musi zminit)
                if (clientIpAddress != null && !clientIpAddress.isBlank()){
                    String clientName = readClientName(clientConnection);
                    if (clientName != null) {
                        ServerSideClientContainer newPlayerClient = new ServerSideClientContainer(clientName, clientIpAddress, clientPortNumber);
                        this.connectedPlayers.add(newPlayerClient);
                        System.out.println("Pripojil se klient: " + clientName + " IP: " + clientIpAddress + " port pro naslouchani: " + clientPortNumber);
                    }
                }
            }
            //Jakmile jsou vsichni hraci (klienti) pripojeni, zacne hra
            generateNewGame();
            gameRunning = true;
            //sending the newly created game to all clients on startup
            sendGameUpdateToPlayers(this.connectedPlayers);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Server naslouchá a čeká na updaty od hráčů.
     *
     * @version etapa 5
     */
    private void listenToPlayerGameUpdates(){
        try {
            Socket clientGameUpdate = serverSocket.accept();
            System.out.println("Dosel update od hrace");
            String incomingIP = clientGameUpdate.getInetAddress().getHostAddress();
            String incomingName = parsePlayerUpdate(clientGameUpdate);
            if (incomingName != null && !incomingName.isBlank() && getClientByIPAndName(incomingIP, incomingName) != null){
                sendGameUpdateToPlayers(getAllClientsExceptFor(incomingName));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Při inicializaci serveru tato metoda přeloží klientův request k připojení (vytáhne z něj klientovo jméno)
     *
     * @param newClient zpráva od klienta
     * @version etapa 5
     */
    private String readClientName(Socket newClient){
        String output = null;
        if (newClient != null) {
            try {
                try (InputStream is = newClient.getInputStream()) {
                    ObjectInputStream ois = new ObjectInputStream(is);
                    String name = (String)ois.readObject();
                    if (name != null && !name.isBlank()){
                        output = name;
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return output;
    }


    /**
     * Získá zapamatovaného klienta (hráče) podle specifikovaného jména a IP adresy
     *
     * @param clientIPAddress IP adresa klienta
     * @param name jméno klienta
     * @version etapa 5
     */
    private ServerSideClientContainer getClientByIPAndName(String clientIPAddress, String name){
        for (var c : this.connectedPlayers){
            if (c.getIPAddress().equals(clientIPAddress) && c.getPlayerName().equals(name)){
                return c;
            }
        }
        return null;
    }

    /**
     * Metoda získá použitelná data z update zprávy od klienta a uloží je
     *
     * @param clientConnection zpráva od klienta
     * @version etapa 5
     */
    private String parsePlayerUpdate(Socket clientConnection) throws IOException, ClassNotFoundException {
        InputStream is = clientConnection.getInputStream();
        ObjectInputStream ois = new ObjectInputStream(is);
        String playerName = (String) ois.readObject();
        GameWorldState newState = (GameWorldState) ois.readObject();
        Game newGameInstance = (Game) ois.readObject();

        if (newState != null && newGameInstance != null){
            this.currentState = newState;
            this.gameInstance = newGameInstance;
        }
        return playerName;
    }

    /**
     * Vrátí všechny zapamatované klienty krom jednoho, jehož jméno je specifikováno v parametru.
     *
     * @param name jméno klienta, kterého nechceme vrátit
     * @version etapa 5
     */
    private List<ServerSideClientContainer> getAllClientsExceptFor(String name){
        List<ServerSideClientContainer> clients = new ArrayList<>();
        for (var c : this.connectedPlayers){
            if (!c.getPlayerName().equals(name)){
                clients.add(c);
            }
        }
        return clients;
    }

    /**
     * Metoda odešle update herních dat a stavu specifikovaným klientům (hráčům).
     *
     * @param clients seznam hráčů kterým se má update odeslat
     * @version etapa 5
     */
    public void sendGameUpdateToPlayers(List<ServerSideClientContainer> clients) throws IOException {
        for (var c : clients){
            Socket sendingSocket = new Socket(c.getIPAddress(), c.getPortListening());
            OutputStream os = sendingSocket.getOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(os);
            oos.writeObject(this.currentState);
            oos.writeObject(this.gameInstance);
            System.out.println("Sent game update to: " + c.getPlayerName() + " on port: " + c.getPortListening());
            sendingSocket.close();
        }
    }

    /**
     * Po připojení všech hráčů vygeneruje novou instanci hry
     *
     * @version etapa 5
     */
    private void generateNewGame(){
        if (this.connectedPlayers.size() == numberOfPlayers) {
            List<Color> availablePlayerColors = new ArrayList<>();
            availablePlayerColors.addAll(Arrays.asList(Color.RED, Color.GREEN, Color.BLUE, Color.PINK));

            List<String> playerNames = new ArrayList<>();
            List<Color> selectedPlayerColors = new ArrayList<>();

            for (var x : this.connectedPlayers) {
                Random randomizer = new Random();
                int colorIndex = randomizer.nextInt(availablePlayerColors.size());
                Color c = availablePlayerColors.get(colorIndex);

                playerNames.add(x.getPlayerName());
                selectedPlayerColors.add(c);
                availablePlayerColors.remove(colorIndex);
            }

            builder.buildGame(playerNames, selectedPlayerColors);
            System.out.println("hra byla buildnuta");
            this.gameInstance = builder.getGame();
            this.currentState = GameWorldState.STARTUP_BUILD_VILLAGE;
        } else {
            throw new IllegalStateException("New game starting without sufficient number of connected players");
        }
    }




    //TEST
    public static void main(String[] args) {
        CatanServer server = new CatanServer(46323, 2, new DefaultPresetBuilder());
        server.run();
    }
}
