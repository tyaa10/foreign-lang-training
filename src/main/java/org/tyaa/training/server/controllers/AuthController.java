package org.tyaa.training.server.controllers;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.tyaa.training.server.models.ResponseModel;
import org.tyaa.training.server.models.RoleModel;
import org.tyaa.training.server.models.UserModel;
import org.tyaa.training.server.services.interfaces.IAuthService;

/**
 * Контроллер аутентификации для регистрации, входа и выхода пользователей
 * */
@Tag(name = "Authentication", description = "Authentication, users, roles")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final IAuthService authService;

    public AuthController(IAuthService authService) {
        this.authService = authService;
    }

    /**
     * Получение списка всех ролей, которые могут иметь пользователи
     * */
    @Operation(method = "Get all roles")
    @Secured("ROLE_ADMIN")
    @GetMapping("/admin/roles")
    public ResponseEntity<ResponseModel> getRoles () {
        return new ResponseEntity<>(authService.getRoles(), HttpStatus.OK);
    }

    @Operation(method = "Create a new role")
    @Secured("ROLE_ADMIN")
    @PostMapping("/admin/roles")
    public ResponseEntity<ResponseModel> createRole (@RequestBody RoleModel roleModel) {
        ResponseModel responseModel = authService.createRole(roleModel);
        return new ResponseEntity<>(
                responseModel,
                (responseModel.getMessage().toLowerCase().contains("created"))
                        ? HttpStatus.CREATED
                        : (responseModel.getMessage().equals("This name is already taken")
                        ? HttpStatus.CONFLICT
                        : HttpStatus.BAD_GATEWAY)
        );
    }

    @Operation(method = "Get users with specific role id")
    @Secured("ROLE_ADMIN")
    @GetMapping("/admin/roles/{id}/users")
    public ResponseEntity<ResponseModel> getUsersByRole(@PathVariable Long id) {
        ResponseModel responseModel =
                authService.getRoleUsers(id);
        return new ResponseEntity<>(
                responseModel,
                (responseModel.getData() != null)
                        ? HttpStatus.OK
                        : HttpStatus.NOT_FOUND
        );
    }

    @Operation(method = "Create a new user")
    @PostMapping("/users")
    public ResponseEntity<ResponseModel> createUser(@RequestBody UserModel userModel) {
        ResponseModel responseModel =
                authService.createUser(userModel);
        return new ResponseEntity<>(
                responseModel,
                (responseModel.getMessage().toLowerCase().contains("created"))
                        ? HttpStatus.CREATED
                        : (responseModel.getMessage().equals("This name is already taken")
                        ? HttpStatus.CONFLICT
                        : HttpStatus.BAD_GATEWAY)
        );
    }

    @Operation(method = "Delete user by id")
    @DeleteMapping(value = "/users/{id}")
    public ResponseEntity<ResponseModel> deleteUser(@PathVariable Long id) {
        return new ResponseEntity<>(authService.deleteUser(id), HttpStatus.NO_CONTENT);
    }

    @Operation(method = "Make the user an admin by id")
    @Secured("ROLE_ADMIN")
    @PatchMapping(value = "/admin/users/{id}/makeadmin")
    public ResponseEntity<ResponseModel> makeUserAdmin(@PathVariable Long id) throws Exception {
        return new ResponseEntity<>(authService.makeUserAdmin(id), HttpStatus.OK);
    }

    @Operation(method = "Check if the user is a guest or not")
    @GetMapping(value = "/users/check")
    // @ResponseBody
    /** @param authentication объект стандартного типа с данными учетной записи
     * пользователя теущего http-сеанса, если ранее произошла успешная аутентификация,
     * получается внедрением зависимости через аргумент метода */
    public ResponseEntity<ResponseModel> checkUser(Authentication authentication) {
        ResponseModel responseModel = authService.check(authentication);
        return new ResponseEntity<>(
                responseModel,
                (responseModel.getData() != null)
                        ? HttpStatus.OK
                        : HttpStatus.UNAUTHORIZED
        );
    }

    @Hidden
    @GetMapping("/users/signedout")
    public ResponseEntity<ResponseModel> signedOut() {
        return new ResponseEntity<>(authService.onSignOut(), HttpStatus.OK);
    }

    @Hidden
    @GetMapping("/users/onerror")
    public ResponseEntity<ResponseModel> onError() {
        return new ResponseEntity<>(authService.onError(), HttpStatus.UNAUTHORIZED);
    }
}
