package fifthelement.theelement.objects;

public class Author {
// TODO: implement proper stuff here

    private int id;
    private String name;


    public Author(int id, String name) {

        this.id = id;
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public int getId() {
        return id;
    }

    public void setName(String newName){
        this.name = newName;
    }

    public void setId(int newId) {
        this.id  = newId;
    }



}