File Upload Example
===================
Java file upload is a sample project to test out different means of uploading files in Spring Boot applications.  The goal here was
to explore the different ways of configuring a Spring Boot application's handling of a multipart POST, to see what the most efficient
way was to handle uploading huge files.  Micro-services often have limited disk space, which makes them not ideal for a service
that allows multiple users to concurrently upload large-ish files if the multipart implementation creates temporary files on the
server for every file uploaded.  Unfortunately, this is standard behavior.

The long and short of playing around is:
* With their standard APIs, both with and without apaches commons-fileupload, uploaded files are stored either as a byte array in memory, or as a temporary file.
  There's no way around this.
* However, it's possible to use commons-fileupload's request parsing API to read an uploaded file as a stream, directly off of the request,
  without it being buffered into memory or a file.
* Websockets aren't explored, but might be a cool way to do things too.

You can configure things to use commons-fileupload, or the standard Spring multipart implementation.  It appears that the
Servlet 3.0 Parts API supersedes commons-fileupload, and indeed, with Spring Boot the default parts implementation uses
a version of commons-fileupload copied into Tomcat (or, if using Jetty, Jetty has its own
`MultiPartInputStreamParser.MultiPart` class for a Parts implementation that behaves the same as that of commons-fileupload).
However, I believe using the Parts API always buffers files into memory/disk, so the parts can be accessed in arbitrary
order.