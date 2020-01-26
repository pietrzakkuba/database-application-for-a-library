package fxapp.containers;

public class Choice {
    private String value;
    private Integer id;

    public String getValue() {
        return value;
    }

    public Integer getId() {
        return id;
    }

    public Choice(Integer id, String value){
        this.id = id;
        this.value = value;
    }
}
