package com.ssafy.account.api;

import com.ssafy.account.api.request.AccessStatusChangeRequest;
import com.ssafy.account.api.request.access.AccessSaveRequest;
import com.ssafy.account.api.request.access.AccountNumberForAccess;
import com.ssafy.account.common.api.Response;
import com.ssafy.account.service.AccessService;
import com.ssafy.external.service.OauthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.ssafy.account.common.api.status.SuccessCode.*;

@RestController
@RequiredArgsConstructor
public class AccessController {

    private final AccessService accessService;
    private final OauthService oauthService;

    @PostMapping("/access/request")
    public Response createAccessRequest(@RequestHeader("id") Long requesterId, @RequestBody AccessSaveRequest request){
        String requesterName = oauthService.getUserName(requesterId);
        Long accessRequest = accessService.createAccessRequest(requesterId, requesterName, request);
        return Response.success(GENERAL_SUCCESS, accessRequest);
    }

    @PostMapping("/access/test")
    public Response test(Long requesterId) {
        String requesterName = oauthService.getUserName(requesterId);
        return Response.success(GENERAL_SUCCESS, requesterName);
    }

    @PutMapping("/access/accept")
    public Response acceptAccessRequest(@RequestBody AccessStatusChangeRequest request) {
        return Response.success(GENERAL_SUCCESS, accessService.acceptAccessRequest(request.getAccessId()));
    }

    @DeleteMapping("/access/reject")
    public Response rejectAccessRequest(@RequestBody AccessStatusChangeRequest request) {
        return Response.success(GENERAL_SUCCESS, accessService.rejectAccessRequest(request.getAccessId()));
    }

    @GetMapping("/access/receive/list")
    public Response getUnacceptedAccessRequestsForAccountOwner(@ModelAttribute AccountNumberForAccess access) {
        return Response.success(GENERAL_SUCCESS, accessService.getUnacceptedAccessRequestsForAccountOwner(access));
    }

    @GetMapping("/access/send/list")
    public Response getUnacceptedAccessRequestsForRequester(@RequestHeader("id") Long memberId) {
        return Response.success(GENERAL_SUCCESS, accessService.getUnacceptedAccessRequestsForRequester(memberId));
    }
}
