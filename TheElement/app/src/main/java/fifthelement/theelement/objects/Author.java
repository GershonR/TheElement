package fifthelement.theelement.objects;

import java.util.UUID;

public class Author{

    private UUID uuid;
    private String name;


    public Author(String name){

        this.uuid = UUID.randomUUID();
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public UUID getUUID(){
        return uuid;
    }

    public void setUUID(UUID uuid) { this.uuid = uuid; }

    public void setName(String newName){
        this.name = newName;
    }

}