package com.ritam.api.versioning;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class VersioningPersonController {

    //Url Versioning
    @GetMapping("/v1/person")
    public PersonV1 getPersonV1(){
        return new PersonV1("Ritam Ghosh");
    }

    @GetMapping("/v2/person")
    public PersonV2 getPersonV2(){
        return new PersonV2(new Name("Ritam", "Ghosh"));
    }


    //Request param versioning
    @GetMapping(path = "/person", params = "version=1")
    public PersonV1 getPersonV1RequestParameter(){
        return new PersonV1("Ritam Ghosh");
    }

    @GetMapping(path = "/person", params = "version=2")
    public PersonV2 getPersonV2RequestParameter(){
        return new PersonV2(new Name("Ritam", "Ghosh"));
    }

    //Custom Header versioning
    @GetMapping(path = "/person/custom-header", headers = "X-API-VERSION=1")
    public PersonV1 getPersonV1CustomHeader(){
        return new PersonV1("Ritam Ghosh");
    }

    @GetMapping(path = "/person/custom-header", headers = "X-API-VERSION=2")
    public PersonV2 getPersonV2CustomHeader(){
        return new PersonV2(new Name("Ritam", "Ghosh"));
    }

    //media type versioning
    @GetMapping(path = "/person/accept-header", produces = "application/vnd.company.app-v1+json")
    public PersonV1 getPersonV1MediaType(){
        return new PersonV1("Ritam Ghosh");
    }

    @GetMapping(path = "/person/accept-header", produces = "application/vnd.company.app-v2+json")
    public PersonV2 getPersonV2MediaType(){
        return new PersonV2(new Name("Ritam", "Ghosh"));
    }

}
