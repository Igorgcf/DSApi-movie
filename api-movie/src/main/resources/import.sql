INSERT INTO tb_user (id, name, email, password) VALUES (UUID(), 'Igor', 'igor@gmail.com', '1234567' );
INSERT INTO tb_user (id, name, email, password) VALUES (UUID(), 'Nanci', 'nanci@gmail.com', '1234567');
INSERT INTO tb_user (id, name, email, password) VALUES (UUID(), 'Vitória', 'vitoria@gmail.com', '1234567');

INSERT INTO tb_role(id, authority) VALUES (UUID(), 'Admin');
INSERT INTO tb_role(id, authority) VALUES (UUID(), 'Member');

SET @user1_id(SELECT id FROM tb_user WHERE name = 'Igor');
SET @user2_id(SELECT id FROM tb_user WHERE name = 'Nanci');
SET @user3_id(SELECT id FROM tb_user WHERE name = 'Vitória');

SET @role1_id(SELECT id FROM tb_role WHERE authority = 'Admin');
SET @role2_id(SELECT id FROM tb_role WHERE authority = 'Member');

INSERT INTO tb_user_role(user_id, role_id) VALUES (@user1_id, @role1_id);
INSERT INTO tb_user_role(user_id, role_id) VALUES (@user2_id, @role2_id);
INSERT INTO tb_user_role(user_id, role_id) VALUES (@user3_id, @role2_id);

INSERT INTO tb_genre (id, name) VALUES (UUID(), 'Ação');
INSERT INTO tb_genre (id, name) VALUES (UUID(), 'Comédia');

SET @genre1_id(SELECT id FROM tb_genre WHERE name = 'Ação');
SET @genre2_id(SELECT id FROM tb_genre WHERE name = 'Comédia');

INSERT INTO tb_movie (id, title, sub_Title, year_Of_Release, img_Url, synopsis, genre_id) VALUES (UUID(), 'O homem de ferro', 'A batalha', 2024, 'www.img/url.com', 'Um homem com poderes incrivéis, salva o mundo enfrentando grandes batalhas', @genre1_id);
INSERT INTO tb_movie (id, title, sub_Title, year_Of_Release, img_Url, synopsis, genre_id) VALUES (UUID(), 'O Garfield', 'O gato sapéca', 2012, 'www.img/url.com', 'Um gato para lá de esperto', @genre2_id);

SET @movie1_id(SELECT id FROM tb_movie WHERE title = 'O homem de ferro');
SET @movie2_id(SELECT id FROM tb_movie WHERE title = 'O Garfield');

INSERT INTO tb_review(id, text, movie_id, user_id) VALUES (UUID(), 'Filmaço', @movie1_id, @user1_id);
INSERT INTO tb_review(id, text, movie_id, user_id) VALUES (UUID(), 'Muito engraçado', @movie2_id, @user2_id);
INSERT INTO tb_review(id, text, movie_id, user_id) VALUES (UUID(), 'Muitas risadas', @movie2_id, @user3_id);
