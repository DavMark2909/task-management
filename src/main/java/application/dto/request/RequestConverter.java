package application.dto.request;

import application.entity.request.Request;
import application.entity.User;

public class RequestConverter {

    public static UserRequestsDto convertToUserRequests(Request request){
        return UserRequestsDto.builder()
                .name(request.getName()).description(request.getDescription())
                .username(request.getIssuer().getUsername()).status(request.getStatus().getName())
                .build();
    }

    public static IssuedRequestsDto convertToIssuedRequests(Request request){
        return IssuedRequestsDto.builder()
                .name(request.getName()).description(request.getDescription())
                .status(request.getStatus().getName()).receivers(request.getReceivers().stream().map(User::getUsername).toList())
                .build();
    }
}
