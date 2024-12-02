package vn.gqhao.jobhunter.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    USER_NOT_EXISTED(400, "User not existed", HttpStatus.NOT_FOUND),
    EMAIL_EXISTED(400, "Email existed", HttpStatus.BAD_REQUEST),
    COMPANY_NOT_EXISTED(400, "Company not existed", HttpStatus.NOT_FOUND),
    JOB_NOT_EXISTED(400, "Job not existed", HttpStatus.NOT_FOUND),
    SKILL_NOT_EXISTED(400, "Skill not existed", HttpStatus.NOT_FOUND),
    SKILL_EXISTED(400, "Skill existed", HttpStatus.BAD_REQUEST),
    RESUME_NOT_EXISTED(400, "Resume not existed", HttpStatus.NOT_FOUND),
    FILE_UPLOAD_INVALID(400, "File upload must be in the correct format", HttpStatus.BAD_REQUEST),
    FILE_UPLOAD_NOT_EXISTED(400, "File upload not existed", HttpStatus.BAD_REQUEST),
    PERMISSION_NOT_EXISTED(400, "Permission not existed", HttpStatus.NOT_FOUND),
    PERMISSION_EXISTED(400, "Permission existed", HttpStatus.BAD_REQUEST),
    ROLE_NOT_EXISTED(400, "Role not existed", HttpStatus.NOT_FOUND),
    ROLE_EXISTED(400, "Role existed", HttpStatus.BAD_REQUEST)
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
