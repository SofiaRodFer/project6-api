package sofiarodfer.project6.controller

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import sofiarodfer.project6.dto.request.UserCreateRequest
import sofiarodfer.project6.dto.request.UserUpdateRequest
import sofiarodfer.project6.dto.response.UserResponse
import sofiarodfer.project6.mapper.UserMapper
import sofiarodfer.project6.service.UserService

@RestController
@RequestMapping("/admin")
class AdminController(
    private val userService: UserService,
    private val userMapper: UserMapper
) {
    @GetMapping("/users")
    fun listUsers(): List<UserResponse> {
        val userDTOs = userService.findAll()
        return userDTOs.map(userMapper::toResponse)
    }

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    fun createUser(@RequestBody request: UserCreateRequest): UserResponse {
        val userDTO = userService.createUser(request)
        return userMapper.toResponse(userDTO)
    }

    @PutMapping("/users/{id}")
    fun updateUser(@PathVariable id: Long, @RequestBody request: UserUpdateRequest): UserResponse {
        val updatedUserDTO = userService.updateUser(id, request)
        return userMapper.toResponse(updatedUserDTO)
    }
}