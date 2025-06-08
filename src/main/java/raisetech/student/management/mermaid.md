```mermaid
classDiagram
    class Student {
        +int id
        +String name
        +String email
        +getDetails()
    }

    class StudentDetail {
        +String address
        +String phoneNumber
        +String birthDate
        +getContactInfo()
    }

    class Course {
        +int courseId
        +String courseName
        +String description
        +getCourseInfo()
    }

    class StudentCourse {
        +int id
        +int studentId
        +int courseId
        +String enrollmentDate
    }

    Student --> StudentDetail : has
    Student --> StudentCourse : enrolls in
    StudentCourse --> Course : refers to
