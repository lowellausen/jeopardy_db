# jeopardy_db

(done in 2016)  
Kind of a database application for Jeopardy! questions and answers. The goal is to load a base of Jeopardy! questions stored
in a json file, and then create many binary files representing that data; the files being: the data itself, and a variety of
B-Trees that are indexing the different attributes presented in the data(category, date, value, program number),
also a Patricia tree for the text information (question).  
With those binary files in hand we can efficiently (luckly) perform searches in that data, as i using a database/dbms.

### screenshots
