package model;

public class DijkstraResult {
    private Integer src;
    private Integer dest;
    private Integer minDistance;
    private String path;

    public DijkstraResult(Integer src, Integer dest, Integer minDistance, String path) {
        this.src = src;
        this.dest = dest;
        this.minDistance = minDistance;
        this.path = path;
    }

    public Integer getSrc() {
        return src;
    }

    public void setSrc(Integer src) {
        this.src = src;
    }

    public Integer getDest() {
        return dest;
    }

    public void setDest(Integer dest) {
        this.dest = dest;
    }

    public Integer getMinDistance() {
        return minDistance;
    }

    public void setMinDistance(Integer minDistance) {
        this.minDistance = minDistance;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
