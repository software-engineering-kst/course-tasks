package kz.lakida.javacourse.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloWorldController {

    @GetMapping("/world")
    public ResponseEntity<String> helloWorld(@RequestParam(name = "name", defaultValue = "no name") String name) {
        return ResponseEntity.ok("<h1>Hello,world</h1><p>Wassup</p> " + name);
    }

    @GetMapping("/peeps")
    public ResponseEntity<String> helloPeeps() {
        return ResponseEntity.ok("Hello, peeps");
    }
}
