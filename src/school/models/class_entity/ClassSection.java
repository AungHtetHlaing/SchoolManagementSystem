package school.models.class_entity;

import school.models.section.Section;

public class ClassSection {
    private int id;
    private String name;
    private Section section;

    public ClassSection(int id, String name, Section section) {
        this.id = id;
        this.name = name;
        this.section = section;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    @Override
    public String toString() {
        return "ClassSection{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", section='" + section + '\'' +
                '}';
    }
}
