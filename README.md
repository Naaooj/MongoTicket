# MongoTicket
=======

Simple task tracking system with GWT and MongoDB aimed to run on Apache Tomcat

## Links
[GWT](https://developers.google.com/web-toolkit/)   
[MongoDB](www.mongodb.org)   
[Tomcat](http://tomcat.apache.org)   

## Maven support
The project can be imported into eclipse after a simple *mvn eclipse:ecplose*.   
But, in order to fully work, fixes have to be done. In fact, several warnings 
relate to the dependant libraries that **must be copied** into the *war/WEB-INF/lib* 
folder. Otherwise there gonna be ClassNotFoundException exceptions.