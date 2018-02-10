STUDENTCLIENT by Erik Gabrielsson Spring 2017
LICENSE: GNU GPLv3

Client Application that should connect to StudentServer. This is an AndroidStudio Project.


Background:
  This project is part two (of two) in a school (Yrgo.se) project to create a full stack application.
  This part is the frontend client. To fulfill the acceptance criteria the Frontend should be a native
  Andriod application. It should be able to present student data provided by StudentServer (part one of 
  the same project) it should be able to communicate by REST with XML or Json. It should be able to handle
  that the Server part went off-line. It should utilize the default SQLite db provided by Andriod. And 
  bouns points if cacheing was implemented.
  
Implementation:
  I choose to implement both json and xml communication and a caching function. Also the application
  prechaches (asynk, background) data for all students so if the server goes offline the cache should
  be at an acceptable level.
  
