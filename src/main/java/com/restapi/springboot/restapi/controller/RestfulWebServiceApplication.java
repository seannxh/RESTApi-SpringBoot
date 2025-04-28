package com.restapi.springboot.restapi.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestfulWebServiceApplication {

    //GetMapping > RequestMapping
    @GetMapping(path = "/hello-world")
    public String helloWorld(){
        return "Hello World";
    }

    @GetMapping(path = "/hello-world-bean")
    public HelloWorldBean helloWorldBean(){
        return new HelloWorldBean("Hello World");
    }

    //{x} <= anything inside the {} parameter in the path is a Path parameter
    //use %s or %f or %d in String.format to represent the variable that will be replaced by a param name
    //String format makes the code format look cleaner
    @GetMapping(path = "/hello-world-bean/path-var/{name}")
    public HelloWorldBean helloWorldPathVar(@PathVariable String name){
        return new HelloWorldBean(String.format("Hello World, %s", name));
    }
}
