# README

This document describes the requirements to be able to compile and run the grpc-gateway, This is a reverse proxy that
allows the execution of grpc services using HTTP Restful from a browser using javascript.

grpc-gateway is a plugin of protoc. It reads gRPC service definition, and generates a reverse-proxy server which 
translates a RESTful JSON API into gRPC. This server is generated according to custom options in your gRPC definition.

It helps you to provide your APIs in both gRPC and RESTful style at the same time.


![grpc-gateway](https://docs.google.com/drawings/d/12hp4CPqrNPFhattL_cIoJptFvlAqm5wLQ0ggqI5mkCg/pub?w=749&h=370)

## Installation Requirements for Mac OS

The following steps needs to be perform only once:

1. Install [homebrew](https://docs.brew.sh/Installation).
2. Install [go](https://golang.org/dl).
3. Install the following dependencies in go:
 - go get golang.org/x/net/context
 - go get google.golang.org/grpc
 - go get -u github.com/grpc-ecosystem/grpc-gateway/protoc-gen-grpc-gateway
 - go get -u github.com/grpc-ecosystem/grpc-gateway/protoc-gen-swagger
 - go get -u github.com/golang/protobuf/protoc-gen-go

## Usage

1. Define a <b>.proto</b> service. Once a proto has been defined the next step is to generate the stubs for <b>go</b> 
(this will be used by the grpc-gateway) as well as the stubs for the desired language in which the service will be 
implemented ([java](https://grpc.io/docs/tutorials/basic/java.html)).
   ```
   syntax = "proto3";
   
   import "google/api/annotations.proto";
   
   option java_multiple_files = true;
   option java_package = "io.grpc.examples.helloworld";
   option java_outer_classname = "HelloWorldProto";
   option objc_class_prefix = "HLW";
   
   package helloworld;
   
   // The greeting service definition.
   service Greeter {
     // Sends a greeting
     rpc SayHello (HelloRequest) returns (HelloReply) {
       option (google.api.http) = {
         post: "/v1/hello"
         body: "*"
       };
     }
   }
   
   // The request message containing the user's name.
   message HelloRequest {
     string name = 1;
   }
   
   // The response message containing the greetings
   message HelloReply {
     string message = 1;
   }
   ```

2. To create the stubs for <b>go</b> run the following command:
   ```
   protoc -I/usr/local/include -I. \
     -I$GOPATH/src \
     -I$GOPATH/src/github.com/grpc-ecosystem/grpc-gateway/third_party/googleapis \
     --go_out=plugins=grpc:. \
     path/to/your_service.proto
    ```
   If the command above fails as follow:
   ```
   protoc-gen-go: program not found or is not executable
   --go_out: protoc-gen-go: Plugin failed with status code 1.
   ```
    Then we need to add the binary plugin path as follow:
    ```
    protoc -I/usr/local/include -I. \
         -I$GOPATH/src \
         -I$GOPATH/src/github.com/grpc-ecosystem/grpc-gateway/third_party/googleapis \
         --go_out=plugins=grpc:. \
         --plugin=protoc-gen-go=/Users/xxx/go/bin/protoc-gen-go \
         path/to/your_service.proto
    ```
3. The next step is to generate the reverse-proxy with the following command:
   ```
   protoc -I/usr/local/include -I. \
     -I$GOPATH/src \
     -I$GOPATH/src/github.com/grpc-ecosystem/grpc-gateway/third_party/googleapis \
     --grpc-gateway_out=logtostderr=true:. \
     path/to/your_service.proto
   ```
   If the command above fails as follow:
   ```
   protoc-gen-grpc-gateway: program not found or is not executable
   --grpc-gateway_out: protoc-gen-grpc-gateway: Plugin failed with status code 1.
   ```
   Then we need to add the binary plugin path as follow:
   ```
     protoc -I/usr/local/include -I. \
          -I$GOPATH/src \
          -I$GOPATH/src/github.com/grpc-ecosystem/grpc-gateway/third_party/googleapis \
          --grpc-gateway_out=logtostderr=true:. \
          --plugin=protoc-gen-grpc-gateway=/Users/xxx/go/bin/protoc-gen-grpc-gateway \
          path/to/your_service.proto
   ```

4. Write an entrypoint:
   ```
   package main
   
   import (
     "flag"
     "net/http"
   
     "github.com/golang/glog"
     "golang.org/x/net/context"
     "github.com/grpc-ecosystem/grpc-gateway/runtime"
     "google.golang.org/grpc"
   
     gw "./proto"
   )
   
   var (
     echoEndpoint = flag.String("echo_endpoint", "localhost:50051", "endpoint of YourService")
   )
   
   func run() error {
     ctx := context.Background()
     ctx, cancel := context.WithCancel(ctx)
     defer cancel()
   
     mux := runtime.NewServeMux()
     opts := []grpc.DialOption{grpc.WithInsecure()}
     err := gw.RegisterGreeterHandlerFromEndpoint(ctx, mux, *echoEndpoint, opts)
     if err != nil {
       return err
     }
   
     return http.ListenAndServe(":8080", mux)
   }
   
   func main() {
     flag.Parse()
     defer glog.Flush()
   
     if err := run(); err != nil {
       glog.Fatal(err)
     }
   }
   ```

5. To test start your grpc server with the port <b>50051</b>

6. Start your reverse-proxy as follow:
   ```
   $ go run main.go 
   ```
7. Test your service using curl:
   ```
   curl --header "Content-Type: application/json"\
    --request POST --data '{"name": "Hola"}' \
    http://localhost:8080/v1/hello
   ```