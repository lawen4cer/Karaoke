package com.Personal;

import com.Personal.model.Song;
import com.Personal.model.SongBook;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class KaraokeMachine {
    private SongBook mSongBook;
    private BufferedReader mReader;
    private Queue<Song> mSongQueue;

    private Map<String, String> mMenu;

    public KaraokeMachine(SongBook songBook) {
        mSongBook = songBook;
        mReader =  new BufferedReader(new InputStreamReader(System.in));
        mSongQueue = new ArrayDeque<Song>();
        mMenu = new HashMap<String, String>();
        mMenu.put("add", "Add a new song to the song book");
        mMenu.put("play", "Play the next song in the queue");
        mMenu.put("choose", "Choose a new song to play!");
        mMenu.put("quit", "Give up. Exit the program.");
    }

    private String promptArtist() throws IOException {
        System.out.println("Available artists:  ");
        List<String> artists = new ArrayList<>(mSongBook.getArtists());
        int index = promptForIndex(artists);
        return artists.get(index);
    }

    private Song promptSongForArtists(String artists) throws IOException {
        List<Song> songs = mSongBook.getSongsForArtist(artists);
        List<String> songTitles = new ArrayList<>();
        for (Song song : songs) {
            songTitles.add(song.getTitle());
        }
        System.out.printf("Available songs for %s: %n", artists);
        int index = promptForIndex(songTitles);
        return songs.get(index);
    }

    private int promptForIndex(List<String> options) throws IOException {
        int counter = 1;
        for(String option : options) {
            System.out.printf("%d.) %s %n", counter, option);
            counter++;
        }
        System.out.print("Your choice:  ");
        String optionAsString = mReader.readLine();
        int choice = Integer.parseInt(optionAsString.trim());
        return choice - 1;
    }

    private String promptAction() throws IOException {
        if(mSongBook.getSongCount() == 1) {
            System.out.printf("There is %d song available and %d in the queue.  Your options are: %n",
                    mSongBook.getSongCount(),
                    mSongQueue.size());
        }
        System.out.printf("There are %d songs available and %d in the queue.  Your options are: %n",
                                    mSongBook.getSongCount(),
                                    mSongQueue.size());
        for(Map.Entry<String, String> option : mMenu.entrySet()) {
            System.out.printf("%s - %s %n", option.getKey(), option.getValue());
        }
        System.out.print("What do you want to do:  ");
        String choice = mReader.readLine();
        return choice.trim().toLowerCase();
    }

    public void run() {
        String choice = "";
        do {
                try {
                    choice = promptAction();
                    switch(choice){
                        case "add":
                            Song song = promptNewSong();
                            mSongBook.addSong(song);
                            System.out.printf("%s added!  %n%n", song);
                            break;
                        case "choose" :
                            String artist = promptArtist();
                            Song artistSong = promptSongForArtists(artist);
                            mSongQueue.add(artistSong);
                            System.out.printf("You chose: %s %n", artistSong);
                            break;
                        case "play" :
                            playNext();
                            break;
                        case "quit":
                            System.out.println("Thanks for playing");
                            break;
                        default:
                            System.out.printf("Unknown choice;  %s. Try again. %n%n%n", choice);
                    }
                } catch(IOException ioe) {
                    System.out.println("Problem with input");
                    ioe.printStackTrace();
                }
        }while(!choice.equals("quit"));
    }

    private Song promptNewSong() throws IOException {
        System.out.print("Enter the artist's name;  ");
        String artist = mReader.readLine();
        System.out.print("Enter the title:  ");
        String title = mReader.readLine();
        System.out.print("Enter the video URL:  ");
        String videoUrl = mReader.readLine();

        return new Song(artist,title,videoUrl);
    }

    public void playNext() {
        Song song =  mSongQueue.poll();
        if (song == null) {
            System.out.println("Sorry, there are no songs in the quest. Use choose from the menu to add some");
        } else {
            System.out.printf("%n%n%n Open %s to hear %s by %s %n%n%n", song.getVideoUrl(),song.getTitle(),song.getArtist());
        }

    }
}
