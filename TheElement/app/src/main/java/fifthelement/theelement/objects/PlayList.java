public class PlayList{
    private int listId;
    private String listName;
    private ArrayList<Song>songs;

    public PlayList(int id, String name){
        this.listId = id;
        this.listName = name;
        song = new ArrayList<Songs>();
    }

    public String getName(){
        return listName;
    }

    public String getId(){
        return listId;
    }

    public ArrayList<Song> getSongs(){
        return songs;
    }

    public void setName(String newName){
        this.listName = newName;
    }

    public void setId(int newId){
        this.listId  = newId;
    }

     public void setSongs(ArrayList<Song>songList){
        this.songs =  songList;
    }
    //OR

    public void addSong(Song newSong){
        this.songs.add(newSong);
    }