# jeopardy_db

(done in 2016)  
Kind of a database application for Jeopardy! questions and answers. The goal is to load a base of Jeopardy! questions stored
in a json file, and then create many binary files representing that data; the files being: the data itself, and a variety of
B-Trees that are indexing the different attributes presented in the data(category, date, value, program number),
also a Patricia tree for the text information (question).  
With those binary files in hand we can efficiently (luckily) perform searches in that data, as i using a database/dbms.

### screenshots

Menu where you can load a the json file and perform your queries (when you load the file all the index files will be created, so it might take a while since the databse is huge):

![Alt text](screenshots/menu.png?raw=true "Menu")

Result screen, filtering for #1 show:

![Alt text](screenshots/list.png?raw=true "Result List")

You click click on Answer to see that answer to that question (or rather the question to that answer). Here we're doing a word seach for "Finland":

![Alt text](screenshots/finland.png?raw=true "Finland")
