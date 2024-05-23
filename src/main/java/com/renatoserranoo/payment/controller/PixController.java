package com.renatoserranoo.payment.controller;

import com.renatoserranoo.payment.dto.PixChargeRequest;
import com.renatoserranoo.payment.service.PixService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/pix")
public class PixController {

    @Autowired
    private PixService pixService;

    @CrossOrigin(origins = "*")
    @GetMapping
    public ResponseEntity pixCreateEVP(){
        JSONObject response = this.pixService.pixCreateEVP();
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(response.toString());
    }

    @CrossOrigin(origins = "*")
    @PostMapping
    public ResponseEntity pixCreateCharge(@RequestBody PixChargeRequest pixChargeRequest){
        JSONObject response = this.pixService.pixCreateCharge(pixChargeRequest);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(response.toString());
    }

}
