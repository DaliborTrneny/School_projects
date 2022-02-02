package cz.mendelu.catan.game.gamebuilder;

import cz.mendelu.catan.player.Player;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameBuilderTest {

    /**
     * @author xmusil5
     * @version etapa 2
     */
    @Test
    void generatePlayers() {
        //setup
        GameBuilder builder = new DefaultPresetBuilder();
        List<String> playerNames = new ArrayList<String>();
        playerNames.add("John");
        playerNames.add("Bob");
        playerNames.add("Candice");
        List<Color> playerColors = new ArrayList<Color>();
        playerColors.add(Color.RED);
        playerColors.add(Color.GREEN);
        playerColors.add(Color.BLUE);
        //when
        List<Player> players = builder.generatePlayers(playerNames, playerColors);
        //then
        assertEquals(3, players.size());

        assertNotNull(players.get(0));
        assertNotNull(players.get(1));
        assertNotNull(players.get(2));

        assertEquals(playerColors.get(0), players.get(0).getColor());
        assertEquals(playerNames.get(0), players.get(0).getName());

        assertEquals(playerColors.get(1), players.get(1).getColor());
        assertEquals(playerNames.get(1), players.get(1).getName());

        assertEquals(playerColors.get(2), players.get(2).getColor());
        assertEquals(playerNames.get(2), players.get(2).getName());
    }

    /**
     * @author xmusil5
     * @version etapa 2
     */
    @Test
    void generatePlayers_parametersNotMatching() {
        //setup
        GameBuilder builder = new DefaultPresetBuilder();
        List<String> playerNames = new ArrayList<String>();
        playerNames.add("Charlie");
        playerNames.add("Alan");

        List<Color> playerColors = new ArrayList<Color>();
        playerColors.add(Color.RED);

        //when + then
        assertThrows(IndexOutOfBoundsException.class, ()->builder.generatePlayers(playerNames, playerColors));
    }
}