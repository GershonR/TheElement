package fifthelement.theelement.business.services;

import java.util.Collections;
import java.util.List;

import fifthelement.theelement.application.Services;
import fifthelement.theelement.objects.Playlist;
import fifthelement.theelement.objects.Song;

public class SongListService {
    private List<Song> allSongsList = null;
    private List<Song> currentSongsList = null;
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

    public void setCurrentSongsList(List<Song> newList){
        currentSongsList = newList;
    }

    public List<Song> getAllSongsList() { return allSongsList; }

    public List<Song> getCurrentSongsList() { return currentSongsList; }


    public int getCurrentSongPlayingIndex() {
        return currentSongPlayingIndex;
    }

    // Skips to the next song in the list
    public Song skipToNextSong() {
        Song toReturn = null;

        if(currentSongsList != null && currentSongsList.size() > 0) {
            currentSongPlayingIndex++;
            if (currentSongPlayingIndex > currentSongsList.size() - 1) {
                currentSongPlayingIndex = 0;
            }

            toReturn = currentSongsList.get(currentSongPlayingIndex);
        }

        return toReturn;
    }

    public void setPlayerCurrentSongs(Playlist playlist){
        setCurrentSongsList(playlist.getSongs());
        setAutoplayEnabled(true);
    }

    // Skips to the previous song in the list
    public Song goToPrevSong() {
        Song toReturn = null;

        if(currentSongsList != null && currentSongsList.size() > 0) {
            currentSongPlayingIndex--;
            if (currentSongPlayingIndex < 0) {
                currentSongPlayingIndex = currentSongsList.size() - 1;
            }

            toReturn = currentSongsList.get(currentSongPlayingIndex);
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
        setCurrentSongsList(getAllSongsList());
        updateShuffledList();
        shuffled = true;
    }

    public void updateShuffledList() {
        Collections.shuffle(currentSongsList);
    }

    public void removeSongFromList(Song song){
        if(currentSongsList != null && song != null){
            boolean removed = false;
            for(int i = 0; i < currentSongsList.size() && !removed; i++){
                if(song.getUUID().equals(currentSongsList.get(i).getUUID())){
                    currentSongsList.remove(i);
                    if(i == getCurrentSongPlayingIndex() && i > 0)
                        setCurrentSongPlayingIndex(i - 1);
                    else if(i == getCurrentSongPlayingIndex())
                        setCurrentSongPlayingIndex(currentSongsList.size()-1);
                    removed = true;
                }
            }
        }
    }

    public void setAutoplayEnabled(boolean newValue){
        autoplayEnabled = newValue;
    }

    public void setShuffled(boolean value){
        shuffled = value;
    }

    public void setCurrentSongPlayingIndex(int currentSongPlayingIndex) { this.currentSongPlayingIndex = currentSongPlayingIndex; }

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
