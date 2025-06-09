package com.carapp.backend.service

import com.carapp.backend.model.dto.AuthResponse
import com.carapp.backend.model.dto.LoginRequest
import com.carapp.backend.model.dto.RegisterRequest
import com.carapp.backend.model.entity.User
import com.carapp.backend.repository.UserRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import java.util.*

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) : UserDetailsService {

    @Value("\${jwt.secret}")
    private lateinit var jwtSecret: String

    @Value("\${jwt.expiration}")
    private var jwtExpiration: Long = 86400000 // 24 hours in milliseconds

    @Transactional
    fun register(request: RegisterRequest): AuthResponse {
        if (userRepository.existsByUsername(request.username)) {
            throw IllegalArgumentException("Username already exists")
        }

        val user = User(
            username = request.username,
            passwordHash = passwordEncoder.encode(request.password)
        )

        val savedUser = userRepository.save(user)
        val token = generateToken(savedUser)
        return AuthResponse(token = token, username = savedUser.username)
    }

    fun login(request: LoginRequest): AuthResponse {
        // Manual authentication instead of using AuthenticationManager
        val user = userRepository.findByUsername(request.username)
            ?: throw UsernameNotFoundException("User not found")

        // Check password manually
        if (!passwordEncoder.matches(request.password, user.passwordHash)) {
            throw BadCredentialsException("Invalid credentials")
        }

        val token = generateToken(user)
        return AuthResponse(token = token, username = user.username)
    }

    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByUsername(username)
            ?: throw UsernameNotFoundException("User not found")

        return org.springframework.security.core.userdetails.User(
            user.id.toString(),
            user.passwordHash,
            emptyList()
        )
    }

    private fun generateToken(user: User): String {
        val claims = mapOf(
            "sub" to user.id.toString(),
            "username" to user.username,
            "created" to Date().time
        )

        return Jwts.builder()
            .setClaims(claims)
            .setExpiration(Date(System.currentTimeMillis() + jwtExpiration))
            .signWith(SignatureAlgorithm.HS512, jwtSecret)
            .compact()
    }
}