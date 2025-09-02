package sofiarodfer.project6.controller

import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import sofiarodfer.project6.dto.request.UserCreateRequest
import sofiarodfer.project6.dto.request.UserUpdateRequest
import sofiarodfer.project6.dto.response.UserResponse
import sofiarodfer.project6.mapper.UserMapper
import sofiarodfer.project6.repository.AccountRepository
import sofiarodfer.project6.service.UserService

@RestController
@RequestMapping("/admin")
class AdminController(
    private val userService: UserService,
    private val accountRepository: AccountRepository,
    private val userMapper: UserMapper
) {
    @GetMapping("/users")
    fun listUsers(): List<UserResponse> {
        val userDTOs = userService.findAll()
        return userDTOs.map { userMapper.toResponse(it, null) }
    }

    @GetMapping("/users/{id}")
    fun getUserById(@PathVariable id: Long): UserResponse {
        val user = userService.findById(id)
        val account = accountRepository.findByUserId(id).orElse(null)
        val userDto = userMapper.toDTO(user)
        return userMapper.toResponse(userDto, account)
    }

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    fun createUser(@Valid @RequestBody request: UserCreateRequest): UserResponse {
        val userDTO = userService.createUser(request)
        val account = accountRepository.findByUserId(userDTO.id).orElse(null)
        return userMapper.toResponse(userDTO, account)
    }

    @PutMapping("/users/{id}")
    fun updateUser(@PathVariable id: Long, @RequestBody request: UserUpdateRequest): UserResponse {
        val updatedUserDTO = userService.updateUser(id, request)
        val account = accountRepository.findByUserId(id).orElse(null)
        return userMapper.toResponse(updatedUserDTO, account)
    }

    @DeleteMapping("/users/{id}")
    fun deleteUser(@PathVariable id: Long): ResponseEntity<Void> {
        userService.deleteUser(id)
        return ResponseEntity.noContent().build()
    }
}