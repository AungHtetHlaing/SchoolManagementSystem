package school.models.subject;

import school.models.class_entity.ClassEntity;

public class SubjectClass {
    private ClassEntity classEntity;
    private Subject subject;

    public SubjectClass(ClassEntity classEntity, Subject subject) {
        this.classEntity = classEntity;
        this.subject = subject;
    }

    public ClassEntity getClassEntity() {
        return classEntity;
    }

    public void setClassEntity(ClassEntity classEntity) {
        this.classEntity = classEntity;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }
}
