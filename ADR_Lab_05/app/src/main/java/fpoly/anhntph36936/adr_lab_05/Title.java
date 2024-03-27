package fpoly.anhntph36936.adr_lab_05;

public class Title {
    private String _id;
    private String name;

    public Title(String _id, String name) {
        this._id = _id;
        this.name = name;
    }

    public Title() {
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
