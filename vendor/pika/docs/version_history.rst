Version History
===============

0.9.13 - 2013-05-15
-------------------
**Major Changes**

- IPv6 Support with thanks to Alessandro Tagliapietra for initial prototype
- Officially remove support for <= Python 2.5 even though it was broken already
- Drop pika.simplebuffer.SimpleBuffer in favor of the Python stdlib collections.deque object
- New default object for receiving content is a "bytes" object which is a str wrapper in Python 2, but paves way for Python 3 support
- New "Raw" mode for frame decoding content frames (#334) addresses issues #331, #229 added by Garth Williamson
- Connection and Disconnection logic refactored, allowing for cleaner separation of protocol logic and socket handling logic as well as connection state management
- New "on_open_error_callback" argument in creating connection objects and new Connection.add_on_open_error_callback method
- New Connection.connect method to cleanly allow for reconnection code
- Support for all AMQP field types, using protocol specified signed/unsigned unpacking

**Backwards Incompatible Changes**

- Method signature for creating connection objects has new argument "on_open_error_callback" which is positionally before "on_close_callback"
- Internal callback variable names in connection.Connection have been renamed and constants used. If you relied on any of these callbacks outside of their internal use, make sure to check out the new constants.
- Connection._connect method, which was an internal only method is now deprecated and will raise a DeprecationWarning. If you relied on this method, your code needs to change.
- pika.simplebuffer has been removed

**Bugfixes**

- BlockingConnection consumer generator does not free buffer when exited (#328)
- Unicode body payloads in the blocking adapter raises exception (#333)
- Support "b" short-short-int AMQP data type (#318)
- Docstring type fix in adapters/select_connection (#316) fix by Rikard Hultén
- IPv6 not supported (#309)
- Stop the HeartbeatChecker when connection is closed (#307)
- Unittest fix for SelectConnection (#336) fix by Erik Andersson
- Handle condition where no connection or socket exists but SelectConnection needs a timeout for retrying a connection (#322)
- TwistedAdapter lagging behind BaseConnection changes (#321) fix by Jan Urbański

**Other**

- Refactored documentation
- Added Twisted Adapter example (#314) by nolinksoft

0.9.12 - 2013-03-18
-------------------

**Bugfixes**

- New timeout id hashing was not unique

0.9.11 - 2013-03-17
-------------------

**Bugfixes**

- Address inconsistent channel close callback documentation and add the signature
  change to the TwistedChannel class (#305)
- Address a missed timeout related internal data structure name change
  introduced in the SelectConnection 0.9.10 release. Update all connection
  adapters to use same signature and docstring (#306).

0.9.10 - 2013-03-16
-------------------

**Bugfixes**

- Fix timeout in twisted adapter (Submitted by cellscape)
- Fix blocking_connection poll timer resolution to milliseconds (Submitted by cellscape)
- Fix channel._on_close() without a method frame (Submitted by Richard Boulton)
- Addressed exception on close (Issue #279 - fix by patcpsc)
- 'messages' not initialized in BlockingConnection.cancel() (Issue #289 - fix by Mik Kocikowski)
- Make queue_unbind behave like queue_bind (Issue #277)
- Address closing behavioral issues for connections and channels (Issue #275)
- Pass a Method frame to Channel._on_close in Connection._on_disconnect (Submitted by Jan Urbański)
- Fix channel closed callback signature in the Twisted adapter (Submitted by Jan Urbański)
- Don't stop the IOLoop on connection close for in the Twisted adapter (Submitted by Jan Urbański)
- Update the asynchronous examples to fix reconnecting and have it work
- Warn if the socket was closed such as if RabbitMQ dies without a Close frame
- Fix URLParameters ssl_options (Issue #296)
- Add state to BlockingConnection addressing (Issue #301)
- Encode unicode body content prior to publishing (Issue #282)
- Fix an issue with unicode keys in BasicProperties headers key (Issue #280)
- Change how timeout ids are generated (Issue #254)
- Address post close state issues in Channel (Issue #302)

** Behavior changes **

- Change core connection communication behavior to prefer outbound writes over reads, addressing a recursion issue
- Update connection on close callbacks, changing callback method signature
- Update channel on close callbacks, changing callback method signature
- Give more info in the ChannelClosed exception
- Change the constructor signature for BlockingConnection, block open/close callbacks
- Disable the use of add_on_open_callback/add_on_close_callback methods in BlockingConnection


0.9.9 - 2013-01-29
------------------

**Bugfixes**

- Only remove the tornado_connection.TornadoConnection file descriptor from the IOLoop if it's still open (Issue #221)
- Allow messages with no body (Issue #227)
- Allow for empty routing keys (Issue #224)
- Don't raise an exception when trying to send a frame to a closed connection (Issue #229)
- Only send a Connection.CloseOk if the connection is still open. (Issue #236 - Fix by noleaf)
- Fix timeout threshold in blocking connection - (Issue #232 - Fix by Adam Flynn)
- Fix closing connection while a channel is still open (Issue #230 - Fix by Adam Flynn)
- Fixed misleading warning and exception messages in BaseConnection (Issue #237 - Fix by Tristan Penman)
- Pluralised and altered the wording of the AMQPConnectionError exception (Issue #237 - Fix by Tristan Penman)
- Fixed _adapter_disconnect in TornadoConnection class (Issue #237 - Fix by Tristan Penman)
- Fixing hang when closing connection without any channel in BlockingConnection (Issue #244 - Fix by Ales Teska)
- Remove the process_timeouts() call in SelectConnection (Issue #239)
- Change the string validation to basestring for host connection parameters (Issue #231)
- Add a poller to the BlockingConnection to address latency issues introduced in Pika 0.9.8 (Issue #242)
- reply_code and reply_text is not set in ChannelException (Issue #250)
- Add the missing constraint parameter for Channel._on_return callback processing (Issue #257 - Fix by patcpsc)
- Channel callbacks not being removed from callback manager when channel is closed or deleted (Issue #261)

0.9.8 - 2012-11-18
------------------

**Bugfixes**

- Channel.queue_declare/BlockingChannel.queue_declare not setting up callbacks property for empty queue name (Issue #218)
- Channel.queue_bind/BlockingChannel.queue_bind not allowing empty routing key
- Connection._on_connection_closed calling wrong method in Channel (Issue #219)
- Fix tx_commit and tx_rollback bugs in BlockingChannel (Issue #217)

0.9.7 - 2012-11-11
------------------

**New features**

- generator based consumer in BlockingChannel (See :doc:`examples/blocking_consumer_generator` for example)

**Changes**

- BlockingChannel._send_method will only wait if explicitly told to

**Bugfixes**

- Added the exchange "type" parameter back but issue a DeprecationWarning
- Dont require a queue name in Channel.queue_declare()
- Fixed KeyError when processing timeouts (Issue # 215 - Fix by Raphael De Giusti)
- Don't try and close channels when the connection is closed (Issue #216 - Fix by Charles Law)
- Dont raise UnexpectedFrame exceptions, log them instead
- Handle multiple synchronous RPC calls made without waiting for the call result (Issues #192, #204, #211)
- Typo in docs (Issue #207 Fix by Luca Wehrstedt)
- Only sleep on connection failure when retry attempts are > 0 (Issue #200)
- Bypass _rpc method and just send frames for Basic.Ack, Basic.Nack, Basic.Reject (Issue #205)

0.9.6 - 2012-10-29
------------------

**New features**

- URLParameters
- BlockingChannel.start_consuming() and BlockingChannel.stop_consuming()
- Delivery Confirmations
- Improved unittests

**Major bugfix areas**

- Connection handling
- Blocking functionality in the BlockingConnection
- SSL
- UTF-8 Handling

**Removals**

- pika.reconnection_strategies
- pika.channel.ChannelTransport
- pika.log
- pika.template
- examples directory

0.9.5 - 2011-03-29
------------------

**Changelog**

- Scope changes with adapter IOLoops and CallbackManager allowing for cleaner, multi-threaded operation
- Add support for Confirm.Select with channel.Channel.confirm_delivery()
- Add examples of delivery confirmation to examples (demo_send_confirmed.py)
- Update uses of log.warn with warning.warn for TCP Back-pressure alerting
- License boilerplate updated to simplify license text in source files
- Increment the timeout in select_connection.SelectPoller reducing CPU utilization
- Bug fix in Heartbeat frame delivery addressing issue #35
- Remove abuse of pika.log.method_call through a majority of the code
- Rename of key modules: table to data, frames to frame
- Cleanup of frame module and related classes
- Restructure of tests and test runner
- Update functional tests to respect RABBITMQ_HOST, RABBITMQ_PORT environment variables
- Bug fixes to reconnection_strategies module
- Fix the scale of timeout for PollPoller to be specified in milliseconds
- Remove mutable default arguments in RPC calls
- Add data type validation to RPC calls
- Move optional credentials erasing out of connection.Connection into credentials module
- Add support to allow for additional external credential types
- Add a NullHandler to prevent the 'No handlers could be found for logger "pika"' error message when not using pika.log in a client app at all.
- Clean up all examples to make them easier to read and use
- Move documentation into its own repository https://github.com/pika/documentation

- channel.py

  - Move channel.MAX_CHANNELS constant from connection.CHANNEL_MAX
  - Add default value of None to ChannelTransport.rpc
  - Validate callback and acceptable replies parameters in ChannelTransport.RPC
  - Remove unused connection attribute from Channel

- connection.py

  - Remove unused import of struct
  - Remove direct import of pika.credentials.PlainCredentials
    - Change to import pika.credentials
  - Move CHANNEL_MAX to channel.MAX_CHANNELS
  - Change ConnectionParameters initialization parameter heartbeat to boolean
  - Validate all inbound parameter types in ConnectionParameters
  - Remove the Connection._erase_credentials stub method in favor of letting the Credentials object deal with  that itself.
  - Warn if the credentials object intends on erasing the credentials and a reconnection strategy other than NullReconnectionStrategy is specified.
  - Change the default types for callback and acceptable_replies in Connection._rpc
  - Validate the callback and acceptable_replies data types in Connection._rpc

- adapters.blocking_connection.BlockingConnection

  - Addition of _adapter_disconnect to blocking_connection.BlockingConnection
  - Add timeout methods to BlockingConnection addressing issue #41
  - BlockingConnection didn't allow you register more than one consumer callback because basic_consume was overridden to block immediately. New behavior allows you to do so.
  - Removed overriding of base basic_consume and basic_cancel methods. Now uses underlying Channel versions of those methods.
  - Added start_consuming() method to BlockingChannel to start the consumption loop.
  - Updated stop_consuming() to iterate through all the registered consumers in self._consumers and issue a basic_cancel.
