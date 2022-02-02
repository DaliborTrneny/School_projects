package cz.mendelu.catan.networking;

public class ServerSideClientContainer {
    private String playerName;
    private String IPAddress;
    private int portListening;

    public ServerSideClientContainer(String name, String ip, int portListening){
        this.playerName = name;
        this.IPAddress = ip;
        this.portListening = portListening;
    }

    public String getIPAddress() {
        return IPAddress;
    }

    public int getPortListening() {
        return portListening;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
}
