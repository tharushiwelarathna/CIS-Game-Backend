package com.tharushi.tomatogame.controller.AppController;

import com.tharushi.tomatogame.dto.common.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping(value = "/application")
@RequiredArgsConstructor
@Log4j2
public class AppController {
    // Storing the application version from the configuration file
    @Value("${appVersion}")
    private String appVersion;

    // Handling requests to get the application version
    @GetMapping(value = "/version")
    // Responding with the application version in a CommonResponse object
    public ResponseEntity<?> getAppVersion() {
        return ResponseEntity.ok(new CommonResponse<>(true, appVersion));
    }


}
