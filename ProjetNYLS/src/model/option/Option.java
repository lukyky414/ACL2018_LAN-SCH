package model.option;

import model.GameState;
import model.LabyrinthGame;
import org.omg.PortableInterceptor.INACTIVE;

import java.util.Map;

public class Option {
    private String[] listeOption;
    private int current;


    public Option(){
        listeOption = new String[5];
        listeOption[0] = "Continue";
        listeOption[1] = "New game";
        listeOption[2] = "Save";
        listeOption[3] = "Load";
        listeOption[4] = "Exit";
        current = 0;
    }

    public String[] getListeOption() {
        return listeOption;
    }

    public void addCurrent(int x){
        if (current + x < listeOption.length)
            current = current + x;
    }

    public void subCurrent(int x){
        if (current - x >= 0)
            current = current - x;
    }

    public int getCurrent(){
        return current;
    }

    public void doOption(LabyrinthGame game){
        switch (current){
            case 0:
                game.setState(GameState.RUN);
            break;
            case 4:
                game.setState(GameState.END);
                break;
        }
    }

    public String toString(){
        StringBuilder sb = new StringBuilder("");
        for (int i = 0; i != listeOption.length; i++){
            sb.append(i).append(" : ").append(listeOption[i]).append("\n");
        }
        sb.append("current : ").append(current).append("\n");
        return sb.toString();
    }

    public static void main(String[] args) {
        Option opt = new Option();
        System.out.println(opt);
    }
}
