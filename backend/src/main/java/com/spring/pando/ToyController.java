package com.spring.pando;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/api2")
public class ToyController {

    @ResponseBody
    @PostMapping("/data")
    public Map<String, Object> getData() {
        Map<String, Object> dummy = new HashMap();
        dummy.put("result", "api data");
        return dummy;
    }

}
