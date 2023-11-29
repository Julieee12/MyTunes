package gui;


import be.Song;
import bll.logic;
import javafx.collections.ObservableList;

import java.util.Observable;

public class MPModel {
    private final static MPModel instance = new MPModel(); //ensures that by using Singelton all controllers use the same model
    private logic playerLogic;
    private MPController observerController;

    public MPModel() {
        this.playerLogic = new logic();
    }

    public static MPModel getInstance(){
        return instance;
    }

    public void createSong(String title, String artist, String category, Double time, String path){
        playerLogic.createSong(title, artist, category, time, path);
        //observerController.initialize(playerLogic.returnSongList());


    }

    public ObservableList<Song> returnSongList(){
        return playerLogic.returnSongList();

    }

}
