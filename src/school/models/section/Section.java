package school.models.section;

public class Section {
    private int id;
    private String section;

    public Section(int id, String section) {
        this.id = id;
        this.section = section;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    @Override
    public String toString() {
        return id + " (" +section + ")";
    }
}
