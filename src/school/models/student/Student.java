package school.models.student;

import school.models.class_entity.ClassEntity;
import school.models.section.Section;

public class Student {
    private int id,age;
    private String name,phone,gender,address,created_at;
    private ClassEntity classEntity;
    private Section section;

    public Student(int id, int age, String phone, String gender, ClassEntity classEntity, Section section, String name, String address, String created_at) {
        this.id = id;
        this.age = age;
        this.phone = phone;
        this.gender = gender;
        this.classEntity = classEntity;
        this.section = section;
        this.name = name;
        this.address = address;
        this.created_at = created_at;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }


    public ClassEntity getClassEntity() {
        return classEntity;
    }

    public void setClassEntity(ClassEntity classEntity) {
        this.classEntity = classEntity;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
