package cz.mendelu.catan.greenfoot.gamemode.playing;

/**
 * Představuje aktuální stav hry, podle kterého se potom řídí přístupy k tlačítkům v GUI ovládání
 * a zároven rozhodování o metodě ke spuštění v actorech
 *
 * @author xmusil5
 */
public enum GameWorldState {
    RUNNING,
    POPUP_WINDOW,
    ROLLING_DICES,

    STARTUP_BUILD_VILLAGE,
    STARTUP_BUILD_ROAD,
    STARTUP_NEXT_TURN,

    TRADING_WITH_PLAYER,
    TRADING_WITH_PORT,

    ACTION_CARD_SELECTING,

    ROAD_BUILDING_CARD_PLAYED,
    INVENTION_CARD_PLAYED,
    MONOPOL_CARD_PLAYED,

    STEALING_RESOURCE_CARDS,

    MOVING_BANDIT,

    BUILDING_VILLAGE,
    BUILDING_CITY,
    BUILDING_ROAD,
    GAME_WON;

    public static String getGameWolrdStateString(GameWorldState state){
        String stateString = "";
        switch (state){
            case RUNNING:
                stateString = "Hra bezi\nvyber akci";
                break;
            case POPUP_WINDOW:
                stateString = "Vyskakovaci okno";
                break;
            case ROLLING_DICES:
                stateString = "Hod kostkami";
                break;
            case STARTUP_BUILD_VILLAGE:
                stateString = "Zvol krizovatku pro\npostaveni vesnice";
                break;
            case STARTUP_BUILD_ROAD:
                stateString = "Zvol cestu pro\npostaveni silnice";
                break;
            case STARTUP_NEXT_TURN:
                stateString = "Az budes pripraven\nukonci svuj tah";
                break;
            case BUILDING_VILLAGE:
                stateString = "Zvol krizovatku pro\npostaveni vesnice";
                break;
            case BUILDING_CITY:
                stateString = "Zvol vesnici pro\npovyseni na mesto";
                break;
            case BUILDING_ROAD:
                stateString = "Zvol cestu pro\npostaveni silnice";
                break;
            case TRADING_WITH_PLAYER:
                stateString = "Vyber hrace k obchodu";
                break;
            case TRADING_WITH_PORT:
                stateString = "Vyber pristavu k obchodu";
                break;
            case INVENTION_CARD_PLAYED:
                stateString = "Vyber si 2 karty\nsurovin z banku";
                break;
            case ACTION_CARD_SELECTING:
                stateString = "Vyber akcni kartu\nk vylozeni";
                break;
            case MONOPOL_CARD_PLAYED:
                stateString = "Vyber typ suroviny, o kterou\nostatnim hrace okrades";
                break;
            case STEALING_RESOURCE_CARDS:
                stateString = "Zvol hrace kteremu\nchces ukrast suroviny";
                break;
            case ROAD_BUILDING_CARD_PLAYED:
                stateString = "Zvol dve cesty\nna kterych postavis silnici";
                break;
            case MOVING_BANDIT:
                stateString = "Zvol pole pro\npresunuti lupice";
                break;
        }
        return stateString;
    }
}
