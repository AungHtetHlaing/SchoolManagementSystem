package school.models.pay_amount;

import school.models.class_entity.ClassEntity;
import school.models.section.Section;

public class PayAmount {
    private int id;
    private ClassEntity classEntity;
    private Section section;
    private int teacher_amount, student_amount;

    public PayAmount(int id, ClassEntity classEntity, Section section, int teacher_amount, int student_amount) {
        this.id = id;
        this.classEntity = classEntity;
        this.section = section;
        this.teacher_amount = teacher_amount;
        this.student_amount = student_amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getTeacher_amount() {
        return teacher_amount;
    }

    public void setTeacher_amount(int teacher_amount) {
        this.teacher_amount = teacher_amount;
    }

    public int getStudent_amount() {
        return student_amount;
    }

    public void setStudent_amount(int student_amount) {
        this.student_amount = student_amount;
    }
}
