package com.ssafy.account.api;

import com.ssafy.account.api.request.access.AccessSaveRequest;
import com.ssafy.account.api.request.access.AccountNumberForAccess;
import com.ssafy.account.common.api.Response;
import com.ssafy.account.service.AccessService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.ssafy.account.common.api.status.SuccessCode.*;

@RestController
@RequiredArgsConstructor
public class AccessController {

    private final AccessService accessService;

    @PostMapping("/request")
    public Response createAccessRequest(@RequestHeader("id") Long requesterId, @RequestHeader("name") String requesterName, @RequestBody AccessSaveRequest request){
        Long accessRequest = accessService.createAccessRequest(requesterId, requesterName, request);
        return Response.success(GENERAL_SUCCESS, accessRequest);
    }

    @PutMapping("/accept/{accessId}")
    public Response acceptAccessRequest(@PathVariable Long accessId) {
        return Response.success(GENERAL_SUCCESS, accessService.acceptAccessRequest(accessId));
    }

    @DeleteMapping("/reject/{accessId}")
    public Response rejectAccessRequest(@PathVariable Long accessId) {
        return Response.success(GENERAL_SUCCESS, accessService.rejectAccessRequest(accessId));
    }

    @GetMapping("/receive/list")
    public Response getUnacceptedAccessRequestsForAccountOwner(@ModelAttribute AccountNumberForAccess access) {
        return Response.success(GENERAL_SUCCESS, accessService.getUnacceptedAccessRequestsForAccountOwner(access));
    }

    @GetMapping("/send/list")
    public Response getUnacceptedAccessRequestsForRequester(@RequestHeader("id") Long memberId) {
        return Response.success(GENERAL_SUCCESS, accessService.getUnacceptedAccessRequestsForRequester(memberId));
    }
}
