package vn.gqhao.jobhunter.controller;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.gqhao.jobhunter.dto.request.SubscriberCreationRequest;
import vn.gqhao.jobhunter.dto.request.SubscriberUpdateRequest;
import vn.gqhao.jobhunter.dto.response.SubscriberResponse;
import vn.gqhao.jobhunter.service.SubscriberService;
import vn.gqhao.jobhunter.util.annotation.ApiMessage;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("${api.prefix}/subscribers")
public class SubscriberController {
    SubscriberService subscriberService;

    @PostMapping()
    @ApiMessage("Create subscriber")
    public ResponseEntity<SubscriberResponse> createSubscriber(@Valid @RequestBody SubscriberCreationRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(subscriberService.handleCreateSubscriber(request));
    }

    @PutMapping()
    @ApiMessage("Update subscriber")
    public ResponseEntity<SubscriberResponse> updateSubscriber(@Valid @RequestBody SubscriberUpdateRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(subscriberService.handleUpdateSubscriber(request));
    }
}
