public class Song{
    private int songId;
    private String songName;
    private ArrayList<Author>authors;//might not include this 
    private ArrayList<Album> albums; //might not include this

    public Song(int id, String name){
        this.albumId = id;
        this.albumName = name;
        albums = new ArrayList<Album>();
        authors = new ArrayList<Author>();
    }

    public String getName(){
        return songName;
    }

    public String getId(){
        return songId;
    }

    public ArrayList<Author> getAuthors(){
        return authors;
    }
    
    public ArrayList<Album> getAlbums(){
        return albums;
    }

    public void setName(String Newname){
        this.songName = newName;
    }

    public void setId(int newId){
        this.songId  = newId;
    }

     public void setAuthors(ArrayList<Author>authorList){
        this.authors =  authorList;
    }
    //OR

    public void addAuthor(Song newAuthor){//to add a single author
        this.authors.add(newAuthor);
    }

      public void setAlbums(ArrayList<Album>albumList){
        this.albums =  albumList;
    }
    //OR

    public void addAlbum(Song newAlbum){//to add a single album
        this.albums.add(newAlbum);
    }
    

}