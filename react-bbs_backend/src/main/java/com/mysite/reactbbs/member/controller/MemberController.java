package com.mysite.reactbbs.member.controller;

import java.util.Date;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import com.mysite.reactbbs.member.dto.request.JoinRequest;
import com.mysite.reactbbs.member.dto.request.LoginRequest;
import com.mysite.reactbbs.member.dto.response.JoinResponse;
import com.mysite.reactbbs.member.dto.response.LoginResponse;
import com.mysite.reactbbs.member.exception.MemberException;
import com.mysite.reactbbs.member.service.MemberService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@Tag(name = "MemberController", description = "회원 API")
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class MemberController {

	private final MemberService service;

	/*
	public MemberController(MemberService service) {
		this.service = service;
	}
	*/
	@Operation(summary = "checkIdDuplicate() - 아이디 중복체크 API ", description = "아이디 중복체크 API")
	@GetMapping
	public ResponseEntity<?> checkIdDuplicate(@RequestParam("id") String id) {
		System.out.println("아이디 중복 요청 성공 : " + id );
		System.out.println("UserController checkIdDuplicate " + new Date());

		HttpStatus status = service.checkIdDuplicate(id);
		return new ResponseEntity<>(status);
	}
	@Operation(summary = "join() - 회원가입 API ", description = "회원가입 API")
	@PostMapping("/join")
	public ResponseEntity<JoinResponse> join(@Valid @RequestBody JoinRequest req) {
		System.out.println("UserController join " + new Date());

		return ResponseEntity.ok(service.join(req));
	}
	@Operation(summary = "login() - 회원 로그인 API ", description = "회원 로그인 API")
	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest req) {
		System.out.println("UserController login " + new Date());

		return ResponseEntity.ok(service.login(req));
	}


	/* 요청 DTO 검증 예외처리 핸들러 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		System.out.println("UserController handleMethodArgumentNotValidException " + new Date());

		BindingResult bs = e.getBindingResult();
		StringBuilder sb = new StringBuilder();
		bs.getFieldErrors().forEach(err -> {
			sb.append(String.format("[%s]: %s.\n입력된 값: %s",
						err.getField(), err.getDefaultMessage(), err.getRejectedValue()));
		});

		// Map 으로 보낸다면 프론트에서 사용하기 더 편리할 듯 !
		return new ResponseEntity<>(sb.toString(), HttpStatus.BAD_REQUEST);
	}

	/* 사용자 관련 요청 예외처리 핸들러 */
	@ExceptionHandler(MemberException.class)
	public ResponseEntity<?> handleUserException(MemberException e) {
		System.out.println("UserController handlerUserException " + new Date());

		return new ResponseEntity<>(e.getMessage(), e.getStatus());
	}
}


