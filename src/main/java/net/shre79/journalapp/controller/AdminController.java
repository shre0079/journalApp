package net.shre79.journalapp.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import net.shre79.journalapp.cache.AppCache;
import net.shre79.journalapp.entity.User;
import net.shre79.journalapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@Tag(name = "Admin APIs", description = "Endpoints for Admin functionalities")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private AppCache appCache;

    @Operation(summary = "Fetch all users", description = "Retrieve a list of all users present in the system")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Users retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No users found")
    })
    @GetMapping("/all-users")
    public ResponseEntity<?> getAllUsers() {
        List<User> all = userService.getAll();
        if (all != null && !all.isEmpty()) {
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Create an admin user", description = "Save an admin user to the system")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Admin user created successfully")
    })
    @PostMapping("/create-admin-user")
    public void createUser(@RequestBody User user) {
        userService.saveAdmin(user);
    }

    @Operation(summary = "Clear application cache", description = "Reset the application cache")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cache cleared successfully")
    })
    @GetMapping("clear-app-cache")
    public void clearAppCache(){
        appCache.init();
    }
}
