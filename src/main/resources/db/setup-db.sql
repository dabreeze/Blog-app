create database blogdb;

create user 'blog_user'@'localhost' identified by 'Blog123';
grant all privileges on blogdb.* to 'blog_user'@'localhost';
flush privileges