package fifthelement.theelement.business.services;

import fifthelement.theelement.objects.Song;

// keeps track of statistics (num of times a song is played, etc)
// must be declared and used by a presentation class that handles playing/skipping of songs.
public class SorterService {

    public SorterService() {
    }

    public void songIsPlayed(Song song) {
        song.incrNumPlayed();
        if( song.getAuthor() != null ) {
            song.getAuthor().incrNumPlayed();
        }
        if( song.getAlbum() != null ) {
            song.getAlbum().incrNumPlayed();
        }
    }

    public void songIsSkipped(Song song) {
        song.decrNumPlayed();
        if( song.getAuthor() != null ) {
            song.getAuthor().decrNumPlayed();
        }
        if( song.getAlbum() != null ) {
            song.getAlbum().decrNumPlayed();
        }
    }


}
