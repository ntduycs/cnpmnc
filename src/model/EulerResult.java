package model;

public class EulerResult {
    private String existPath;
    private String path;

    public EulerResult(String existPath, String path) {
        this.existPath = existPath;
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getExistPath() {
        return existPath;
    }

    public void setExistPath(String existPath) {
        this.existPath = existPath;
    }
}
