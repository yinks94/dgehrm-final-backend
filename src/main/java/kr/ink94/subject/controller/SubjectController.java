package kr.ink94.subject.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import kr.ink94.global.dto.ApiResponse;
import kr.ink94.subject.dto.SubjectCreateRequest;
import kr.ink94.subject.dto.SubjectResponse;
import kr.ink94.subject.service.SubjectService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("${app.api.base-path:/api/v1}/subjects")
@RequiredArgsConstructor
public class SubjectController {

    private final SubjectService subjectService;

    @PostMapping
    public ResponseEntity<ApiResponse<SubjectResponse>> create(@Valid @RequestBody SubjectCreateRequest request) {
        SubjectResponse response = subjectService.create(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(HttpStatus.CREATED, "과목을 등록했습니다.", response));

    }

}