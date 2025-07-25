INSERT INTO students (name, kana_name, nickname, email, area, age, sex)
VALUES
( '山田太郎', 'ヤマダタロウ', 'タロ', 'taro@example.com', '東京', 25, '男性'),
('鈴木一郎', 'スズキイチロウ', 'イチ', 'ichiro@example.com', '大阪', 30, '男性'),
( '田中花子', 'タナカハナコ', 'ハナ', 'hana@example.com', '北海道', 22, '女性'),
( '佐藤良子', 'サトウリョウコ', 'リョウ', 'ryoko@example.com', '福岡', 28, '女性'),
( '伊藤悠', 'イトウハルカ', 'ハル', 'haruka@example.com', '愛知', 35, 'その他');

INSERT INTO students_courses (student_id, course_name, course_start_at, course_end_at, application_status)
VALUES
('1', 'Javaコース', '2023-04-01 09:00:00', '2023-07-01 15:00:00', 'PROVISIONAL'),
('1', 'AWSコース', '2023-05-01 10:00:00', '2023-08-01 16:00:00', 'PROVISIONAL'),
('2', 'デザインコース', '2023-06-01 11:00:00', '2023-09-01 17:00:00', 'PROVISIONAL'),
('3', 'Web制作コース', '2023-07-01 12:00:00', '2023-10-01 18:00:00', 'PROVISIONAL'),
('3', 'デザインコース', '2023-08-01 13:00:00', '2023-11-01 19:00:00', 'PROVISIONAL'),
('3', 'マーケティングコース', '2023-09-01 09:00:00', '2024-01-01 15:00:00', 'PROVISIONAL'),
('4', 'Javaコース', '2023-10-01 10:00:00', '2024-02-01 16:00:00', 'PROVISIONAL'),
('4', 'マーケティングコース', '2023-11-01 11:00:00', '2024-03-01 17:00:00', 'PROVISIONAL'),
('5', 'AWSコース', '2023-12-01 12:00:00', '2024-04-01 18:00:00', 'PROVISIONAL'),
('1', 'Web制作コース', '2024-01-01 13:00:00', '2024-05-01 19:00:00', 'PROVISIONAL');