package fifthelement.theelement.business.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fifthelement.theelement.application.Services;
import fifthelement.theelement.objects.Song;

public class SongListService {
    private List<Song> allSongsList = null;
    private List<Song> currentSongsList = null;
    private List<Song> shuffledList = null;
    private boolean shuffled = false;
    private boolean autoplayEnabled = false;
    private int currentSongPlayingIndex = 0;

    public SongListService() {
        this.allSongsList = Services.getSongService().getSongs();
    }

    public SongListService(List<Song> songs) {
        this.allSongsList = songs;
    }

    public void setAllSongsList(List<Song> newList){
        allSongsList = newList;
    }
    //Method to set the current list of currentSongsList to play currentSongsList from
    public void setCurrentSongsList(List<Song> newList){
        currentSongsList = newList;
    }

    public List<Song> getAllSongsList() { return allSongsList; }

    public List<Song> getCurrentSongsList() { return currentSongsList; }


    // Skips to the next song in the list
    public Song skipToNextSong() {
        Song toReturn = null;

        if(currentSongsList != null && currentSongsList.size() > 0) {
            currentSongPlayingIndex++;
            if (currentSongPlayingIndex > currentSongsList.size() - 1) {
                currentSongPlayingIndex = 0;
            }

            if(shuffled) {
                toReturn = shuffledList.get(currentSongPlayingIndex);
            } else {
                toReturn = currentSongsList.get(currentSongPlayingIndex);
            }
        }

        return toReturn;
    }

    // Skips to the previous song in the list
    public Song goToPrevSong() {
        Song toReturn = null;

        if(currentSongsList != null && currentSongsList.size() > 0) {
            currentSongPlayingIndex--;
            if (currentSongPlayingIndex < 0) {
                currentSongPlayingIndex = currentSongsList.size() - 1;
            }

            if(shuffled) {
                toReturn = shuffledList.get(currentSongPlayingIndex);
            } else {
                toReturn = currentSongsList.get(currentSongPlayingIndex);
            }
        }

        return toReturn;
    }

    public Song getSongAtIndex(int index){
        Song toReturn = null;

        if(currentSongsList != null && index >= 0 && index < currentSongsList.size()){
            toReturn = currentSongsList.get(index);
            currentSongPlayingIndex = index;
        }

        return toReturn;
    }

    public void shuffle() {
        updateShuffledList();
        shuffled = true;
    }

    public void updateShuffledList() {
        shuffledList = new ArrayList<>();
        shuffledList.addAll(currentSongsList);
        Collections.shuffle(shuffledList);
    }

    public void removeSongFromList(Song song){
        if(currentSongsList != null){
            currentSongsList.remove(song);
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

    public boolean getShuffled(){
        return shuffled;
    }

    public boolean getAutoplayEnabled(){
        return autoplayEnabled;
    }

    public void sortSongs(List<Song> songs) {
        Collections.sort(songs);
    }
}
