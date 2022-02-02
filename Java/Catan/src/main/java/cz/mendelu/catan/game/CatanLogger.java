package cz.mendelu.catan.game;

import java.util.ArrayList;
import java.util.List;

public class CatanLogger {
    private static final int MAX_LOG_HISTORY = 200;

    private List<String> logs;

    private static CatanLogger logger;
    public static CatanLogger getCatanLogger(){
        if (CatanLogger.logger == null){
            CatanLogger.logger = new CatanLogger();
            return CatanLogger.logger;
        } else {
            return CatanLogger.logger;
        }
    }

    private CatanLogger(){
        this.logs = new ArrayList<>();
    }

    public void addLog(String log){
        //Pojistka proti zahlceni loggeru
        if (this.logs.size() > MAX_LOG_HISTORY){
            this.logs.clear();
        }
        this.logs.add(log);
    }

    public String getLastLog(){
        int numberOfLogs = this.logs.size();
        if (numberOfLogs>0){
            return this.logs.get(numberOfLogs-1);
        } else {
            return "";
        }
    }

}
