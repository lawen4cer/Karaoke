package com.Personal.WRice.Main;

import com.Personal.KaraokeMachine;
import com.Personal.model.Song;
import com.Personal.model.SongBook;

public class Main {
    public static void main(String[] args) {
        SongBook songBook = new SongBook();
        KaraokeMachine machine = new KaraokeMachine(songBook);
        machine.run();
    }

}
