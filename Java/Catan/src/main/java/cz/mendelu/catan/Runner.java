package cz.mendelu.catan;

import bh.greenfoot.runner.GreenfootRunner;
import cz.mendelu.catan.greenfoot.GameWorld;


public class Runner extends GreenfootRunner {
    static {
        bootstrap(Runner.class,
                Configuration.forWorld(GameWorld.class)
                        .projectName("Catan")
                        .hideControls(true)
        );
    }

    public static void main(String[] args) {
        GreenfootRunner.main(args);
    }
}

