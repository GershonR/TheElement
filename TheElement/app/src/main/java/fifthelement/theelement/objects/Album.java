public class Album{
    private int albumId;
    private String albumName;
    private ArrayList<Author> authors;
    private ArrayList<Song> songs;

    public Album(int id, String name){
        this.albumId = id;
        this.albumName = name;
        authors = new ArrayList<Author>();
        songs = new ArrayList<Song>();
    }

    public String getName(){
        return albumName;
    }

    public String getId(){
        return albumId;
    }

    public ArrayList<Author> getAuthors(){
        return authors;
    }
    
    public ArrayList<Song> getsongs(){
        return songs;
    }

    public void setName(String Newname){
        this.albumName = newName;
    }

    public void setId(int newId){
        this.albumId  = neweId;
    }

    public void setAuthors(ArrayList<Author>authorList){
        this.authors =  authorList;
    }
    //OR

    public void addAuthor(Song newAuthor){//to add a single author
        this.authors.add(newAuthor);
    }

    public void setSongs(ArrayList<Song>songList){
        this.songs = songList;
    }
    //OR

    public void addSong(Song newSong){
        this.songs.add(newSong);
    }



}