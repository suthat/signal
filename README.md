Signal 
======

Signal is An Opensource Framework for Multiple-Consumers Notification Service. It was found, designed, and developed at School of Information Technology, KMUTT (Thailand) or SIT (http://www.sit.kmutt.ac.th)

Users are beginning to rely on notifications, both in their private and working lives.
Real-time messages deliver up-to-the-minute direct to users’ mobile phones, tablets, cameras , watches, glasses, or desktops. (F. Wilson. “Mobile Notifications”. A VC musings of a VC in NYC (1st March 1 2011)
http://www.avc.com/a_vc/2011/03/mobile-notifications.html)

#### Problems of existed notification service ####

It ends up with different notifications on different devices.

Less feedback (most of them have only ack, status).

If some services, smart devices, and IO devices want to provide notification messages to the users by creating a simply and describable statement.

Commercial Licenses (mostly, for service costs and financial).

> + Signal is written on Java based-application (J2EE)
> + Folder "Signal" is a main project (Core Application)
> + Folder "SignalRT" is a real-time scheduling service 
> + Folder "Jignal" is a Signal 's Java client library (Dev and Test)
> + Folder "libs" contains all of opensource libraries which are used in this project
> + Folder "lang" contains examples of notification languages. It 's Signal proposed languages
> + Folder "db" contains simple relational database schema and content (MySQL exported file) 
> + Folder "script" contains examples of Signal provider and client libraries
> + Folder "test" contains examples of test scripts and test files


#### Opensource dependencies ####

* Java JDK 1.7 (http://www.java.com)
* Tomcat 7.0 (http://tomcat.apache.org)
* MySQL 5.5+ (http://www.mysql.com)
* RabbitMQ (http://www.rabbitmq.com)
* RabbitMQ STOMP (http://www.rabbitmq.com/stomp.html)
* RabbitMQ Managemant (http://www.rabbitmq.com/management.html)

All code on this site is licensed under the Apache License (http://www.apache.org/licenses) unless stated otherwise. School of Information Technology, KMUTT (Thailand).