public class Author{

    private int id;
    private String name;


    public Author(int id; String name){

        this.id = id;
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public String getId(){
        return id;
    }

    public void setName(String newName){
        this.name = newName;
    }

    public void setId(int newId){
        this.id  = newId;
    }



}