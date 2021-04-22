package de.ft.fingerrise;

import java.util.Timer;
import java.util.TimerTask;

public class Settings {
    public static boolean soundEnabled = true;
    public static boolean musicEnabled = true;
    public static int coins = 0;

    /**
     * Load several Variables from shared preferences
     */
    public static void init() {

        soundEnabled = FingerRise.settings.getBoolean("sound", true);
        musicEnabled = FingerRise.settings.getBoolean("music", true);
        coins = FingerRise.settings.getInteger("coins", 0);
        LevelConfig.highestLevel = FingerRise.settings.getInteger("highestLevel",0);

        //Save settings every 5 minutes to shared preferences
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                save();
            }
        },0,1000*60*5);

        //TODO temp go to hightest Level
        LevelConfig.currentLevel = LevelConfig.highestLevel;

    }

    public static void save() {

        FingerRise.settings.putBoolean("sound",soundEnabled);
        FingerRise.settings.putBoolean("music",musicEnabled);
        FingerRise.settings.putInteger("highestLevel",LevelConfig.highestLevel);
        FingerRise.settings.putInteger("coins", coins);

        FingerRise.settings.flush();


    }


}
