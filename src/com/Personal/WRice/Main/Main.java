package com.Personal.WRice.Main;

import com.Personal.KaraokeMachine;
import com.Personal.model.Song;
import com.Personal.model.SongBook;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        SongBook songBook = new SongBook();
        songBook.importFrom("songs.txt");
        KaraokeMachine machine = new KaraokeMachine(songBook);
        machine.run();
        System.out.println("Saving book....");
        songBook.exportTo("songs.txt");
    }


}
