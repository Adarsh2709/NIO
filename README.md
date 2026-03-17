# Java NIO Event-Driven Server Runtime

This repository contains an experimental implementation of a non-blocking server runtime built from scratch using Java NIO.

The project focuses on understanding how event-driven networking systems internally manage connection registration, readiness-based I/O scheduling, and structured request processing pipelines. It is intended for architectural exploration and learning rather than production deployment.

---

## Architecture Overview

The server follows a selector-driven execution model where a single event loop monitors multiple channels and dispatches processing only when I/O operations can proceed without blocking.

### Execution Flow


Server.start()
→ Accept incoming connection
→ Register channel with selector
→ Event loop waits for readiness events
→ Retrieve connection context
→ Execute handler pipeline
→ Generate and write response


This approach demonstrates how modern networking runtimes coordinate concurrency using readiness signals instead of allocating a dedicated thread per connection.

---

## Project Structure


nio-server/

main/
Main.java

core/
Server.java
EventLoop.java
Connection.java
ChannelHandler.java

pipeline/
HandlerPipeline.java
RouterHandler.java
LoggingHandler.java

protocol/
HttpParser.java
HttpRequest.java
HttpResponse.java

util/


### main

Contains the application entry point responsible for bootstrapping the server runtime and initiating the event loop.

### core

Implements fundamental runtime components including server lifecycle management, selector-based event loop execution, connection state tracking, and low-level channel interaction.

### pipeline

Defines the sequential request processing chain. Handlers encapsulate specific responsibilities such as logging, routing, and response preparation, enabling extensible request lifecycle control.

### protocol

Provides minimal HTTP parsing and encoding logic to transform raw network buffers into structured request and response objects.

### util

Shared helper utilities used across runtime modules.

---

## Event Loop Model

The runtime continuously monitors I/O readiness using the selector mechanism.  
When a channel becomes ready, the corresponding connection context is retrieved and processing is delegated to the handler pipeline. This enables efficient multiplexing of multiple connections within a controlled execution flow.

---

## Objectives

This implementation explores:

- Event-driven concurrency models  
- Non-blocking network programming using Java NIO  
- Internal architecture patterns used in networking frameworks  
- Request dispatch and handler pipeline design  
- Systems-oriented backend runtime concepts  

---

## Future Enhancements

- Multi-threaded reactor model  
- Write buffering and backpressure control  
- Persistent connection handling  
- Dynamic middleware injection  
- Runtime observability and metrics  
- Secure channel (TLS) support  
