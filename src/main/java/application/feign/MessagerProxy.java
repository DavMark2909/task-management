package application.feign;

import application.dto.feign.FeignMessage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name="messager", configuration = FeignClientConfig.class, url = "http://localhost:7777")
public interface MessagerProxy {

    @PostMapping("/chats/create-system-message")
    void sendSystemMessage(FeignMessage message);

}
