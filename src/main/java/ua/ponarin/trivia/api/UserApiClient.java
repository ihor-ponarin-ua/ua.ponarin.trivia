package ua.ponarin.trivia.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ua.ponarin.trivia.model.User;

import javax.validation.constraints.NotBlank;

@FeignClient(name = "userApiClient", url = "${api.users}")
@Validated
public interface UserApiClient {
    @GetMapping("/users/{id}")
    User getById(@NotBlank @PathVariable String id);
}
