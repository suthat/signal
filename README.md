## Signal

This research addressed challenges of the cross-platform push messaging service where every modern devices could send messages and receive feedback among a multitude of different receiving devices. The design and implementation of Signal—a universal cross-platform messaging system—was proposed as a solution. Signal provided both the notification service middleware and notification messaging application. It comprised a set of messaging languages for describing, querying and mapping notification as well as defining and customizing feedback.

> Users are beginning to rely on notifications, both in their private and working lives. Real-time messages deliver up-to-the-minute direct to users’ mobile phones, tablets, cameras , watches, glasses, or desktops. (F. Wilson. “Mobile Notifications”. A VC musings of a VC in NYC (1st March 1 2011) http://www.avc.com/a_vc/2011/03/mobile-notifications.html)

#### Problems of existed notifications

* Lack of a unified service for sending multiple different notification payloads on different platform.
* Lack of customizable feedback. Most notification providers permitted only basic payload without feedback.
* Lack of capable open platform candidates.

#### Solutions

* Designing the messaging languages that include Messaging Description Language (MDL), Messaging Query Language (MQL) and Messaging Mapping Language (MML). These languages present a flexible and unified way of creating describable, understandable, and extendable notification and feedback payloads. They allow developers to deliver real-time messages to different receiver’s platforms without the hassle of needing to learn individual platform-specific libraries and APIs
* Designing the extendable messaging language parsers with support for multiple target platforms and a built-in real-time scheduling service (specific for MDL and MML). The parsers present themselves as the convenient tool for developers of the cross-platform messaging applications to achieve real-time messaging delivery in a flexible, easy and efficient manner.
* Developing an open source software for cross-platform messaging services and also to contribute new software to the open source community that fosters new ideas and features improvement with other developers.

#### Propose of Notification Languages ####

* Messaging Description Language (MDL)  
MDL is a core component of the proposed messaging languages. It consists of two distinct parts, namely, a notification metadata and a payload description that specifically describes authentication data, notification data, feedback data, real-time conditions, target APIs and target devices. All mentioned data are stored in a single standard JSON format. MDL is designed to be a describable, human-readable and extendable language.

* Messaging Query Language (MQL)  
MQL is an SQL-like language that can be used by developers to easily retrieve the notification data, its status and feedback from Signal.

* Messaging Mapping Language (MML)  
The JSON keys in MML are structurally similar to those of MDL. While MDL emphasizes the unified description language for specifying notification payloads uniformly, MML mainly focuses on ensuring seamless interoperation between MDL and HTTP-based non-MDL statements possibly received from or sent to certain IO devices not relying on major notification platforms.

#### License
All code on this site is licensed under the Apache License (http://www.apache.org/licenses) unless stated otherwise. School of Information Technology, KMUTT (Thailand).
