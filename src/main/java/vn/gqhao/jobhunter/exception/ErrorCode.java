package vn.gqhao.jobhunter.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    JOB_NOT_EXISTED(400, "Job not existed", HttpStatus.NOT_FOUND),
    SKILL_NOT_EXISTED(400, "Skill not existed", HttpStatus.NOT_FOUND),
    SKILL_EXISTED(400, "Skill existed", HttpStatus.BAD_REQUEST)
    ;

    private int code;
    private String message;
    private HttpStatusCode statusCode;
    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }
}
