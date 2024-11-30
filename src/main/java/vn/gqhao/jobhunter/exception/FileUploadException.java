package vn.gqhao.jobhunter.exception;

public class FileUploadException extends AppException{
    public FileUploadException(ErrorCode errorCode) {
        super(errorCode);
    }
}
