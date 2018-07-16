package fifthelement.theelement.objects;

import java.util.UUID;

public class Song implements Comparable<Song>{
    private UUID uuid;
    private String songName;
    private String path;
    private String genre;
    private Author author;
    private Album album;
    private int numPlayed;
    private double rating;

    public Song(String name, String path){
        this.uuid = UUID.randomUUID();
        this.songName = name;
        this.path = path;
        this.genre = "";
        author = null;
        album = null;
        this.numPlayed = 0;
        rating = 0;
    }

    public Song(UUID uuid, String name, String path){
        this.uuid = uuid;
        this.songName = name;
        this.path = path;
        this.genre = "";
        author = null;
        album = null;
        this.numPlayed = 0;
        rating = 0;
    }

    public Song(UUID uuid, String name, String path, Author author, Album album, String genre){
        this.uuid = uuid;
        this.songName = name;
        this.path = path;
        this.author = author;
        this.album = album;
        this.genre = genre;
        this.numPlayed = 0;
    }

    public Song(UUID uuid, String name, String path, Author author, Album album, String genre, int numPlayed){
        this.uuid = uuid;
        this.songName = name;
        this.path = path;
        this.author = author;
        this.album = album;
        this.genre = genre;
        this.numPlayed = numPlayed;
        rating = 0;
    }

    public Song(UUID uuid, String name, String path, Author author, Album album, String genre, double rating) {
        this.uuid = uuid;
        this.songName = name;
        this.path = path;
        this.author = author;
        this.album = album;
        this.genre = genre;
        this.numPlayed = 0;
        this.rating = rating;
    }

    public Song(UUID uuid, String name, String path, Author author, Album album, String genre, int numPlayed, double rating) {
        this.uuid = uuid;
        this.songName = name;
        this.path = path;
        this.author = author;
        this.album = album;
        this.genre = genre;
        this.numPlayed = numPlayed;
        this.rating = rating;
    }

    // getters
    public UUID getUUID(){
        return uuid;
    }

    public String getName(){
        return songName;
    }

    public String getPath() {
        return path;
    }

    public String getGenre() {
        return genre;
    }

    public Author getAuthor(){
        return author;
    }

    public Album getAlbum(){
        return album;
    }

    public int getNumPlayed() {
        return numPlayed;
    }

    public double getRating() { return rating; }

    // setters
    public void setUUID(UUID uuid) {
        this.uuid = uuid;
    }

    public void setName(String newName){
        this.songName = newName;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setAuthor(Author author){
        this.author =  author;
    }

    public void setAlbum(Album album){
        this.album =  album;
    }

    public void setRating(double rating){ this.rating = rating; }

    public void setNumPlayed(int numPlayed) {
        this.numPlayed = numPlayed;
    }

    public void incrNumPlayed() {
        this.numPlayed++;
    }

    public void decrNumPlayed() {
        if( this.numPlayed > 0 ) {
            this.numPlayed--;
        }
    }

    @Override
    public int compareTo(Song song){
        return songName.compareTo(song.getName());
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof Song && ((Song) object).getUUID() == this.getUUID();
    }
}
