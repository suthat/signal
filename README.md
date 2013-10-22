Signal 
======

Signal is An Opensource Framework for Multiple-Consumers Notification Service. It was found, designed, and developed at School of Information Technology, KMUTT (Thailand) or SIT (http://www.sit.kmutt.ac.th)

> Users are beginning to rely on notifications, both in their private and working lives.
> Real-time messages deliver up-to-the-minute direct to users’ mobile phones, tablets, cameras , watches, glasses, or desktops. (F. Wilson. “Mobile Notifications”. A VC musings of a VC in NYC (1st March 1 2011)
http://www.avc.com/a_vc/2011/03/mobile-notifications.html)

#### Problems of existed notifications ####

> * It ends up with different notifications on different devices.
> * Less feedback (most of them have only ack, status).
> * If some services, smart devices, and IO devices want to provide notification messages to the users by creating a simply and describable statement.
> * Commercial Licenses (mostly, for service costs and financial).

#### Solutions ####

> * Design and propose notification description, mapping and query language which allows any provider to delivers messages to multiple type of user’s devices. 
> * Design and propose notification languages parsers and a real-time notification service that works with the proposed languages and parser.
> * Develop an opensource framework for multiple-consumers notification service.

#### Propose of Notification Languages ####

> * Notification Description Language (NDL)
> It will describes notification data, feedback, realtime conditions, channels, devices, etc. in a single standard format.
> * Notification Query Language (NQL)
> It will uses for retrieving notification data, status and its feedback.  
> * Notification Mapping Language (NML)
> It will uses for mapping notification data and feedback from different protocols to NDL. For example, mapping notification data created by a heat sensor or a water leak sensor in the home to NDL and notify to user’s devices.

#### On Github Right Now ####

> + Signal is written on Java based-application (J2EE)
> + Folder "Signal" is a main project (Core Application) - Simple Web Application for Signal provider, All Language Parsers, All Signal provider and consumer APIs 
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

#### Author ####
> * Suthat Ronglong
> * M.Sc.Computer Science
> * suthat@do.in.th

<form action="https://www.paypal.com/cgi-bin/webscr" method="post" target="_top">
<input type="hidden" name="cmd" value="_s-xclick">
<input type="hidden" name="encrypted" value="-----BEGIN PKCS7-----MIIHbwYJKoZIhvcNAQcEoIIHYDCCB1wCAQExggEwMIIBLAIBADCBlDCBjjELMAkGA1UEBhMCVVMxCzAJBgNVBAgTAkNBMRYwFAYDVQQHEw1Nb3VudGFpbiBWaWV3MRQwEgYDVQQKEwtQYXlQYWwgSW5jLjETMBEGA1UECxQKbGl2ZV9jZXJ0czERMA8GA1UEAxQIbGl2ZV9hcGkxHDAaBgkqhkiG9w0BCQEWDXJlQHBheXBhbC5jb20CAQAwDQYJKoZIhvcNAQEBBQAEgYAf5d7JR+2M1H+iIkFnkLNA04kbdLKICAWhZrE17nGEu9BzXnxM8kFnGQX4ValZYE33UsXHo0Z2Sd9RxJQyyrQHFVQvcgeYUo9eBN2afPVR6OIqruNekOSnxUk+e7pogYrjU3Ox/fliT7PmjT7xfq0tA2pt53mbtSouOpKlktWtQDELMAkGBSsOAwIaBQAwgewGCSqGSIb3DQEHATAUBggqhkiG9w0DBwQIybDxcRrBeQ6Agcge268iE2m5IfrDvU4T0RKYxVOitnWoSf0fmx0DtMH49yldB101KPlKEg8YGOsyl2mRQftFEHEfjUv8ZR4Ms1eLVEpLusuWOKFXAZuwo2q7lKh/g4o6FCIunbz5Urw9lRpUuyqdTuH3O/UxhLqlcZPK1xMIrZajV7Ivk/8ZeeHRVMLzCRygCU5S+sZ56Ua0qOZcOcUQwBeAxXYVIp7YKsmEO3C35zlpgmm7P7Zv+ZO1RwbqZ84YdJdtcXV8uEJbBwKCwfOP2SoBhqCCA4cwggODMIIC7KADAgECAgEAMA0GCSqGSIb3DQEBBQUAMIGOMQswCQYDVQQGEwJVUzELMAkGA1UECBMCQ0ExFjAUBgNVBAcTDU1vdW50YWluIFZpZXcxFDASBgNVBAoTC1BheVBhbCBJbmMuMRMwEQYDVQQLFApsaXZlX2NlcnRzMREwDwYDVQQDFAhsaXZlX2FwaTEcMBoGCSqGSIb3DQEJARYNcmVAcGF5cGFsLmNvbTAeFw0wNDAyMTMxMDEzMTVaFw0zNTAyMTMxMDEzMTVaMIGOMQswCQYDVQQGEwJVUzELMAkGA1UECBMCQ0ExFjAUBgNVBAcTDU1vdW50YWluIFZpZXcxFDASBgNVBAoTC1BheVBhbCBJbmMuMRMwEQYDVQQLFApsaXZlX2NlcnRzMREwDwYDVQQDFAhsaXZlX2FwaTEcMBoGCSqGSIb3DQEJARYNcmVAcGF5cGFsLmNvbTCBnzANBgkqhkiG9w0BAQEFAAOBjQAwgYkCgYEAwUdO3fxEzEtcnI7ZKZL412XvZPugoni7i7D7prCe0AtaHTc97CYgm7NsAtJyxNLixmhLV8pyIEaiHXWAh8fPKW+R017+EmXrr9EaquPmsVvTywAAE1PMNOKqo2kl4Gxiz9zZqIajOm1fZGWcGS0f5JQ2kBqNbvbg2/Za+GJ/qwUCAwEAAaOB7jCB6zAdBgNVHQ4EFgQUlp98u8ZvF71ZP1LXChvsENZklGswgbsGA1UdIwSBszCBsIAUlp98u8ZvF71ZP1LXChvsENZklGuhgZSkgZEwgY4xCzAJBgNVBAYTAlVTMQswCQYDVQQIEwJDQTEWMBQGA1UEBxMNTW91bnRhaW4gVmlldzEUMBIGA1UEChMLUGF5UGFsIEluYy4xEzARBgNVBAsUCmxpdmVfY2VydHMxETAPBgNVBAMUCGxpdmVfYXBpMRwwGgYJKoZIhvcNAQkBFg1yZUBwYXlwYWwuY29tggEAMAwGA1UdEwQFMAMBAf8wDQYJKoZIhvcNAQEFBQADgYEAgV86VpqAWuXvX6Oro4qJ1tYVIT5DgWpE692Ag422H7yRIr/9j/iKG4Thia/Oflx4TdL+IFJBAyPK9v6zZNZtBgPBynXb048hsP16l2vi0k5Q2JKiPDsEfBhGI+HnxLXEaUWAcVfCsQFvd2A1sxRr67ip5y2wwBelUecP3AjJ+YcxggGaMIIBlgIBATCBlDCBjjELMAkGA1UEBhMCVVMxCzAJBgNVBAgTAkNBMRYwFAYDVQQHEw1Nb3VudGFpbiBWaWV3MRQwEgYDVQQKEwtQYXlQYWwgSW5jLjETMBEGA1UECxQKbGl2ZV9jZXJ0czERMA8GA1UEAxQIbGl2ZV9hcGkxHDAaBgkqhkiG9w0BCQEWDXJlQHBheXBhbC5jb20CAQAwCQYFKw4DAhoFAKBdMBgGCSqGSIb3DQEJAzELBgkqhkiG9w0BBwEwHAYJKoZIhvcNAQkFMQ8XDTEzMDkxMzA0MzA0NlowIwYJKoZIhvcNAQkEMRYEFPwy62qTFA9zeUXmrfPDF8x9IEDeMA0GCSqGSIb3DQEBAQUABIGAVJrkMbsx/UQN6fAyPhENsLriTBlZSMIp0xncxGyTKYKvAf32Vv6qlhkTgrreC+c2/ejUXF/7YIUagcSlL824Ui+PSU2f+CD7LwRadGcefRzf4m/s3+54rdfxDEIh4vk7+vksZkIEbQSNzRxFWhEwlmNN2hvBOaJ0gGqS46VboiE=-----END PKCS7-----
">
<input type="image" src="https://www.paypalobjects.com/en_US/i/btn/btn_donateCC_LG.gif" border="0" name="submit" alt="PayPal - The safer, easier way to pay online!">
<img alt="" border="0" src="https://www.paypalobjects.com/en_GB/i/scr/pixel.gif" width="1" height="1">
</form>

All code on this site is licensed under the Apache License (http://www.apache.org/licenses) unless stated otherwise. School of Information Technology, KMUTT (Thailand).