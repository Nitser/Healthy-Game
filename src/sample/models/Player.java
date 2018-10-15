package sample.models;

public class Player {

    private String name;
    private long firstGamePoints=0;

    public Player(String name){
        this.name = name;
    }

    public Player(String name, long firstGamePoints){
        this.name = name;
        this.firstGamePoints = firstGamePoints;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getFirstGamePoints() {
        return firstGamePoints;
    }

    public void setFirstGamePoints(long firstGamePoints) {
        this.firstGamePoints = firstGamePoints;
    }
}
