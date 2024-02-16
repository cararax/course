-- delete from tb_lesson;
-- delete from tb_module;
-- delete from tb_course;

-- Insert data into CourseModel table
INSERT INTO tb_course (course_id, course_level, course_status, creation_date, description, image_url, last_update_date,
                       name, user_instructor)
VALUES ('4a0277df-dbd7-46b0-ac7f-3dfe86627f3c', 'BEGINNER', 'IN_PROGRESS', '2024-02-14', 'Introduction to Programming',
        'https://example.com/course1.jpg', '2024-02-14', 'Java Programming 101',
        '5a0277df-dbd7-46b0-ac7f-3dfe86627f3c'),
       ('4a0277df-dbd7-46b0-ac7f-3dfe86627f3d', 'INTERMEDIATE', 'CONCLUDED', '2024-02-14', 'Advanced Java Concepts',
        'https://example.com/course2.jpg', '2024-02-14', 'Java Programming 201',
        '5a0277df-dbd7-46b0-ac7f-3dfe86627f3d');

-- Insert data into ModuleModel table
INSERT INTO tb_module (module_id, course_course_id, creation_date, description, title)
VALUES ('4a0277df-dbd7-46b0-ac7f-3dfe86627f3e', '4a0277df-dbd7-46b0-ac7f-3dfe86627f3c', '2024-02-14',
        'Introduction to Java basics', 'Java Basics'),
       ('4a0277df-dbd7-46b0-ac7f-3dfe86627f3f', '4a0277df-dbd7-46b0-ac7f-3dfe86627f3c', '2024-03-14',
        'Object-oriented programming concepts', 'OOP Concepts'),
       ('4a0277df-dbd7-46b0-ac7f-3dfe86627f4a', '4a0277df-dbd7-46b0-ac7f-3dfe86627f3d', '2024-04-14',
        'Advanced data structures and algorithms', 'Data Structures'),
       ('4a0277df-dbd7-46b0-ac7f-3dfe86627f4b', '4a0277df-dbd7-46b0-ac7f-3dfe86627f3d', '2024-05-14',
        'Multithreading and concurrency', 'Concurrency');

-- Insert data into LessonModel table
INSERT INTO tb_lesson (lesson_id, module_module_id, creation_date, description, title, video_url)
VALUES ('4a0277df-dbd7-46b0-ac7f-3dfe86627f4b', '4a0277df-dbd7-46b0-ac7f-3dfe86627f3e', '2024-02-14',
        'Introduction to variables and data types', 'Variables and Data Types', 'https://example.com/lesson1.mp4'),
       ('4b0277df-dbd7-46b0-ac7f-3dfe86627f4b', '4a0277df-dbd7-46b0-ac7f-3dfe86627f3e', '2024-02-14',
        'Control flow statements in Java', 'Control Flow', 'https://example.com/lesson2.mp4'),
       ('4c0277df-dbd7-46b0-ac7f-3dfe86627f4b', '4a0277df-dbd7-46b0-ac7f-3dfe86627f3f', '2024-02-14',
        'Inheritance and polymorphism in Java', 'Inheritance and Polymorphism', 'https://example.com/lesson3.mp4'),
       ('4d0277df-dbd7-46b0-ac7f-3dfe86627f4b', '4a0277df-dbd7-46b0-ac7f-3dfe86627f3f', '2024-02-14',
        'Encapsulation and abstraction principles', 'Encapsulation and Abstraction', 'https://example.com/lesson4.mp4'),
       ('4e0277df-dbd7-46b0-ac7f-3dfe86627f4b', '4a0277df-dbd7-46b0-ac7f-3dfe86627f4a', '2024-02-14',
        'Data structures: arrays and linked lists', 'Arrays and Linked Lists', 'https://example.com/lesson5.mp4'),
       ('4f0277df-dbd7-46b0-ac7f-3dfe86627f4b', '4a0277df-dbd7-46b0-ac7f-3dfe86627f4a', '2024-02-14',
        'Tree and graph data structures', 'Trees and Graphs', 'https://example.com/lesson6.mp4'),
       ('4a1277df-dbd7-46b0-ac7f-3dfe86627f4b', '4a0277df-dbd7-46b0-ac7f-3dfe86627f4b', '2024-02-14',
        'Understanding multithreading in Java', 'Multithreading Basics', 'https://example.com/lesson7.mp4'),
       ('4a2277df-dbd7-46b0-ac7f-3dfe86627f4b', '4a0277df-dbd7-46b0-ac7f-3dfe86627f4b', '2024-02-14',
        'Concurrency control mechanisms', 'Concurrency Control', 'https://example.com/lesson8.mp4');
