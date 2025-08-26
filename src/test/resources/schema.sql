   CREATE TABLE IF NOT EXISTS students(
    id VARCHAR(36) PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    kana_name VARCHAR(50) NOT NULL,
    nickname VARCHAR(50),
    email VARCHAR(50) NOT NULL,
    area VARCHAR(50),
    age INT,
    sex VARCHAR(10),
    remark TEXT,
    is_deleted BOOLEAN
    );

CREATE TABLE students_courses (
  id INT AUTO_INCREMENT PRIMARY KEY,
  student_id VARCHAR(36) NOT NULL,
  course_name VARCHAR(50) NOT NULL,
  course_start_at TIMESTAMP,
  course_end_at TIMESTAMP,
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  application_status VARCHAR(20) NOT NULL DEFAULT 'PROVISIONAL'
);