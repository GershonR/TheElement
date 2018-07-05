package fifthelement.theelement.business.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fifthelement.theelement.objects.Song;

public class SongListService {
    private List<Song> songList = null;
    private List<Song> shuffledList = null;
    private boolean shuffled = false;
    private boolean autoplayEnabled = false;
    private int currentSongPlayingIndex = 0;

    //Method to set the current list of songList to play songList from
    public void setSongList(List<Song> newList){
        songList = newList;
        if(shuffled){
            shuffle();
        }
    }

    public List<Song> getSongList(){
        return songList;
    }

    // Skips to the next song in the list
    public Song skipToNextSong() {
        Song toReturn = null;

        if(songList != null) {
            currentSongPlayingIndex++;
            if (currentSongPlayingIndex > songList.size() - 1) {
                currentSongPlayingIndex = 0;
            }

            if(shuffled) {
                toReturn = shuffledList.get(currentSongPlayingIndex);
            } else {
                toReturn = songList.get(currentSongPlayingIndex);
            }
        }

        return toReturn;
    }

    // Skips to the previous song in the list
    public Song goToPrevSong() {
        Song toReturn = null;

        if(songList != null) {
            currentSongPlayingIndex--;
            if (currentSongPlayingIndex < 0) {
                currentSongPlayingIndex = songList.size() - 1;
            }

            if(shuffled) {
                toReturn = shuffledList.get(currentSongPlayingIndex);
            } else {
                toReturn = songList.get(currentSongPlayingIndex);
            }
        }

        return toReturn;
    }

    public Song getSongAtIndex(int index){
        Song toReturn = null;

        if(songList != null && index >= 0 && index < songList.size()){
            toReturn = songList.get(index);
            currentSongPlayingIndex = index;
        }

        return toReturn;
    }

    public void shuffle() {
        shuffledList = new ArrayList<>();
        shuffledList.addAll(songList);
        Collections.shuffle(shuffledList);
        shuffled = true;
    }

    public void updateShuffledList() {
        shuffledList = new ArrayList<>();
        shuffledList.addAll(songList);
        Collections.shuffle(shuffledList);
    }

    public void removeSongFromList(Song song){
        if(songList != null){
            songList.remove(song);
        }
    }

    public void setShuffleEnabled(boolean value){
        shuffled = value;
    }

    public void setAutoplayEnabled(boolean newValue){
        autoplayEnabled = newValue;
    }

    public void setShuffled(boolean value){
        shuffled = value;
    }

    public boolean getAutoplayEnabled(){
        return autoplayEnabled;
    }

    public void sortSongs(List<Song> songs) {
        Collections.sort(songs);
    }
}
